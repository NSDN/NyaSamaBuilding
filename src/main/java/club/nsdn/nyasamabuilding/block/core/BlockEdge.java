package club.nsdn.nyasamabuilding.block.core;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Edge/trim variant of a building family, ported from the 1.12.2 {@code BlockEdge}.
 *
 * <p>An edge piece that hugs one side of the block and comes in three heights. The 1.12.2 code
 * carried four AABB triples (one per horizontal facing) plus a metadata bit-packing scheme; in
 * 1.21.1 the same thing is expressed as two block state properties and a small shape table:</p>
 * <ul>
 *   <li>{@code FACING} -- {@link BlockStateProperties#HORIZONTAL_FACING}, the modern replacement for
 *       {@code BlockHorizontal.FACING};</li>
 *   <li>{@code HALF} -- a custom {@link EnumProperty} whose values implement
 *       {@link StringRepresentable} (1.21.1's replacement for {@code IStringSerialable});</li>
 *   <li>shapes -- {@link VoxelShape} boxes instead of {@code AxisAlignedBB}, precomputed in a table
 *       because {@code getShape} is hot.</li>
 * </ul>
 *
 * <p>Metadata handling ({@code getStateFromMeta}/{@code getMetaFromState}) disappears: 1.21.1 block
 * states are serialised by name, not by a packed integer.</p>
 *
 * <p><b>Not ported yet</b> (marked rather than silently dropped): the 1.12.2 {@code onBlockActivated}
 * let you right-click the upper part of an edge with the same item to grow it from TOP/BOTTOM into
 * TALL. The 1.21.1 interaction hook changed shape, so this convenience behaviour is deferred
 * together with the similar slab-merge quirk in {@link BlockSlab}.</p>
 */
public class BlockEdge extends Block {

    /**
     * Not {@code simpleCodec}: that helper only works for blocks whose constructor takes nothing but
     * {@code Properties}. An edge also carries its base block (for the display name), so the codec is
     * assembled explicitly, exactly like vanilla's {@code FlowerPotBlock}.
     */
    public static final MapCodec<BlockEdge> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base").forGetter(edge -> edge.baseBlock),
                    propertiesCodec())
            .apply(instance, BlockEdge::new));

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = EnumProperty.create("half", Half.class);

    /** The three heights an edge can take. Named as in 1.12.2 ({@code EnumHalf}). */
    public enum Half implements StringRepresentable {
        TOP("top"),
        BOTTOM("bottom"),
        TALL("tall");

        private final String name;

        Half(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /** [facing index][half index]; built once because getShape is called for every collision test. */
    private static final VoxelShape[][] SHAPES = new VoxelShape[4][3];

    static {
        for (Direction facing : new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST }) {
            for (Half half : Half.values()) {
                SHAPES[facing.get2DDataValue()][half.ordinal()] = box(facing, half);
            }
        }
    }

    private final Block baseBlock;

    public BlockEdge(Block baseBlock, BlockBehaviour.Properties properties) {
        super(properties);
        this.baseBlock = baseBlock;
        registerDefaultState(defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.BOTTOM));
    }

    /** 1.12.2 boxes, verbatim: NORTH/SOUTH hug the Z axis, WEST/EAST the X axis. */
    private static VoxelShape box(Direction facing, Half half) {
        double y0 = half == Half.TOP ? 0.5D : 0.0D;
        double y1 = half == Half.BOTTOM ? 0.5D : 1.0D;
        switch (facing) {
            case NORTH:
                return Shapes.box(0.0D, y0, 0.375D, 1.0D, y1, 1.0D);
            case SOUTH:
                return Shapes.box(0.0D, y0, 0.0D, 1.0D, y1, 0.625D);
            case WEST:
                return Shapes.box(0.375D, y0, 0.0D, 1.0D, y1, 1.0D);
            case EAST:
                return Shapes.box(0.0D, y0, 0.0D, 0.625D, y1, 1.0D);
            default:
                return Shapes.block();
        }
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(FACING).get2DDataValue()][state.getValue(HALF).ordinal()];
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    /*
     * NOT PORTED: the 1.12.2 override of {@code isSideSolid}, which declared TALL solid on the Y axis
     * and on the face it looks away from, TOP solid on UP and BOTTOM solid on DOWN.
     *
     * Verified with javap against neoforge-21.1.256-merged.jar: {@code BlockBehaviour} has no
     * {@code isFaceSturdy} (nor any other per-face solidity hook) any more -- in 1.21.1 face
     * solidity is derived from the collision shape by {@code BlockState#isFaceSturdy}. So this
     * behaviour has no direct override point and is left as a known deviation rather than faked by
     * inflating the collision box (which would change where players can walk).
     */

    /** Ported from {@code getStateForPlacement}: face the player, flip to TOP when clicked low/high. */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        Direction clicked = context.getClickedFace();
        double hitY = context.getClickLocation().y - context.getClickedPos().getY();
        Half half = Half.BOTTOM;
        if (clicked == Direction.DOWN || (clicked != Direction.UP && hitY > 0.5D)) {
            half = Half.TOP;
        }
        return defaultBlockState().setValue(FACING, facing).setValue(HALF, half);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    /**
     * Ported from the 1.12.2 {@code onBlockActivated}: right-clicking an edge's upper half while
     * holding the same item grows it from TOP/BOTTOM into TALL.
     */
    @Override
    protected net.minecraft.world.ItemInteractionResult useItemOn(net.minecraft.world.item.ItemStack stack,
                                                                 BlockState state, net.minecraft.world.level.Level level,
                                                                 BlockPos pos, net.minecraft.world.entity.player.Player player,
                                                                 net.minecraft.world.InteractionHand hand,
                                                                 net.minecraft.world.phys.BlockHitResult hit) {
        if (!level.isClientSide && stack.is(asItem())) {
            double hitY = hit.getLocation().y - pos.getY();
            if (hit.getDirection().getAxis() == Direction.Axis.Y && hitY >= 0.5D
                    && state.getValue(HALF) != Half.TALL) {
                level.setBlockAndUpdate(pos, state.setValue(HALF, Half.TALL));
                return net.minecraft.world.ItemInteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    @Override
    public MutableComponent getName() {
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.Edge.name"));
    }
}
