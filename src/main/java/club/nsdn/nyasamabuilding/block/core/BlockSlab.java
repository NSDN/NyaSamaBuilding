package club.nsdn.nyasamabuilding.block.core;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockSlab extends net.minecraft.block.BlockSlab {

    public final IBlockState baseBlockState;

    public BlockSlab(IBlockState state, String name, String id) {
        super(state.getMaterial());
        this.baseBlockState = state;
        setUnlocalizedName(name);
        setRegistryName(NyaSamaBuilding.MODID, id);
        setHardness(state.getBlockHardness(null, null));
        setResistance(blockHardness * 2.0F);
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

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName();
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return null;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)  {
        if (!world.isRemote) {
            if (player.getHeldItemMainhand().getItem() == ItemBlock.getItemFromBlock(this)) {
                if (
                    facing.getAxis() == EnumFacing.Axis.Y ||
                    (state.getValue(HALF) == EnumBlockHalf.TOP && hitY < 0.5) ||
                    (state.getValue(HALF) == EnumBlockHalf.BOTTOM && hitY > 0.5)
                ) {
                    world.setBlockState(pos, baseBlockState);
                    world.markBlockRangeForRenderUpdate(pos, pos);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();
        state = state.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;
        if (state.getValue(HALF) == EnumBlockHalf.TOP) {
            meta |= 8;
        }
        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }

}
