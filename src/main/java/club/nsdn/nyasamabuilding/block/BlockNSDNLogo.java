package club.nsdn.nyasamabuilding.block;

import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * NSDN logo glass panel, ported from the 1.12.2
 * {@code club.nsdn.nyasamabuilding.block.BlockNSDNLogo}.
 *
 * <p>Registered in {@code BlockLoader} (line 76) as {@code blocks.add(new BlockNSDNLogo())} with
 * {@code setRegistryName(NyaSamaBuilding.MODID, "nsb_nsdn")} and
 * {@code setUnlocalizedName("NSDNLogo")}. Note the registry id is {@code nsb_nsdn} while its texture
 * is {@code nsdn_logo} -- that mismatch is in the 1.12.2 assets and is preserved, not corrected.
 * {@code BlockNSDNLogo extends BlockGlass} with no behaviour of its own, so as with
 * {@link BlockSign} the 1.21.1 class is empty.</p>
 */
public class BlockNSDNLogo extends TransparentBlock {

    public BlockNSDNLogo(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
