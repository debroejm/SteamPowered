package com.majorpotato.steampowered.multiblock;


public interface IMultiblockTileEntity {

    public void setMultiblock(MultiblockData data);
    public MultiblockData getMultiblock();
    public boolean hasMultiblock();

    public MultiblockType getMultiblockType();

    public MultiblockData createNewMultiblock(int patternID);

    public void checkMultiblockCreation();

    public void markDirty();
}
