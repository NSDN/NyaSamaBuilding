package club.nsdn.nyasamabuilding.block.core;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Fence-gate variant of a building family, ported from the 1.12.2 {@code BlockFenceGate}.
 *
 * <p>The 1.12.2 class was 214 lines of hand-written gate behaviour: open/close on use, redstone
 * reaction in {@code neighborChanged}, {@code IN_WALL} detection from neighbouring walls,
 * axis-dependent hitboxes and even the "re-face the player when opening" rule. <b>All of that now
 * lives in vanilla {@link FenceGateBlock}</b>, so the port only has to supply two things:</p>
 * <ul>
 *   <li>the base block, for the display name (the 1.12.2 {@code trans.FenceGate.name} suffix);</li>
 *   <li>the open/close sounds, chosen from the family's material. The modern constructor
 *       {@code FenceGateBlock(Properties, SoundEvent, SoundEvent)} takes them directly, which is why
 *       no {@code WoodType} is needed -- the families here are stone, metal, sand, wood, glass and
 *       cloth rather than the vanilla wood variants.</li>
 * </ul>
 *
 * <p>Deliberately not overridden: {@code getShape}, {@code useWithoutItem}, {@code neighborChanged},
 * {@code rotate}/{@code mirror}. Re-implementing any of them would be a behaviour <i>change</i>, not
 * a port.</p>
 */
public class BlockFenceGate extends FenceGateBlock {

    private final Block baseBlock;

    public BlockFenceGate(Block baseBlock, BlockBehaviour.Properties properties, SoundEvent openSound, SoundEvent closeSound) {
        super(properties, openSound, closeSound);
        this.baseBlock = baseBlock;
    }

    /** Metal families swing like an iron door; everything else uses the wooden gate sounds. */
    public static SoundEvent openSoundFor(String type) {
        return "iron".equals(type) ? SoundEvents.IRON_DOOR_OPEN : SoundEvents.FENCE_GATE_OPEN;
    }

    public static SoundEvent closeSoundFor(String type) {
        return "iron".equals(type) ? SoundEvents.IRON_DOOR_CLOSE : SoundEvents.FENCE_GATE_CLOSE;
    }

    @Override
    public MutableComponent getName() {
        return Component.empty()
                .append(baseBlock.getName())
                .append(Component.translatable("trans.FenceGate.name"));
    }
}
