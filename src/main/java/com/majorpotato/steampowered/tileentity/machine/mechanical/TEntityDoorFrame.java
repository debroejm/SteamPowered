package com.majorpotato.steampowered.tileentity.machine.mechanical;


import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.machine.IWrenchUser;
import com.majorpotato.steampowered.util.machine.MachineType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TEntityDoorFrame extends TEntityMechanical implements IWrenchUser {

    private Direction facing = Direction.NORTH;
    private TEntityDoorBody child;

    private boolean open = true;

    public TEntityDoorFrame() {
        super();
        /*
        TileEntity ent = worldObj.getTileEntity(xCoord + facing.X(), yCoord + facing.Y(), zCoord + facing.Z());
        if(ent instanceof TEntityDoorBody)
        {
            child = (TEntityDoorBody)ent;
            child.setInfo(1, facing);
        }
        */
        sendUpdates = true;
    }

    @Override
    public MachineType getType() { return MachineType.CONSUMER; }

    @Override
    public boolean canSideConnect(Direction side)
    {
        return !(side == facing);
    }

    @Override
    public int getMaxForce() { return 20; }

    @Override
    public int getBreakingForce() { return 50; }

    @Override
    public boolean rotateBlock(Direction side)
    {
        if(child == null && side != Direction.UP && side != Direction.DOWN) {
            facing = side;
            return true;
        }
        return false;
    }

    @Override
    public Direction getFacing() { return facing; }

    @Override
    public boolean wrenchBlock()
    {
        return false;
    }

    @Override
    public String getCustomWrenchMessage() { return ""; }

    public void withdraw()
    {
        if(child != null)
            child.withdraw();
    }

    public int powerLevel(Direction side)
    {
        int POWAH = worldObj.getIndirectPowerLevelTo(xCoord, yCoord, zCoord, Direction.NORTH.ID());
        int nP = worldObj.getIndirectPowerLevelTo(xCoord, yCoord, zCoord, Direction.SOUTH.ID()); if(nP > POWAH) POWAH = nP;
        nP = worldObj.getIndirectPowerLevelTo(xCoord, yCoord, zCoord, Direction.EAST.ID()); if(nP > POWAH) POWAH = nP;
        nP = worldObj.getIndirectPowerLevelTo(xCoord, yCoord, zCoord, Direction.WEST.ID()); if(nP > POWAH) POWAH = nP;
        if(side != Direction.DOWN) {
            nP = worldObj.getIndirectPowerLevelTo(xCoord, yCoord, zCoord, Direction.UP.ID()); if(nP > POWAH) POWAH = nP;
        }
        if(side != Direction.UP) {
            nP = worldObj.getIndirectPowerLevelTo(xCoord, yCoord, zCoord, Direction.DOWN.ID()); if(nP > POWAH) POWAH = nP;
        }
        return POWAH;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if(worldObj.isAirBlock(xCoord+facing.X(), yCoord+facing.Y(), zCoord+facing.Z())) child = null;

        TileEntity above = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
        TileEntity below = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);

            if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) ||
                    (above instanceof TEntityDoorFrame && ((TEntityDoorFrame)above).getFacing() == facing && ((TEntityDoorFrame)above).isFrameAbovePowered() ) ||
                    (below instanceof TEntityDoorFrame && ((TEntityDoorFrame)below).getFacing() == facing && ((TEntityDoorFrame)below).isFrameBelowPowered() )
                    ) {
                if (open) {
                    if(child != null)
                        child.extend();
                    else {
                        if (worldObj.isAirBlock(xCoord + facing.X(), yCoord + facing.Y(), zCoord + facing.Z())) {
                            worldObj.setBlock(xCoord + facing.X(), yCoord + facing.Y(), zCoord + facing.Z(), ModBlocks.machineDoorBody);
                            TileEntity ent = worldObj.getTileEntity(xCoord + facing.X(), yCoord + facing.Y(), zCoord + facing.Z());
                            if (ent instanceof TEntityDoorBody) {
                                child = (TEntityDoorBody) ent;
                                child.setInfo(1, facing);
                            }
                        }
                    }
                    open = false;
                }
            } else if (!open) {
                withdraw();
                open = true;
            }
    }

    public boolean isFrameBelowPowered()
    {
        if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) return true;
        TileEntity below = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
        if(below instanceof TEntityDoorFrame && ((TEntityDoorFrame)below).getFacing() == facing)
        {
            return ((TEntityDoorFrame)below).isFrameBelowPowered();
        }
        return false;
    }

    public boolean isFrameAbovePowered()
    {
        if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) return true;
        TileEntity above = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
        if(above instanceof TEntityDoorFrame && ((TEntityDoorFrame)above).getFacing() == facing)
        {
            return ((TEntityDoorFrame)above).isFrameAbovePowered();
        }
        return false;
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeInt(facing.ID());
        data.writeBoolean(open);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        this.facing = Direction.fromID(data.readInt());
        this.open = data.readBoolean();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Facing", facing.ID());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.facing = Direction.fromID(compound.getInteger("Facing"));
    }
}
