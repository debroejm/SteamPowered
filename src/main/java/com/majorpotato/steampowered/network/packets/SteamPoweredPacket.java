package com.majorpotato.steampowered.network.packets;


import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.Unpooled;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class SteamPoweredPacket {

    public final static String CHANNEL_NAME = "STP";

    public enum PacketType {

        TILE_ENTITY,
        EFFECT,
        KEY_PRESS,
        MULTIBLOCK,
        INVENTORY_UPDATE,
        AURA_DATA,
    }

    public FMLProxyPacket getPacket() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try {
            data.writeByte(getID());
            writeData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FMLProxyPacket(Unpooled.wrappedBuffer(bytes.toByteArray()), CHANNEL_NAME);
    }

    public abstract void writeData(DataOutputStream data) throws IOException;

    public abstract void readData(DataInputStream data) throws IOException;

    public abstract int getID();

    @Override
    public String toString() { return getClass().getSimpleName(); }
}
