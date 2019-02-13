package club.nsdn.nyasamabuilding.block;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockSign extends BlockGlass {

    public BlockSign() {
        super(Material.GLASS, false);
        setUnlocalizedName("NSBSign");
        setRegistryName(NyaSamaBuilding.MODID, "nsb_sign");
        setHardness(2.0F);
        setLightLevel(1);
        setSoundType(SoundType.GLASS);
        setResistance(10.0F);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

}
