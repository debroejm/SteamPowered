package com.majorpotato.steampowered.multiblock;


import com.majorpotato.steampowered.network.PacketBuilder;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityAlloyFurnace;
import com.majorpotato.steampowered.tileentity.machine.steam.TEntitySteamBoiler;
import com.majorpotato.steampowered.multiblock.pattern.Pattern;
import com.majorpotato.steampowered.multiblock.pattern.ShapeCube;
import com.majorpotato.steampowered.multiblock.pattern.ShapeCubeHollow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class MultiblockData {
    protected NBTTagCompound NBTData = new NBTTagCompound();

    // ------------------
    // | Static Methods |
    // ------------------

    // Exist mostly for Pattern handling

    private static HashMap<MultiblockType,List<Pattern>> patternIndex = new HashMap<MultiblockType,List<Pattern>>();

    /**
     * Adds a pattern to the global listing of patterns.
     * @param type - MultiblockType to sort the pattern under.
     * @param in - Pattern to add.
     */
    public static void addPattern(MultiblockType type, Pattern in) {
        if(patternIndex.containsKey(type)) {
            List<Pattern> patterns = patternIndex.get(type);
            patterns.add(in);
        } else {
            List<Pattern> patterns = new ArrayList<Pattern>();
            patterns.add(in);
            patternIndex.put(type, patterns);
        }
    }

    /**
     * Grabs a pattern from the global listing based off of its ID.
     * @param type - MultiblockType to find the pattern under.
     * @param ID of the Pattern to find.
     * @return the Pattern the user requested, or null if it is not found.
     */
    public static Pattern getPattern(MultiblockType type, int ID) {
        List<Pattern> patterns = patternIndex.get(type);
        if(patterns == null) return null;
        for(Pattern ptrn : patterns) {
            if(ptrn.getID() == ID)  return ptrn;
        }
        return null;
    }

    // Hard-coded Recipes that are added.
    static {
        addPattern(MultiblockType.ALLOY_FURNACE, new Pattern(0).addShape(new ShapeCube(TEntityAlloyFurnace.class,3,1,3,1,0,1)).addShape(new ShapeCubeHollow(TEntityAlloyFurnace.class,5,3,5,2,1,2,true)));
        addPattern(MultiblockType.ALLOY_FURNACE, new Pattern(1).addShape(new ShapeCube(TEntityAlloyFurnace.class,2,1,2,0,0,0)).addShape(new ShapeCubeHollow(TEntityAlloyFurnace.class,4,3,4,1,1,1,true)));
        addPattern(MultiblockType.ALLOY_FURNACE, new Pattern(2).addShape(new ShapeCube(TEntityAlloyFurnace.class,3,2,3,1,0,1)).addShape(new ShapeCubeHollow(TEntityAlloyFurnace.class,5,4,5,2,1,2,true)));
        addPattern(MultiblockType.ALLOY_FURNACE, new Pattern(3).addShape(new ShapeCube(TEntityAlloyFurnace.class,2,2,2,0,0,0)).addShape(new ShapeCubeHollow(TEntityAlloyFurnace.class,4,4,4,1,1,1,true)));
        //addPattern(MultiblockType.ALLOY_FURNACE, new Pattern(3).addShape(new ShapeCube(TEntityAlloyFurnace.class,2,2,2,0,0,0)));

        addPattern(MultiblockType.STEAM_BOILER, new Pattern(0).addShape(new ShapeCubeHollow(TEntitySteamBoiler.class,3,3,3,0,0,0)));
        addPattern(MultiblockType.STEAM_BOILER, new Pattern(1).addShape(new ShapeCubeHollow(TEntitySteamBoiler.class,4,4,4,0,0,0)));
    }

    /**
     * Tries to find a Pattern that exists in the world currently and returns it.
     * @param type - MultiblockType to find the pattern under.
     * @param world to check for Pattern existence.
     * @param xPos in the world to start check.
     * @param yPos in the world to start check.
     * @param zPos in the world to start check.
     * @return Pattern found, or null if no Pattern fits into the world at the position given.
     */
    public static Pattern checkPatternsForFit(MultiblockType type, World world, int xPos, int yPos, int zPos) {
        //System.out.println("Pattern Count: "+patterns.size());
        List<Pattern> patterns = patternIndex.get(type);
        if(patterns == null) return null;
        for(Pattern ptrn : patterns) {
            if(ptrn.validate(world, xPos, yPos, zPos)) {
                return ptrn;
            }
        }
        return null;
    }

    // Keep a copy of the worldObj and coords of where the Multiblock is located in this data.
    protected World worldObj;
    public int xCoord, yCoord, zCoord;
    protected Pattern checkPattern;

    protected boolean sendUpdates = false;
    private int tickClock = -5;

    // List of IMultiblock instances that make up the entire Multiblock
    protected ArrayList<IMultiblockTileEntity> multiblocks = new ArrayList<IMultiblockTileEntity>();
    private int updateCount = 0;

    // Method stub for figuring out what type of Multiblock each sub-class belongs to. Each will be unique.
    public abstract MultiblockType getMultiblockType();

    // Method stub for comparator output.
    public abstract int getComparatorOutput();

    // Method stub for the Multiblock private update cycle. This exists because the main update function must not be overwritten.
    protected abstract void privateUpdate();

    /**
     * Basic constructor for a set of MultiblockData.
     * @param world that the Multiblock exists in.
     * @param x position of the Multiblock.
     * @param y position of the Multiblock.
     * @param z position of the Multiblock.
     * @param patternID of the Pattern the Multiblock is formed from.
     */
    public MultiblockData(World world, int x, int y, int z, int patternID) {
        worldObj = world;
        xCoord = x; yCoord = y; zCoord = z;
        this.checkPattern = getPattern(getMultiblockType(), patternID);
    }

    public World getWorldObj() { return worldObj; }
    public Pattern getPattern() { return checkPattern; }

    /**
     * Adds an IMultiblock instance to the internal list.
     * This should be done whenever this MultiblockData is assigned to an IMultiblock instance.
     * @param multiblock instance to add.
     */
    public void addMultiblockPart(IMultiblockTileEntity multiblock) {
        if(multiblockPartExists(multiblock)) return;
        multiblocks.add(multiblock);
    }

    /**
     * Removes an IMultiblock instance from the internal list.
     * Should only be done when the Multiblock is being destroyed.
     * @param multiblock instance to remove.
     */
    public void removeMultiblockPart(IMultiblockTileEntity multiblock) {
        multiblocks.remove(multiblock);
    }

    /**
     * Checks to see if an IMultiblock instance already exists in the internal list.
     * @param multiblock instance to check for.
     * @return True if the IMultiblock instance exists in the internal list.
     */
    private boolean multiblockPartExists(IMultiblockTileEntity multiblock) {
        return multiblocks.contains(multiblock);
    }

    /**
     * Event function for OnDestroy. Should be called when the Multiblock is being destroyed.
     */
    public void onDestroy() {
        //System.out.println("MBonDestroy");
        for(int i = 0; i < multiblocks.size(); i++) {
            multiblocks.get(i).setMultiblock(null);
            multiblocks.get(i).markDirty();
        }
    }

    /**
     * Checks to see if the existing Pattern this Multiblock uses is currently valid in the world.
     * @return True if the Pattern is valid, or False if it is not or if it is null.
     */
    public final boolean checkPattern() {
        return checkPattern != null && checkPattern.validate(worldObj, xCoord, yCoord, zCoord);
    }

    /**
     * Update cycle function. Should be called by every IMultiblock instance's update cycle function.
     */
    public final void updateMultiblock() {
        updateCount++;
        if(updateCount >= multiblocks.size()) updateCount = 0;
        else return;

        tickClock++;
        if(tickClock > 20) tickClock = 1;
        if(tickClock == 0) clockLoad();
        if(tickClock == 5 || tickClock == 10 || tickClock == 15 || tickClock == 20) clock5Tick();
        if(tickClock == 10 || tickClock == 20) clock10Tick();
        if(tickClock == 20) clock20Tick();

        privateUpdate();
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("MBPatternID", checkPattern.getID());
    }
    public void readFromNBT(NBTTagCompound compound) {
        checkPattern = getPattern(getMultiblockType(), compound.getInteger("MBPatternID"));
    }

    public void writePacketData(DataOutputStream data) throws IOException {

    }
    public void readPacketData(DataInputStream data) throws IOException {

    }

    public final void sendUpdateToClient() {
        PacketBuilder.instance().sendMultiblockPacketToClients(this);
    }

    protected void clockLoad() {

    }

    protected void clock5Tick() {

    }

    protected void clock10Tick() {
        if(sendUpdates) sendUpdateToClient();
    }

    protected void clock20Tick() {

    }

}
