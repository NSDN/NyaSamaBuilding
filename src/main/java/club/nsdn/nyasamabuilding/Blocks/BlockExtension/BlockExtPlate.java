package club.nsdn.nyasamabuilding.Blocks.BlockExtension;

/**
 * Created by Administrator on 2016/5/4.
 */

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockBase;
import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockPaneBase;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import net.minecraft.block.material.Material;

public class BlockExtPlate extends CommonBlockPaneBase {

    private CommonBlockBase basicBlock;


    public BlockExtPlate(String name, CommonBlockBase BasicBlock){
        super(BasicBlock.getBlockTexture(),Material.iron,name);
        basicBlock = BasicBlock;
        setIconLocation(BasicBlock.getBlockTexture());
        setBlockTextureName(BasicBlock.getBlockTexture());
        setHardness(basicBlock.getDefaultHardness());
        setLightLevel(basicBlock.getDefaultLightLevel());
        setStepSound(basicBlock.stepSound);
        setResistance(basicBlock.getDefaultResistance());
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }
}