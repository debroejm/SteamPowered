package com.majorpotato.steampowered.network;

import com.majorpotato.steampowered.network.packets.PacketInventoryUpdate;
import com.majorpotato.steampowered.network.packets.PacketMultiblock;
import com.majorpotato.steampowered.network.packets.PacketTileEntity;
import com.majorpotato.steampowered.tileentity.TEntitySP;
import com.majorpotato.steampowered.multiblock.MultiblockData;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PacketBuilder {

    private static PacketBuilder instance;

    public static PacketBuilder instance() {
        if (instance == null)
            instance = new PacketBuilder();
        return instance;
    }

    private PacketBuilder() {

    }

    public void sendTileEntityPacket(TEntitySP ent) {
        if(ent.getWorldObj() instanceof WorldServer) {
            WorldServer world = (WorldServer) ent.getWorldObj();
            PacketTileEntity pkt = new PacketTileEntity(ent);
            PacketDispatcher.sendToWatchers(pkt, world, ent.xCoord, ent.zCoord);
            //System.out.println("Packet Sent: "+ent.getClass().toString());
        }
    }

    public void sendMultiblockPacketToClients(MultiblockData MBD) {
        if(MBD.getWorldObj() instanceof WorldServer) {
            WorldServer world = (WorldServer) MBD.getWorldObj();
            PacketMultiblock pkt = new PacketMultiblock(MBD);
            PacketDispatcher.sendToWatchers(pkt, world, MBD.xCoord, MBD.zCoord);
        }
    }

    public void sendInventoryUpdateRequestToServer(IInventory inventory, int slotID, World worldObj, int x, int y, int z) {
        if(worldObj.isRemote) {
            PacketInventoryUpdate pkt = new PacketInventoryUpdate(inventory, slotID, worldObj, x, y, z);
            PacketDispatcher.sendToServer(pkt);
        }
    }

    /*
    public void sendAllAuraDataToPlayer(EntityPlayerMP player) {
        //PacketAuraData pkt = new PacketAuraData(AuraWorldData.get(player.worldObj).getWorldNet().getAllNodes());
        PacketDispatcher.sendToPlayer(pkt, player);
    }

    public void sendNodeDataToPlayer(EntityPlayerMP player, List<AuraNode> nodes) {
        PacketAuraData pkt = new PacketAuraData(nodes);
        PacketDispatcher.sendToPlayer(pkt, player);
    }
    */
}
