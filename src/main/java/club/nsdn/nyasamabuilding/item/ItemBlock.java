package club.nsdn.nyasamabuilding.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

/**
 * Created by drzzm32 on 2019.2.13.
 */
public class ItemBlock extends net.minecraft.item.ItemBlock {

    public ItemBlock(Block block) {
        super(block);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return block.getLocalizedName();
    }

}
