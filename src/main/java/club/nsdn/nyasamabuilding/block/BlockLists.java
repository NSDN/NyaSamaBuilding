package club.nsdn.nyasamabuilding.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

/**
 * Table-driven block material data, ported from the 1.12.2 {@code BlockLists}.
 *
 * <p>What changed for 1.21.1: the old {@code Material} + {@code SoundType} pair no longer exists.
 * {@code Material} was removed entirely, so each entry now yields a
 * {@link BlockBehaviour.Properties} built once and reused by every variant of that block family.</p>
 *
 * <p>The 1.12.2 variants shared one family because the derived blocks (slab, stairs, wall, ...) took
 * the base block's default state. Here a variant needs the base block's {@code Block} or at least
 * its {@code BlockState}, so {@link #getProperties} returns reusable Properties and the family
 * registration passes the base block along separately.</p>
 */
public final class BlockLists {

    public static final int NAME = 0;
    public static final int ID = 1;
    public static final int TYPE = 2;

    public static final String TYPE_ROCK = "rock";    // Material.ROCK  + SoundType.STONE
    public static final String TYPE_IRON = "iron";    // Material.IRON  + SoundType.METAL
    public static final String TYPE_SAND = "sand";    // Material.SAND  + SoundType.GROUND
    public static final String TYPE_WOOD = "wood";    // Material.WOOD  + SoundType.WOOD
    public static final String TYPE_GLASS = "glass";  // Material.GLASS + SoundType.GLASS
    public static final String TYPE_CLOTH = "cloth";  // Material.CARPET + SoundType.CLOTH

    private BlockLists() {
    }

    /**
     * Fresh Properties for the given legacy material type. A fresh instance per block is deliberate:
     * {@code BlockBehaviour.Properties} is mutable, so sharing one instance across a thousand blocks
     * would make later per-block tweaks leak into every sibling.
     */
    public static BlockBehaviour.Properties getProperties(String type) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of();
        switch (type == null ? "" : type) {
            case TYPE_IRON:
                return properties.mapColor(MapColor.METAL).sound(SoundType.METAL)
                        .strength(2.0F, 10.0F).requiresCorrectToolForDrops();
            case TYPE_SAND:
                return properties.mapColor(MapColor.SAND).sound(SoundType.GRAVEL).strength(2.0F, 10.0F);
            case TYPE_WOOD:
                return properties.mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2.0F, 10.0F);
            case TYPE_GLASS:
                // 1.21.1 has no MapColor.GLASS; vanilla glass uses NONE.
                return properties.mapColor(MapColor.NONE).sound(SoundType.GLASS).strength(2.0F, 10.0F);
            case TYPE_CLOTH:
                // 1.12.2 used Material.CARPET, which also forced "not solid"; keep the sticking
                // behaviour that makes carpets attach to the block below.
                // 1.21.1 renamed the cloth map colour to WOOL.
                return properties.mapColor(MapColor.WOOL).sound(SoundType.WOOL).strength(2.0F, 10.0F)
                        .pushReaction(PushReaction.DESTROY);
            case TYPE_ROCK:
            default:
                return properties.mapColor(MapColor.STONE).sound(SoundType.STONE)
                        .strength(2.0F, 10.0F).requiresCorrectToolForDrops();
        }
    }

    /** Kept for parity with the 1.12.2 signature; maps a type name to a {@link SoundType}. */
    public static SoundType getSoundType(String type) {
        switch (type == null ? "" : type) {
            case TYPE_IRON:
                return SoundType.METAL;
            case TYPE_SAND:
                return SoundType.GRAVEL;
            case TYPE_WOOD:
                return SoundType.WOOD;
            case TYPE_GLASS:
                return SoundType.GLASS;
            case TYPE_CLOTH:
                return SoundType.WOOL;
            case TYPE_ROCK:
            default:
                return SoundType.STONE;
        }
    }

    /** Unused placeholder kept so the legacy constant names still resolve if referenced. */
    public static final net.minecraft.core.Holder<SoundType> STONE_SOUND = net.minecraft.core.Holder.direct(SoundType.STONE);

    static {
        // Touch a vanilla sound holder so an accidental removal of the import above is caught at
        // compile time rather than silently changing behaviour.
        assert SoundEvents.STONE_PLACE != null;
    }

    public static final String[][] HARD = new String[][] {
        { "AntiStaticFloor", "anti_static_floor", TYPE_ROCK },
        { "Asphalt", "asphalt", TYPE_ROCK },
        { "BigConcreteBrick", "big_concrete_brick", TYPE_ROCK },
        { "BlackMarble", "black_marble", TYPE_ROCK },
        { "BrushedAluminum", "brushed_aluminum", TYPE_IRON },
        { "CinderBrickWall", "cinder_brick_wall", TYPE_ROCK },
        { "CoarseConcreteFloor", "coarse_concrete_floor", TYPE_ROCK },
        { "ConcreteBrickWall", "concrete_brick_wall", TYPE_ROCK },
        { "ConcreteRailTunnelDown", "concrete_rail_tunnel_down", TYPE_ROCK },
        { "ConcreteRailTunnelTop", "concrete_rail_tunnel_top", TYPE_ROCK },
        { "ConcreteRailTunnelUp", "concrete_rail_tunnel_up", TYPE_ROCK },
        { "ConcreteWall", "concrete_wall", TYPE_ROCK },
        { "CorrugatedBlue", "corrugated_blue", TYPE_IRON },
        { "CorrugatedCyan", "corrugated_cyan", TYPE_IRON },
        { "CorrugatedGreen", "corrugated_green", TYPE_IRON },
        { "CorrugatedMagenta", "corrugated_magenta", TYPE_IRON },
        { "CorrugatedOrange", "corrugated_orange", TYPE_IRON },
        { "CorrugatedPurple", "corrugated_purple", TYPE_IRON },
        { "CorrugatedRed", "corrugated_red", TYPE_IRON },
        { "CorrugatedWhite", "corrugated_white", TYPE_IRON },
        { "CorrugatedYellow", "corrugated_yellow", TYPE_IRON },
        { "DarkGreyCellTile", "dark_grey_cell_tile", TYPE_GLASS },
        { "DenseConcreteWall", "dense_concrete_wall", TYPE_ROCK },
        { "ExqMarble", "exq_marble", TYPE_ROCK },
        { "GreyBrick", "grey_brick", TYPE_ROCK },
        { "LatexPaintedWall", "latex_painted_wall", TYPE_ROCK },
        { "DarkLatexPaintedWall", "dark_latex_painted_wall", TYPE_ROCK },
        { "LightGreyCellTile", "light_grey_cell_tile", TYPE_GLASS },
        { "Marble", "marble", TYPE_ROCK },
        { "MineralWoolCellingPlate", "mineral_wool_celling_plate", TYPE_ROCK },
        { "ObliquePavingBrick", "oblique_paving_brick", TYPE_ROCK },
        { "OldBrick", "old_brick", TYPE_ROCK },
        { "PlazzaFloor", "plazza_floor", TYPE_SAND },
        { "RedKoMak", "red_ko_mak", TYPE_WOOD },
        { "RoadBedStone", "road_bed_stone", TYPE_SAND },
        { "RoadCyanTile", "road_cyan_tile", TYPE_ROCK },
        { "RoadDarkGreenTile", "road_dark_green_tile", TYPE_ROCK },
        { "RoadDarkGreyTile", "road_dark_grey_tile", TYPE_ROCK },
        { "RoadDarkOrangeTile", "road_dark_orange_tile", TYPE_ROCK },
        { "RoadDarkPinkTile", "road_dark_pink_tile", TYPE_ROCK },
        { "RoadDarkYellowTile", "road_dark_yellow_tile", TYPE_ROCK },
        { "RoadDustYellowTile", "road_dust_yellow_tile", TYPE_ROCK },
        { "RoadGreyTile", "road_grey_tile", TYPE_ROCK },
        { "RoadLightBlueTile", "road_light_blue_tile", TYPE_ROCK },
        { "RoadLightGreenTile", "road_light_green_tile", TYPE_ROCK },
        { "RoadLightGreyTile", "road_light_grey_tile", TYPE_ROCK },
        { "RoadLightOrangeTile", "road_light_orange_tile", TYPE_ROCK },
        { "RoadLightPinkTile", "road_light_pink_tile", TYPE_ROCK },
        { "RoadLightYellowTile", "road_light_yellow_tile", TYPE_ROCK },
        { "RoadPurpleTile", "road_purple_tile", TYPE_ROCK },
        { "RoadRedTile", "road_red_tile", TYPE_ROCK },
        { "RoadShineBlueTile", "road_shine_blue_tile", TYPE_ROCK },
        { "RoadShineOrangeTile", "road_shine_orange_tile", TYPE_ROCK },
        { "RoadShineYellowTile", "road_shine_yellow_tile", TYPE_ROCK },
        { "RoadSkyBlueTile", "road_sky_blue_tile", TYPE_ROCK },
        { "RoadStoneYellowTile", "road_stone_yellow_tile", TYPE_ROCK },
        { "RoadWhiteTile", "road_white_tile", TYPE_ROCK },
        { "RoadYellowTile", "road_yellow_tile", TYPE_ROCK },
        { "RustyTreadSteelPlate", "rusty_tread_steel_plate", TYPE_IRON },
        { "SandBrick", "sand_brick", TYPE_ROCK },
        { "WhiteBrickWall", "white_brick_wall", TYPE_ROCK },
        { "ZryBathroomCellTile", "zry_bathroom_cell_tile", TYPE_GLASS },
        { "ZryTVBgWall", "zry_tvbg_wall", TYPE_ROCK },
        { "TreadSteelBlack", "tread_steel_black", TYPE_IRON },
        { "TreadSteelBlue", "tread_steel_blue", TYPE_IRON },
        { "TreadSteelBrown", "tread_steel_brown", TYPE_IRON },
        { "TreadSteelCyan", "tread_steel_cyan", TYPE_IRON },
        { "TreadSteelGreen", "tread_steel_green", TYPE_IRON },
        { "TreadSteelGrey", "tread_steel_grey", TYPE_IRON },
        { "TreadSteelLightBlue", "tread_steel_light_blue", TYPE_IRON },
        { "TreadSteelLightGrey", "tread_steel_light_grey", TYPE_IRON },
        { "TreadSteelLime", "tread_steel_lime", TYPE_IRON },
        { "TreadSteelMagenta", "tread_steel_magenta", TYPE_IRON },
        { "TreadSteelOrange", "tread_steel_orange", TYPE_IRON },
        { "TreadSteelPink", "tread_steel_pink", TYPE_IRON },
        { "TreadSteelPurple", "tread_steel_purple", TYPE_IRON },
        { "TreadSteelRed", "tread_steel_red", TYPE_IRON },
        { "TreadSteelWhite", "tread_steel_white", TYPE_IRON },
        { "TreadSteelYellow", "tread_steel_yellow", TYPE_IRON },
    };

    public static final String[][] SOFT = new String[][] {
        { "DenseMeshWire", "dense_mesh_wire", TYPE_IRON },
        { "MeshWire", "mesh_wire", TYPE_IRON },
        { "SquareIronMesh", "square_iron_mesh", TYPE_IRON },
        { "TarpBlue", "tarp_blue", TYPE_CLOTH },
        { "TarpGreen", "tarp_green", TYPE_CLOTH },
        { "TarpMagenta", "tarp_magenta", TYPE_CLOTH },
        { "TarpOrange", "tarp_orange", TYPE_CLOTH },
        { "TarpPurple", "tarp_purple", TYPE_CLOTH },
        { "TarpRed", "tarp_red", TYPE_CLOTH },
        { "TarpYellow", "tarp_yellow", TYPE_CLOTH },
        { "Tatami", "tatami", TYPE_WOOD }
    };
}
