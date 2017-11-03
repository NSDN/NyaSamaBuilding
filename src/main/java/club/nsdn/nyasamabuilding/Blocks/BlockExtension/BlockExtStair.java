package club.nsdn.nyasamabuilding.Blocks.BlockExtension;

/**
 * Created by drzzm32 on 2016.3.25.
 */

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockBase;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class BlockExtStair extends BlockStairs {

    public BlockExtStair(String blockName, CommonBlockBase BasicBlock) {
        super(BasicBlock,0);
        setBlockName(blockName);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
        lightOpacity = 0;
    }
}
