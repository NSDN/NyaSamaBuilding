package club.nsdn.nyasamabuilding.block.core;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * The plain full-cube block that every "hard" material family is built from.
 *
 * <p>Ported from the 1.12.2 {@code BlockCube}, which extended {@code Block} directly and did its
 * setup through setters ({@code setHardness}, {@code setResistance}, {@code setSoundType},
 * {@code setCreativeTab}). In 1.21.1 all of that is baked into {@link BlockBehaviour.Properties},
 * which the caller supplies, and everything else (registry name, creative tab membership) moves to
 * {@code DeferredRegister} registration.</p>
 *
 * <p>The {@link #CODEC} is not optional any more: since 1.20.5 every {@code Block} subclass must
 * implement {@code codec()} and have that codec registered in {@code BuiltInRegistries.BLOCK_TYPE},
 * otherwise the block cannot be serialised (and DataGen/model loading fails).</p>
 */
public class BlockCube extends Block {

    public static final MapCodec<BlockCube> CODEC = simpleCodec(BlockCube::new);

    public BlockCube(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }
}
