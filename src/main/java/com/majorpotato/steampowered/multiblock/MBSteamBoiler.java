package com.majorpotato.steampowered.multiblock;


import com.majorpotato.steampowered.tileentity.machine.steam.TEntitySteamBoiler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MBSteamBoiler extends MultiblockData{

    private static final int MAX_HEAT = 2000;

    private int coolTickDelay = 40;
    private int heatTickDelay = 8;

    private int burningFireboxCount = 0;
    private int allFireboxCount = 0;

    private int heatLevel = 0;
    private int heatMax = MAX_HEAT/2;

    public MBSteamBoiler(World world, int x, int y, int z, int patternID) {
        super(world, x, y, z, patternID);
        sendUpdates = true;
    }

    @Override
    public MultiblockType getMultiblockType() { return MultiblockType.STEAM_BOILER; }

    @Override
    public int getComparatorOutput() {
        return getHeatScaled(15);
    }

    public int getHeat() { return heatLevel; }
    public boolean isBurning() { return burningFireboxCount > 0; }

    public int getHeatScaled(int par1) { return ( (this.heatLevel * par1) / MAX_HEAT); }

    public int getHeatMaxScaled(int par1) {
        if(heatMax >= MAX_HEAT) return 0;
        return ( ((MAX_HEAT - heatMax) * par1) / MAX_HEAT);
    }

    private int maxHeat() { return Math.min(heatMax, MAX_HEAT); }

    public int getSteamPressure() {
        return (int)((double)(110+15*multiblocks.size())*((double)heatLevel/(double)maxHeat()));
    }

    @Override
    protected void clock10Tick() {
        super.clock10Tick();

        burningFireboxCount = 0;
        allFireboxCount = 0;

        for (IMultiblockTileEntity multiblock : multiblocks) {
            if (multiblock instanceof TEntitySteamBoiler) {
                TEntitySteamBoiler tSB = (TEntitySteamBoiler) multiblock;
                if (tSB.hasFirebox()) allFireboxCount++;
                if (tSB.isFedHeat()) burningFireboxCount++;
            }
        }

        heatMax = 25*allFireboxCount;
    }

    @Override
    protected void privateUpdate() {
        if(!this.worldObj.isRemote)
        {
            boolean update = false;

            if(burningFireboxCount > 0)
            {
                heatTickDelay -= burningFireboxCount;
                if(heatTickDelay < 1)
                {
                    if(++heatLevel > maxHeat()) heatLevel = maxHeat();
                    heatTickDelay = (8 + (heatLevel / 40))*allFireboxCount;

                }
                coolTickDelay = 40;
            }
            else
            {
                if(--coolTickDelay < 1)
                {
                    if(--heatLevel < 0) heatLevel = 0;
                    coolTickDelay = 40;
                }
                heatTickDelay = (8 + (heatLevel / 40))*allFireboxCount;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("HeatLevel", heatLevel);
        compound.setInteger("MaxHeat", heatMax);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.heatLevel = compound.getInteger("HeatLevel");
        this.heatMax = compound.getInteger("MaxHeat");
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeInt(heatLevel);
        data.writeInt(heatMax);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        heatLevel = data.readInt();
        heatMax = data.readInt();
    }


}
