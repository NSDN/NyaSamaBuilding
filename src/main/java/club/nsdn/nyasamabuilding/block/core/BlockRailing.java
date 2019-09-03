package club.nsdn.nyasamabuilding.block.core;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by drzzm32 on 2019.8.27.
 */
public class BlockRailing extends net.minecraft.block.BlockFence {

    public final IBlockState baseBlockState;

    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    public BlockRailing(IBlockState state, String name, String id) {
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
        boolean flag = shape == BlockFaceShape.MIDDLE_POLE && (block instanceof BlockRailing || block instanceof BlockFenceGate);
        return !isExcepBlockForAttachWithPiston(block) && shape == BlockFaceShape.SOLID || flag;
    }

    @Override
    public void addCollisionBoxToList(
            IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB aabb,
            @Nonnull List<AxisAlignedBB> list, @Nullable Entity entity, boolean flag) {
        state = state.getActualState(world, pos);
        boolean down = state.getValue(DOWN);

        addCollisionBoxToList(pos, aabb, list, PILLAR_AABB.contract(0, down ? 0 : -0.5, 0));
        if (state.getValue(NORTH)) {
            addCollisionBoxToList(pos, aabb, list, NORTH_AABB.contract(0, down ? 0 : -0.5, 0));
        }

        if (state.getValue(EAST)) {
            addCollisionBoxToList(pos, aabb, list, EAST_AABB.contract(0, down ? 0 : -0.5, 0));
        }

        if (state.getValue(SOUTH)) {
            addCollisionBoxToList(pos, aabb, list, SOUTH_AABB.contract(0, down ? 0 : -0.5, 0));
        }

        if (state.getValue(WEST)) {
            addCollisionBoxToList(pos, aabb, list, WEST_AABB.contract(0, down ? 0 : -0.5, 0));
        }

    }

    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        state = this.getActualState(state, world, pos);
        boolean down = state.getValue(DOWN);
        return BOUNDING_BOXES[getBoundingBoxIdx(state)].contract(0, down ? 0 : -0.5, 0);
    }

    private static int getBoundingBoxIdx(IBlockState state) {
        int i = 0;
        if (state.getValue(NORTH)) {
            i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
        }

        if (state.getValue(EAST)) {
            i |= 1 << EnumFacing.EAST.getHorizontalIndex();
        }

        if (state.getValue(SOUTH)) {
            i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
        }

        if (state.getValue(WEST)) {
            i |= 1 << EnumFacing.WEST.getHorizontalIndex();
        }

        return i;
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

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, UP, DOWN);
    }

    @Override
    @Nonnull
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return super.getActualState(state, world, pos)
                .withProperty(UP,
                        world.isSideSolid(pos.down(), EnumFacing.UP, true) ||
                           world.getBlockState(pos.down()).getBlock() instanceof net.minecraft.block.BlockFence
                )
                .withProperty(DOWN,
                        world.isSideSolid(pos.down(), EnumFacing.UP, true) ||
                           world.getBlockState(pos.down()).getBlock() instanceof net.minecraft.block.BlockFence
                );
    }

}
