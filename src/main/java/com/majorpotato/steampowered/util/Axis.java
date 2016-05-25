package com.majorpotato.steampowered.util;


public enum Axis {
    X(0), Y(1), Z(2), UNKNOWN(-1);
    int value;

    private Axis(int i) { value = i; }

    public static Axis fromDirection(Direction direct)
    {
        if(direct == Direction.UP || direct == Direction.DOWN)
            return Y;
        if(direct == Direction.NORTH || direct == Direction.SOUTH)
            return Z;
        if(direct == Direction.EAST || direct == Direction.WEST)
            return X;
        return UNKNOWN;
    }

    public static Axis fromID(int i)
    {
        switch(i) {
            case 0:
                return X;
            case 1:
                return Y;
            case 2:
                return Z;
            default:
                return UNKNOWN;
        }
    }

    public int ID() { return value; }

    public String toString()
    {
        switch(value) {
            case 0: return "X";
            case 1: return "Y";
            case 2: return "Z";
            default: return "UNKNOWN";
        }
    }
}
