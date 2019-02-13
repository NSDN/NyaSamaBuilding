package club.nsdn.nyasamabuilding.event;

import club.nsdn.nyasamabuilding.block.BlockLoader;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class EventRegister {

    public static void registerCommon() {
        MinecraftForge.EVENT_BUS.register(BlockLoader.instance());

        MinecraftForge.EVENT_BUS.register(ToolHandler.instance());

    }

    public static void registerServer() {

    }

    public static void registerClient() {

    }

}
