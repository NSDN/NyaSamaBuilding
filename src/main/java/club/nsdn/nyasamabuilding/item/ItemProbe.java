package club.nsdn.nyasamabuilding.item;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created by drzzm32 on 2019.2.23.
 *
 * <p>A probe tool with two functions, both kept from the 1.12.2 class: right-clicking a block with a
 * <em>sneaking</em> player runs the command stored in the held stack's {@code command} NBT key
 * <b>as that player</b>, while a plain use reports the clicked block; the companion {@code /probeCmd}
 * command is what writes that key. The command class is a static nested class here, exactly as in
 * 1.12.2, which is also the shape {@code nyasamarailway}'s ported commands use.</p>
 *
 * <h2>Mapping notes (1.12.2 &rarr; 1.21.1)</h2>
 * <table>
 *   <caption>per-member</caption>
 *   <tr><th>1.12.2</th><th>1.21.1</th></tr>
 *   <tr><td>{@code ItemProbe()} with {@code super(Item.ToolMaterial.IRON)} plus
 *       {@code setUnlocalizedName}/{@code setCreativeTab}/{@code setRegistryName}</td>
 *       <td>{@link Item.Properties} plus {@link Tiers#IRON}, both handed in by {@link BuildingItems},
 *       which also owns the registry name ({@code nyasamabuilding:probe}) and therefore the lang key
 *       {@code item.nyasamabuilding.probe}. <b>No creative tab is set</b> -- see the class-level note
 *       below.</td></tr>
 *   <tr><td>{@code onItemRightClick} &rarr; {@code ActionResult(SUCCESS, heldStack)}</td>
 *       <td>{@code use} &rarr; {@code InteractionResultHolder.success(heldStack)}. No side effect,
 *       unchanged.</td></tr>
 *   <tr><td>{@code onItemUseFirst(player, world, pos, facing, hit, hand)}</td>
 *       <td>{@code onItemUseFirst(ItemStack, UseOnContext)} -- the NeoForge {@code IItemExtension} hook
 *       that {@code ItemStack.onItemUseFirst} calls (verified in bytecode: the call to
 *       {@code Item.onItemUseFirst} lives in {@code ItemStack.lambda$onItemUseFirst$17}). The 1.12.2
 *       arguments are read off the context: {@code getLevel()}, {@code getPlayer()},
 *       {@code getClickedPos()}.</td></tr>
 *   <tr><td>{@code !world.isRemote} / {@code world.getMinecraftServer()} /
 *       {@code server.getCommandManager().executeCommand(player, cmd)}</td>
 *       <td>{@code !level.isClientSide} / {@code level.getServer()} /
 *       {@code server.getCommands().performPrefixedCommand(player.createCommandSourceStack(), cmd)}.
 *       The source is built from the player, so the command runs with the player's own permission
 *       level -- the same "as that player" semantics as 1.12.2's {@code executeCommand(sender, ...)}.
 *       {@code performPrefixedCommand} also strips an optional leading {@code '/'}, matching 1.12.2's
 *       {@code CommandHandler.executeCommand}.</td></tr>
 *   <tr><td>{@code stack.hasTagCompound()} / {@code getTagCompound()} / {@code tag.hasKey("command")} /
 *       {@code getString("command")}</td>
 *       <td>{@code stack.get(DataComponents.CUSTOM_DATA)} /
 *       {@link CustomData#copyTag()} / {@code tag.contains("command")} / {@code tag.getString("command")}.
 *       {@code ItemStack#hasTagCompound/getTagCompound/setTagCompound} were removed in 1.20.5; the
 *       {@link CompoundTag} behind {@code CUSTOM_DATA} is serialised into the very same {@code tag}
 *       compound of the stack, so the stored key and its wire format are unchanged (the same migration
 *       {@code nyasamarailway}'s {@code util.ItemNBTHelper} uses).</td></tr>
 *   <tr><td>{@code player.sendMessage(new TextComponentString(str))}</td>
 *       <td>{@code player.displayClientMessage(Component.literal(str), false)} -- 1.12.2's
 *       {@code EntityPlayer#sendMessage} was status-bar/chat agnostic server-side; {@code false} keeps
 *       it in chat.</td></tr>
 *   <tr><td>{@code Block.getIdFromBlock(block)} + {@code block.getMetaFromState(state)}</td>
 *       <td>{@code BuiltInRegistries.BLOCK.getId(block)} plus the registry name. <b>Metadata has no
 *       1.21.1 counterpart</b> (a {@code BlockState} is a set of properties, not a 0..15 index), so the
 *       reported text names the block instead of inventing a number; the report is still "which block
 *       did I just click".</td></tr>
 *   <tr><td>{@code return EnumActionResult.SUCCESS} on the server and {@code FAIL} on the client</td>
 *       <td>{@code InteractionResult.SUCCESS} / {@code InteractionResult.FAIL}. Both values stop the
 *       block from being used, which is the 1.12.2 behaviour of that pair.</td></tr>
 * </table>
 *
 * <p><b>Creative tab:</b> 1.12.2 put this item in {@code CreativeTabLoader.tabNyaSamaBuilding}. The
 * ported module has no creative-tab code at all yet (the lang key
 * {@code itemGroup.tabNyaSamaBuilding} is already reserved for it), and adding one would be a new
 * feature rather than a port, so the item is currently reachable by {@code /give} only -- the same
 * state as every other item of this module.</p>
 */
public class ItemProbe extends ToolBase {

    public ItemProbe(Item.Properties properties) {
        super(properties, Tiers.IRON);
    }

    /** 1.12.2 {@code onItemRightClick}: success, and nothing else happens. */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player == null) {
            // 1.21.1 allows a UseOnContext without a player (1.12.2's hook always had one); there is
            // nobody to run a command as, so the 1.12.2 success branch cannot be taken.
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                MinecraftServer server = level.getServer();
                if (server != null) {
                    ItemStack held = player.getMainHandItem();
                    if (held.getItem() instanceof ItemProbe) {
                        CustomData data = held.get(DataComponents.CUSTOM_DATA);
                        if (data != null) {
                            CompoundTag tag = data.copyTag();
                            if (tag.contains("command")) {
                                String cmd = tag.getString("command");
                                if (!cmd.isEmpty())
                                    server.getCommands().performPrefixedCommand(
                                            player.createCommandSourceStack(), cmd);
                            }
                        }
                    }
                }
            } else {
                BlockState state = level.getBlockState(context.getClickedPos());
                Block block = state.getBlock();

                int id = BuiltInRegistries.BLOCK.getId(block);

                String str = "[NSB] Block ID: " + id + " " + BuiltInRegistries.BLOCK.getKey(block);
                player.displayClientMessage(Component.literal(str), false);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    /**
     * 1.12.2 {@code ItemProbe.CmdSetCommand}: {@code probeCmd <command...>} writes its argument into
     * the held probe's {@code command} NBT key, which {@link #onItemUseFirst} then runs.
     *
     * <p>{@code CommandBase}/{@code ICommand}/{@code ICommandManager} do not exist in 1.21.1; the
     * command is a Brigadier tree registered from {@code RegisterCommandsEvent} (the wiring line is in
     * {@code NyaSamaBuilding}). The 1.12.2 message bodies are kept verbatim, split into
     * {@code sendSuccess} for the informational lines and {@code sendFailure} for the two
     * {@code [ERROR]} lines, exactly as the sibling ports do.</p>
     */
    public static class CmdSetCommand {

        /** 1.12.2 {@code getName()}, and the single entry of its {@code getAliases()} list. */
        public static final String NAME = "probeCmd";

        private CmdSetCommand() {
        }

        /** 1.12.2 {@code getUsage(ICommandSender)}, verbatim. The sender argument was never read. */
        public static String getUsage() {
            return "probeCmd <command...>";
        }

        /** 1.12.2 {@code checkPermission(MinecraftServer, ICommandSender)}. */
        private static boolean checkPermission(CommandSourceStack source) {
            if (source.getEntity() instanceof Player player) {
                if (source.getServer().isDedicatedServer()) {
                    return source.getServer().getPlayerList().isOp(player.getGameProfile());
                } else {
                    return player.isCreative();
                }
            }

            return false;
        }

        /**
         * 1.12.2 {@code execute}, from the {@code args} array onward.
         *
         * <p>1.12.2's parser had already split the input on {@code " "} (after a {@code trim()}), so the
         * tail arrived as a word array; Brigadier hands the same tail over as one {@code greedyString}.
         * It is split again here, which reproduces the 1.12.2 word list (interior empty tokens survive,
         * the trailing one is already removed by the trim) and therefore the exact string that is stored
         * and later executed -- including the trailing space the 1.12.2 loop appended.</p>
         */
        private static int execute(CommandContext<CommandSourceStack> context) {
            CommandSourceStack source = context.getSource();

            if (!(source.getEntity() instanceof Player player)) {
                source.sendFailure(Component.literal(ChatFormatting.RED + "[ERROR] This command can only be executed by player!"));
                return 0;
            }

            ItemStack stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof ItemProbe)) {
                source.sendFailure(Component.literal(ChatFormatting.RED + "[ERROR] You should held Probe!"));
                return 0;
            }

            String cmd = "";
            for (String arg : StringArgumentType.getString(context, "command").trim().split(" "))
                cmd = cmd.concat(arg + " ");

            // `cmd` is reassigned in the loop above, so the lambda captures below take a final copy
            // (the same note PublicInfoCore.InfoSheetCommand carries); the string itself is unchanged.
            final String command = cmd;

            // 1.12.2 created an empty tag when the stack had none and wrote the string into it;
            // CustomData.update is that exact step, and it keeps the "command" key.
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString("command", command));

            source.sendSuccess(() -> Component.literal(ChatFormatting.GRAY + "ProbeCmd: \"" + command + "\""), false);
            return 1;
        }

        /** The 1.12.2 command, as a Brigadier tree. See the class javadoc for the wiring line. */
        public static LiteralArgumentBuilder<CommandSourceStack> buildCommand() {
            return Commands.literal(NAME)
                    .requires(CmdSetCommand::checkPermission)
                    .executes(context -> {
                        // 1.12.2 args.length < 1.
                        context.getSource().sendSuccess(() -> Component.literal(ChatFormatting.AQUA + getUsage()), false);
                        return 1;
                    })
                    .then(Commands.argument("command", StringArgumentType.greedyString())
                            .executes(CmdSetCommand::execute));
        }
    }
}
