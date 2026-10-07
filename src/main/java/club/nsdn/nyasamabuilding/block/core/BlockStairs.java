package club.nsdn.nyasamabuilding.block.core;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Stairs variant of a building family, ported from the 1.12.2 {@code BlockStairs}.
 *
 * <p>1.21.1 differences: the class is {@code StairBlock} (no plural), it is constructed from the
 * base block's {@link BlockState} plus {@link BlockBehaviour.Properties}, and {@code setLightOpacity}
 * / {@code setUnlocalizedName} / {@code setCreativeTab} are gone (properties carry the first, the
 * registry carries the name, and 1.21.1 has no per-block creative tab assignment).</p>
 */
public class BlockStairs extends StairBlock {

    private final Block baseBlock;

    public BlockStairs(Block baseBlock, BlockState baseState, BlockBehaviour.Properties properties) {
        super(baseState, properties);
        this.baseBlock = baseBlock;
    }

    /**
     * Reuses the vanilla stairs codec: this subclass adds no block state, so its persisted type is
     * the vanilla one. Only classes extending {@code Block} directly need a custom BLOCK_TYPE entry.
     */

    /** 1.12.2 behaviour: base block name plus the {@code trans.<Variant>.name} suffix. */
    @Override
    public MutableComponent getName() {
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.Stairs.name"));
    }

    @Override
    public String getDescriptionId() {
        return super.getDescriptionId();
    }
}
