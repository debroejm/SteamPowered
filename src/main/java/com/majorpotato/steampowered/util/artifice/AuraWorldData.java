package com.majorpotato.steampowered.util.artifice;

public class AuraWorldData /*extends WorldSavedData*/ {

    /*
    private static final String IDENTIFIER = "spAura";

    AuraNodeNet worldNet = new AuraNodeNet();

    public AuraWorldData() {
        //super(IDENTIFIER);
    }

    public AuraWorldData(String identifier) {
        //super(identifier);
    }

    public AuraNodeNet getWorldNet() { return getWorldNet(false); }
    public AuraNodeNet getWorldNet(boolean dirty) {
        //if(dirty) markDirty();
        return worldNet;
    }

    /*
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        worldNet.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        worldNet.writeToNBT(compound);
    }

    public static AuraWorldData get(World world) {
        AuraWorldData data = null;
        try {
            data = (AuraWorldData) world.perWorldStorage.loadData(AuraWorldData.class, IDENTIFIER);
        } catch (Exception ex) {

        }
        if (data == null) {
            data = new AuraWorldData();
            world.perWorldStorage.setData(IDENTIFIER, data);
        }
        return data;

    }
    */

}
