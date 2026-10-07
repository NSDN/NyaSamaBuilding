package club.nsdn.nyasamabuilding.block.core;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Carpet variant of a building family, ported from the 1.12.2 {@code BlockCarpet}.
 *
 * <p>Two families used this: HARD ones (a carpet cut from the family's full block) and SOFT ones
 * (tarp / tatami, built straight from material+sound). The {@code baseBlock}-less constructor keeps
 * the SOFT shape, where the display name falls back to the registry entry.</p>
 *
 * <p>Vanilla {@link CarpetBlock} already provides everything the 1.12.2 code hand-rolled: the
 * 1/16-high collision and shape box ({@code CARPET_AABB}), non-opaque rendering, the "only the top
 * face is drawn" rule, and the requirement of a solid block underneath. So the overrides for
 * {@code getBoundingBox}, {@code isOpaqueCube}, {@code isFullCube}, {@code shouldSideBeRendered} and
 * {@code getBlockFaceShape} all disappear rather than being translated.</p>
 */
public class BlockCarpet extends CarpetBlock {

    /** Null for the SOFT families, which have no full cube to borrow a name from. */
    private final Block baseBlock;

    public BlockCarpet(Block baseBlock, BlockBehaviour.Properties properties) {
        super(properties);
        this.baseBlock = baseBlock;
    }

    public BlockCarpet(BlockBehaviour.Properties properties) {
        super(properties);
        this.baseBlock = null;
    }

    @Override
    public MutableComponent getName() {
        if (baseBlock == null) {
            return super.getName();
        }
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.Carpet.name"));
    }
}
