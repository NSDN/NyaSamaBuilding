package club.nsdn.nyasamabuilding.Blocks.BlockExtension;

/**
 * Created by drzzm32 on 2016.3.25.
 */

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockBase;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockExtSlab extends BlockSlab {

    protected String textureLocation = "";
    private String name;
    protected void setIconLocation(String textureLocation) { this.textureLocation = textureLocation; }

    public BlockExtSlab(String blockName, CommonBlockBase BasicBlock) {
        super(false,Material.rock);
        setBlockName(blockName);
        name = blockName;
        setIconLocation(BasicBlock.getBlockTexture());
        setBlockTextureName(BasicBlock.getBlockTexture());
        setHardness(1.5F);
        setLightLevel(0F);
        setStepSound(soundTypeStone);
        setResistance(10.0F);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
        lightOpacity = 0;
    }

    @Override
    public String func_150002_b(int p_150002_1_) {
        return "";
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
