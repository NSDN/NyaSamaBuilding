package club.nsdn.nyasamabuilding;

import club.nsdn.nyasamabuilding.block.BuildingBlocks;
import club.nsdn.nyasamabuilding.item.BuildingItems;
import club.nsdn.nyasamabuilding.item.ItemProbe;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NyaSama Building entry point (Minecraft 1.21.1 / NeoForge).
 *
 * <p>Ported from the 1.12.2 shape that used {@code @Mod.Instance}, {@code @SidedProxy} plus
 * {@code @Mod.EventHandler} preInit/init/postInit. As in {@code nyasamalib}, the proxies are gone:
 * registration happens through {@link BuildingBlocks} and {@link BuildingItems} on the mod bus, and
 * anything client-only belongs in a {@code Dist.CLIENT} class.</p>
 *
 * <p>The 1.12.2 {@code serverLoad(FMLServerStartingEvent)} hook registered
 * {@code new ItemProbe.CmdSetCommand()}, i.e. the {@code /probeCmd} command. 1.21.1 has neither that
 * lifecycle event nor {@code CommandBase}, so the command is a Brigadier tree
 * ({@link ItemProbe.CmdSetCommand#buildCommand()}) registered from a <b>game-bus</b>
 * {@link RegisterCommandsEvent} listener added in the constructor below. The listener is added
 * directly here -- not behind a loader object or a {@code postInit} method -- so the registration path
 * is the one call the mod loader is guaranteed to make, which is the failure mode this pack has hit
 * before ("registration layer shipped but never invoked").</p>
 */
@Mod(NyaSamaBuilding.MODID)
public class NyaSamaBuilding {

    public static final String MODID = "nyasamabuilding";
    public static final String NAME = "NyaSama Building";
    public static final String VERSION = "3.0.0";

    public static final Logger LOGGER = LoggerFactory.getLogger("NyaSamaBuilding");

    public NyaSamaBuilding(IEventBus modBus, ModContainer container, Dist dist) {
        BuildingBlocks.register(modBus);
        BuildingItems.register(modBus);
        modBus.addListener(this::commonSetup);

        // 1.12.2 NyaSamaBuilding.serverLoad(FMLServerStartingEvent):
        //     event.registerServerCommand(new ItemProbe.CmdSetCommand());
        // RegisterCommandsEvent is a game-bus event, so it is listened for on NeoForge.EVENT_BUS
        // (the same call shape nyasamarailway's EventRegister.registerCommon uses).
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class,
                event -> event.getDispatcher().register(ItemProbe.CmdSetCommand.buildCommand()));

        LOGGER.info("[{}] loading on {} (NeoForge, MC 1.21.1)", MODID, dist);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> LOGGER.info("[{}] block and item registration complete", MODID));
    }
}
