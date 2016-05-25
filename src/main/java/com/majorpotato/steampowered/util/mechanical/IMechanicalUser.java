package com.majorpotato.steampowered.util.mechanical;


import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.machine.MachineType;

public interface IMechanicalUser {
    public final static int TYPE_PRODUCER = 1,
            TYPE_CONSUMER = TYPE_PRODUCER+1,
            TYPE_TRANSPORT = TYPE_CONSUMER+1,
            TYPE_NULL = TYPE_TRANSPORT+1,
            TYPE_SUPPORT = TYPE_NULL+1;

    public abstract MachineType getType();
    public abstract void giveForce(MechanicalPacket packet, Direction sideFrom, IMechanicalUser source);
    public abstract int getMaxForce();
    public abstract int getCurrentForce();
    public abstract int getBreakingForce();
    public abstract double getRotationAnglePercent();
    public abstract boolean canSideConnect(Direction side);
    public abstract boolean isSideConnected(Direction side);
    public abstract MechanicalPacket getLastPacket();
    public abstract boolean reversesForce(Direction in, Direction out);
}
