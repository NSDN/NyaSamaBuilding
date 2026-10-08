package club.nsdn.nyasamabuilding.item;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registration for the module's <b>items</b>, replacing the 1.12.2 {@code ItemLoader}.
 *
 * <p>1.12.2's {@code ItemLoader} kept a {@code LinkedList<Item>} that
 * {@code RegistryEvent.Register<Item>} flushed and {@code ModelRegistryEvent} walked to bind icons.
 * Both halves are gone in 1.21.1: a {@link DeferredRegister} is registered once with the mod bus, and
 * item models are pure JSON under {@code assets/<modid>/models/item/} -- there is no
 * {@code ModelLoader.setCustomModelResourceLocation} equivalent to write. The registry id therefore
 * also fixes the lang key, which for this item is {@code item.nyasamabuilding.probe}
 * ({@code assets/nyasamabuilding/lang/{en_us,zh_cn}.json}, values "Probe" / 探针).</p>
 *
 * <p>The 1.12.2 loader registered exactly one item of its own -- {@code ItemProbe} -- because the
 * block items were built by {@code BlockLoader}; those stay with {@code block.BuildingBlocks}. The
 * three model files ({@code models/item/probe.json} plus the three {@code textures/item/probe_*.png})
 * were already carried over by the resource pass, so registering the item here is what makes them
 * visible.</p>
 */
public final class BuildingItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NyaSamaBuilding.MODID);

    /**
     * 1.12.2 {@code items.add(new ItemProbe())}. The registry id {@code probe} is 1.12.2's
     * ({@code ItemProbe}'s {@code setRegistryName(MODID, "probe")}); {@code ItemProbe} itself adds
     * {@code Tiers.IRON} and the stack size / durability that its 1.12.2 constructor used to set.
     */
    public static final DeferredItem<ItemProbe> PROBE =
            ITEMS.register("probe", () -> new ItemProbe(new Item.Properties()));

    private BuildingItems() {
    }

    /** Called from the mod constructor with the mod event bus, like {@code BuildingBlocks.register}. */
    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
