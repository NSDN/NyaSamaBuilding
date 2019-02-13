package club.nsdn.nyasamabuilding.event;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class ToolHandler {
    private static ToolHandler instance;

    public static ToolHandler instance() {
        if (instance == null)
            instance = new ToolHandler();
        return instance;
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {

    }

}
