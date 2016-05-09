package club.nsdn.nyasamabuilding.Blocks.NoEntity;

/**
 * Created by Administrator on 2016/5/4.
 */

import club.nsdn.nyasamabuilding.Blocks.CommonBlockBase;
import club.nsdn.nyasamabuilding.Blocks.CommonBlockContainerBase;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import club.nsdn.nyasamabuilding.ModLoader;

public class BlockNyasamaBuildingLogo extends CommonBlockBase {


    public BlockNyasamaBuildingLogo(){
        super(Material.glass,"NyasamaBuildingLogo");
        //setIconLocation("nyasamabuilding:logo_icon");
        setBlockTextureName("nyasamabuilding:logo");
        setHardness(2.0F);
        setLightLevel(0.75F);
        setStepSound(soundTypeGlass);
        setResistance(10.0F);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                                    EntityPlayer player, int metadata, float what, float these, float are) {
        TileEntity tileEntity = world.getTileEntity(x,y,z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        //code to open gui explained later
        player.openGui(ModLoader.MODID, 0, world, x, y, z);
        return true;
    }


}