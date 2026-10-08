package club.nsdn.nyasamabuilding.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Tool base class of {@code nyasamabuilding}, ported from the 1.12.2
 * {@code club.nsdn.nyasamabuilding.item.ToolBase}.
 *
 * <p>Its only 1.12.2 subclass in this module is {@link ItemProbe}; 1.12.2 also had a byte-identical
 * copy in {@code nyasamatelecom/api/tool}, whose port already exists in that module. The copy is kept
 * here rather than imported, because a building item must not depend on the telecom module (the pack's
 * single-ownership rule, {@code docs/12-HANDOVER.md}).</p>
 *
 * <p>Almost every method was renamed or reshaped in 1.21.1, so the mapping is listed rather than left
 * to the diff:</p>
 * <table>
 *   <caption>per-member</caption>
 *   <tr><th>1.12.2</th><th>1.21.1</th></tr>
 *   <tr><td>{@code ToolMaterial}</td><td>{@link Tier} ({@code getMaxUses} -&gt; {@code getUses},
 *       {@code getEnchantability} -&gt; {@code getEnchantmentValue}, repair item -&gt;
 *       {@code getRepairIngredient()})</td></tr>
 *   <tr><td>{@code setMaxStackSize(1)} / {@code setMaxDamage(...)} <b>in this constructor</b></td>
 *       <td>{@link Item.Properties} ({@code stacksTo(1).durability(...)}). The values are unchanged,
 *       but they now arrive through the properties object the caller passes in, exactly as the
 *       telecom port does it.</td></tr>
 *   <tr><td>{@code hitEntity} / {@code onBlockDestroyed}</td><td>{@code hurtEnemy} / {@code mineBlock};
 *       {@code damageItem} -&gt; {@code hurtAndBreak(amount, entity, slot)}. The entity argument is
 *       kept verbatim: 1.12.2 passed {@code target} (the victim), so this does too.</td></tr>
 *   <tr><td>{@code onItemRightClick} / {@code onItemUse} / {@code onItemUseFirst}</td>
 *       <td>{@code use} / {@code useOn(UseOnContext)} / {@code onItemUseFirst(ItemStack, UseOnContext)}
 *       (the last one is {@code IItemExtension}'s hook; the 1.12.2 player/world/hand arguments are
 *       reached through the context)</td></tr>
 *   <tr><td>{@code itemInteractionForEntity} / {@code onUpdate}</td><td>{@code interactLivingEntity} /
 *       {@code inventoryTick}</td></tr>
 *   <tr><td>{@code getIsRepairable} + {@code OreDictionary.itemMatches}</td>
 *       <td>{@code isValidRepairItem} with the tier's {@code Ingredient} -- the ore dictionary does not
 *       exist in 1.21.1</td></tr>
 *   <tr><td>{@code isFull3D()} ({@code @SideOnly(Side.CLIENT)})</td><td><b>removed</b>: the held-item
 *       transform is data driven now, and {@code @SideOnly} does not exist</td></tr>
 *   <tr><td>{@code getItemEnchantability()}</td><td>{@code getEnchantmentValue()}</td></tr>
 * </table>
 *
 * <p><b>Recorded deviation in {@link #updateTileEntity(BlockEntity)}:</b> the 1.12.2 body ended with
 * {@code markBlockRangeForRenderUpdate(pos, pos)}, which has no 1.21.1 counterpart (the client re-reads
 * a section from {@code sendBlockUpdated}). Nothing in 1.12.2 called the method -- in this module it has
 * zero callers -- so the lost line has no observable effect; it is recorded rather than silently
 * dropped.</p>
 */
public class ToolBase extends Item {

    public Tier tier;

    public ToolBase(Item.Properties properties, Tier tier) {
        super(properties.stacksTo(1).durability(tier.getUses()));
        this.tier = tier;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(2, target, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(1, miningEntity, EquipmentSlot.MAINHAND);
        }
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return this.tier.getRepairIngredient().test(repairCandidate)
                || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return false;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        return InteractionResult.PASS;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }

    /** 1.12.2 shape kept for a tool that has to re-read a block entity on the client. */
    public void updateTileEntity(BlockEntity blockEntity) {
        if (blockEntity == null || blockEntity.getLevel() == null) {
            return;
        }
        BlockState state = blockEntity.getBlockState();
        blockEntity.getLevel().sendBlockUpdated(blockEntity.getBlockPos(), state, state, 2);
    }
}
