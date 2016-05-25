package com.majorpotato.steampowered.util;

public enum Direction {
    DOWN(0), UP(1), NORTH(2), SOUTH(3), WEST(4), EAST(5), UNKNOWN(-1);
    private int value;

    private Direction(int value) {
        this.value = value;
    }

    public int ID() { return value; }

    public Direction opposite() {
        if(value % 2 == 0) return fromID(value+1);
        else return fromID(value-1);
    }

    public static Direction fromID(int ID) {
        switch (ID) {
            case 0: return Direction.DOWN;
            case 1: return Direction.UP;
            case 2: return Direction.NORTH;
            case 3: return Direction.SOUTH;
            case 4: return Direction.WEST;
            case 5: return Direction.EAST;
        }
        return UNKNOWN;
    }

    public int X() {
        if( value == 4 ) return -1;
        if( value == 5 ) return 1;
        return 0;
    }

    public int Y() {
        if( value == 0 ) return -1;
        if( value == 1 ) return 1;
        return 0;
    }

    public int Z() {
        if( value == 2 ) return -1;
        if( value == 3 ) return 1;
        return 0;
    }

    public String toString()
    {
        switch (value) {
            case 0: return "Down";
            case 1: return "Up";
            case 2: return "North";
            case 3: return "South";
            case 4: return "West";
            case 5: return "East";
        }
        return "Unknown";
    }
}
