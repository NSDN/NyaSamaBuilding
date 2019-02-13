package club.nsdn.nyasamabuilding.block.core;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockVSlab extends BlockStairs {

    public final IBlockState baseBlockState;

    public BlockVSlab(IBlockState state, String name, String id) {
        super(state);
        baseBlockState = state;
        setLightOpacity(0);
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
            baseName = I18n.format(getUnlocalizedName() + ".name");
        String blockType = getClass().getSimpleName().replace("Block", "");
        return baseName + I18n.format("trans." + blockType + ".name");
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB aabb, List<AxisAlignedBB> list, @Nullable Entity entity, boolean flag) {
        if (!flag) {
            state = this.getActualState(state, world, pos);
        }

        Iterator var8 = getCollisionBoxList(state).iterator();

        while(var8.hasNext()) {
            AxisAlignedBB axisalignedbb = (AxisAlignedBB)var8.next();
            addCollisionBoxToList(pos, aabb, list, axisalignedbb);
        }

    }

    public List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
        List<AxisAlignedBB> list = Lists.newArrayList();
        /**
         * Edited for collision modification
         * */
        /*boolean flag = state.getValue(HALF) == EnumHalf.TOP;
        list.add(flag ? AABB_SLAB_TOP : AABB_SLAB_BOTTOM);*/
        EnumShape shape = state.getValue(SHAPE);
        if (shape == EnumShape.STRAIGHT || shape == EnumShape.INNER_LEFT || shape == EnumShape.INNER_RIGHT) {
            list.add(getCollQuarterBlock(state));
        }

        if (shape != EnumShape.STRAIGHT) {
            list.add(getCollEighthBlock(state));
        }

        return list;
    }

    public AxisAlignedBB expandUp(AxisAlignedBB aabb) {
        return aabb.expand(0, 0.5, 0);
    }

    public AxisAlignedBB expandDown(AxisAlignedBB aabb) {
        return aabb.expand(0, -0.5, 0);
    }

    /**
     * Edited for collision modification
     * */
    public AxisAlignedBB getCollQuarterBlock(IBlockState state) {
        boolean flag = state.getValue(HALF) == EnumHalf.TOP;
        AxisAlignedBB aabb;
        switch(state.getValue(FACING)) {
            case NORTH:
            default:
                aabb = flag ? AABB_QTR_BOT_NORTH : AABB_QTR_TOP_NORTH;
                break;
            case SOUTH:
                aabb = flag ? AABB_QTR_BOT_SOUTH : AABB_QTR_TOP_SOUTH;
                break;
            case WEST:
                aabb = flag ? AABB_QTR_BOT_WEST : AABB_QTR_TOP_WEST;
                break;
            case EAST:
                aabb = flag ? AABB_QTR_BOT_EAST : AABB_QTR_TOP_EAST;
                break;
        }
        aabb = flag ? expandUp(aabb) : expandDown(aabb);
        return aabb;
    }

    /**
     * Edited for collision modification
     * */
    public AxisAlignedBB getCollEighthBlock(IBlockState state) {
        EnumFacing facing = state.getValue(FACING);
        EnumFacing rotFacing;
        switch(state.getValue(SHAPE)) {
            case OUTER_LEFT:
            default:
                rotFacing = facing;
                break;
            case OUTER_RIGHT:
                rotFacing = facing.rotateY();
                break;
            case INNER_RIGHT:
                rotFacing = facing.getOpposite();
                break;
            case INNER_LEFT:
                rotFacing = facing.rotateYCCW();
        }

        boolean flag = state.getValue(HALF) == EnumHalf.TOP;
        AxisAlignedBB aabb;
        switch(rotFacing) {
            case NORTH:
            default:
                aabb = flag ? AABB_OCT_BOT_NW : AABB_OCT_TOP_NW;
                break;
            case SOUTH:
                aabb = flag ? AABB_OCT_BOT_SE : AABB_OCT_TOP_SE;
                break;
            case WEST:
                aabb = flag ? AABB_OCT_BOT_SW : AABB_OCT_TOP_SW;
                break;
            case EAST:
                aabb = flag ? AABB_OCT_BOT_NE : AABB_OCT_TOP_NE;
                break;
        }
        aabb = flag ? expandUp(aabb) : expandDown(aabb);
        return aabb;
    }

    @Override
    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d vecA, Vec3d vecB) {
        List<RayTraceResult> list = Lists.newArrayList();
        Iterator var7 = getCollisionBoxList(this.getActualState(state, world, pos)).iterator();

        while(var7.hasNext()) {
            AxisAlignedBB axisalignedbb = (AxisAlignedBB)var7.next();
            list.add(this.rayTrace(pos, vecA, vecB, axisalignedbb));
        }

        RayTraceResult raytraceresult1 = null;
        double d1 = 0.0D;
        Iterator var10 = list.iterator();

        while(var10.hasNext()) {
            RayTraceResult raytraceresult = (RayTraceResult)var10.next();
            if (raytraceresult != null) {
                double d0 = raytraceresult.hitVec.squareDistanceTo(vecB);
                if (d0 > d1) {
                    raytraceresult1 = raytraceresult;
                    d1 = d0;
                }
            }
        }

        return raytraceresult1;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        EnumShape shape = state.getValue(SHAPE);
        if (shape == EnumShape.STRAIGHT)
            return getCollQuarterBlock(state);
        else if (shape == EnumShape.INNER_LEFT || shape == EnumShape.INNER_RIGHT)
            return Block.FULL_BLOCK_AABB;
        else
            return getCollEighthBlock(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
        EnumShape shape = state.getValue(SHAPE);
        if (shape == EnumShape.STRAIGHT)
            return getCollQuarterBlock(state);
        else if (shape == EnumShape.INNER_LEFT || shape == EnumShape.INNER_RIGHT)
            return Block.FULL_BLOCK_AABB;
        else
            return getCollEighthBlock(state);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing) {
        state = this.getActualState(state, world, pos);
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            return BlockFaceShape.UNDEFINED;
        } else {
           EnumShape shape = state.getValue(SHAPE);
            if (shape != EnumShape.OUTER_LEFT && shape != EnumShape.OUTER_RIGHT) {
                EnumFacing enumfacing = state.getValue(FACING);
                switch(shape) {
                    case INNER_RIGHT:
                        return enumfacing != facing && enumfacing != facing.rotateYCCW() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
                    case INNER_LEFT:
                        return enumfacing != facing && enumfacing != facing.rotateY() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
                    case STRAIGHT:
                        return enumfacing == facing ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
                    default:
                        return BlockFaceShape.UNDEFINED;
                }
            } else {
                return BlockFaceShape.UNDEFINED;
            }
        }
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        if (ForgeModContainer.disableStairSlabCulling) {
            return super.doesSideBlockRendering(state, world, pos, facing);
        } else if (state.isOpaqueCube()) {
            return true;
        } else {
            state = this.getActualState(state, world, pos);
            EnumFacing side = state.getValue(FACING);
            BlockStairs.EnumShape shape = state.getValue(SHAPE);
            if (facing == EnumFacing.UP) {
                return false;
            } else if (facing == EnumFacing.DOWN) {
                return false;
            } else if (shape != BlockStairs.EnumShape.OUTER_LEFT && shape != BlockStairs.EnumShape.OUTER_RIGHT) {
                if (facing == side) {
                    return true;
                } else if (shape == BlockStairs.EnumShape.INNER_LEFT && facing.rotateY() == side) {
                    return true;
                } else {
                    return shape == BlockStairs.EnumShape.INNER_RIGHT && facing.rotateYCCW() == side;
                }
            } else {
                return false;
            }
        }
    }

}
