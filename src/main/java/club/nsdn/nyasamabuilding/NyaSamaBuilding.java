package club.nsdn.nyasamabuilding;

import club.nsdn.nyasamabuilding.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

/**
 * Created by drzzm32 on 2019.2.12.
 */
@Mod(modid = NyaSamaBuilding.MODID, name = NyaSamaBuilding.NAME, version = NyaSamaBuilding.VERSION)
public class NyaSamaBuilding {

    @Mod.Instance
    public static NyaSamaBuilding instance;
    public static final String MODID = "nyasamabuilding";
    public static final String NAME = "NyaSama Building";
    public static final String VERSION = "2.0";
    public static Logger logger;

    @SidedProxy(clientSide = "club.nsdn.nyasamabuilding.proxy.ClientProxy",
                serverSide = "club.nsdn.nyasamabuilding.proxy.ServerProxy")
    public static CommonProxy proxy;

    public static NyaSamaBuilding getInstance() {
        return instance;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
