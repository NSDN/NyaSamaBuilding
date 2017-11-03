package club.nsdn.nyasamabuilding.Blocks.Common;

/**
 * Created by Administrator on 2016/5/4.
 */

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockBase;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import net.minecraft.block.material.Material;

public class TextureBlockWood extends CommonBlockBase {

    public final String BlockID;


    public TextureBlockWood(String name, String texture){
        super(Material.wood,name);
        BlockID = name;
        setIconLocation(texture);
        setBlockTextureName(texture);
        setHardness(0.5F);
        setLightLevel(0F);
        setStepSound(soundTypeWood);
        setResistance(4.0F);
        //setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

}