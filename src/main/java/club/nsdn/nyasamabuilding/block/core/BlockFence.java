package club.nsdn.nyasamabuilding.block.core;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockFence extends net.minecraft.block.BlockFence {

    public final IBlockState baseBlockState;

    public BlockFence(IBlockState state, String name, String id) {
        super(state.getMaterial(), state.getMapColor(null, null));
        baseBlockState = state;
        setUnlocalizedName(name);
        setRegistryName(NyaSamaBuilding.MODID, id);
        setHardness(state.getBlockHardness(null, null));
        setResistance(blockHardness * 5.0F);
        setSoundType(state.getBlock().getSoundType());
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

    @Override
    public boolean canConnectTo(IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing) {
        IBlockState state = world.getBlockState(pos);
        BlockFaceShape shape = state.getBlockFaceShape(world, pos, facing);
        Block block = state.getBlock();
        boolean flag = shape == BlockFaceShape.MIDDLE_POLE && (
                (block instanceof net.minecraft.block.BlockFence && !(block instanceof BlockRailing)) ||
                 block instanceof BlockFenceGate
        );
        return !isExcepBlockForAttachWithPiston(block) && shape == BlockFaceShape.SOLID || flag;
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
