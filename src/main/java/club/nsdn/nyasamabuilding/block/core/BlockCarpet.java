package club.nsdn.nyasamabuilding.block.core;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockCarpet extends Block {

    protected static final AxisAlignedBB CARPET_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    public final IBlockState baseBlockState;

    public BlockCarpet(SoundType soundType, String name, String id) {
        super(Material.CARPET);
        baseBlockState = null;
        setTickRandomly(true);
        setUnlocalizedName(name);
        setSoundType(soundType);
        setRegistryName(NyaSamaBuilding.MODID, id);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
    }

    public BlockCarpet(IBlockState state, String name, String id) {
        super(Material.CARPET);
        baseBlockState = state;
        setTickRandomly(true);
        setUnlocalizedName(name);
        setSoundType(state.getBlock().getSoundType());
        setRegistryName(NyaSamaBuilding.MODID, id);
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

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return CARPET_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        if (facing == EnumFacing.UP) {
            return true;
        } else {
            return world.getBlockState(pos.offset(facing)).getBlock() == this ? true : super.shouldSideBeRendered(state, world, pos, facing);
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing) {
        return facing == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

}
