package club.nsdn.nyasamabuilding.Items;

/**
 * Created by Administrator on 2016/5/8.
 */

import net.minecraft.item.Item;

public class CommonItemBase extends Item{
    public CommonItemBase(String Name){
        setUnlocalizedName(Name);
        setMaxStackSize(64);
    }
}
