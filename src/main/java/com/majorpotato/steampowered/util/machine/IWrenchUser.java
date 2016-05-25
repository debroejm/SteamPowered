package com.majorpotato.steampowered.util.machine;


import com.majorpotato.steampowered.util.Direction;

public interface IWrenchUser {
    public abstract boolean rotateBlock(Direction side);
    public abstract boolean wrenchBlock();
    public abstract Direction getFacing();
    public abstract String getCustomWrenchMessage();
}
