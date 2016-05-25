package com.majorpotato.steampowered.multiblock.pattern;


import com.majorpotato.steampowered.multiblock.IMultiblockTileEntity;
import com.majorpotato.steampowered.multiblock.MultiblockData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ShapeCube extends PatternShape {
    private Class<? extends IMultiblockTileEntity> blockType;
    
    private int xSize, ySize, zSize, xCenter, yCenter, zCenter;

    public ShapeCube(Class<? extends IMultiblockTileEntity> blockType, int xSize, int ySize, int zSize, int xCenter, int yCenter, int zCenter) {
        this.blockType=blockType;
        this.xSize = xSize; this.xCenter = xCenter;
        this.ySize = ySize; this.yCenter = yCenter;
        this.zSize = zSize; this.zCenter = zCenter;
    }
    
    public boolean validate(World worldObj, int xPos, int yPos, int zPos) {
        for(int x = 0; x < xSize; x++) {
            for(int y = 0; y < ySize; y++) {
                for(int z = 0; z < zSize; z++) {
                    if(!checkBlockAt(worldObj, xPos - xCenter + x, yPos - yCenter + y, zPos - zCenter + z, blockType)) return false;
                }
            }
        }
        return true;
    }

    public void setAll(World worldObj, int xPos, int yPos, int zPos, MultiblockData data) {
        for(int x = 0; x < xSize; x++) {
            for(int y = 0; y < ySize; y++) {
                for(int z = 0; z < zSize; z++) {
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
