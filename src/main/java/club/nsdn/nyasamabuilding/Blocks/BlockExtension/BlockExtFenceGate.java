package club.nsdn.nyasamabuilding.Blocks.BlockExtension;

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockBase;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.util.IIcon;

/**
 * Created by Administrator on 2016/5/30.
 */
public class BlockExtFenceGate extends BlockFenceGate {

    private CommonBlockBase basicBlock;

    public BlockExtFenceGate(String Name, CommonBlockBase BasicBlock){
        super();
        basicBlock = BasicBlock;
        //this.blockMaterial = Material.rock;
        setBlockName(Name);
        setHardness(1.5F);
        setLightLevel(0F);
        setBlockTextureName(BasicBlock.getBlockTexture());
        setStepSound(soundTypeStone);
        setResistance(10.0F);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return basicBlock.getBlockTextureFromSide(p_149691_1_);
    }
}
