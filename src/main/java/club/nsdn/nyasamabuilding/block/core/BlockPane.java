package club.nsdn.nyasamabuilding.block.core;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Pane variant of a building family, ported from the 1.12.2 {@code BlockPane}.
 *
 * <p>Renamed in modern Minecraft: the pane class is {@link IronBarsBlock}. The 1.12.2 constructor
 * took a {@code Material} plus a {@code boolean} (always {@code false} here), both replaced by the
 * supplied {@link BlockBehaviour.Properties}.</p>
 *
 * <p>Used by two families: the {@code HARD} entries that ask for a pane, and the {@code SOFT}
 * entries (tarp / mesh / tatami), where 1.12.2 built it directly from material+sound rather than
 * from a base block. Both shapes are served by the {@code baseBlock}-less constructor below.</p>
 */
public class BlockPane extends IronBarsBlock {

    /** Null for the SOFT families, which have no full cube to borrow a name from. */
    private final Block baseBlock;

    private final String legacyName;

    public BlockPane(Block baseBlock, BlockBehaviour.Properties properties) {
        super(properties);
        this.baseBlock = baseBlock;
        this.legacyName = null;
    }

    /** SOFT-family shape: no base cube, so the item name comes from the registry entry. */
    public BlockPane(BlockBehaviour.Properties properties) {
        super(properties);
        this.baseBlock = null;
        this.legacyName = null;
    }


    @Override
    public MutableComponent getName() {
        if (baseBlock == null) {
            return super.getName();
        }
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.Pane.name"));
    }
}
