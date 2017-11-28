package club.nsdn.nyasamabuilding.Loaders;

/**
 * Created by Administrator on 2016/5/4.
 */

import club.nsdn.nyasamabuilding.Blocks.WithEntity.BlockNyaSamaBuildingLogo;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class TileEntitiesLoader {
    public TileEntitiesLoader(FMLInitializationEvent event) {

        GameRegistry.registerTileEntity(
                BlockNyaSamaBuildingLogo.TileEntityNyasamaBuildingLogo.class,
                "NyasamaBuildingLogo");
    }
}
