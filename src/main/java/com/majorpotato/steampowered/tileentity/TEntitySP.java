package com.majorpotato.steampowered.tileentity;


import com.majorpotato.steampowered.network.PacketBuilder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.tileentity.TileEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TEntitySP extends TileEntity {

    protected boolean sendUpdates = false;
    private int tickClock = -5;

    @Override
    public void updateEntity() {
        super.updateEntity();

        tickClock++;
        if(tickClock > 20) tickClock = 1;
        if(tickClock == 0) clockLoad();
        if(tickClock == 5 || tickClock == 10 || tickClock == 15 || tickClock == 20) clock5Tick();
        if(tickClock == 10 || tickClock == 20) clock10Tick();
        if(tickClock == 20) clock20Tick();
    }
    @Override
    public void writeToNBT(NBTTagCompound par1)
    {
        super.writeToNBT(par1);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1)
    {
        super.readFromNBT(par1);
    }

    public void writePacketData(DataOutputStream data) throws IOException {

    }

    public void readPacketData(DataInputStream data) throws IOException {

    }

    public void sendUpdateToClient() {
        PacketBuilder.instance().sendTileEntityPacket(this);
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

    public void onDestroy() {
        
    }
}
