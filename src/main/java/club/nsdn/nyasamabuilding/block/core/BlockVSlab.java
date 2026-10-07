package club.nsdn.nyasamabuilding.block.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Vertical-slab variant of a building family, ported from the 1.12.2 {@code BlockVSlab}.
 *
 * <p>Like {@link BlockStrip} it inherits from stairs and drops the half-slab box (1.12.2 had that
 * line commented out under <i>"Edited for collision modification"</i>), then stretches the remaining
 * thin piece to full height -- a "vertical slab".</p>
 *
 * <p><b>One 1.12.2 expression had to be adapted, and it matters.</b> The original used</p>
 * <pre>
 * aabb = flag ? expandUp(aabb) : expandDown(aabb);   // flag = (HALF == TOP)
 * expandUp  = expand(0, +0.5, 0)
 * expandDown = expand(0, -0.5, 0)
 * </pre>
 * <p>{@code AxisAlignedBB#expand} grows <i>both</i> ends, so {@code expandUp} on the bottom quarter
 * gives y = -0.5 .. 1.0 (full height, half a block of it below the block). But {@code expandDown} on
 * the top quarter (y = 0.5 .. 1.0) gives <b>minY = 1.0 &gt; maxY = 0.5</b> -- an inverted box, i.e.
 * no collision at all and nothing selectable, so a BOTTOM-half vertical slab would be walk-through
 * and unbreakable in 1.12.2. That looks like an unnoticed sign error rather than a design choice.</p>
 *
 * <p>An inverted box cannot even be expressed in 1.21.1 ({@code VoxelShape} rejects min &gt; max), so
 * the only expressible reading is the intended one: whichever quarter the original picked, stretch it
 * to full height. The original's choice of quarter (bottom quarter for HALF=TOP, top quarter for
 * HALF=BOTTOM) is preserved, so the piece stays on the same side of the block as before; only the
 * vertical extent is now symmetric. <b>Flagged for author confirmation.</b></p>
 *
 * <p>Not ported: {@code onBlockActivated}, which replaced the piece with its full base block when
 * clicked exactly on the {@code 0.5} line. Same deferral as {@link BlockSlab}/{@link BlockEdge}.</p>
 */
public class BlockVSlab extends StairBlock {

    private final Block baseBlock;

    private final BlockState baseState;

    public BlockVSlab(Block baseBlock, BlockState baseState, BlockBehaviour.Properties properties) {
        super(baseState, properties);
        this.baseBlock = baseBlock;
        this.baseState = baseState;
    }

    /**
     * Ported from the 1.12.2 {@code onBlockActivated}: clicking the exact middle line (x or z = 0.5)
     * with the same item in hand replaces the vertical slab with its full base block.
     */
    @Override
    protected net.minecraft.world.ItemInteractionResult useItemOn(net.minecraft.world.item.ItemStack stack,
                                                                 BlockState state, net.minecraft.world.level.Level level,
                                                                 BlockPos pos, net.minecraft.world.entity.player.Player player,
                                                                 net.minecraft.world.InteractionHand hand,
                                                                 net.minecraft.world.phys.BlockHitResult hit) {
        if (!level.isClientSide && stack.is(asItem())) {
            double localX = hit.getLocation().x - pos.getX();
            double localZ = hit.getLocation().z - pos.getZ();
            net.minecraft.core.Direction.Axis axis = hit.getDirection().getAxis();
            boolean onLine = (axis == net.minecraft.core.Direction.Axis.X && localX == 0.5D)
                    || (axis == net.minecraft.core.Direction.Axis.Z && localZ == 0.5D);
            if (onLine) {
                level.setBlockAndUpdate(pos, baseState);
                return net.minecraft.world.ItemInteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    /** The thin piece at the height the 1.12.2 code selected, before the vertical stretch. */
    private static VoxelShape piece(Direction facing, Half takenHalf) {
        boolean bottom = takenHalf == Half.BOTTOM;
        switch (facing) {
            case NORTH:
                return bottom ? Shapes.or(OCTET_NNN, OCTET_PNN) : Shapes.or(OCTET_NPN, OCTET_PPN);
            case SOUTH:
                return bottom ? Shapes.or(OCTET_NNP, OCTET_PNP) : Shapes.or(OCTET_NPP, OCTET_PPP);
            case WEST:
                return bottom ? Shapes.or(OCTET_NNN, OCTET_NNP) : Shapes.or(OCTET_NPN, OCTET_NPP);
            case EAST:
            default:
                return bottom ? Shapes.or(OCTET_PNN, OCTET_PNP) : Shapes.or(OCTET_PPN, OCTET_PPP);
        }
    }

    private static VoxelShape octet(Direction rotated, Half takenHalf) {
        boolean bottom = takenHalf == Half.BOTTOM;
        switch (rotated) {
            case SOUTH:
                return bottom ? OCTET_PNP : OCTET_PPP;
            case WEST:
                return bottom ? OCTET_NNP : OCTET_NPP;
            case EAST:
                return bottom ? OCTET_PNN : OCTET_PPN;
            case NORTH:
            default:
                return bottom ? OCTET_NNN : OCTET_NPN;
        }
    }

    /** Replaces 1.12.2's {@code expand(0, ±0.5, 0)}: stretch half a block each way, vertically. */
    private static VoxelShape stretch(VoxelShape shape) {
        AABB box = shape.bounds();
        return Shapes.box(box.minX, box.minY - 0.5D, box.minZ, box.maxX, box.maxY + 0.5D, box.maxZ);
    }

    private static Direction rotateFor(Direction facing, StairsShape shape) {
        switch (shape) {
            case OUTER_RIGHT:
                return facing.getClockWise();
            case INNER_RIGHT:
                return facing.getOpposite();
            case INNER_LEFT:
                return facing.getCounterClockWise();
            case OUTER_LEFT:
            default:
                return facing;
        }
    }

    private static VoxelShape collision(BlockState state) {
        Direction facing = state.getValue(FACING);
        Half half = state.getValue(HALF);
        StairsShape shape = state.getValue(SHAPE);
        // 1.12.2: flag = (HALF == TOP) -> pick the BOTTOM quarter in that case.
        Half taken = half == Half.TOP ? Half.BOTTOM : Half.TOP;

        VoxelShape result = stretch(piece(facing, taken));
        if (shape != StairsShape.STRAIGHT) {
            result = Shapes.or(result, stretch(octet(rotateFor(facing, shape), taken)));
        }
        return result.optimize();
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return collision(state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        Half half = state.getValue(HALF);
        StairsShape shape = state.getValue(SHAPE);
        Half taken = half == Half.TOP ? Half.BOTTOM : Half.TOP;

        if (shape == StairsShape.STRAIGHT) {
            return stretch(piece(facing, taken));
        }
        if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
            // 1.12.2 returned Block.FULL_BLOCK_AABB for inner shapes.
            return Shapes.block();
        }
        return stretch(octet(rotateFor(facing, shape), taken));
    }

    @Override
    public MutableComponent getName() {
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.VSlab.name"));
    }
}
