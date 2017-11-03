package club.nsdn.nyasamabuilding.Blocks.BlockExtension;

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockBase;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

/**
 * Created by Administrator on 2016/5/30.
 */
public class BlockExtFence extends BlockFence {
    public BlockExtFence(String Name, CommonBlockBase BasicBlock){
        super(BasicBlock.getBlockTexture(), Material.rock);
        setBlockName(Name);
        setHardness(1.5F);
        setLightLevel(0F);
        setStepSound(soundTypeStone);
        setResistance(10.0F);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

    @Override
    public boolean canConnectFenceTo(IBlockAccess p_149826_1_, int p_149826_2_, int p_149826_3_, int p_149826_4_)
    {
        Block block = p_149826_1_.getBlock(p_149826_2_, p_149826_3_, p_149826_4_);
        if(block == this || block == Blocks.fence_gate){return true;}
        if(block.getMaterial().isOpaque() && block.renderAsNormalBlock()){
            Material mat = block.getMaterial();
            if(mat == Material.clay){
                return true;
            }
            if(mat == Material.cloth){
                return true;
            }
            if(mat == Material.glass){
                return true;
            }
            if(mat == Material.ground){
                return true;
            }
            if(mat == Material.iron){
                return true;
            }
            if(mat == Material.rock){
                return true;
            }
            if(mat == Material.wood){
                return true;
            }
            return false;
        }

        if(block instanceof BlockExtFence){
            return true;
        }
        if(block instanceof BlockExtFenceGate){
            return true;
        }
        return false;
    }
}
