package club.nsdn.nyasamabuilding.block.core;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockFenceGate extends BlockHorizontal {

    public static final PropertyBool OPEN = PropertyBool.create("open");
    public static final PropertyBool IN_WALL = PropertyBool.create("in_wall");
    protected static final AxisAlignedBB AABB_HITBOX_ZAXIS = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
    protected static final AxisAlignedBB AABB_HITBOX_XAXIS = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_HITBOX_ZAXIS_INWALL = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 0.8125D, 0.625D);
    protected static final AxisAlignedBB AABB_HITBOX_XAXIS_INWALL = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 0.8125D, 1.0D);
    protected static final AxisAlignedBB AABB_COLLISION_BOX_ZAXIS = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D);
    protected static final AxisAlignedBB AABB_COLLISION_BOX_XAXIS = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.5D, 1.0D);

    public final IBlockState baseBlockState;

    public BlockFenceGate(IBlockState state, String name, String id) {
        super(state.getMaterial(), state.getMapColor(null, null));
        baseBlockState = state;
        setUnlocalizedName(name);
        setRegistryName(NyaSamaBuilding.MODID, id);
        setHardness(state.getBlockHardness(null, null));
        setResistance(blockHardness * 5.0F);
        setSoundType(state.getBlock().getSoundType());
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);

        setDefaultState(this.blockState.getBaseState().withProperty(OPEN, false).withProperty(IN_WALL, false));
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
        state = this.getActualState(state, world, pos);
        if ((Boolean)state.getValue(IN_WALL)) {
            return ((EnumFacing)state.getValue(FACING)).getAxis() == EnumFacing.Axis.X ? AABB_HITBOX_XAXIS_INWALL : AABB_HITBOX_ZAXIS_INWALL;
        } else {
            return ((EnumFacing)state.getValue(FACING)).getAxis() == EnumFacing.Axis.X ? AABB_HITBOX_XAXIS : AABB_HITBOX_ZAXIS;
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        EnumFacing.Axis enumfacing$axis = ((EnumFacing)state.getValue(FACING)).getAxis();
        if (enumfacing$axis == EnumFacing.Axis.Z && (world.getBlockState(pos.west()).getBlock() instanceof BlockWall || world.getBlockState(pos.east()).getBlock() instanceof BlockWall) || enumfacing$axis == EnumFacing.Axis.X && (world.getBlockState(pos.north()).getBlock() instanceof BlockWall || world.getBlockState(pos.south()).getBlock() instanceof BlockWall)) {
            state = state.withProperty(IN_WALL, true);
        }

        return state;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rotation) {
        return state.withProperty(FACING, rotation.rotate((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror) {
        return state.withRotation(mirror.toRotation((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).getMaterial().isSolid() ? super.canPlaceBlockAt(world, pos) : false;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        if ((Boolean)state.getValue(OPEN)) {
            return NULL_AABB;
        } else {
            return ((EnumFacing)state.getValue(FACING)).getAxis() == EnumFacing.Axis.Z ? AABB_COLLISION_BOX_ZAXIS : AABB_COLLISION_BOX_XAXIS;
        }
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
    public boolean isPassable(IBlockAccess world, BlockPos pos) {
        return (Boolean)world.getBlockState(pos).getValue(OPEN);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase player) {
        boolean flag = world.isBlockPowered(pos);
        return this.getDefaultState().withProperty(FACING, player.getHorizontalFacing()).withProperty(OPEN, flag).withProperty(IN_WALL, false);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand p_onBlockActivated_5_, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if ((Boolean)state.getValue(OPEN)) {
            state = state.withProperty(OPEN, false);
            world.setBlockState(pos, state, 10);
        } else {
            EnumFacing enumfacing = EnumFacing.fromAngle((double)player.rotationYaw);
            if (state.getValue(FACING) == enumfacing.getOpposite()) {
                state = state.withProperty(FACING, enumfacing);
            }

            state = state.withProperty(OPEN, true);
            world.setBlockState(pos, state, 10);
        }

        world.playEvent(player, (Boolean)state.getValue(OPEN) ? 1008 : 1014, pos, 0);
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighbor) {
        if (!world.isRemote) {
            boolean flag = world.isBlockPowered(pos);
            if (state.getValue(OPEN) != flag) {
                world.setBlockState(pos, state.withProperty(OPEN, flag), 2);
                world.playEvent(null, flag ? 1008 : 1014, pos, 0);
            }
        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return true;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(OPEN, (meta & 4) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();

        if ((Boolean)state.getValue(OPEN)) {
            i |= 4;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, OPEN, IN_WALL});
    }

    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof net.minecraft.block.BlockFenceGate && state.getBlockFaceShape(world, pos, facing) == BlockFaceShape.MIDDLE_POLE) {
            Block connector = world.getBlockState(pos.offset(facing)).getBlock();
            return connector instanceof BlockFence || connector instanceof BlockWall;
        } else {
            return false;
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing) {
        if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
            return ((EnumFacing)state.getValue(FACING)).getAxis() == facing.rotateY().getAxis() ? BlockFaceShape.MIDDLE_POLE : BlockFaceShape.UNDEFINED;
        } else {
            return BlockFaceShape.UNDEFINED;
        }
    }

}
