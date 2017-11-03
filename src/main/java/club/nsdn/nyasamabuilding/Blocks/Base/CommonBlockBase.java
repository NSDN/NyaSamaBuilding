package club.nsdn.nyasamabuilding.Blocks.Base;

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

public class CommonBlockBase extends Block{

    protected String textureLocation = "";
    protected String rTexture = "";
    protected float defaultHardness = 0;
    protected float defaultLightLevel = 0;
    protected float defaultResistance = 0;
    public void setIconLocation(String textureLocation) { this.textureLocation = textureLocation; }

    public CommonBlockBase(String blockName) {
        super(Material.rock);
        setBlockName(blockName);
    }

    public CommonBlockBase(Material material, String blockName) {
        super(material);
        setBlockName(blockName);
    }

    @Override
    public Block setHardness(float hardness){
        defaultHardness = hardness;
        return super.setHardness(hardness);
    }

    @Override
    public Block setLightLevel(float lightLevel){
        defaultLightLevel = lightLevel;
        return super.setLightLevel(lightLevel);
    }

    @Override
    public Block setResistance(float resistance){
        defaultResistance = resistance;
        return super.setResistance(resistance);
    }



    @Override
    public Block setBlockTextureName(String texture){
        super.setBlockTextureName(texture);
        rTexture = texture;
        return this;
    }

    public String getBlockTexture(){
        return rTexture;
    }

    public float getDefaultHardness(){
        return defaultHardness;
    }

    public float getDefaultLightLevel(){
        return defaultLightLevel;
    }

    public float getDefaultResistance(){
        return defaultResistance;
    }

    @Override
    public int getRenderType() {
        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return true;
    }

    public boolean renderAsNormalBlock() {
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister icon)
    {
        this.blockIcon = icon.registerIcon(textureLocation);
    }

}
