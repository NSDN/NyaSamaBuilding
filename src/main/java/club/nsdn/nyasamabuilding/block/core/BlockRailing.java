package club.nsdn.nyasamabuilding.block.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Railing variant of a building family, ported from the 1.12.2 {@code BlockRailing}.
 *
 * <p>The interesting part of the port is UP/DOWN. 1.12.2 computed them inside {@code getActualState}
 * -- i.e. on every state query -- which no longer exists in 1.21.1. The modern substitute is:</p>
 * <ol>
 *   <li>declare the properties in {@link #createBlockStateDefinition};</li>
 *   <li>recompute them in {@link #updateShape} when a neighbour changes (which is what replaces the
 *       "actual state" mechanism);</li>
 *   <li>express the half-block downward extension through {@link VoxelShape} unions instead of
 *       contracting an AABB.</li>
 * </ol>
 *
 * <p><b>Faithfulness note / suspected original bug.</b> The 1.12.2 source computed <i>both</i>
 * {@code UP} and {@code DOWN} from {@code pos.down()}:</p>
 * <pre>
 * .withProperty(UP,   belowIsSolid || belowIsFence)
 * .withProperty(DOWN, belowIsSolid || belowIsFence)
 * </pre>
 * <p>so the two values are always identical, and the name "UP" suggests the author meant
 * {@code pos.up()} for one of them. This port reproduces the original behaviour exactly (both from
 * the block below) rather than silently "fixing" it; deciding whether UP should look upward is the
 * author's call.</p>
 */
public class BlockRailing extends FenceBlock {

    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    private final Block baseBlock;

    public BlockRailing(Block baseBlock, BlockBehaviour.Properties properties) {
        super(properties);
        this.baseBlock = baseBlock;
        registerDefaultState(defaultBlockState().setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP, DOWN);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        BlockState updated = super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        return withVertical(updated, level, pos);
    }

    /**
     * Recomputes UP/DOWN from the block below, matching the 1.12.2 formulas verbatim (see the class
     * note about UP also reading the block below).
     */
    private static BlockState withVertical(BlockState state, BlockGetter level, BlockPos pos) {
        BlockPos below = pos.below();
        boolean supported = level.getBlockState(below).isFaceSturdy(level, below, Direction.UP)
                || level.getBlockState(below).getBlock() instanceof FenceBlock;
        return state.setValue(UP, supported).setValue(DOWN, supported);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return extendDown(super.getShape(state, level, pos, context), state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return extendDown(super.getCollisionShape(state, level, pos, context), state);
    }

    /** 1.12.2 did {@code aabb.contract(0, down ? 0 : -0.5, 0)}: without support the shape reaches half a block lower. */
    private static VoxelShape extendDown(VoxelShape shape, BlockState state) {
        if (state.getValue(DOWN)) {
            return shape;
        }
        return Shapes.joinUnoptimized(shape, shape.move(0.0D, -0.5D, 0.0D), BooleanOp.OR).optimize();
    }

    @Override
    public MutableComponent getName() {
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.Railing.name"));
    }
}
