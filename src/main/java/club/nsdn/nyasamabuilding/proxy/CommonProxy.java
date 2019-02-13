package club.nsdn.nyasamabuilding.proxy;

import club.nsdn.nyasamabuilding.event.EventRegister;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import net.minecraftforge.fml.common.event.*;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        new CreativeTabLoader(event);
        EventRegister.registerCommon();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
