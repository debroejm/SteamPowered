package com.majorpotato.steampowered.tileentity.machine.mechanical;

import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.tileentity.TEntitySP;
import com.majorpotato.steampowered.util.machine.MachineType;
import com.majorpotato.steampowered.util.mechanical.IMechanicalUser;
import com.majorpotato.steampowered.util.mechanical.MechanicalPacket;
import net.minecraft.tileentity.TileEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public abstract class TEntityMechanical extends TEntitySP implements IMechanicalUser {
    protected MechanicalPacket lastPacket;
    private ArrayList<MechanicalPacket> packetsLastTick = new ArrayList<MechanicalPacket>();
    protected int forceAmount = 0;
    protected int rotationAmount = 0;
    protected int ROTATION_MAX = 10000;

    protected IMechanicalUser[] connectedSides = new IMechanicalUser[6];

    public TEntityMechanical()
    {
        super();
    }

    @Override
    public int getCurrentForce() { return forceAmount; }

    @Override
    public double getRotationAnglePercent()
    {
        return ((double)rotationAmount/(double)ROTATION_MAX);
    }

    @Override
    public MechanicalPacket getLastPacket() { return lastPacket; }

    @Override
    public boolean reversesForce(Direction in, Direction out) { return false; }

    @Override
    public void updateEntity() {
        super.updateEntity();

        // EAST
        TileEntity tEnt = this.worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
        if(canSideConnect(Direction.EAST) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.WEST))
            connectedSides[Direction.EAST.ID()] = (IMechanicalUser)tEnt;
        else connectedSides[Direction.EAST.ID()] = null;

        // WEST
        tEnt = this.worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
        if(canSideConnect(Direction.WEST) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.EAST))
            connectedSides[Direction.WEST.ID()] = (IMechanicalUser)tEnt;
        else connectedSides[Direction.WEST.ID()] = null;

        // NORTH
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
        if(canSideConnect(Direction.NORTH) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.SOUTH))
            connectedSides[Direction.NORTH.ID()] = (IMechanicalUser)tEnt;
        else connectedSides[Direction.NORTH.ID()] = null;

        // SOUTH
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
        if(canSideConnect(Direction.SOUTH) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.NORTH))
            connectedSides[Direction.SOUTH.ID()] = (IMechanicalUser)tEnt;
        else connectedSides[Direction.SOUTH.ID()] = null;

        // TOP
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
        if(canSideConnect(Direction.UP) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.DOWN))
            connectedSides[Direction.UP.ID()] = (IMechanicalUser)tEnt;
        else connectedSides[Direction.UP.ID()] = null;

        // BOTTOM
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
        if(canSideConnect(Direction.DOWN) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.UP))
            connectedSides[Direction.DOWN.ID()] = (IMechanicalUser)tEnt;
        else connectedSides[Direction.DOWN.ID()] = null;

        if(!worldObj.isRemote) {

            //if(forceTickAmount < 0) System.out.println(forceTickAmount + " : " + forceAmount);

            int totalForceLastTick = 0;
            for(MechanicalPacket packet : packetsLastTick)
                totalForceLastTick += packet.getForce();

            if(Math.abs(totalForceLastTick) >= getMaxForce())
            {
                if(totalForceLastTick > 0) totalForceLastTick = getMaxForce();
                else totalForceLastTick = getMaxForce()*-1;
            }

            if(forceAmount != totalForceLastTick) {
                forceAmount = totalForceLastTick;
                sendUpdateToClient();
            }

            if (Math.abs(forceAmount) >= getBreakingForce()) {
                //if(!worldObj.isRemote)
                //    worldObj.createExplosion(null, xCoord, yCoord, zCoord, 0.5F, true);
            }

            /*
            if (Math.abs(forceAmount) > getMaxForce()) {
                if (forceTickAmount == 0) {
                    if (forceAmount > 0) forceAmount--;
                    else forceAmount++;
                } else {
                    // Overloaded Animation HERE
                }
            }*/

            packetsLastTick.clear();

        }

        rotationAmount += getCurrentForce();
        if(rotationAmount > ROTATION_MAX) rotationAmount -= ROTATION_MAX;
        if(rotationAmount < 0) rotationAmount += ROTATION_MAX;
    }

    @Override
    public void giveForce(MechanicalPacket packet, Direction sideFrom, IMechanicalUser source)
    {
        if(worldObj.isRemote) return;
        if(packet.equals(lastPacket)) {
            if(packet.getForce()*-1 == lastPacket.getForce()) {
                packet.nullify();
                lastPacket.nullify();
            }
            return;
        }

        packetsLastTick.add(packet);
        lastPacket = packet;

        MechanicalPacket sendPacket = packet;

        if(!(getType() == MachineType.TRANSPORT || getType() == MachineType.SUPPORT) && sideFrom != Direction.UNKNOWN) return;

        if(!(sideFrom == Direction.EAST)) {
            if(connectedSides[Direction.EAST.ID()] != null && connectedSides[Direction.EAST.ID()].getType() != MachineType.PRODUCER && !(getType() == MachineType.SUPPORT && connectedSides[Direction.EAST.ID()].getType() == MachineType.SUPPORT)) {
                if (reversesForce(sideFrom, Direction.EAST))
                    connectedSides[Direction.EAST.ID()].giveForce(sendPacket.reverse(), Direction.WEST, this);
                else connectedSides[Direction.EAST.ID()].giveForce(sendPacket, Direction.WEST, this);
            }
        }

        if(!(sideFrom == Direction.WEST)) {
            if(connectedSides[Direction.WEST.ID()] != null && connectedSides[Direction.WEST.ID()].getType() != MachineType.PRODUCER && !(getType() == MachineType.SUPPORT && connectedSides[Direction.WEST.ID()].getType() == MachineType.SUPPORT)) {
                if(reversesForce(sideFrom, Direction.WEST))
                    connectedSides[Direction.WEST.ID()].giveForce(sendPacket.reverse(), Direction.EAST, this);
                else connectedSides[Direction.WEST.ID()].giveForce(sendPacket, Direction.EAST, this);
            }
        }

        if(!(sideFrom == Direction.SOUTH)) {
            if(connectedSides[Direction.SOUTH.ID()] != null && connectedSides[Direction.SOUTH.ID()].getType() != MachineType.PRODUCER && !(getType() == MachineType.SUPPORT && connectedSides[Direction.SOUTH.ID()].getType() == MachineType.SUPPORT)) {
                if(reversesForce(sideFrom, Direction.SOUTH))
                    connectedSides[Direction.SOUTH.ID()].giveForce(sendPacket.reverse(), Direction.NORTH, this);
                else connectedSides[Direction.SOUTH.ID()].giveForce(sendPacket, Direction.NORTH, this);
            }
        }

        if(!(sideFrom == Direction.NORTH)) {
            if(connectedSides[Direction.NORTH.ID()] != null && connectedSides[Direction.NORTH.ID()].getType() != MachineType.PRODUCER && !(getType() == MachineType.SUPPORT && connectedSides[Direction.NORTH.ID()].getType() == MachineType.SUPPORT)) {
                if(reversesForce(sideFrom, Direction.NORTH))
                    connectedSides[Direction.NORTH.ID()].giveForce(sendPacket.reverse(), Direction.SOUTH, this);
                else connectedSides[Direction.NORTH.ID()].giveForce(sendPacket, Direction.SOUTH, this);
            }
        }

        if(!(sideFrom == Direction.UP)) {
            if(connectedSides[Direction.UP.ID()] != null && connectedSides[Direction.UP.ID()].getType() != MachineType.PRODUCER && !(getType() == MachineType.SUPPORT && connectedSides[Direction.UP.ID()].getType() == MachineType.SUPPORT)) {
                if(reversesForce(sideFrom, Direction.UP))
                    connectedSides[Direction.UP.ID()].giveForce(sendPacket.reverse(), Direction.DOWN, this);
                else connectedSides[Direction.UP.ID()].giveForce(sendPacket, Direction.DOWN, this);
            }
        }

        if(!(sideFrom == Direction.DOWN)) {
            if(connectedSides[Direction.DOWN.ID()] != null && connectedSides[Direction.DOWN.ID()].getType() != MachineType.PRODUCER && !(getType() == MachineType.SUPPORT && connectedSides[Direction.DOWN.ID()].getType() == MachineType.SUPPORT)) {
                if(reversesForce(sideFrom, Direction.DOWN))
                    connectedSides[Direction.DOWN.ID()].giveForce(sendPacket.reverse(), Direction.UP, this);
                else connectedSides[Direction.DOWN.ID()].giveForce(sendPacket, Direction.UP, this);
            }
        }
    }

    @Override
    public boolean isSideConnected(Direction side)
    {
        if(connectedSides[side.ID()] != null) return true;
        else return false;
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeInt(forceAmount);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        this.forceAmount =data.readInt();
    }
}
