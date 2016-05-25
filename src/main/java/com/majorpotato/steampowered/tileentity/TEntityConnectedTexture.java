package com.majorpotato.steampowered.tileentity;


import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class TEntityConnectedTexture extends TileEntity {
    boolean[][][] neighbors = new boolean[3][3][3];

    public boolean[][][] getNeighbors() { return neighbors; }

    public void checkNeighbors() {
        Block thisBlock = worldObj.getBlock(xCoord, yCoord, zCoord);
        boolean updated = false;
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                for(int z = 0; z < 3; z++) {
                    boolean temp = false;
                    if(worldObj.getBlock(xCoord-1+x, yCoord-1+y, zCoord-1+z).equals(thisBlock)) {
                        temp = true;
                    }
                    if(temp != neighbors[x][y][z]) updated = true;
                    neighbors[x][y][z] = temp;
                }
            }
        }
        if(updated) {
            for(int x = 0; x < 3; x++) {
                for(int y = 0; y < 3; y++) {
                    for(int z = 0; z < 3; z++) {
                        if(x == 1 && y == 1 && z == 1) continue;
                        TileEntity ent = worldObj.getTileEntity(xCoord-1+x,yCoord-1+y,zCoord-1+z);
                        if(ent instanceof TEntityConnectedTexture) {
                            ((TEntityConnectedTexture)ent).checkNeighbors();
                        }
                    }
                }
            }
        }
    }
}
