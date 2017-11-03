package club.nsdn.nyasamabuilding.Proxy;

/**
 * Created by drzzm32 on 2016.3.25.
 */

import club.nsdn.nyasamabuilding.Blocks.WithEntity.BlockNyasamaBuildingLogo;
import club.nsdn.nyasamabuilding.GUI.SvrContainer.svrctCreditScreen;
import club.nsdn.nyasamabuilding.Loaders.BlockLoader;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import club.nsdn.nyasamabuilding.Loaders.ItemLoader;
import club.nsdn.nyasamabuilding.Loaders.TileEntitiesLoader;
import club.nsdn.nyasamabuilding.GUI.GUIScreen.scrCreditScreen;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.event.*;

public class CommonProxy
        implements IGuiHandler {

    public void preInit(FMLPreInitializationEvent event)
    {
        new CreativeTabLoader(event);
        new ItemLoader(event);
        new BlockLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {

        new TileEntitiesLoader(event);
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world,
                                      int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(tileEntity instanceof BlockNyasamaBuildingLogo.TileEntityNyasamaBuildingLogo){
            return new svrctCreditScreen();
        }
        return null;
    }

    //returns an instance of the Gui you made earlier
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world,
                                      int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if(tileEntity instanceof BlockNyasamaBuildingLogo.TileEntityNyasamaBuildingLogo){
            return new scrCreditScreen();
        }
        return null;
    }

}
