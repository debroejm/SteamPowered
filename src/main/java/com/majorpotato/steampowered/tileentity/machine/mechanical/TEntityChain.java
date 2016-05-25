package com.majorpotato.steampowered.tileentity.machine.mechanical;

import com.majorpotato.steampowered.util.Axis;
import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.machine.IWrenchUser;
import com.majorpotato.steampowered.util.machine.MachineType;

public class TEntityChain extends TEntityMechanical implements IWrenchUser {

    private Axis axis;

    public TEntityChain() {

    }

    @Override
    public MachineType getType() {
        return MachineType.TRANSPORT;
    }

    @Override
    public int getMaxForce() {
        return 50;
    }

    @Override
    public int getBreakingForce() {
        return 75;
    }

    @Override
    public boolean canSideConnect(Direction side) {
        return false;
    }

    @Override
    public boolean rotateBlock(Direction side)
    {
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
    public String getCustomWrenchMessage() { return ""; }
}
