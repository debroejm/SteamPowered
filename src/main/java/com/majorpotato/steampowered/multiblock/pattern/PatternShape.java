package com.majorpotato.steampowered.multiblock.pattern;


import com.majorpotato.steampowered.multiblock.IMultiblockTileEntity;
import com.majorpotato.steampowered.multiblock.MultiblockData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class PatternShape {


    public abstract boolean validate(World worldObj, int xPos, int yPos, int zPos);
    public abstract void setAll(World worldObj, int xPos, int yPos, int zPos, MultiblockData data);

    protected final boolean checkBlockAt(World worldObj, int xPos, int yPos, int zPos, Class<? extends IMultiblockTileEntity> blockType) {
        TileEntity ent = worldObj.getTileEntity(xPos, yPos, zPos);
        if(blockType.isInstance(ent)) {
            if(((IMultiblockTileEntity)ent).hasMultiblock()) return false;
        } else return false;
        return true;
    }
}
