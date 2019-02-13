package club.nsdn.nyasamabuilding.block.core;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.text.translation.I18n;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockStairs extends net.minecraft.block.BlockStairs {

    public final IBlockState baseBlockState;

    public BlockStairs(IBlockState state, String name, String id) {
        super(state);
        baseBlockState = state;
        setUnlocalizedName(name);
        setRegistryName(NyaSamaBuilding.MODID, id);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

    @Override
    public String getLocalizedName() {
        String baseName;
        if (baseBlockState != null)
            baseName = baseBlockState.getBlock().getLocalizedName();
        else
            baseName = I18n.translateToLocal(getUnlocalizedName() + ".name");
        String blockType = getClass().getSimpleName().replace("Block", "");
        return baseName + I18n.translateToLocal("trans." + blockType + ".name");
    }

}
