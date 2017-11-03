package club.nsdn.nyasamabuilding.Blocks.Base;

/**
 * Created by drzzm32 on 2016.3.25.
 */

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class CommonBlockFallingBase extends BlockFalling{

    protected String textureLocation = "";
    protected String rTexture = "";
    protected float defaultHardness = 0;
    protected float defaultLightLevel = 0;
    protected float defaultResistance = 0;
    public void setIconLocation(String textureLocation) { this.textureLocation = textureLocation; }

    public CommonBlockFallingBase(String blockName) {
        super(Material.rock);
        setBlockName(blockName);
    }

    public CommonBlockFallingBase(Material material, String blockName) {
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
