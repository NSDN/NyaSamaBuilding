package club.nsdn.nyasamabuilding.creativetab;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.*;
import net.minecraft.creativetab.CreativeTabs;
import club.nsdn.nyasamabuilding.block.BlockLoader;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class CreativeTabLoader {

    public static CreativeTabs tabNyaSamaBuilding;

    public CreativeTabLoader(FMLPreInitializationEvent event) {
        tabNyaSamaBuilding = new CreativeTabs("tabNyaSamaBuilding") {
            @Override
            public ItemStack getTabIconItem() {
                return BlockLoader.itemBlocks.get(BlockLoader.logo).getDefaultInstance();
            }
        };
    }

}
