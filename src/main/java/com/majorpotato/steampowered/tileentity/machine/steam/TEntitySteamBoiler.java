package com.majorpotato.steampowered.tileentity.machine.steam;


import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityFirebox;
import com.majorpotato.steampowered.util.TieredMaterial;
import com.majorpotato.steampowered.util.machine.MachineType;
import com.majorpotato.steampowered.multiblock.MBSteamBoiler;
import com.majorpotato.steampowered.multiblock.MultiblockData;
import com.majorpotato.steampowered.multiblock.MultiblockType;
import net.minecraft.tileentity.TileEntity;

public class TEntitySteamBoiler extends TEntitySteamMultiblock {

    private boolean[] sides = {false, false, false, false, false, false};
    private boolean fedHeat = false;
    private boolean hasFirebox = false;

    public TEntitySteamBoiler()
    {
        super();
        machineType = MachineType.PRODUCER;
        material = TieredMaterial.Steel;
    }

    public int getHeat() {
        if(hasMultiblock())
            return ((MBSteamBoiler)multiblock).getHeat();
        else return 0;
    }
    public int getSteamPressure() {
        if(hasMultiblock())
            return ((MBSteamBoiler)multiblock).getSteamPressure();
        else return 0;
    }
    public boolean isBurning()
    {
        if(hasMultiblock())
            return ((MBSteamBoiler)multiblock).isBurning();
        else return false;
    }

    @Override
    public MultiblockType getMultiblockType() { return MultiblockType.STEAM_BOILER; }

    @Override
    public MultiblockData createNewMultiblock(int patternID) {
        return new MBSteamBoiler(worldObj, xCoord, yCoord, zCoord, patternID);
    }

    public boolean isFedHeat() { return fedHeat; }
    public boolean hasFirebox() { return hasFirebox; }

    @Override
    public boolean canSideConnect(Direction side)
    {
        if(side == Direction.UP || side == Direction.DOWN)
            return false;
        else
            return true;
    }

    public boolean[] getSideData() { return sides; }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        // Reset
        sides[Direction.NORTH.ID()] = false;
        sides[Direction.SOUTH.ID()] = false;
        sides[Direction.WEST.ID()] = false;
        sides[Direction.EAST.ID()] = false;

        // EAST
        if (this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) instanceof TEntitySteamBoiler) {
            sides[Direction.EAST.ID()] = true;
            /*
            // NORTH EAST
            if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) instanceof TEntitySteamBoiler && this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord - 1) instanceof TEntitySteamBoiler) {
                sides[Direction.EAST.ID()] = true;
                sides[Direction.NORTH.ID()] = true;
            }
            // SOUTH EAST
            if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) instanceof TEntitySteamBoiler && this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord + 1) instanceof TEntitySteamBoiler) {
                sides[Direction.EAST.ID()] = true;
                sides[Direction.SOUTH.ID()] = true;
            }
            */
        }
        // WEST
        if (this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) instanceof TEntitySteamBoiler) {
            sides[Direction.WEST.ID()] = true;
            /*
            // NORTH WEST
            if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) instanceof TEntitySteamBoiler && this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord - 1) instanceof TEntitySteamBoiler) {
                sides[Direction.WEST.ID()] = true;
                sides[Direction.NORTH.ID()] = true;
            }
            // SOUTH WEST
            if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) instanceof TEntitySteamBoiler && this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord + 1) instanceof TEntitySteamBoiler) {
                sides[Direction.WEST.ID()] = true;
                sides[Direction.SOUTH.ID()] = true;
            }
            */
        }

        // NORTH
        if (this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) instanceof TEntitySteamBoiler)
            sides[Direction.NORTH.ID()] = true;

        // SOUTH
        if (this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) instanceof TEntitySteamBoiler)
            sides[Direction.SOUTH.ID()] = true;

        // TOP
        if (this.worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) instanceof TEntitySteamBoiler)
            sides[Direction.UP.ID()] = true;
        else
            sides[Direction.UP.ID()] = false;

        // BOTTOM
        if (this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof TEntitySteamBoiler)
            sides[Direction.DOWN.ID()] = true;
        else
            sides[Direction.DOWN.ID()] = false;

        if(hasMultiblock()) {
            int steamPressureOut = ((MBSteamBoiler) multiblock).getSteamPressure();
            if (steamPressureOut > 0) steam.setSteamPressure(steamPressureOut);
        }
    }

    @Override
    protected void clock5Tick() {
        super.clock5Tick();
        hasFirebox = false; fedHeat = false;
        TileEntity fireboxEnt = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
        if( fireboxEnt instanceof TEntityFirebox ) {
            hasFirebox = true;
            fedHeat = ((TEntityFirebox)fireboxEnt).isBurning();
        }
    }

    @Override
    protected void clock10Tick() {
        super.clock10Tick();
        if(worldObj.isAirBlock(xCoord, yCoord+1, zCoord) && isBurning()) {
            worldObj.spawnParticle("cloud", (xCoord + Math.random()), (yCoord + 1.0), (zCoord + Math.random()), 0.1-Math.random()*0.2, Math.random()*0.3, 0.1-Math.random()*0.2);
        }
    }
}
