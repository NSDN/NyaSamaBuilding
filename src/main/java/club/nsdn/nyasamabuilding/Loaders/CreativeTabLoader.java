package club.nsdn.nyasamabuilding.Loaders;

/**
 * Created by drzzm32 on 2016.3.25.
 */

import cpw.mods.fml.common.event.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabLoader {

    public static CreativeTabs tabNyaSamaBuilding;

    public CreativeTabLoader(FMLPreInitializationEvent event) {
        tabNyaSamaBuilding = new CreativeTabs("tabNyaSamaBuilding") {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(BlockLoader.logoBlock);
            }
        };
    }

}
