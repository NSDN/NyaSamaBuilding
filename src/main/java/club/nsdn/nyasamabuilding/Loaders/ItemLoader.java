package club.nsdn.nyasamabuilding.Loaders;

/**
 * Created by drzzm32 on 2016.3.25.
 */


import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemLoader {

    //public static Item itemFireExtinguisher;

    private static void register(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }

    public ItemLoader(FMLPreInitializationEvent event) {
        /*itemFireExtinguisher = new ItemFireExtinguisher();
        register(itemFireExtinguisher, "itemFireExtinguisher");*/

    }
}
