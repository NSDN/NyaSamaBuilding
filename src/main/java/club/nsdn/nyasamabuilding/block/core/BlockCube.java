package club.nsdn.nyasamabuilding.block.core;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockCube extends Block {

    public BlockCube(Material material, SoundType soundType, String name, String id) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(NyaSamaBuilding.MODID, id);
        setHardness(2.0F);
        setResistance(blockHardness * 5.0F);
        setSoundType(soundType);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

}
