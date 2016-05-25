package com.majorpotato.steampowered.multiblock;


import com.majorpotato.steampowered.recipes.AlloyFurnaceRecipe;
import com.majorpotato.steampowered.recipes.Recipe;
import com.majorpotato.steampowered.recipes.RecipeListing;
import com.majorpotato.steampowered.recipes.RecipeType;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityAlloyFurnace;
import com.sun.istack.internal.NotNull;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MBAlloyFurnace extends MBInventory {

    private static final int OPERATION_TIME = 100;
    private static final int OPERATION_TIME_UPDATE = OPERATION_TIME / 25;
    private static final int MAX_HEAT = 2000;
    private int MAX_BURN = 1;

    public int progressTime;

    private double xpStored = 0.0;

    private int coolTickDelay = 40;
    private int heatTickDelay = 8;

    private int burningFireboxCount = 0;
    private int allFireboxCount = 0;
    private int chimneyStackLevel = 0;

    public int progressHeatNeeded = MAX_HEAT+1;
    private int heatLevel = 0;
    private int heatMax = MAX_HEAT/2;

    private ArrayList<AlloyFurnaceRecipe> recipes = new ArrayList<AlloyFurnaceRecipe>();
    private Integer[] heatNeeded;

    private boolean inputDesynced = true;

    public MBAlloyFurnace(World world, int x, int y, int z, int patternID) {
        super(world, x, y, z, patternID);
        displayName = "Alloy Furnace";
        IDName = "alloyFurnace";
        //sendUpdates = true;
        OUTPUTS = 3;
        INPUTS = 3;
        SLOT_OUTPUT = 0;
        SLOT_INPUT = 0;
        inventory = new ItemStack[INPUTS];
    }

    @Override
    public void addMultiblockPart(IMultiblockTileEntity multiblock) {
        super.addMultiblockPart(multiblock);
        inputDesynced = true;
    }

    @Override
    public MultiblockType getMultiblockType() { return MultiblockType.ALLOY_FURNACE; }

    public int getHeat() { return heatLevel; }
    public int getProgressTime() { return progressTime; }
    public boolean isBurning()
    {
        return burningFireboxCount > 0;
    }

    @SideOnly(Side.CLIENT)
    public int getProgressScaled(int par1)
    {
        return ( (this.progressTime * par1) / OPERATION_TIME);
    }

    @SideOnly(Side.CLIENT)
    public int getHeatScaled(int par1) { return ( (this.heatLevel * par1) / MAX_HEAT); }

    @SideOnly(Side.CLIENT)
    @NotNull
    public int[] getHeatNeededScaled(int par1) {
        if(heatNeeded == null) return new int[0];
        int[] out = new int[heatNeeded.length];
        for(int i = 0; i < heatNeeded.length; i++) {
            if (heatNeeded[i] > MAX_HEAT) out[i] = -1;
            else out[i] = ((heatNeeded[i] * par1) / MAX_HEAT);
        }
        return out;
    }

    @SideOnly(Side.CLIENT)
    public int getHeatMaxScaled(int par1) {
        if(heatMax >= MAX_HEAT) return 0;
        return ( ((MAX_HEAT - heatMax) * par1) / MAX_HEAT);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack)
    {
        //if(i >= SLOT_OUTPUT && i < SLOT_OUTPUT+OUTPUTS) return false;
        return true;
    }

    private int maxHeat() { return Math.min(heatMax, MAX_HEAT); }

    @Override
    protected void clock10Tick() {
        super.clock10Tick();

        burningFireboxCount = 0;
        allFireboxCount = 0;
        chimneyStackLevel = 3;

        for(int i = 0; i < multiblocks.size(); i++) {
            if(multiblocks.get(i) instanceof TEntityAlloyFurnace) {
                allFireboxCount++;
                TEntityAlloyFurnace tAF = (TEntityAlloyFurnace)multiblocks.get(i);
                if(tAF.isFedHeat()) burningFireboxCount++;
                if(tAF.getChimneyStacks() < chimneyStackLevel) chimneyStackLevel = tAF.getChimneyStacks();
            }
        }

        heatMax = 200+100*allFireboxCount;
    }

    @Override
    protected void privateUpdate() {

        if(inputDesynced) {
            INPUTS = multiblocks.size();
            OUTPUTS = multiblocks.size();
            inventory = new ItemStack[INPUTS];
        }

        if(!this.worldObj.isRemote)
        {
            boolean update = false;

            if(burningFireboxCount > 0)
            {
                heatTickDelay -= burningFireboxCount;
                if(heatTickDelay < 1)
                {
                    if(++heatLevel > maxHeat()) heatLevel = maxHeat();
                    heatTickDelay = (8 + (heatLevel / (10+10*chimneyStackLevel)))*allFireboxCount;
                    update = true;
                }
                coolTickDelay = 40;
            }
            else
            {
                if(--coolTickDelay < 1)
                {
                    if(--heatLevel < 0) heatLevel = 0;
                    coolTickDelay = 40;
                    update = true;
                }
                heatTickDelay = (8 + (heatLevel / (10+10*chimneyStackLevel)))*allFireboxCount;
            }

            if (canBurn())
            {
                if( ++progressTime == OPERATION_TIME)
                {
                    progressTime = 0;
                    completeBurn();
                }
                if((progressTime % OPERATION_TIME_UPDATE) == 0) update = true;
            } else progressTime = 0;

            //System.out.println("Heat:"+heatLevel+" | HeatNeeded:"+progressHeatNeeded+" | Progress:"+progressTime+" | BurnTime:"+currentTime+" | Ash:"+ashLevel);

            if(xpStored > 1.0) {
                for(int i = 0; i < (int)xpStored; i++) {
                    worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, xCoord, yCoord, zCoord, 1));
                }
            }

            if(update) markDirty();
        }
    }

    private boolean canBurn()
    {
        for (AlloyFurnaceRecipe recipe : recipes) {
            if (heatLevel >= recipe.getHeatNeeded()) return true;
        }
        return false;
    }

    @Override
    public void markDirty() {
        updateCurrentRecipes();
        super.markDirty();
    }

    // Updates the internal recipe listing based on the current inventory.
    private void updateCurrentRecipes()
    {
        ItemStack[] inputs = getInputSlots();
        ArrayList<Recipe> checkedRecipes = new ArrayList<Recipe>();
        ArrayList<Item> checkedItems = new ArrayList<Item>();
        ArrayList<Integer> tHN = new ArrayList<Integer>();
        for(int i = recipes.size()-1; i >= 0; i--) {
            checkedRecipes.add(recipes.get(i));
            if(!recipes.get(i).checkInput(inputs) || !recipes.get(i).checkOutput(inputs)) recipes.remove(i);
            else if(!tHN.contains(recipes.get(i).getHeatNeeded())) tHN.add(recipes.get(i).getHeatNeeded());
        }
        for(ItemStack slot : inputs) {
            if(slot == null) continue;
            Item item = slot.getItem();
            if(checkedItems.contains(item)) continue;
            checkedItems.add(item);
            List<Recipe> possibilities = RecipeListing.getRecipesByInput(RecipeType.ALLOY_FURNACE, item);
            if(possibilities == null) continue;
            for(Recipe recipe : possibilities) {
                if(checkedRecipes.contains(recipe)) continue;
                checkedRecipes.add(recipe);
                if(recipe.checkInput(inputs) && recipe.checkOutput(inputs)) {
                    recipes.add((AlloyFurnaceRecipe)recipe);
                    if(!tHN.contains(((AlloyFurnaceRecipe)recipe).getHeatNeeded())) tHN.add(((AlloyFurnaceRecipe)recipe).getHeatNeeded());
                }
            }
        }
        heatNeeded = tHN.toArray(new Integer[tHN.size()]);
    }

    public void completeBurn()
    {
        if(this.canBurn()) {
            ItemStack[] inputSlots = getInputSlots();
            for (Recipe recipe : recipes) {
                ItemStack[] inputNeeded = recipe.getInput();
                for (ItemStack stack : inputNeeded) {
                    int amountNeeded = stack.stackSize;
                    for (ItemStack slot : inputSlots) {
                        if (slot == null) continue;
                        if (slot.isItemEqual(stack) || OreDictionary.itemMatches(slot, stack, false)) {
                            if (slot.stackSize > amountNeeded) {
                                slot.stackSize -= amountNeeded;
                                amountNeeded = 0;
                                break;
                            } else {
                                amountNeeded -= slot.stackSize;
                                slot.stackSize = 0;
                            }
                        }
                        if (amountNeeded < 1) break;
                    }
                }
                ItemStack[] outputNeeded = recipe.getOutput();
                for (ItemStack stack : outputNeeded) {
                    int amountNeeded = stack.stackSize;
                    int firstEmptySlot = -1;
                    for (int i = 0; i < inputSlots.length; i++) {
                        if (inputSlots[i] == null) {
                            if (firstEmptySlot < 0) firstEmptySlot = i;
                            continue;
                        }
                        if (inputSlots[i].isItemEqual(stack) || OreDictionary.itemMatches(inputSlots[i], stack, false)) {
                            int room = (inputSlots[i].getMaxStackSize() - inputSlots[i].stackSize);
                            if (room >= amountNeeded) {
                                inputSlots[i].stackSize += amountNeeded;
                                amountNeeded = 0;
                                break;
                            } else if (room > 0) {
                                inputSlots[i].stackSize = inputSlots[i].getMaxStackSize();
                                amountNeeded -= room;
                            }
                        }
                    }
                    if (amountNeeded > 0) {
                        if (firstEmptySlot >= 0) {
                            inputSlots[firstEmptySlot] = new ItemStack(stack.getItem(), amountNeeded);
                        }
                    }
                }
                xpStored += recipe.getXP();
            }
            markDirty();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("ProgressTime", progressTime);
        compound.setInteger("HeatLevel", heatLevel);
        compound.setInteger("MaxHeat", heatMax);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.progressTime = compound.getInteger("ProgressTime");
        this.heatLevel = compound.getInteger("HeatLevel");
        this.heatMax = compound.getInteger("MaxHeat");
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        if(!worldObj.isRemote) {
            data.writeBoolean(true);
            data.writeInt(progressTime);
            data.writeInt(heatLevel);
            data.writeInt(heatMax);
            if (heatNeeded == null) data.writeInt(0);
            else {
                data.writeInt(heatNeeded.length);
                for (Integer tHN : heatNeeded) {
                    data.writeInt(tHN);
                }
            }
        } else data.writeBoolean(false);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        if(data.readBoolean()) {
            progressTime = data.readInt();
            heatLevel = data.readInt();
            heatMax = data.readInt();
            heatNeeded = new Integer[data.readInt()];
            for (int i = 0; i < heatNeeded.length; i++) {
                heatNeeded[i] = data.readInt();
            }
        }
    }


}
