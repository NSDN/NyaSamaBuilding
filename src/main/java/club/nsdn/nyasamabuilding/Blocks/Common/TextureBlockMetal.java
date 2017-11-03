package club.nsdn.nyasamabuilding.Blocks.Common;

/**
 * Created by Administrator on 2016/5/4.
 */

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockBase;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import net.minecraft.block.material.Material;

public class TextureBlockMetal extends CommonBlockBase {

    public final String BlockID;


    public TextureBlockMetal(String name, String texture){
    super(Material.iron,name);
    BlockID = name;
    setIconLocation(texture);
    setBlockTextureName(texture);
    setHardness(3.0F);
    setLightLevel(0F);
    setStepSound(soundTypeMetal);
    setResistance(10.0F);
    //setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
}

}