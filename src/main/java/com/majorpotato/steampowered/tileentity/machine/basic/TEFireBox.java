package com.majorpotato.steampowered.tileentity.machine.basic;


import com.majorpotato.steampowered.tileentity.TEConnectable;
import com.majorpotato.steampowered.util.connect.IConnectable;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

public class TEFireBox extends TEConnectable implements ITickable {

    public static final int FUEL_MAX = 4000;

    protected int fuelAmount = 0;
    public int getFuelAmount() { return fuelAmount; }
    public void setFuelAmount(int amount) {
        this.fuelAmount = amount;
        markDirty();
        fuelUnbalanced = true;
    }

    protected boolean fuelUnbalanced = false;

    protected boolean burning = false;
    public boolean isBurning() { return burning; }
    public void ignite() {
        if(fuelAmount > 0 && !burning) {
            burning = true;
            markDirty();
            worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
        }
    }

    public ItemStack addFuel(ItemStack stack) {
        if(stack == null || stack.stackSize < 1) return null;
        int moreFuel = 0;
        if(stack.getItem() == Items.COAL && stack.getMetadata() == 0)
            moreFuel = 1000;
        else if(stack.getItem() == Items.COAL && stack.getMetadata() == 1)
            moreFuel = 500;
        else return stack;

        if(moreFuel > FUEL_MAX-fuelAmount) return stack;

        stack.stackSize--;
        fuelAmount += moreFuel;

        markDirty();
        worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
        fuelUnbalanced = true;

        if(stack.stackSize < 1) return null;
        else return stack;
    }

    protected void rebalanceFuel() {
        boolean changed = false;
        for(EnumFacing facing : EnumFacing.HORIZONTALS) {
            if(getConnected(facing) instanceof TEFireBox) {
                TEFireBox other = (TEFireBox)getConnected(facing);
                if(getFuelAmount() - other.getFuelAmount() > 50) {
                    int half = getFuelAmount() - other.getFuelAmount();
                    other.setFuelAmount(other.getFuelAmount()+half);
                    setFuelAmount(getFuelAmount()-half);
                    changed = true;
                }
            }
        }
        if(!changed) fuelUnbalanced = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fuelAmount = compound.getInteger("fuelAmount");
        burning = compound.getBoolean("burning");
    }

    @Override
    @MethodsReturnNonnullByDefault
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("fuelAmount", fuelAmount);
        compound.setBoolean("burning", burning);
        return super.writeToNBT(compound);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void update() {
        boolean dirty = false;

        if(isBurning()) {
            fuelAmount--;
            if(fuelAmount < 1) {
                fuelAmount = 0;
                burning = false;
            }
            for(EnumFacing facing : EnumFacing.HORIZONTALS) {
                IConnectable connectable = getConnected(facing);
                if(connectable instanceof TEFireBox)
                    ((TEFireBox)connectable).ignite();
            }
            dirty = true;
        }

        if(fuelUnbalanced) {
            rebalanceFuel();
            dirty = true;
        }

        if(dirty) {
            markDirty();
            worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
        }
    }

    /**
     * Checks whether a certain IConnectable can connect to this IConnectable on the specified side.
     *
     * @param side  to connect to.
     * @param other IConnectable instance that is connecting.
     * @return true if a connection is allowed, false otherwise.
     */
    @Override
    public boolean canConnect(EnumFacing side, IConnectable other) {
        return side.getAxis().isHorizontal() && other instanceof TEFireBox;
    }
}
