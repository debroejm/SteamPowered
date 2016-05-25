package com.majorpotato.steampowered.handler;


import com.majorpotato.steampowered.network.packets.PacketMultiblock;
import com.majorpotato.steampowered.network.packets.PacketTileEntity;
import com.majorpotato.steampowered.network.packets.SteamPoweredPacket;
import com.majorpotato.steampowered.network.packets.global.PacketAuraData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PacketHandler {

    private static final SteamPoweredPacket.PacketType[] packetTypes = SteamPoweredPacket.PacketType.values();
    public static final PacketHandler INSTANCE = new PacketHandler();
    public final FMLEventChannel channel;

    private PacketHandler() {
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(SteamPoweredPacket.CHANNEL_NAME);
        channel.register(this);
    }

    public static void init() {
        // NOOP
    }

    @SubscribeEvent
    public void onPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        byte[] data = new byte[event.packet.payload().readableBytes()];
        event.packet.payload().readBytes(data);

        onPacketData(data, ((NetHandlerPlayServer) event.handler).playerEntity);
    }

    @SubscribeEvent
    public void onPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        byte[] data = new byte[event.packet.payload().readableBytes()];
        event.packet.payload().readBytes(data);

        //System.out.println("PacketHandler Packet Received");

        onPacketData(data, null);
    }

    public void onPacketData(byte[] bData, EntityPlayerMP player) {
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(bData));
        try {
            SteamPoweredPacket pkt;

            byte packetID = data.readByte();

            if (packetID < 0)
                return;

            SteamPoweredPacket.PacketType type = packetTypes[packetID];
            switch (type) {
                case TILE_ENTITY:
                    pkt = new PacketTileEntity();
                    break;
                case MULTIBLOCK:
                    pkt = new PacketMultiblock();
                    break;
                case AURA_DATA:
                    pkt = new PacketAuraData();
                    break;
                default:
                    return;
            }
            if (pkt != null)
                pkt.readData(data);
        } catch (IOException e) {
            System.out.println("Exception in PacketHandler.onPacketData: {0} "+e);
        }
    }
}
