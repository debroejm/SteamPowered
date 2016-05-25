package com.majorpotato.steampowered.tileentity.machine.mechanical;


import com.majorpotato.steampowered.util.Axis;
import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.machine.IWrenchUser;
import com.majorpotato.steampowered.util.machine.MachineType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TEntityAxle extends TEntityMechanical implements IWrenchUser {
    private Axis axis;
    private boolean gearFormed = false;

    private ArrayList<TEntityGear> children = new ArrayList<TEntityGear>();

    public TEntityAxle() {
        super();
        axis = Axis.UNKNOWN;
    }

    public boolean isGear() { return gearFormed; }

    public void addChild(TEntityGear gear) {
        if(!children.contains(gear)) children.add(gear);
        gearFormed = true;
    }

    public void destroyChildren()
    {
        for(int i = children.size()-1; i >= 0; i--) {
            children.get(i).clearFromMaster(this);
            children.remove(i);
        }
        children.clear();
        gearFormed = false;
        sendUpdateToClient();
        //System.out.println("" + gearFormed + isGear());
        //worldObj.notifyBlockChange(xCoord, yCoord, zCoord, ModBlocks.machineAxle);
        //worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }

    /*
    @Override
    protected void clock20Tick() {
        if(!worldObj.isRemote) {
            System.out.println("Server: "+gearFormed);
        } else {
            System.out.println("Client: "+gearFormed);
        }
    }
    */

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        destroyChildren();
    }

    @Override
    public String getCustomWrenchMessage() { return ""; }

    @Override
    public int getMaxForce() { return 50; }

    @Override
    public int getBreakingForce() { return 75; }

    @Override
    public boolean canSideConnect(Direction side)
    {
        Axis ax = Axis.fromDirection(side);
        TileEntity ent = worldObj.getTileEntity(xCoord+side.X(), yCoord+side.Y(), zCoord+side.Z());
        if(ent instanceof TEntityGear && gearFormed) {
            if(ax != axis) return true;
            else return false;
        }
        else if(ax == axis) return true;
        return false;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if(worldObj.isRemote) return;

        if(axis == Axis.UNKNOWN)
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
    }

    @Override
    protected void clockLoad() {
        super.clockLoad();

        sendUpdateToClient();
    }

    @Override
    public MachineType getType() { return MachineType.TRANSPORT; }

    @Override
    public boolean rotateBlock(Direction side)
    {
        if(isGear()) return false;
        axis = Axis.fromDirection(side);
        sendUpdateToClient();
        return true;
    }

    @Override
    public Direction getFacing()
    {
        if(axis == Axis.X) return Direction.EAST;
        if(axis == Axis.Y) return Direction.UP;
        if(axis == Axis.Z) return Direction.SOUTH;
        return Direction.UNKNOWN;
    }

    public Axis getAxis()
    {
        return axis;
    }

    @Override
    public boolean wrenchBlock()
    {
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Axis", axis.ID());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.axis = Axis.fromID(compound.getInteger("Axis"));
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeInt(axis.ID());
        data.writeBoolean(gearFormed);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        this.axis = Axis.fromID(data.readInt());
        this.gearFormed = data.readBoolean();
    }
}
