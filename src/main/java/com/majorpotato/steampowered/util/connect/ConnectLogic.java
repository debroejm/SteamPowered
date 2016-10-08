package com.majorpotato.steampowered.util.connect;

import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

/**
 * Simple Utility-Logic Container Class for Block/TileEntity connections.
 * Generally used with TileEntities implementing IConnectable.
 * @author MajorPotato
 */
public class ConnectLogic {

    /**
     * Gets the connected instance of IConnectable for a certain side, if it exists.
     * @param facing - side to check for connection.
     * @return IConnectable attached to side if one is connected, null otherwise.
     */
    public @Nullable IConnectable get(EnumFacing facing) { return connected[facing.ordinal()]; }

    /**
     * Sets the connected instance of IConnectable for a certain side.
     * @param facing - side to set connection for.
     * @param connectable - IConnectable instance to set with.
     */
    public void set(EnumFacing facing, @Nullable IConnectable connectable) {
        connected[facing.ordinal()] = connectable;
    }

    protected IConnectable[] connected = new IConnectable[EnumFacing.values().length];

    @Override
    public String toString() {
        String result = "";
        for(EnumFacing facing : EnumFacing.values())
            result += facing + "=" + (connected[facing.ordinal()] != null) + ", ";
        return result;
    }

}
