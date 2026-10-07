package club.nsdn.nyasamabuilding.block.core;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Strip variant of a building family, ported from the 1.12.2 {@code BlockStrip}.
 *
 * <p><b>This is the one variant where 1.12.2 deliberately deviates from vanilla stairs.</b> The
 * original source carries the comment <i>"Edited for collision modification"</i> next to a
 * <b>commented-out</b> line that would have added the half-slab box:</p>
 * <pre>
 * /*boolean flag = state.getValue(HALF) == EnumHalf.TOP;
 * list.add(flag ? AABB_SLAB_TOP : AABB_SLAB_BOTTOM);*&#47;
 * </pre>
 * <p>So a strip has <i>no</i> half-slab collision at all -- only the quarter piece (plus the eighth
 * piece on corners). That is a gameplay-visible property (players can walk through the space the
 * slab would have occupied), and it is reproduced here rather than "fixed" to vanilla stairs.</p>
 *
 * <p>Geometry is composed from the protected {@code OCTET_*} shapes that {@link StairBlock} already
 * exposes (each is an 8x8x8 sixteenth box), so the 1.12.2 {@code AABB_QTR_*} / {@code AABB_OCT_*}
 * constants become exact unions instead of hard-coded numbers:</p>
 * <ul>
 *   <li>quarter = the two octets of that corner at the same height (e.g. NORTH+TOP = NPN+PPN);</li>
 *   <li>eighth = the single octet of the rotated corner;</li>
 *   <li>collision = quarter for STRAIGHT/INNER_*, plus eighth for anything that is not STRAIGHT;</li>
 *   <li>selection shape = quarter for STRAIGHT, the opposite half-slab for INNER_*, eighth for OUTER_*
 *       (this asymmetry is 1.12.2 behaviour: {@code getBoundingBox} differed from the collision list).</li>
 * </ul>
 */
public class BlockStrip extends StairBlock {

    private final Block baseBlock;

    public BlockStrip(Block baseBlock, BlockState baseState, BlockBehaviour.Properties properties) {
        super(baseState, properties);
        this.baseBlock = baseBlock;
    }

    /** 1.12.2 {@code getCollQuarterBlock}: an 8x8x16 or 16x8x8 box at the facing's side. */
    private static VoxelShape quarter(Direction facing, Half half) {
        boolean bottom = half == Half.BOTTOM;
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

    /** 1.12.2 {@code getCollEighthBlock}: the single octet picked by shape + facing rotation. */
    private static VoxelShape eighth(Direction facing, StairsShape shape, Half half) {
        Direction rotated;
        switch (shape) {
            case OUTER_RIGHT:
                rotated = facing.getClockWise();
                break;
            case INNER_RIGHT:
                rotated = facing.getOpposite();
                break;
            case INNER_LEFT:
                rotated = facing.getCounterClockWise();
                break;
            case OUTER_LEFT:
            default:
                rotated = facing;
                break;
        }
        boolean bottom = half == Half.BOTTOM;
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

    @Override
    protected VoxelShape getCollisionShape(BlockState state, net.minecraft.world.level.BlockGetter level,
                                           net.minecraft.core.BlockPos pos,
                                           net.minecraft.world.phys.shapes.CollisionContext context) {
        Direction facing = state.getValue(FACING);
        Half half = state.getValue(HALF);
        StairsShape shape = state.getValue(SHAPE);

        VoxelShape result = quarter(facing, half);
        if (shape != StairsShape.STRAIGHT) {
            result = Shapes.or(result, eighth(facing, shape, half));
        }
        return result.optimize();
    }

    @Override
    protected VoxelShape getShape(BlockState state, net.minecraft.world.level.BlockGetter level,
                                 net.minecraft.core.BlockPos pos,
                                 net.minecraft.world.phys.shapes.CollisionContext context) {
        Direction facing = state.getValue(FACING);
        Half half = state.getValue(HALF);
        StairsShape shape = state.getValue(SHAPE);

        if (shape == StairsShape.STRAIGHT) {
            return quarter(facing, half);
        }
        if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
            // 1.12.2 returned the *opposite* half-slab here (HALF==BOTTOM -> TOP_AABB).
            return half == Half.BOTTOM ? TOP_AABB : BOTTOM_AABB;
        }
        return eighth(facing, shape, half);
    }

    @Override
    public MutableComponent getName() {
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.Strip.name"));
    }
}
