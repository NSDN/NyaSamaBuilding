package club.nsdn.nyasamabuilding;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import club.nsdn.nyasamabuilding.Proxy.CommonProxy;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

@Mod(modid = ModLoader.MODID, version = ModLoader.VERSION)
public class ModLoader
{
    public static final String MODID = "ztouhouDecoration";
    public static final String VERSION = "0.1";

    public static PrintStream console = new PrintStream(new FileOutputStream(FileDescriptor.out));

    @Mod.Instance("gui")
    public static ModLoader instance;
    
//    @EventHandler
//    public void init(FMLInitializationEvent event)
//    {
//		// some example code
//        System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
//    }

    @SidedProxy(clientSide = "ClientProxy",
            serverSide = "CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

}
