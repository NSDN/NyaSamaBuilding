package club.nsdn.nyasamabuilding;

import club.nsdn.nyasamabuilding.block.BuildingBlocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NyaSama Building entry point (Minecraft 1.21.1 / NeoForge).
 *
 * <p>Ported from the 1.12.2 shape that used {@code @Mod.Instance}, {@code @SidedProxy} plus
 * {@code @Mod.EventHandler} preInit/init/postInit. As in {@code nyasamalib}, the proxies are gone:
 * registration happens through {@link BuildingBlocks} on the mod bus, and anything client-only
 * belongs in a {@code Dist.CLIENT} class.</p>
 *
 * <p>The 1.12.2 {@code serverLoad} hook that registered {@code ItemProbe.CmdSetCommand} is not wired
 * yet -- that command is pending its Brigadier port.</p>
 */
@Mod(NyaSamaBuilding.MODID)
public class NyaSamaBuilding {

    public static final String MODID = "nyasamabuilding";
    public static final String NAME = "NyaSama Building";
    public static final String VERSION = "3.0.0";

    public static final Logger LOGGER = LoggerFactory.getLogger("NyaSamaBuilding");

    public NyaSamaBuilding(IEventBus modBus, ModContainer container, Dist dist) {
        BuildingBlocks.register(modBus);
        modBus.addListener(this::commonSetup);
        LOGGER.info("[{}] loading on {} (NeoForge, MC 1.21.1)", MODID, dist);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> LOGGER.info("[{}] block registration complete", MODID));
    }
}