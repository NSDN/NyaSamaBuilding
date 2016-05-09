package club.nsdn.nyasamabuilding.Loaders;

/**
 * Created by Administrator on 2016/5/4.
 */

import club.nsdn.nyasamabuilding.Blocks.NoEntity.BlockGreyBrick;
import club.nsdn.nyasamabuilding.Models.modelStandardCube;
import club.nsdn.nyasamabuilding.Renderer.BaseRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class TileEntityModelBinder {
    public TileEntityModelBinder(FMLInitializationEvent event) {

        /*ClientRegistry.bindTileEntitySpecialRenderer(
                BlockFireEmergencyLightCelling.TileEntityFireEmergencyLightCelling.class,
                new BaseRenderer(
                        new modelCellingLightClassic(),
                        "ztouhoudecor:textures/blocks/CellingLightClassic.png"));*/
    }
}
