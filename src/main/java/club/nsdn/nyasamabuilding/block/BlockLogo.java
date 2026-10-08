package club.nsdn.nyasamabuilding.block;

import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Logo glass panel, ported from the 1.12.2 {@code club.nsdn.nyasamabuilding.block.BlockLogo}.
 *
 * <p>Registered in {@code BlockLoader} (line 77) as {@code logo = new BlockLogo()}, i.e. the single
 * instance was also kept in the static {@code BlockLoader.logo} field for the logo renderer, and it
 * carried {@code setRegistryName(NyaSamaBuilding.MODID, "nsb_logo")} plus
 * {@code setUnlocalizedName("NSBLogo")}. Like {@link BlockSign} the class body was just
 * {@code extends BlockGlass} and the shared material setters, so the 1.21.1 class is empty and the
 * properties come from the registrar.</p>
 */
public class BlockLogo extends TransparentBlock {

    public BlockLogo(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
