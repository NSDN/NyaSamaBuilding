package club.nsdn.nyasamabuilding.Loaders;

/**
 * Created by drzzm32 on 2016.3.25.
 */

//import com.swzry.hellomcforge.TileEntities.*;
import club.nsdn.nyasamabuilding.Blocks.NoEntity.BlockNyasamaBuildingLogo;
import club.nsdn.nyasamabuilding.Materials.MatEmergencyLight;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class BlockLoader {
    public static Block logoBlock;
    public static Block greyBrick;

    private static void register(Block block, String name) {
        GameRegistry.registerBlock(block, name);
    }

    public BlockLoader(FMLPreInitializationEvent event) {
        //matEmergencyLight = new MatEmergencyLight(MapColor.airColor);

        logoBlock = new BlockNyasamaBuildingLogo();
        register(logoBlock, "nyasamaBuildingLogo");
        greyBrick = new BlockNyasamaBuildingLogo();
        register(greyBrick, "greyBrick");

    }

}