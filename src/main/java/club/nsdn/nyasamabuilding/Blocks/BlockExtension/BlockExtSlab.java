package club.nsdn.nyasamabuilding.Blocks.BlockExtension;

/**
 * Created by drzzm32 on 2016.3.25.
 */

import club.nsdn.nyasamabuilding.Blocks.Base.CommonBlockBase;
import club.nsdn.nyasamabuilding.Loaders.BlockLoader;
import club.nsdn.nyasamabuilding.Loaders.CreativeTabLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockExtSlab extends BlockSlab {

    protected String textureLocation = "";
    private String name;
    protected void setIconLocation(String textureLocation) { this.textureLocation = textureLocation; }

    public BlockExtSlab(String blockName, CommonBlockBase BasicBlock) {
        super(false,Material.rock);
        setBlockName(blockName);
        name = blockName;
        setIconLocation(BasicBlock.getBlockTexture());
        setBlockTextureName(BasicBlock.getBlockTexture());
        setHardness(1.5F);
        setLightLevel(0F);
        setStepSound(soundTypeStone);
        setResistance(10.0F);
        setCreativeTab(CreativeTabLoader.tabNyaSamaBuilding);
        lightOpacity = 0;
    }

    @Override
    public String func_150002_b(int p_150002_1_) {
        return "";
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int facing) {
        if (this.field_150004_a) {
            return super.shouldSideBeRendered(world, x, y, z, facing);
        } else if (facing != 1 && facing != 0 && !super.shouldSideBeRendered(world, x, y, z, facing)) {
            return false;
        } else {
            int var6 = x + Facing.offsetsXForSide[Facing.oppositeSide[facing]];
            int var7 = y + Facing.offsetsYForSide[Facing.oppositeSide[facing]];
            int var8 = z + Facing.offsetsZForSide[Facing.oppositeSide[facing]];
            boolean onTop = (world.getBlockMetadata(var6, var7, var8) & 8) != 0;
            if (onTop) {
                if (facing == 0) {
                    return true;
                } else if (facing == 1 && super.shouldSideBeRendered(world, x, y, z, facing)) {
                    return true;
                } else {
                    return !func_150003_a(world.getBlock(x, y, z)) || (world.getBlockMetadata(x, y, z) & 8) == 0;
                }
            } else if (facing == 1) {
                return true;
            } else if (facing == 0 && super.shouldSideBeRendered(world, x, y, z, facing)) {
                return true;
            } else {
                return !func_150003_a(world.getBlock(x, y, z)) || (world.getBlockMetadata(x, y, z) & 8) != 0;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    protected static boolean func_150003_a(Block block) {
        return block instanceof BlockExtSlab;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z) {
        if (func_150003_a(this)) {
            return Item.getItemFromBlock(this);
        } else {
            return Item.getItemFromBlock(Blocks.stone_slab);
        }
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z), target = null;
        ItemStack stack = player.getCurrentEquippedItem();

        if (block != null && stack != null) {
            if (Item.getItemFromBlock(block) == stack.getItem()) {
                if (hitY == 0.5) {
                    String key = block.getUnlocalizedName().toLowerCase();
                    key = key.replace("tile.", "");
                    key = key.replace("slab", "");
                    for (String i : BlockLoader.blocks.keySet()) {
                        if (i.toLowerCase().equals(key)) {
                            target = BlockLoader.blocks.get(i);
                            break;
                        }
                    }
                    if (target != null) {
                        world.setBlock(x, y, z, target);
                        world.playSoundEffect(
                                (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F),
                                block.stepSound.getBreakSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F
                        );
                    }
                    return true;
                }
            }
        }

        return false;
    }

}
