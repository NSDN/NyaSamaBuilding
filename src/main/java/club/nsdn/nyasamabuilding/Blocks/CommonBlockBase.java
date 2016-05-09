package club.nsdn.nyasamabuilding.Blocks;

/**
 * Created by drzzm32 on 2016.3.25.
 */

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CommonBlockBase extends Block {

    protected String textureLocation = "";
    protected void setIconLocation(String textureLocation) { this.textureLocation = textureLocation; }

    public CommonBlockBase(String blockName) {
        super(Material.rock);
        setBlockName(blockName);
    }

    public CommonBlockBase(Material material, String blockName) {
        super(material);
        setBlockName(blockName);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public void registerBlockIcons(IIconRegister icon)
    {
        this.blockIcon = icon.registerIcon(textureLocation);
    }

}
