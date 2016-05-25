package com.majorpotato.steampowered.multiblock.pattern;


import com.majorpotato.steampowered.multiblock.IMultiblockTileEntity;
import com.majorpotato.steampowered.multiblock.MultiblockData;
import com.majorpotato.steampowered.util.math.Vector3i;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiblockPattern {

    protected Block[][][] shape;
    protected List<Block> blockTypes = new ArrayList<Block>();

    public MultiblockPattern() {
        setShape();
    }

    protected abstract void setShape();

    /**
     * Determines whether a block even exists in this pattern.
     * @param worldObj block exists in
     * @param xCoord of block
     * @param yCoord of block
     * @param zCoord of block
     * @return true if the specified block is in this pattern, false otherwise.
     */
    public boolean isFocusValid(World worldObj, int xCoord, int yCoord, int zCoord) {
        return isFocusValid(worldObj.getBlock(xCoord, yCoord, zCoord));
    }
    /**
     * Determines whether a block even exists in this pattern.
     * @param focus block
     * @return true if the specified block is in this pattern, false otherwise.
     */
    public boolean isFocusValid(Block focus) {
        if(focus == null) return false;
        for(Block block : blockTypes) if(block.equals(focus)) return true;
        return false;
    }

    /**
     * Checks this pattern to see if it matches a selection focused around a single block.
     * @param worldObj to check with
     * @param xCoord of focus block
     * @param yCoord of focus block
     * @param zCoord of focus block
     * @return a Vector3i containing the offset of the pattern matching the focus. If no valid pattern offsets can be found, returns null.
     */
    public Vector3i checkPattern(World worldObj, int xCoord, int yCoord, int zCoord) {
        if(shape == null) return null; // Pattern not set

        for(int xCheck = 0; xCheck < shape.length; xCheck++) {
            for(int yCheck = 0; yCheck < shape[xCheck].length; yCheck++) {
                for(int zCheck = 0; zCheck < shape[xCheck][yCheck].length; zCheck++) {

                    boolean valid = true;
                    for(int x = 0; x < shape.length; x++) {
                        for(int y = 0; y < shape[x].length; y++) {
                            for(int z = 0; z < shape[x][y].length; z++) {
                                if(shape[x][y][z] == null) continue; // Don't care about this location

                                if(!shape[x][y][z].equals(worldObj.getBlock(xCoord-xCheck+x, yCoord-yCheck+y, zCoord-zCheck+z))) { valid = false; break; }
                            }
                            if(!valid) break;
                        }
                        if(!valid) break;
                    }

                    if(valid) return new Vector3i(xCheck, yCheck, zCheck);
                }
            }
        }

        return null;
    }

    /**
     * Performs final operations on the blocks of a pattern, such as setting references to the multiblock data object.
     * @param worldObj multiblock exists in
     * @param xCoord of focus block
     * @param yCoord of focus block
     * @param zCoord of focus block
     * @param offset of pattern from focus (Vector3i); does nothing if this is null
     * @param mbData - multiblock data object
     */
    public final void setPattern(World worldObj, int xCoord, int yCoord, int zCoord, Vector3i offset, MultiblockData mbData) {
        if(offset == null) return;
        setPattern(worldObj, xCoord, yCoord, zCoord, offset.x, offset.y, offset.z, mbData);
    }
    /**
     * Performs final operations on the blocks of a pattern, such as setting references to the multiblock data object.
     * @param worldObj multiblock exists in
     * @param xCoord of focus block
     * @param yCoord of focus block
     * @param zCoord of focus block
     * @param xOffset of pattern from focus
     * @param yOffset of pattern from focus
     * @param zOffset of pattern from focus
     * @param mbData - multiblock data object
     */
    public final void setPattern(World worldObj, int xCoord, int yCoord, int zCoord, int xOffset, int yOffset, int zOffset, MultiblockData mbData) {

        //mbData.setPattern(this);

        for(int x = 0; x < shape.length; x++) {
            for(int y = 0; y < shape[x].length; y++) {
                for(int z = 0; z < shape[x][y].length; z++) {
                    TileEntity tent = worldObj.getTileEntity(xCoord-xOffset+x, yCoord-yOffset+y, zCoord-zOffset+z);
                    if(tent instanceof IMultiblockTileEntity) ((IMultiblockTileEntity)tent).setMultiblock(mbData);
                }
            }
        }

    }
}
