package com.majorpotato.steampowered.tileentity.machine.basic;

import com.majorpotato.steampowered.SteamPowered;
import com.majorpotato.steampowered.handler.ConfigHandler;
import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.init.ModRecipes;
import com.majorpotato.steampowered.recipe.AlloySmeltingRecipe;
import com.majorpotato.steampowered.recipe.GenericRecipe;
import com.majorpotato.steampowered.recipe.RecipeType;
import com.majorpotato.steampowered.tileentity.TileEntitySP;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class TEHotPlate extends TileEntitySP implements ITickable, ISidedInventory {

    public static final int INVENTORY_SIZE = 9;

    public static final int RECIPE_TICKS_DURATION = 50;

    protected ItemStack[] inventory = new ItemStack[INVENTORY_SIZE];
    protected int[] inputSlots;

    protected boolean inventoryDirty = false;

    public static final int MAX_HEAT = 2000;
    protected float lastHeat = 0;
    protected float currentHeat = 0;
    public int getCurrentHeat() { return (int)currentHeat; }

    protected AlloySmeltingRecipe currentRecipe = null;
    protected int currentRecipeTicks = 0;
    public AlloySmeltingRecipe getCurrentRecipe() { return currentRecipe; }

    public TEHotPlate() {
        super();
        inputSlots = new int[INVENTORY_SIZE];
        for(int i = 0; i < inputSlots.length; i++) inputSlots[i] = i;
    }

    public ItemStack setInput(ItemStack stack) {
        if(stack == null) return null;
        if(!GenericRecipe.isItemValid(RecipeType.ALLOY_SMELTING, stack)) return stack;
        boolean dirty = false;
        for(int i = 0; i < INVENTORY_SIZE; i++) {
            if(inventory[i] == null) {
                dirty = true;
                if(stack.stackSize > getInventoryStackLimit()) {
                    inventory[i] = stack.copy();
                    inventory[i].stackSize = getInventoryStackLimit();
                    stack.stackSize -= getInventoryStackLimit();
                } else {
                    inventory[i] = stack;
                    stack = null;
                    break;
                }
            } else if(stack.isItemEqual(inventory[i]) && inventory[i].stackSize < inventory[i].getMaxStackSize()) {
                dirty = true;
                if((inventory[i].getMaxStackSize()-inventory[i].stackSize) >= stack.stackSize) {
                    inventory[i].stackSize += stack.stackSize;
                    stack = null;
                    break;
                } else {
                    stack.stackSize -= (inventory[i].getMaxStackSize()-inventory[i].stackSize);
                    inventory[i].stackSize = inventory[i].getMaxStackSize();
                }
            }
        }
        if(dirty) {
            markDirty();
            inventoryDirty = true;
            worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
        }
        return stack;
    }

    public boolean hasHeatSource() {
        Block source = worldObj.getBlockState(pos.down()).getBlock();
        if(source == Blocks.LAVA || source == Blocks.FIRE) return true;
        TileEntity tent = worldObj.getTileEntity(pos.down());
        if(tent instanceof TEFireBox && ((TEFireBox)tent).isBurning()) return true;
        return false;
    }

    public float getHeatMultiplier() {
        TileEntity tent = worldObj.getTileEntity(pos.down());
        if(tent instanceof TEFireBox && ((TEFireBox)tent).isBurning()) return 1.0f;
        Block source = worldObj.getBlockState(pos.down()).getBlock();
        if(source == Blocks.LAVA) return 0.4f;
        if(source == Blocks.FIRE) return 0.25f;
        return 0.0f;
    }

    @Override
    public void update() {
        if(worldObj.isRemote) return;

        boolean dirty = false;

        if(inventoryDirty) {

            /*
            // Temporary
            for(int i = INVENTORY_INPUT_SIZE; i < INVENTORY_TOTAL_SIZE; i++) {
                if(inventory[i] != null && !worldObj.isRemote) {
                    EntityItem item = new EntityItem(worldObj);
                    item.setEntityItemStack(inventory[i]);
                    item.setPosition(pos.getX()+0.5,pos.getY()+1.0,pos.getZ()+0.5);
                    worldObj.spawnEntityInWorld(item);
                    inventory[i] = null;
                }
            }
            */

            GenericRecipe newRecipe = ModRecipes.getValidRecipe(RecipeType.ALLOY_SMELTING, this, 0, INVENTORY_SIZE);
            if(newRecipe != currentRecipe) currentRecipeTicks = 0;
            currentRecipe = (AlloySmeltingRecipe)newRecipe;
            inventoryDirty = false;
        }

        if(currentRecipe != null && currentHeat >= currentRecipe.getRequiredHeat()) {
            currentRecipeTicks++;
            if(currentRecipeTicks > RECIPE_TICKS_DURATION) {
                currentRecipeTicks = 0;
                currentRecipe.processInventory(this, 0, INVENTORY_SIZE);
                dirty = true;
            }
        }

        float heatGained = getHeatMultiplier() * 0.37f * (ConfigHandler.DEBUG_MODE ? 60 : 1);
        if(heatGained > 0.0f) {
            currentHeat += heatGained;
            if(currentHeat > MAX_HEAT) currentHeat = MAX_HEAT;
        } else if(currentHeat > 0.0f) {
            currentHeat -= 1.0f;
            if(currentHeat < 0.0f) currentHeat = 0.0f;
        }
        if(Math.abs(currentHeat-lastHeat) > 20.0f) {
            dirty = true;
            lastHeat = currentHeat;
        }
        if(dirty) {
            markDirty();
            worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    @Override
    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public int[] getSlotsForFace(EnumFacing side) {
        switch(side) {
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
            case DOWN:
                return inputSlots;
            default:
                return new int[]{};
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        switch(direction) {
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
            case DOWN:
                return index >= 0 && index < INVENTORY_SIZE && GenericRecipe.isItemValid(RecipeType.ALLOY_SMELTING, stack);
            default:
                return false;
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        switch(direction) {
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
            case DOWN:
                return index >= 0 && index < INVENTORY_SIZE;
            default:
                return false;
        }
    }

    @Override
    public int getSizeInventory() {
        return INVENTORY_SIZE;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory[index];
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = ItemStackHelper.getAndSplit(inventory, index, count);
        if (stack != null) {
            this.markDirty();
            inventoryDirty = true;
        }
        return stack;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        markDirty();
        inventoryDirty = true;
        return ItemStackHelper.getAndRemove(inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        inventory[index] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
            stack.stackSize = this.getInventoryStackLimit();
        markDirty();
        inventoryDirty = true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void openInventory(EntityPlayer player) {

    }

    @Override
    @ParametersAreNonnullByDefault
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if(index >= 0 && index < INVENTORY_SIZE) return true;
        else return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    @MethodsReturnNonnullByDefault
    public String getName() {
        return "TileEntityHotPlate";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    @MethodsReturnNonnullByDefault
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("tile."+ SteamPowered.MODID+":"+ ModBlocks.machineHotPlate.getName()+".name");
    }

    @Override
    @MethodsReturnNonnullByDefault
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        for(int i = 0; i < INVENTORY_SIZE; i++) {
            if(inventory[i] == null) continue;
            NBTTagCompound stackTag = inventory[i].writeToNBT(new NBTTagCompound());
            data.setTag("Inventory"+i,stackTag);
        }
        data.setFloat("HeatAmount", currentHeat);
        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        for(int i = 0; i < INVENTORY_SIZE; i++) {
            NBTTagCompound stackTag = data.getCompoundTag("Inventory"+i);
            inventory[i] = ItemStack.loadItemStackFromNBT(stackTag);
        }
        currentHeat = data.getFloat("HeatAmount");
        inventoryDirty = true;
    }
}
