package club.nsdn.nyasamabuilding.block;

import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Glass sign panel, ported from the 1.12.2 {@code club.nsdn.nyasamabuilding.block.BlockSign}.
 *
 * <p>The 1.12.2 source registered this in {@code BlockLoader} (line 75) as
 * {@code setRegistryName(NyaSamaBuilding.MODID, "nsb_sign")} with
 * {@code setUnlocalizedName("NSBSign")}, and its whole class body was {@code extends BlockGlass} plus
 * setters -- hardness 2.0, resistance 10.0, light level 1, {@code SoundType.GLASS}. It had no
 * behaviour of its own, so like the sibling {@code nyasamaoptics} panels the 1.21.1 class is empty:
 * the material data lives in the {@code BlockBehaviour.Properties} the registrar passes in, and
 * 1.21.1 has no bare {@code BlockGlass} -- the plain glass behaviour is {@link TransparentBlock},
 * which already carries the render and occlusion rules.</p>
 *
 * <p>The registration itself (registry name {@code nyasamabuilding:nsb_sign}, the {@code BlockItem},
 * and the creative-tab membership that 1.12.2 did with {@code setCreativeTab}) lives in
 * {@link BuildingBlocks}, as it does for every other block of this module.</p>
 */
public class BlockSign extends TransparentBlock {

    public BlockSign(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
