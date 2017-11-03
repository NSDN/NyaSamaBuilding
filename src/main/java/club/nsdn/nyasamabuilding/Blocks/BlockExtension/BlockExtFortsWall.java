package club.nsdn.nyasamabuilding.Blocks.BlockExtension;

/**
 * Created by drzzm32 on 2016.3.25.
 */

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockBase;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockExtFortsWall extends BlockWall {
    private CommonBlockBase basicBlock;

    public BlockExtFortsWall(String blockName, CommonBlockBase BasicBlock) {
        super(BasicBlock);
        basicBlock = BasicBlock;
        setBlockName(blockName);
        setBlockTextureName(BasicBlock.getBlockTexture());
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return basicBlock.getBlockTextureFromSide(p_149691_1_);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
    }
}
