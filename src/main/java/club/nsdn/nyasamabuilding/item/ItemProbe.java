package club.nsdn.nyasamabuilding.item;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by drzzm32 on 2019.2.23.
 */
public class ItemProbe extends ToolBase {

    public ItemProbe() {
        super(Item.ToolMaterial.IRON);
        setUnlocalizedName("Probe");
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
        setRegistryName(NyaSamaBuilding.MODID, "probe");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (!world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            int id = Block.getIdFromBlock(block);
            int meta = block.getMetaFromState(state);

            String str = "[NSB] Block ID: " + id +":" + meta;
            player.sendMessage(new TextComponentString(str));
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

}
