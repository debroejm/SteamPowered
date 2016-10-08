package com.majorpotato.steampowered.util;


import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Utility Interface for retrieving debug information to display on screen, if debug is enabled.
 * Used for Blocks and Entities.
 * @author MajorPotato
 */
public interface IDebuggable {

    /**
     * Gets a set of text debug information to display.
     * Different lines are represented as different Strings in the array.
     * @param world - World instance that the implementer exists in.
     * @param pos - Position of the implementer in previously mentioned World.
     * @return String array of debug information.
     */
    String[] getDebugInformation(World world, BlockPos pos);

}
