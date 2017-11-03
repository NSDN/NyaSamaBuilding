package club.nsdn.nyasamabuilding.GUI.SvrContainer;

/**
 * Created by Administrator on 2016/5/8.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;


public class svrctCreditScreen extends Container {

    public svrctCreditScreen(){

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

}
