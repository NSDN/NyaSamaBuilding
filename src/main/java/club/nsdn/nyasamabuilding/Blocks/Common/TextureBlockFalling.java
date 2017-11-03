package club.nsdn.nyasamabuilding.Blocks.Common;

/**
 * Created by Administrator on 2016/5/4.
 */

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockBase;
import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockFallingBase;
import net.minecraft.block.material.Material;

public class TextureBlockFalling extends CommonBlockFallingBase {

    public final String BlockID;


    public TextureBlockFalling(String name, String texture){
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