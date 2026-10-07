package club.nsdn.nyasamabuilding.block.core;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Wall variant of a building family, ported from the 1.12.2 {@code BlockWall}.
 *
 * <p>The 1.12.2 subclass existed mostly to collapse the wall's metadata variants
 * ({@code getStateFromMeta} / {@code getMetaFromState} / {@code damageDropped} all pinned to the
 * default state) and to expose a single {@code getSubBlocks} entry. In 1.21.1 block states are
 * driven by the block state definition rather than metadata, and creative-tab listing is automatic,
 * so all of those overrides disappear.</p>
 *
 * <p>Note the wall's own connection enum ({@code WallSide}) differs from the old metadata approach;
 * the vanilla behaviour is kept as-is.</p>
 */
public class BlockWall extends WallBlock {

    private final Block baseBlock;

    public BlockWall(Block baseBlock, BlockBehaviour.Properties properties) {
        super(properties);
        this.baseBlock = baseBlock;
    }


    @Override
    public MutableComponent getName() {
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.Wall.name"));
    }
}
