package club.nsdn.nyasamabuilding.Blocks.NoEntity;

/**
 * Created by Administrator on 2016/5/4.
 */

import club.nsdn.nyasamabuilding.Blocks.CommonBlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;

public class BlockGreyBrick extends CommonBlockBase {


    public BlockGreyBrick(){
        super(Material.rock,"GreyBrick");
        //setIconLocation("nyasamabuilding:logo_icon");
        setBlockTextureName("nyasamabuilding:GreyBrick");
        setHardness(1.5F);
        setLightLevel(0F);
        setStepSound(soundTypeStone);
        setResistance(10.0F);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

}