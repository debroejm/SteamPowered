package com.majorpotato.steampowered.tileentity.machine.basic;

import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.tileentity.TEntitySP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;


public class TEntityFirebox extends TEntitySP {
    private int MAX_BURN_TIME = 1600*4;
    private int burnTime = 0;
    private int lastBurnTime = 0;
    private int loadDelay = 20;
    private int lastComparatorSignal = 0;

    private boolean burning = false;

    private boolean[] sides = {false, false, false, false, false, false};

    public TEntityFirebox() {
        super();
    }

    public boolean isBurning() { return burning; }

    public int getComparatorOutput() { return (burnTime * 15) / MAX_BURN_TIME; }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        // EAST
        if(this.worldObj.getTileEntity(xCoord+1, yCoord, zCoord) instanceof TEntityFirebox)
            sides[Direction.EAST.ID()] = true;
        else
            sides[Direction.EAST.ID()] = false;

        // WEST
        if(this.worldObj.getTileEntity(xCoord-1, yCoord, zCoord) instanceof TEntityFirebox)
            sides[Direction.WEST.ID()] = true;
        else
            sides[Direction.WEST.ID()] = false;

        // NORTH
        if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord-1) instanceof TEntityFirebox)
            sides[Direction.NORTH.ID()] = true;
        else
            sides[Direction.NORTH.ID()] = false;

        // SOUTH
        if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord+1) instanceof TEntityFirebox)
            sides[Direction.SOUTH.ID()] = true;
        else
            sides[Direction.SOUTH.ID()] = false;

        /*
        // TOP
        if(this.worldObj.getTileEntity(xCoord, yCoord+1, zCoord) instanceof TEntityFirebox)
            sides[Direction.UP.ID()] = true;
        else
            sides[Direction.UP.ID()] = false;

        // BOTTOM
        if(this.worldObj.getTileEntity(xCoord, yCoord-1, zCoord) instanceof TEntityFirebox)
            sides[Direction.DOWN.ID()] = true;
        else
            sides[Direction.DOWN.ID()] = false;
        */

        if(loadDelay > 0) loadDelay--;
        if(loadDelay < 1) sendUpdateToClient();

        int newComparatorSignal = getComparatorOutput();
        if(lastComparatorSignal != newComparatorSignal) {
            //System.out.println("New Signal: " + newComparatorSignal);
            worldObj.notifyBlockChange(xCoord, yCoord, zCoord, ModBlocks.machineFirebox);
            lastComparatorSignal = newComparatorSignal;
        }

        if(worldObj.isRemote) return;

        if(burnTime > 0) {
            if(sides[Direction.DOWN.ID()]) {
                TEntityFirebox tEnt = (TEntityFirebox)this.worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
                burnTime = tEnt.addFuel(burnTime);
                if(burning && !tEnt.isBurning()) tEnt.startBurn();
            }

            if(sides[Direction.UP.ID()]) {
                TEntityFirebox tEnt = (TEntityFirebox)this.worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
                if(burning && !tEnt.isBurning()) tEnt.startBurn();
            }

            int count = 0;
            if(sides[Direction.NORTH.ID()]) count++;
            if(sides[Direction.SOUTH.ID()]) count++;
            if(sides[Direction.EAST.ID()]) count++;
            if(sides[Direction.WEST.ID()]) count++;

            int initialBurnTime = burnTime;
            if(sides[Direction.NORTH.ID()]) {
                TEntityFirebox tEnt = (TEntityFirebox)this.worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
                int give = (initialBurnTime - tEnt.getCurrentBurnTime()) / (count*2);
                if(give > 50) burnTime = burnTime - give + tEnt.addFuel(give);
                if(burning && !tEnt.isBurning()) tEnt.startBurn();
            }
            if(sides[Direction.SOUTH.ID()]) {
                TEntityFirebox tEnt = (TEntityFirebox)this.worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
                int give = (initialBurnTime - tEnt.getCurrentBurnTime()) / (count*2);
                if(give > 50) burnTime = burnTime - give + tEnt.addFuel(give);
                if(burning && !tEnt.isBurning()) tEnt.startBurn();
            }
            if(sides[Direction.EAST.ID()]) {
                TEntityFirebox tEnt = (TEntityFirebox)this.worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
                int give = (initialBurnTime - tEnt.getCurrentBurnTime()) / (count*2);
                if(give > 50) burnTime = burnTime - give + tEnt.addFuel(give);
                if(burning && !tEnt.isBurning()) tEnt.startBurn();
            }
            if(sides[Direction.WEST.ID()]) {
                TEntityFirebox tEnt = (TEntityFirebox)this.worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
                int give = (initialBurnTime - tEnt.getCurrentBurnTime()) / (count*2);
                if(give > 50) burnTime = burnTime - give + tEnt.addFuel(give);
                if(burning && !tEnt.isBurning()) tEnt.startBurn();
            }

            if(burning && !sides[Direction.UP.ID()]) {
                burnTime--;
            }
        }

        if(burnTime < 1) {
            burning = false;
            //worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
            //worldObj.markBlockRangeForRenderUpdate(xCoord-1,yCoord-1,zCoord-1,xCoord+1,yCoord+1,zCoord+1);
        }

        List<EntityItem> entities = worldObj.getEntitiesWithinAABB(EntityItem.class,
                AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1.0, zCoord, xCoord + 1.0, yCoord + 1.2, zCoord + 1.0));
        for (EntityItem ent : entities) {
            ItemStack stack = ent.getEntityItem();
            if(stack.isItemEqual(new ItemStack(Items.coal)) && burnTime <= (MAX_BURN_TIME-1600)) {
                stack.stackSize--;
                burnTime += 1600;
                break;
            }
        }

        if(lastBurnTime != burnTime) sendUpdateToClient();
        lastBurnTime = burnTime;
    }

    public void startBurn() {
        if(burnTime > 0) {
            burning = true;
            //worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
            //worldObj.markBlockRangeForRenderUpdate(xCoord-1,yCoord-1,zCoord-1,xCoord+1,yCoord+1,zCoord+1);
        }
    }

    public boolean[] getSideData() { return sides; }
    public boolean isSideConnected(Direction direct) {
        //System.out.println(sides[0] + ", " + sides[1] + ", " + sides[2] + ", " + sides[3] + ", " + sides[4] + ", " + sides[5]);
        return sides[direct.ID()];
    }

    public int getCurrentBurnTime() { return burnTime; }
    public int addFuel( int addedBurnTime ) {
        burnTime += addedBurnTime;
        if(burnTime > MAX_BURN_TIME) {
            int excess = burnTime - MAX_BURN_TIME;
            burnTime = MAX_BURN_TIME;
            return excess;
        }
        return 0;
    }

    public double getCurrentFuelPercentage() {
        //if(burnTime > 0) System.out.println(burnTime);
        double percent = ((double)burnTime)/((double)MAX_BURN_TIME);
        //if(percent > 0.0) System.out.println(percent);
        return percent;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Fuel", burnTime);
        compound.setBoolean("Burning", burning);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.burnTime = compound.getInteger("Fuel");
        this.burning = compound.getBoolean("Burning");
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeInt(burnTime);
        data.writeBoolean(burning);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        this.burnTime = data.readInt();
        boolean tBurn = data.readBoolean();
        if(tBurn != burning) worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
        this.burning = tBurn;
    }

}
