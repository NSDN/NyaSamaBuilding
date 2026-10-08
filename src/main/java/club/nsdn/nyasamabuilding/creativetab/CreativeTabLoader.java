package club.nsdn.nyasamabuilding.creativetab;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.block.BuildingBlocks;
import club.nsdn.nyasamabuilding.item.BuildingItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * The module's creative tab, ported from the 1.12.2 {@code CreativeTabLoader} (24 lines).
 *
 * <p>1.12.2 built a {@code CreativeTabs} anonymous subclass inside a constructor that was called from
 * {@code CommonProxy.preInit}, and every block joined it through {@code setCreativeTab}. In 1.21.1 the
 * tab is a registry object, so this class becomes a static holder: a
 * {@code DeferredRegister<CreativeModeTab>} plus the tab itself, registered from the mod constructor
 * alongside the block and item registers.</p>
 *
 * <p>Two 1.12.2 details are preserved on purpose:</p>
 * <ul>
 *   <li>the tab's unlocalised name {@code tabNyaSamaBuilding}, hence the translation key
 *       {@code itemGroup.tabNyaSamaBuilding} -- the same key the existing {@code en_us}/{@code zh_cn}
 *       language files use ("NyaSama Building" / 喵玉土木);</li>
 *   <li>the icon is the {@code nsb_logo} block, which 1.12.2 held in {@code BlockLoader.logo} and its
 *       tab asked for by {@code itemBlocks.get(BlockLoader.logo).getDefaultInstance()}.</li>
 * </ul>
 *
 * <p>What replaces {@code setCreativeTab}: the tab enumerates its items itself. Both registers are
 * walked, because 1.12.2 also split them -- {@code BlockLoader} built one {@code ItemBlock} per block,
 * while {@code ItemLoader} contributed the single {@code ItemProbe} -- and both ended up in
 * {@code tabNyaSamaBuilding}. Enumerating both keeps the register that owns each id as the single
 * source of truth for what the tab shows.</p>
 *
 * <p><b>This module had no tab at all before:</b> the 1.21.1 port shipped the 1049 block items and
 * {@code probe} without any wiring to a {@code CreativeModeTab}, so none of them could be found in
 * creative mode (only {@code /give} reached them). This class is that missing layer.</p>
 */
public class CreativeTabLoader {

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NyaSamaBuilding.MODID);

    /** 1.12.2 {@code tabNyaSamaBuilding}. */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB =
            TABS.register("tab_nyasamabuilding", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tabNyaSamaBuilding"))
                    .icon(() -> new ItemStack(BuildingBlocks.LOGO.get()))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Item, ? extends Item> item : BuildingBlocks.ITEMS.getEntries()) {
                            output.accept(item.get());
                        }
                        for (DeferredHolder<Item, ? extends Item> item : BuildingItems.ITEMS.getEntries()) {
                            output.accept(item.get());
                        }
                    })
                    .build());

    private CreativeTabLoader() {
    }

    public static void register(IEventBus modBus) {
        TABS.register(modBus);
    }
}
