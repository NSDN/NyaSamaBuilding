package club.nsdn.nyasamabuilding.Blocks.Base;

/**
 * Created by drzzm32 on 2016.3.25.
 */

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

import static net.minecraftforge.common.util.ForgeDirection.*;

public class CommonBlockPaneBase extends BlockPane {

    protected String textureLocation = "";
    protected void setIconLocation(String textureLocation) { this.textureLocation = textureLocation; }

    public CommonBlockPaneBase(String iconTexture,Material material, String blockName) {
        super(iconTexture,iconTexture,material,false);
        setBlockName(blockName);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(this);
    }

}
