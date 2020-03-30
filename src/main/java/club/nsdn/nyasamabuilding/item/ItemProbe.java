package club.nsdn.nyasamabuilding.item;

import club.nsdn.nyasamabuilding.NyaSamaBuilding;
import club.nsdn.nyasamabuilding.creativetab.CreativeTabLoader;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOps;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            if (player.isSneaking()) {
                MinecraftServer server = world.getMinecraftServer();
                if (server != null) {
                    ICommandManager manager = server.getCommandManager();
                    ItemStack stack = player.getHeldItemMainhand();
                    if (stack.getItem() instanceof ItemProbe && stack.hasTagCompound()) {
                        NBTTagCompound tag = stack.getTagCompound();
                        if (tag != null && tag.hasKey("command")) {
                            String cmd = tag.getString("command");
                            if (!cmd.isEmpty())
                                manager.executeCommand(player, cmd);
                        }
                    }
                }
            } else {
                IBlockState state = world.getBlockState(pos);
                Block block = state.getBlock();

                int id = Block.getIdFromBlock(block);
                int meta = block.getMetaFromState(state);

                String str = "[NSB] Block ID: " + id + ":" + meta;
                player.sendMessage(new TextComponentString(str));
            }

            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.FAIL;
    }

    public static class CmdSetCommand extends CommandBase {

        public CmdSetCommand(){
            aliases = Lists.newArrayList("probeCmd");
        }

        private final List<String> aliases;

        @Override
        @Nonnull
        public String getName() {
            return "probeCmd";
        }

        @Override
        @Nonnull
        public String getUsage(@Nonnull ICommandSender sender) {
            return "probeCmd <command...>";
        }

        @Override
        @Nonnull
        public List<String> getAliases() {
            return aliases;
        }

        @Override
        public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
            if (args.length < 1) {
                sender.sendMessage(new TextComponentString(TextFormatting.AQUA + getUsage(sender)));
                return;
            }

            if (!(sender instanceof EntityPlayer)) {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "[ERROR] This command can only be executed by player!"));
                return;
            }

            EntityPlayer player = (EntityPlayer) sender;
            ItemStack stack = player.getHeldItemMainhand();
            if (!(stack.getItem() instanceof ItemProbe)) {
                player.sendMessage(new TextComponentString(TextFormatting.RED + "[ERROR] You should held Probe!"));
                return;
            }

            String cmd = "";
            for (String arg : args)
                cmd = cmd.concat(arg + " ");

            NBTTagCompound tag;
            if (!stack.hasTagCompound())
                tag = new NBTTagCompound();
            else
                tag = stack.getTagCompound();
            if (tag == null)
                tag = new NBTTagCompound();
            tag.setString("command", cmd);
            stack.setTagCompound(tag);

            player.sendMessage(new TextComponentString(TextFormatting.GRAY + "ProbeCmd: \"" + cmd + "\""));
        }

        @Override
        public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
            if (sender instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) sender;
                if (server.isDedicatedServer()) {
                    UserListOps listOps = server.getPlayerList().getOppedPlayers();
                    ArrayList<String> list = Lists.newArrayList(listOps.getKeys());
                    return list.contains(player.getName());
                } else {
                    return player.isCreative();
                }
            }

            return false;
        }

        @Override
        @Nonnull
        public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
            return Collections.emptyList();
        }

    }

}
