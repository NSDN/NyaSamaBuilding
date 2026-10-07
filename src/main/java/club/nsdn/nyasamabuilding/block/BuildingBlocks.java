package club.nsdn.nyasamabuilding.block;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.block.core.BlockCube;
import club.nsdn.nyasamabuilding.block.core.BlockFence;
import club.nsdn.nyasamabuilding.block.core.BlockPane;
import club.nsdn.nyasamabuilding.block.core.BlockStairs;
import club.nsdn.nyasamabuilding.block.core.BlockWall;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registration for all {@code nyasamabuilding} blocks and their items.
 *
 * <p>Replaces the 1.12.2 {@code BlockLoader}, which used {@code RegistryEvent.Register<Block>} plus
 * {@code RegistryEvent.Register<Item>} and a hand-built {@code LinkedList<Block>}. Here the tables in
 * {@link BlockLists} are walked once inside {@code DeferredRegister} suppliers, so registration order
 * is decided by NeoForge and a typo in a name fails loudly at startup instead of silently dropping
 * an entry.</p>
 *
 * <p>Model/icon registration also disappears: {@code ModelLoader.setCustomModelResourceLocation} is
 * gone in 1.21.1 and item models are pure JSON under {@code assets/<modid>/models/item/}. Writing
 * those JSON files (or generating them via DataGen) replaces the old client event handler.</p>
 *
 * <p>Current scope: the {@code HARD} table's full cubes. The 13 derived variants per family
 * (carpet, edge, railing, roof, fence, fence gate, pane, slab, stairs, strip, vslab, vstrip, wall)
 * are being ported one class at a time; each needs its own {@code codec()} and shape logic.</p>
 */
public final class BuildingBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NyaSamaBuilding.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NyaSamaBuilding.MODID);

    /**
     * Every custom {@link Block} subclass needs its codec in {@code BuiltInRegistries.BLOCK_TYPE};
     * without this the block cannot be serialised (1.20.5+ requirement).
     */
    public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, NyaSamaBuilding.MODID);

    private BuildingBlocks() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        BLOCK_TYPES.register(modBus);
    }

    static {
        BLOCK_TYPES.register("cube", () -> BlockCube.CODEC);

        // HARD families: the full cube first, then the derived shapes that borrow from it.
        // The variant suppliers call holder.get() lazily; NeoForge runs a DeferredRegister's
        // suppliers in registration order, so the base cube is already registered by then.
        for (String[] info : BlockLists.HARD) {
            String id = info[BlockLists.ID];
            String type = info[BlockLists.TYPE];
            DeferredBlock<BlockCube> base = cube(id, type);
            variants(base, base.get().defaultBlockState(), id, type);
        }

        // SOFT families have no full cube in 1.12.2 either: a pane and (later) a carpet each.
        for (String[] info : BlockLists.SOFT) {
            softPane(info[BlockLists.ID], info[BlockLists.TYPE]);
        }
    }

    /** Registers one full cube plus its {@code BlockItem}. */
    public static DeferredBlock<BlockCube> cube(String id, String type) {
        DeferredBlock<BlockCube> block = BLOCKS.register(id + "_block", () -> new BlockCube(BlockLists.getProperties(type)));
        // The 1.12.2 code built an ItemBlock per registered Block; registerSimpleBlockItem is the
        // modern equivalent and keeps the item name identical to the block name.
        ITEMS.registerSimpleBlockItem(block);
        return block;
    }

    /**
     * The shapes derived from a HARD family's full cube.
     *
     * <p>1.12.2 also derived carpet, edge, railing, roof, fence gate, slab, strip, vslab and vstrip
     * from each family; those classes are still being ported, so only the ones with a direct vanilla
     * counterpart are registered here.</p>
     */
    public static void variants(DeferredBlock<BlockCube> base, BlockState baseState, String id, String type) {
        registerWithItem(id + "_stairs",
                () -> new BlockStairs(base.get(), baseState, BlockLists.getProperties(type)));
        registerWithItem(id + "_fence",
                () -> new BlockFence(base.get(), BlockLists.getProperties(type)));
        registerWithItem(id + "_wall",
                () -> new BlockWall(base.get(), BlockLists.getProperties(type)));
        registerWithItem(id + "_pane",
                () -> new BlockPane(base.get(), BlockLists.getProperties(type)));
    }

    /** SOFT families: material-and-sound based pane, no base cube. */
    public static void softPane(String id, String type) {
        registerWithItem(id + "_pane", () -> new BlockPane(BlockLists.getProperties(type)));
    }

    private static <T extends Block> DeferredBlock<T> registerWithItem(String name, java.util.function.Supplier<T> supplier) {
        DeferredBlock<T> block = BLOCKS.register(name, supplier);
        ITEMS.registerSimpleBlockItem(block);
        return block;
    }
}
