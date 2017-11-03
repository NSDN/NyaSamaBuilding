package club.nsdn.nyasamabuilding.Blocks.Common;

/**
 * Created by Administrator on 2016/5/4.
 */

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockBase;
import net.minecraft.block.material.Material;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;

public class TextureBlockBrick extends CommonBlockBase {

    public final String BlockID;


    public TextureBlockBrick(String name, String texture){
        super(Material.rock,name);
        BlockID = name;
        setIconLocation(texture);
        setBlockTextureName(texture);
        setHardness(1.5F);
        setLightLevel(0F);
        setStepSound(soundTypeStone);
        setResistance(10.0F);
        //setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

}