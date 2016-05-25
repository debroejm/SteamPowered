package com.majorpotato.steampowered.util.math;


public class Vector3i {

    public int x;
    public int y;
    public int z;

    public Vector3i() {
        x = 0; y = 0; z = 0;
    }

    public Vector3i(int value) {
        x = value; y = value; z = value;
    }

    public Vector3i(int x, int y, int z) {
        this.x = x; this.y = y; this.z = z;
    }
}
