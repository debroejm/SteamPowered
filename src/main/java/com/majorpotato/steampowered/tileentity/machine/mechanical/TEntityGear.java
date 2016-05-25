package com.majorpotato.steampowered.tileentity.machine.mechanical;


import com.majorpotato.steampowered.util.Axis;
import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.machine.MachineType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TEntityGear extends TEntityMechanical {

    private ArrayList<TEntityAxle> masters = new ArrayList<TEntityAxle>();
    private int[] masterLoadX, masterLoadY, masterLoadZ;
    private boolean selfDestructing = false; // Stops infinite loops

    public TEntityGear() {
        super();
    }

    @Override
    public int getMaxForce() { return 1000; }

    @Override
    public int getBreakingForce() { return 1000; }

    @Override
    public boolean canSideConnect(Direction side)
    {
        TileEntity ent = worldObj.getTileEntity(xCoord+side.X(), yCoord+side.Y(), zCoord+side.Z());
        if(ent instanceof TEntityAxle) {
            if(((TEntityAxle)ent).getAxis() != Axis.fromDirection(side))
                return true;
            else return false;
        }
        return false;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    @Override
    protected void clockLoad() {
        super.clockLoad();

        if(masterLoadX != null) {
            for (int i = 0; i < masterLoadX.length; i++) {
                TileEntity ent = worldObj.getTileEntity(masterLoadX[i], masterLoadY[i], masterLoadZ[i]);
                System.out.println(ent.getClass().toString());
                if (ent instanceof TEntityAxle) {
                    System.out.println(((TEntityAxle)ent).isGear());
                    this.addMaster((TEntityAxle) ent);
                }
            }
        }

        if(masters.size() < 1 && !worldObj.isRemote) worldObj.setBlockToAir(xCoord, yCoord, zCoord);
    }

    public void clearFromMaster(TEntityAxle master) {
        if(masters.contains(master)) masters.remove(master);
        if(masters.size() < 1) {
            selfDestructing = true;
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //System.out.println("OnDestroy:"+slave);
        if(selfDestructing) return;
        selfDestructing = true;
            //System.out.println(masters.size());
            for(int i = 0; i < masters.size(); i++)
            {
                masters.get(i).destroyChildren();
            }
    }

    public void addMaster(TEntityAxle m) { masters.add(m); m.addChild(this); }
    public ArrayList<TEntityAxle> getMasters() { return masters; }

    @Override
    public MachineType getType()
    {
        return MachineType.TRANSPORT;
    }

    @Override
    public boolean reversesForce(Direction in, Direction out) {
        if(Axis.fromDirection(in) == Axis.fromDirection(out)) return true;
        if(in == Direction.DOWN) { if(out == Direction.EAST || out == Direction.NORTH) return true; }
        if(in == Direction.UP) { if(out == Direction.WEST || out == Direction.SOUTH) return true; }
        if(in == Direction.WEST) { if(out == Direction.UP || out == Direction.SOUTH) return true; }
        if(in == Direction.EAST) { if(out == Direction.DOWN || out == Direction.NORTH) return true; }
        if(in == Direction.NORTH) { if(out == Direction.EAST || out == Direction.DOWN) return true; }
        if(in == Direction.SOUTH) { if(out == Direction.WEST || out == Direction.UP) return true; }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("MasterCount", masters.size());
        for(int i = 0; i < masters.size(); i++) {
            compound.setInteger("MasterX"+i, masters.get(i).xCoord);
            compound.setInteger("MasterY"+i, masters.get(i).yCoord);
            compound.setInteger("MasterZ"+i, masters.get(i).zCoord);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        int count = compound.getInteger("MasterCount");

        masterLoadX = new int[count];
        masterLoadY = new int[count];
        masterLoadZ = new int[count];

        for(int i = 0; i < count; i++) {
            masterLoadX[i] = compound.getInteger("MasterX"+i);
            masterLoadY[i] = compound.getInteger("MasterY"+i);
            masterLoadZ[i] = compound.getInteger("MasterZ"+i);
        }
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
    }

}
