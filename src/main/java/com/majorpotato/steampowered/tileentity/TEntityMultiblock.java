package com.majorpotato.steampowered.tileentity;


import com.majorpotato.steampowered.multiblock.*;
import com.majorpotato.steampowered.multiblock.pattern.Pattern;
import net.minecraft.nbt.NBTTagCompound;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class TEntityMultiblock extends TEntitySP implements IMultiblockTileEntity {
    protected MultiblockData multiblock;

    @Override
    public MultiblockData getMultiblock() { return multiblock; }

    @Override
    public void setMultiblock(MultiblockData data) {
        multiblock = data;
        sendUpdateToClient();
    }

    @Override
    public boolean hasMultiblock() { return (multiblock != null); }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(multiblock != null) multiblock.onDestroy();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(multiblock != null) multiblock.updateMultiblock();
    }

    @Override
    protected void clock20Tick() {
        super.clock20Tick();

        checkMultiblockCreation();
    }

    @Override
    public void checkMultiblockCreation() {
        if(multiblock == null) {
            Pattern ptrn = MultiblockData.checkPatternsForFit(getMultiblockType(), worldObj, xCoord, yCoord, zCoord);
            if(ptrn != null) {
                ptrn.setAll(worldObj, xCoord, yCoord, zCoord, createNewMultiblock(ptrn.getID()));
            }
        }
    }

    @Override
    public void markDirty() {
        sendUpdateToClient();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if(multiblock != null && multiblock.xCoord == xCoord && multiblock.yCoord == yCoord && multiblock.zCoord == zCoord) multiblock.writeToNBT(compound);
        else compound.setInteger("MBPatternID", -1);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        int patternID = compound.getInteger("MBPatternID");
        if(patternID > -1) {
            Pattern ptrn = MultiblockData.getPattern(getMultiblockType(), patternID);
            if(ptrn.validate(worldObj, xCoord, yCoord, zCoord)) {
                ptrn.setAll(worldObj, xCoord, yCoord, zCoord, createNewMultiblock(ptrn.getID()));
            }
        }
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeBoolean(multiblock != null);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        if(!data.readBoolean())  multiblock = null;
    }
}
