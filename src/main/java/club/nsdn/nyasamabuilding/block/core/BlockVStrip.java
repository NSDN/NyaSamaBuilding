package club.nsdn.nyasamabuilding.block.core;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Vertical strip variant of a building family, ported from the 1.12.2 {@code BlockVStrip}.
 *
 * <p>A quarter-block post occupying one corner of the block, rotated by facing. Like the edge piece
 * it drops the 1.12.2 metadata bit-packing ({@code getStateFromMeta}/{@code getMetaFromState}) in
 * favour of a single {@code FACING} property, and the four AABB constants become a precomputed
 * {@link VoxelShape} table.</p>
 *
 * <p>1.12.2 declared {@code isSideSolid = false} for every face. As verified with javap, 1.21.1 has
 * no per-face solidity hook on {@code BlockBehaviour} -- face solidity follows from the collision
 * shape -- so the quarter-block shape alone expresses the same thing and the override is not needed
 * (unlike {@link BlockEdge}, where the 1.12.2 rule was deliberately <i>more</i> solid than its
 * shape, which has no equivalent).</p>
 */
public class BlockVStrip extends Block {

    public static final MapCodec<BlockVStrip> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base").forGetter(strip -> strip.baseBlock),
                    propertiesCodec())
            .apply(instance, BlockVStrip::new));

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    /** [facing 2D index] -> shape; built once because getShape is on the collision hot path. */
    private static final VoxelShape[] SHAPES = new VoxelShape[4];

    static {
        SHAPES[Direction.NORTH.get2DDataValue()] = Shapes.box(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D);
        SHAPES[Direction.SOUTH.get2DDataValue()] = Shapes.box(0.5D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D);
        SHAPES[Direction.WEST.get2DDataValue()] = Shapes.box(0.0D, 0.0D, 0.5D, 0.5D, 1.0D, 1.0D);
        SHAPES[Direction.EAST.get2DDataValue()] = Shapes.box(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
    }

    private final Block baseBlock;

    public BlockVStrip(Block baseBlock, BlockBehaviour.Properties properties) {
        super(properties);
        this.baseBlock = baseBlock;
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(FACING).get2DDataValue()];
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public MutableComponent getName() {
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.VStrip.name"));
    }
}
