package com.majorpotato.steampowered.util.connect;


import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utility Interface for TileEntities that are 'connectable', or can connect to each other.
 * IE pipes and some multiblock structures.
 * @author MajorPotato
 */
public interface IConnectable {

    /**
     * Gets the (usually) internally used ConnectLogic Object.
     * This method is mostly used for the unlisted ConnectProperty
     * @return ConnectLogic Object.
     */
    @Nonnull ConnectLogic getConnectLogic();

    /**
     * Checks whether a certain IConnectable can connect to this IConnectable on the specified side.
     * @param side to connect to.
     * @param other IConnectable instance that is connecting.
     * @return true if a connection is allowed, false otherwise.
     */
    boolean canConnect(EnumFacing side, IConnectable other);

    /**
     * Checks whether a certain side is currently connected to an IConnectable.
     * Usually simply checks if connection != null.
     * @param side to check.
     * @return true if side is currently connected, false otherwise.
     */
    boolean isConnected(EnumFacing side);

    /**
     * @param side to check.
     * @return connected IConnectable instance, null if not connected.
     */
    @Nullable IConnectable getConnected(EnumFacing side);

    /**
     * Sets the specified side connection to a specified IConnectable instance (Does not check if allowed)
     * @param side to connect to.
     * @param connectable to connect with.
     */
    void setConnected(EnumFacing side, @Nullable IConnectable connectable);
}
