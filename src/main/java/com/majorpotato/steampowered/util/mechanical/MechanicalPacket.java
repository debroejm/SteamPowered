package com.majorpotato.steampowered.util.mechanical;

public class MechanicalPacket {
    private int force;
    private MechanicalPacket parent = null;

    public MechanicalPacket(int forceAmount) { force = forceAmount; }
    private MechanicalPacket(int forceAmount, MechanicalPacket parent) { force = forceAmount; this.parent = parent; }

    public int getForce() { return force; }
    public MechanicalPacket reverse() { return new MechanicalPacket(force*-1, this); }
    public MechanicalPacket limit(int amount) { if(amount > force || amount < 0) return this; else return new MechanicalPacket(amount, this); }
    public MechanicalPacket reduce(int amount) { int nint = force-amount; if(nint < 0) return null; else return new MechanicalPacket(nint, this); }
    public void nullify() { force = 0; }

    public boolean equals(Object other)
    {
        if(other instanceof MechanicalPacket)
        {
            if(super.equals(other)) return true;
            else {
                if (parent == null) return false;
                else return parent.equals(other);
            }
        }
        else return false;
    }
}
