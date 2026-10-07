package club.nsdn.nyasamabuilding.block.core;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Slab variant of a building family, ported from the 1.12.2 {@code BlockSlab}.
 *
 * <p>What the 1.12.2 class did, and where it went:</p>
 * <ul>
 *   <li>{@code createBlockState} / {@code getStateFromMeta} / {@code getMetaFromState} pinning a
 *       single HALF property -- replaced by vanilla {@link SlabBlock}'s own state definition, which
 *       already carries {@code TYPE} (bottom/top/double).</li>
 *   <li>{@code isDouble()}/{@code getVariantProperty()}/{@code getTypeForItem()} -- all gone from
 *       the modern slab; the double slab is a {@code TYPE} value rather than a second block.</li>
 *   <li>{@code onBlockActivated} merging the slab into its full block when clicked at exactly
 *       {@code hitY == 0.5} while holding the matching item -- <b>not ported yet</b>. It is a
 *       1.12.2-convenience quirk layered on top of vanilla behaviour (vanilla still merges a slab
 *       when you <i>place</i> a matching slab onto it) and the hook changed shape in 1.21.1, so it
 *       is tracked as a Phase 3 follow-up rather than silently dropped.</li>
 * </ul>
 */
public class BlockSlab extends SlabBlock {

    private final Block baseBlock;

    public BlockSlab(Block baseBlock, BlockState baseState, BlockBehaviour.Properties properties) {
        super(properties);
        this.baseBlock = baseBlock;
    }

    @Override
    public MutableComponent getName() {
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.Slab.name"));
    }
}
