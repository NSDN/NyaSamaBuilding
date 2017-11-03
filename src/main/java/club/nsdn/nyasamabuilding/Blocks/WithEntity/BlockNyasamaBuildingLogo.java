package club.nsdn.nyasamabuilding.Blocks.WithEntity;

/**
 * Created by Administrator on 2016/5/4.
 */

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockContainerBase;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import club.nsdn.nyasamabuilding.ModLoader;

public class BlockNyasamaBuildingLogo extends CommonBlockContainerBase {

    public static class TileEntityNyasamaBuildingLogo extends TileEntity {
    }

    public BlockNyasamaBuildingLogo(){
        super(Material.glass,"NyasamaBuildingLogo");
        setIconLocation("nyabuilding:logo");
        setBlockTextureName("nyabuilding:logo");
        setHardness(2.0F);
        setLightLevel(0.75F);
        setStepSound(soundTypeGlass);
        setResistance(10.0F);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return true;
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

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityNyasamaBuildingLogo();
    }

    @Override
    public int getRenderType() {
        return 0;
    }


}