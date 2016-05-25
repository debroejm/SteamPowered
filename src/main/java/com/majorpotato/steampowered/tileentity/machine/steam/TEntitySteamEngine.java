package com.majorpotato.steampowered.tileentity.machine.steam;


import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.machine.MachineType;
import com.majorpotato.steampowered.util.mechanical.IMechanicalUser;
import com.majorpotato.steampowered.util.mechanical.MechanicalPacket;
import net.minecraft.tileentity.TileEntity;

public class TEntitySteamEngine extends TEntitySteam implements IMechanicalUser {

    private IMechanicalUser[] connectedSides_Mechanical = new IMechanicalUser[6];

    public TEntitySteamEngine() {
        super();
    }

    @Override
    public boolean reversesForce(Direction in, Direction out) { return false; }

    @Override
    public double getRotationAnglePercent() { return 0.0; }

    @Override
    public boolean isSideConnected(Direction side)
    {
        return false;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        // EAST
        TileEntity tEnt = this.worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
        if(canSideConnect(Direction.EAST) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.WEST))
            connectedSides_Mechanical[Direction.EAST.ID()] = (IMechanicalUser)tEnt;
        else connectedSides_Mechanical[Direction.EAST.ID()] = null;

        // WEST
        tEnt = this.worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
        if(canSideConnect(Direction.WEST) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.EAST))
            connectedSides_Mechanical[Direction.WEST.ID()] = (IMechanicalUser)tEnt;
        else connectedSides_Mechanical[Direction.WEST.ID()] = null;

        // NORTH
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
        if(canSideConnect(Direction.NORTH) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.SOUTH))
            connectedSides_Mechanical[Direction.NORTH.ID()] = (IMechanicalUser)tEnt;
        else connectedSides_Mechanical[Direction.NORTH.ID()] = null;

        // SOUTH
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
        if(canSideConnect(Direction.SOUTH) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.NORTH))
            connectedSides_Mechanical[Direction.SOUTH.ID()] = (IMechanicalUser)tEnt;
        else connectedSides_Mechanical[Direction.SOUTH.ID()] = null;

        // TOP
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
        if(canSideConnect(Direction.UP) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.DOWN))
            connectedSides_Mechanical[Direction.UP.ID()] = (IMechanicalUser)tEnt;
        else connectedSides_Mechanical[Direction.UP.ID()] = null;

        // BOTTOM
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
        if(canSideConnect(Direction.DOWN) && tEnt instanceof IMechanicalUser && ((IMechanicalUser)tEnt).canSideConnect(Direction.UP))
            connectedSides_Mechanical[Direction.DOWN.ID()] = (IMechanicalUser)tEnt;
        else connectedSides_Mechanical[Direction.DOWN.ID()] = null;

        MechanicalPacket sendPacket = new MechanicalPacket((int)(steam.getSteamPressure()*100));

            if(connectedSides_Mechanical[Direction.EAST.ID()] != null && connectedSides_Mechanical[Direction.EAST.ID()].getType() != MachineType.PRODUCER)
                connectedSides_Mechanical[Direction.EAST.ID()].giveForce(sendPacket, Direction.WEST, this);

            if(connectedSides_Mechanical[Direction.WEST.ID()] != null && connectedSides_Mechanical[Direction.WEST.ID()].getType() != MachineType.PRODUCER)
                connectedSides_Mechanical[Direction.WEST.ID()].giveForce(sendPacket, Direction.EAST, this);

            if(connectedSides_Mechanical[Direction.SOUTH.ID()] != null && connectedSides_Mechanical[Direction.SOUTH.ID()].getType() != MachineType.PRODUCER)
                connectedSides_Mechanical[Direction.SOUTH.ID()].giveForce(sendPacket, Direction.NORTH, this);

            if(connectedSides_Mechanical[Direction.NORTH.ID()] != null && connectedSides_Mechanical[Direction.NORTH.ID()].getType() != MachineType.PRODUCER)
                connectedSides_Mechanical[Direction.NORTH.ID()].giveForce(sendPacket, Direction.SOUTH, this);

            if(connectedSides_Mechanical[Direction.UP.ID()] != null && connectedSides_Mechanical[Direction.UP.ID()].getType() != MachineType.PRODUCER)
                connectedSides_Mechanical[Direction.UP.ID()].giveForce(sendPacket, Direction.DOWN, this);

            if(connectedSides_Mechanical[Direction.DOWN.ID()] != null && connectedSides_Mechanical[Direction.DOWN.ID()].getType() != MachineType.PRODUCER)
                connectedSides_Mechanical[Direction.DOWN.ID()].giveForce(sendPacket, Direction.UP, this);
    }

    @Override
    protected void clock20Tick() {
        super.clock20Tick();
        //if(steamAmount > 10)
        //    steamAmount -= 10;
        //else steamAmount = 0;
    }

    @Override
    public boolean canSideConnect(Direction side) { return true; }

    @Override
    public MachineType getType() { return MachineType.TRANSPORT; }

    @Override
    public int getCurrentForce() { return -1; }

    @Override
    public int getMaxForce() { return 100; }

    @Override
    public int getBreakingForce() { return 500; }

    @Override
    public void giveForce(MechanicalPacket packet, Direction sideFrom, IMechanicalUser source) { }

    @Override
    public MechanicalPacket getLastPacket() { return null; }
}
