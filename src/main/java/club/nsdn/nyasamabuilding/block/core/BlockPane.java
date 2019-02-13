package club.nsdn.nyasamabuilding.block.core;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockPane extends net.minecraft.block.BlockPane {

    public final IBlockState baseBlockState;

    public BlockPane(Material material, SoundType soundType, String name, String id) {
        super(material, false);
        baseBlockState = null;
        setUnlocalizedName(name);
        setRegistryName(NyaSamaBuilding.MODID, id);
        setHardness(1.0F);
        setResistance(blockHardness * 5.0F);
        setSoundType(soundType);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

    public BlockPane(IBlockState state, String name, String id) {
        super(state.getMaterial(), false);
        baseBlockState = state;
        setUnlocalizedName(name);
        setRegistryName(NyaSamaBuilding.MODID, id);
        setHardness(state.getBlockHardness(null, null));
        setResistance(blockHardness * 5.0F);
        setSoundType(state.getBlock().getSoundType());
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

    @Override
    public String getLocalizedName() {
        String baseName;
        if (baseBlockState != null)
            baseName = baseBlockState.getBlock().getLocalizedName();
        else
            baseName = I18n.format(getUnlocalizedName() + ".name");
        String blockType = getClass().getSimpleName().replace("Block", "");
        return baseName + I18n.format("trans." + blockType + ".name");
    }

}
