package com.majorpotato.steampowered.network.packets.global;


import com.majorpotato.steampowered.network.packets.SteamPoweredPacket;
import com.majorpotato.steampowered.util.artifice.AuraNode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class PacketAuraData extends SteamPoweredPacket {

    List<AuraNode> nodeList;

    public PacketAuraData() { super(); }
    public PacketAuraData(List<AuraNode> nodeList) {
        this.nodeList = nodeList;
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException {
        data.writeInt(nodeList.size());
        for(int i = 0; i < nodeList.size(); i++) {
            //nodeList.get(i).writeData(data);
        }
    }

    @Override
    public void readData(DataInputStream data) throws IOException {
        int nodeCount = data.readInt();
        //AuraNodeNet net = AuraWorldData.get(Minecraft.getMinecraft().thePlayer.worldObj).getWorldNet(true);
        for(int i = 0; i < nodeCount; i++) {
            //AuraNode node = new AuraNode(data);
            //if(!net.contains(node)) net.addNode(node);
        }
    }

    @Override
    public int getID() { return PacketType.AURA_DATA.ordinal(); }
}
