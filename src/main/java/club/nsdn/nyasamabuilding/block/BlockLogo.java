package club.nsdn.nyasamabuilding.block;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockLogo extends BlockGlass {

    public BlockLogo() {
        super(Material.GLASS, false);
        setUnlocalizedName("NSBLogo");
        setRegistryName(NyaSamaBuilding.MODID, "nsb_logo");
        setHardness(2.0F);
        setLightLevel(1);
        setSoundType(SoundType.GLASS);
        setResistance(10.0F);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

}
