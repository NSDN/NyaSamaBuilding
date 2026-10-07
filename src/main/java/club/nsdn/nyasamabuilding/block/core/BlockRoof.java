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
 * Roof variant of a building family, ported from the 1.12.2 {@code BlockRoof}.
 *
 * <p>Like {@link BlockRailing} it extends the fence shape and carries extra vertical state. Unlike
 * the railing, the 1.12.2 formula here is internally consistent:</p>
 * <pre>
 * UP   = !(blockAbove is a fence)      -> "nothing continues upward, so cap me off"
 * DOWN = belowIsSolid || belowIsFence
 * </pre>
 * <p>When {@code UP} is true the block becomes a full cube (1.12.2 used
 * {@code Block.FULL_BLOCK_AABB}); otherwise the fence shape is used, extended half a block down
 * when there is no support below.</p>
 *
 * <p>{@code getActualState} is gone in 1.21.1, so the properties are declared, recomputed in
 * {@link #updateShape}, and the shape is expressed as a {@link VoxelShape}.</p>
 */
public class BlockRoof extends FenceBlock {

    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    private final Block baseBlock;

    public BlockRoof(Block baseBlock, BlockBehaviour.Properties properties) {
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

    /** Recomputes UP/DOWN with the 1.12.2 formulas verbatim: UP looks above, DOWN looks below. */
    private static BlockState withVertical(BlockState state, BlockGetter level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockPos below = pos.below();
        boolean capped = !(level.getBlockState(above).getBlock() instanceof FenceBlock);
        boolean supported = level.getBlockState(below).isFaceSturdy(level, below, Direction.UP)
                || level.getBlockState(below).getBlock() instanceof FenceBlock;
        return state.setValue(UP, capped).setValue(DOWN, supported);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(UP)) {
            return Shapes.block();
        }
        return extendDown(super.getShape(state, level, pos, context), state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(UP)) {
            return Shapes.block();
        }
        return extendDown(super.getCollisionShape(state, level, pos, context), state);
    }

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
                .append(Component.translatable("trans.Roof.name"));
    }
}
