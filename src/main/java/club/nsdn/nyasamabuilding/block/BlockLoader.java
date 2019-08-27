package club.nsdn.nyasamabuilding.block;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.block.core.*;
import club.nsdn.nyasamabuilding.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.Tuple;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import static club.nsdn.nyasamabuilding.block.BlockLists.*;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockLoader {

    private static BlockLoader instance;
    public static BlockLoader instance() {
        if (instance == null) instance = new BlockLoader();
        return instance;
    }

    public static LinkedList<Block> blocks;
    public static LinkedHashMap<Block, Item> itemBlocks;
    public static Block logo;

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        NyaSamaBuilding.logger.info("registering Blocks");
        event.getRegistry().registerAll(blocks.toArray(new Block[0]));
    }

    @SubscribeEvent
    public void registerItemBlocks(RegistryEvent.Register<Item> event) {
        NyaSamaBuilding.logger.info("registering ItemBlocks");
        for (Block b : blocks) {
            String regName = b.getUnlocalizedName().toLowerCase();
            if (b.getRegistryName() != null)
                regName = b.getRegistryName().toString().split(":")[1];
            itemBlocks.put(b, new ItemBlock(b).setRegistryName(NyaSamaBuilding.MODID, regName));
        }
        event.getRegistry().registerAll(itemBlocks.values().toArray(new Item[0]));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerItemBlockModels(ModelRegistryEvent event) {
        NyaSamaBuilding.logger.info("registering ItemBlock Models (Block's Icon)");
        for (Item i : itemBlocks.values()) {
            String regName = i.getUnlocalizedName().toLowerCase();
            if (i.getRegistryName() != null)
                regName = i.getRegistryName().toString();
            ModelLoader.setCustomModelResourceLocation(i, 0,
                    new ModelResourceLocation(regName, "inventory")
            );
        }
    }

    public BlockLoader() {
        blocks = new LinkedList<>();
        itemBlocks = new LinkedHashMap<>();

        blocks.add(new BlockSign());
        blocks.add(new BlockNSDNLogo());
        logo = new BlockLogo();
        blocks.add(logo);

        Block block;
        Tuple<Material, SoundType> extInfo;
        for (String[] info : HARD) {
            extInfo = getExtInfo(info[TYPE]);
            block = new BlockCube(extInfo.getFirst(), extInfo.getSecond(), info[NAME], info[ID] + "_" + "block");
            blocks.add(block);
            blocks.add(new BlockCarpet(block.getDefaultState(), info[NAME] + "Carpet", info[ID] + "_" + "carpet"));
            blocks.add(new BlockEdge(block.getDefaultState(), info[NAME] + "Edge", info[ID] + "_" + "edge"));
            blocks.add(new BlockRailing(block.getDefaultState(), info[NAME] + "Railing", info[ID] + "_" + "railing"));
            blocks.add(new BlockFence(block.getDefaultState(), info[NAME] + "Fence", info[ID] + "_" + "fence"));
            blocks.add(new BlockFenceGate(block.getDefaultState(), info[NAME] + "FenceGate", info[ID] + "_" + "fence_gate"));
            blocks.add(new BlockPane(block.getDefaultState(), info[NAME] + "Pane", info[ID] + "_" + "pane"));
            blocks.add(new BlockSlab(block.getDefaultState(), info[NAME] + "Slab", info[ID] + "_" + "slab"));
            blocks.add(new BlockStairs(block.getDefaultState(), info[NAME] + "Stairs", info[ID] + "_" + "stairs"));
            blocks.add(new BlockStrip(block.getDefaultState(), info[NAME] + "Strip", info[ID] + "_" + "strip"));
            blocks.add(new BlockVSlab(block.getDefaultState(), info[NAME] + "VSlab", info[ID] + "_" + "vslab"));
            blocks.add(new BlockVStrip(block.getDefaultState(), info[NAME] + "VStrip", info[ID] + "_" + "vstrip"));
            blocks.add(new BlockWall(block.getDefaultState(), info[NAME] + "Wall", info[ID] + "_" + "wall"));
        }

        for (String[] info : SOFT) {
            extInfo = getExtInfo(info[TYPE]);
            blocks.add(new BlockCarpet(extInfo.getSecond(), info[NAME], info[ID] + "_" + "carpet"));
            blocks.add(new BlockPane(extInfo.getFirst(), extInfo.getSecond(), info[NAME], info[ID] + "_" + "pane"));
        }
    }

}