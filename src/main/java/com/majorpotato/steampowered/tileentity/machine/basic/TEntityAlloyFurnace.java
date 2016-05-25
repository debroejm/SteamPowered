package com.majorpotato.steampowered.tileentity.machine.basic;


import com.majorpotato.steampowered.block.machine.basic.MachineChimneyStack;
import com.majorpotato.steampowered.tileentity.TEntityMultiblock;
import com.majorpotato.steampowered.multiblock.MBAlloyFurnace;
import com.majorpotato.steampowered.multiblock.MultiblockData;
import com.majorpotato.steampowered.multiblock.MultiblockType;
import net.minecraft.tileentity.TileEntity;

public class TEntityAlloyFurnace extends TEntityMultiblock {

    private boolean fedHeat = false;
    private int chimneyStacks = 0;

    public TEntityAlloyFurnace()
    {
        super();
    }

    public boolean isBurning()
    {
        return ((MBAlloyFurnace)multiblock).isBurning();
    }

    @Override
    public MultiblockType getMultiblockType() { return MultiblockType.ALLOY_FURNACE; }

    @Override
    public MultiblockData createNewMultiblock(int patternID) {
        return new MBAlloyFurnace(worldObj, xCoord, yCoord, zCoord, patternID);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if(hasMultiblock() && fedHeat && chimneyStacks > 0) {
            worldObj.spawnParticle("smoke", (xCoord + Math.random()), (yCoord + 1.0 + (float)chimneyStacks), (zCoord + Math.random()), 0, 0.05, 0);
        }
    }

    public boolean isFedHeat() { return fedHeat; }
    public int getChimneyStacks() { return chimneyStacks; }

    @Override
    protected void clock5Tick() {
        super.clock5Tick();
        fedHeat = false;
        TileEntity fireboxEnt = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
        if( fireboxEnt instanceof TEntityFirebox ) fedHeat = ((TEntityFirebox)fireboxEnt).isBurning();
        if( worldObj.getBlock(xCoord, yCoord+1, zCoord) instanceof MachineChimneyStack) {
            chimneyStacks = 1;
            if( worldObj.getBlock(xCoord, yCoord+2, zCoord) instanceof MachineChimneyStack) {
                chimneyStacks = 2;
                if( worldObj.getBlock(xCoord, yCoord+3, zCoord) instanceof MachineChimneyStack) {
                    chimneyStacks = 3;
                }
            }
        } else chimneyStacks = 0;
    }
}
