package club.nsdn.nyasamabuilding.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Tuple;

/**
 * Created by drzzm32 on 2019.2.12.
 */
public class BlockLists {

    public static final int NAME = 0;
    public static final int ID = 1;
    public static final int TYPE = 2;

    public static final String TYPE_ROCK = "rock";     // Material.ROCK + SoundType.STONE
    public static final String TYPE_IRON = "iron";     // Material.IRON + SoundType.METAL
    public static final String TYPE_SAND = "sand";     // Material.SAND + SoundType.GROUND
    public static final String TYPE_WOOD = "wood";     // Material.WOOD + SoundType.WOOD
    public static final String TYPE_GLASS = "glass";  // Material.GLASS + SoundType.GLASS
    public static final String TYPE_CLOTH = "cloth"; // Material.CARPET + SoundType.CLOTH

    public static Tuple<Material, SoundType> getExtInfo(String type) {
        switch (type) {
            case TYPE_ROCK:
                return new Tuple<>(Material.ROCK, SoundType.STONE);
            case TYPE_IRON:
                return new Tuple<>(Material.IRON, SoundType.METAL);
            case TYPE_SAND:
                return new Tuple<>(Material.SAND, SoundType.GROUND);
            case TYPE_WOOD:
                return new Tuple<>(Material.WOOD, SoundType.WOOD);
            case TYPE_GLASS:
                return new Tuple<>(Material.GLASS, SoundType.GLASS);
            case TYPE_CLOTH:
                return new Tuple<>(Material.CARPET, SoundType.CLOTH);
        }
        return new Tuple<>(Material.GOURD, SoundType.GROUND);
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
