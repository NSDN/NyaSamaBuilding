package club.nsdn.nyasamabuilding.block.core;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Fence variant of a building family, ported from the 1.12.2 {@code BlockFence}.
 *
 * <p>The 1.12.2 class overrode {@code canConnectTo(IBlockAccess, BlockPos, EnumFacing)} to keep
 * railings ({@code BlockRailing}) out of fence connections. That hook changed shape in 1.21.1
 * ({@code FenceBlock#canConnectTo} now works on cached state flags) and is not ported yet, so fences
 * currently connect to everything vanilla allows. Tracked as a Phase 3 follow-up.</p>
 */
public class BlockFence extends FenceBlock {

    private final Block baseBlock;

    public BlockFence(Block baseBlock, BlockBehaviour.Properties properties) {
        super(properties);
        this.baseBlock = baseBlock;
    }


    @Override
    public MutableComponent getName() {
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.Fence.name"));
    }
}
