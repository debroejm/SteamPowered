package com.majorpotato.steampowered.tileentity.machine.mechanical;


import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.machine.MachineType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TEntityDoorBody extends TEntityMechanical {
    private TEntityDoorBody child = null;

    private int extendPercent = 1;
    private boolean extending = true;

    private Direction facing = Direction.UNKNOWN;
    private int generation = 6;

    public TEntityDoorBody() {
        super();
        sendUpdates = true;
    }

    @Override
    public MachineType getType() { return MachineType.NULL; }

    @Override
    public boolean canSideConnect(Direction side) { return false; }

    @Override
    public int getMaxForce() { return -1; }

    @Override
    public int getBreakingForce() { return -1; }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        TileEntity tent = worldObj.getTileEntity(xCoord + facing.opposite().X(), yCoord + facing.opposite().Y(), zCoord + facing.opposite().Z());
        if(!(tent instanceof TEntityDoorBody || tent instanceof TEntityDoorFrame) || facing == Direction.UNKNOWN) {
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
            return;
        }
        else if(tent instanceof TEntityDoorBody) {
            if(((TEntityDoorBody)tent).getFacing() != facing)
                worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        }
        else if(tent instanceof TEntityDoorFrame) {
            if(((TEntityDoorFrame)tent).getFacing() != facing)
                worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        }

        if(child == null) {
            tent = worldObj.getTileEntity(xCoord + facing.X(), yCoord + facing.Y(), zCoord + facing.Z());
            if(tent instanceof TEntityDoorBody && generation < 5 && ((TEntityDoorBody)tent).facing == this.facing) {
                child = (TEntityDoorBody)tent;
                extending = true;
                extendPercent = 100;
            }
        }

        if(worldObj.isAirBlock(xCoord+facing.X(), yCoord+facing.Y(), zCoord+facing.Z())) {
            child = null;
        }
        if(extending && extendPercent < 100) extendPercent += 20;
        else if(extendPercent > 0 && !extending && child == null) extendPercent -= 20;
        if(extendPercent < 1) worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        if(extendPercent > 99)
        {
            if(generation < 5 && worldObj.isAirBlock(xCoord+facing.X(), yCoord+facing.Y(), zCoord+facing.Z()))
            {
                worldObj.setBlock(xCoord+facing.X(), yCoord+facing.Y(), zCoord+facing.Z(), ModBlocks.machineDoorBody);
                TileEntity ent = worldObj.getTileEntity(xCoord+facing.X(), yCoord+facing.Y(), zCoord+facing.Z());
                if(ent instanceof TEntityDoorBody)
                {
                    child = (TEntityDoorBody)ent;
                    child.setInfo(generation+1, facing);
                }
            }
            extendPercent = 100;
        }
    }

    public void setInfo(int generation, Direction facing)
    {
        this.generation = generation;
        this.facing = facing;
        /*
        TileEntity ent = worldObj.getTileEntity(xCoord + facing.X(), yCoord + facing.Y(), zCoord + facing.Z());
        if(ent instanceof TEntityDoorBody && this.generation < 5 && ((TEntityDoorBody)ent).getFacing() == Direction.UNKNOWN)
        {
            child = (TEntityDoorBody)ent;
            child.setInfo(this.generation+1, this.facing);
        }
        */
    }

    public void withdraw()
    {
        extending = false;
        if(child != null)
        {
            child.withdraw();
        }
    }

    public void extend()
    {
        extending = true;
        if(child != null)
        {
            child.extend();
        }
    }

    public boolean isExtended()
    {
        if(child != null)
            return child.isExtended();
        else
            return (extendPercent > 99);
    }

    public Direction getFacing() { return facing; }
    public double extendPercent() { return (double)extendPercent/100; }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeInt(facing.ID());
        data.writeInt(generation);
        data.writeInt(extendPercent);
        data.writeBoolean(extending);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        this.facing = Direction.fromID(data.readInt());
        generation = data.readInt();
        extendPercent = data.readInt();
        extending = data.readBoolean();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Facing", facing.ID());
        compound.setInteger("Generation", generation);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.facing = Direction.fromID(compound.getInteger("Facing"));
        this.generation = compound.getInteger("Generation");
    }
}
