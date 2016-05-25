package com.majorpotato.steampowered.network.packets;


import com.majorpotato.steampowered.tileentity.TEntitySP;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketTileEntity extends SteamPoweredPacket {

    private TEntitySP ent;

    public PacketTileEntity() { super(); }

    public PacketTileEntity(TEntitySP ent) {
        this.ent = ent;
    }

    @SideOnly(Side.CLIENT)
    private static World getWorld() {
        Minecraft mc = FMLClientHandler.instance().getClient();
        if (mc != null)
            return mc.theWorld;
        return null;
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException {
        data.writeInt(ent.xCoord);
        data.writeInt(ent.yCoord);
        data.writeInt(ent.zCoord);
        ent.writePacketData(data);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void readData(DataInputStream data) throws IOException {
        World world = this.getWorld();
        if (world == null) {
            return;
        }

        int x = data.readInt();
        int y = data.readInt();
        int z = data.readInt();

        if ( y < 0 || !world.blockExists(x, y, z)) {
            return;
        }

        TileEntity te = world.getTileEntity(x, y, z);

        if( te instanceof TEntitySP ) {
            ent = (TEntitySP) te;
        } else
            ent = null;

        if (ent == null) {
            world.removeTileEntity(x, y, z);
            te = world.getTileEntity(x, y, z);
            if( te instanceof TEntitySP)
                ent = (TEntitySP) te;
        }

        if (ent == null)
            return;

        //System.out.println("Packet Received: "+ent.getClass().toString());

        try {
            ent.readPacketData(data);
        } catch (IOException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw ex;
        }
    }

    @Override
    public int getID() { return PacketType.TILE_ENTITY.ordinal(); }
}
