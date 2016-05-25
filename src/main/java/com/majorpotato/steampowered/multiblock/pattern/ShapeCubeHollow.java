package com.majorpotato.steampowered.multiblock.pattern;


import com.majorpotato.steampowered.multiblock.IMultiblockTileEntity;
import com.majorpotato.steampowered.multiblock.MultiblockData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ShapeCubeHollow extends PatternShape {
    private Class<? extends IMultiblockTileEntity> blockType = null;

    private int xSize, ySize, zSize, xCenter, yCenter, zCenter;
    private boolean blackList;

    public ShapeCubeHollow(Class<? extends IMultiblockTileEntity> blockType, int xSize, int ySize, int zSize, int xCenter, int yCenter, int zCenter) { this(blockType, xSize, ySize, zSize, xCenter, yCenter, zCenter, false); }
    public ShapeCubeHollow(Class<? extends IMultiblockTileEntity> blockType, int xSize, int ySize, int zSize, int xCenter, int yCenter, int zCenter, boolean blackList) {
        this.blockType=blockType;
        this.xSize = xSize; this.xCenter = xCenter;
        this.ySize = ySize; this.yCenter = yCenter;
        this.zSize = zSize; this.zCenter = zCenter;
        this.blackList = blackList;
    }

    public boolean validate(World worldObj, int xPos, int yPos, int zPos) {
        for(int x = 0; x < xSize; x++) {
            for(int y = 0; y < ySize; y++) {
                for(int z = 0; z < zSize; z++) {
                    if(x == 0 || y == 0 || z == 0 || x == xSize-1 || y == ySize-1 || z == zSize-1) {
                        if(checkBlockAt(worldObj, xPos - xCenter + x, yPos - yCenter + y, zPos - zCenter + z, blockType)) {
                            if(blackList) return false;
                        } else {
                            if(!blackList) return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void setAll(World worldObj, int xPos, int yPos, int zPos, MultiblockData data) {
        if(blackList) return;
        for(int x = 0; x < xSize; x++) {
            for(int y = 0; y < ySize; y++) {
                for(int z = 0; z < zSize; z++) {
                    if(x == 0 || y == 0 || z == 0 || x == xSize-1 || y == ySize-1 || z == zSize-1) {
                        TileEntity ent = worldObj.getTileEntity(xPos - xCenter + x, yPos - yCenter + y, zPos - zCenter + z);
                        if(ent instanceof IMultiblockTileEntity) {
                            ((IMultiblockTileEntity)ent).setMultiblock(data);
                            data.addMultiblockPart((IMultiblockTileEntity)ent);
                        }
                    }
                }
            }
        }
    }
}
