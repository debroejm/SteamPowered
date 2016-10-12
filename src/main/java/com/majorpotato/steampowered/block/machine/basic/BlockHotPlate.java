package com.majorpotato.steampowered.block.machine.basic;


import com.majorpotato.steampowered.SteamPowered;
import com.majorpotato.steampowered.block.BlockTESP;
import com.majorpotato.steampowered.recipe.GenericRecipe;
import com.majorpotato.steampowered.tileentity.machine.basic.TEHotPlate;
import com.majorpotato.steampowered.util.IDebuggable;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class BlockHotPlate extends BlockTESP implements IBlockColor, IDebuggable {

    protected static final AxisAlignedBB AABB_BASE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.95D, 1.0D);

    public BlockHotPlate() {
        super(Material.ROCK);
        setUnlocalizedName("machineHotPlate");
        setHarvestLevel("pickaxe",1);
        setHardness(3.5F);
        setResistance(23.0F);
    }

    /*
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }*/

    @Override
    @ParametersAreNonnullByDefault
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BASE);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        super.onEntityCollidedWithBlock(world, pos, state, entity);
        TileEntity tent = world.getTileEntity(pos);
        if(entity instanceof EntityItem && tent instanceof TEHotPlate && !world.isRemote) {
            EntityItem item = (EntityItem)entity;
            ItemStack stack = item.getEntityItem();
            stack = ((TEHotPlate)tent).setInput(stack);
            if(stack == null) item.setDead();
            else item.setEntityItemStack(stack);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if(hitY > 0.975f && te instanceof TEHotPlate/* && hand == EnumHand.MAIN_HAND*/) {
            int index = (int)(hitX / 0.3334f) + 3 * (int)(hitZ / 0.3334f);
            TEHotPlate hotPlate = (TEHotPlate)te;
            if(index < 0 || index >= TEHotPlate.INVENTORY_SIZE) return false;
            ItemStack stack = hotPlate.getStackInSlot(index);
            if(stack == null && heldItem != null) {
                if(hotPlate.canInsertItem(index, heldItem, EnumFacing.DOWN))
                    hotPlate.setInventorySlotContents(index, heldItem);
            } else if(heldItem == null) {
                playerIn.setHeldItem(hand, stack);
                hotPlate.setInventorySlotContents(index, null);
            } else {
                boolean pop = false;
                if(heldItem.isItemEqual(stack)) {
                    if (hotPlate.canInsertItem(index, heldItem, EnumFacing.DOWN)) {
                        int diff = Math.min(stack.getMaxStackSize()-stack.stackSize, heldItem.stackSize);
                        stack.stackSize += diff;
                        heldItem.stackSize -= diff;
                        if(heldItem.stackSize < 1) playerIn.setHeldItem(hand, null);
                    } else {
                        int diff = Math.min(heldItem.getMaxStackSize()-heldItem.stackSize, stack.stackSize);
                        stack.stackSize -= diff;
                        heldItem.stackSize += diff;
                        if(stack.stackSize < 1) hotPlate.setInventorySlotContents(index, null);
                        else pop = true;
                    }
                } else pop = true;
                if(pop && !worldIn.isRemote) {
                    EntityItem entItem = new EntityItem(playerIn.worldObj, playerIn.posX, playerIn.posY, playerIn.posZ, stack);
                    hotPlate.setInventorySlotContents(index, null);
                    playerIn.worldObj.spawnEntityInWorld(entItem);
                }
            }
            return true;
        } else return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TEHotPlate();
    }

    @Override public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) { return false; }
    @Override public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    @Override public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    @Override public boolean isVisuallyOpaque() { return false; }

    @Override
    @ParametersAreNonnullByDefault
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        if(tintIndex == 1 && pos != null && worldIn != null) {
            TileEntity tent = worldIn.getTileEntity(pos);
            if(tent instanceof TEHotPlate) {
                TEHotPlate plate = (TEHotPlate)tent;
                int g = (0xFF - (int)( 0x99 * ((float)plate.getCurrentHeat() / (float)TEHotPlate.MAX_HEAT) )) << 8;
                int b = 0xFF - (int)( 0x99 * ((float)plate.getCurrentHeat() / (float)TEHotPlate.MAX_HEAT) );
                return 0xFF0000 + g + b;
            }
        }
        return 0xFFFFFF;
    }

    @Override
    public String[] getDebugInformation(World world, BlockPos pos) {
        TileEntity tent = world.getTileEntity(pos);
        if(tent instanceof TEHotPlate) {
            TEHotPlate hotplate = (TEHotPlate)tent;
            ItemStack[] inventory = new ItemStack[hotplate.getSizeInventory()];
            for(int i = 0; i < inventory.length; i++) inventory[i] = hotplate.getStackInSlot(i);
            String[] result = new String[hotplate.getSizeInventory()+3];
            result[0] = "Hot Plate Debug:";
            GenericRecipe recipe = hotplate.getCurrentRecipe();
            result[1] = "  Recipe Type: " + (recipe == null ? "None" : recipe.getName());
            result[2] = "  Heat: " + hotplate.getCurrentHeat();
            for(int i = 0; i < inventory.length; i++)
                result[3+i] = "  Slot "+i+": " + (inventory[i] == null ? "None" : (inventory[i].getDisplayName() + ": " + inventory[i].stackSize));
            return result;
        } else return null;
    }
}
