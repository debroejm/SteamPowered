package com.majorpotato.steampowered.network.packets;

import com.majorpotato.steampowered.tileentity.TEntityMultiblock;
import com.majorpotato.steampowered.multiblock.MultiblockData;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class PacketMultiblock extends SteamPoweredPacket {


    private MultiblockData MBD;

    public PacketMultiblock() { super(); }

    public PacketMultiblock(MultiblockData MBD) {
        this.MBD = MBD;
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
        data.writeInt(MBD.xCoord);
        data.writeInt(MBD.yCoord);
        data.writeInt(MBD.zCoord);
        MBD.writePacketData(data);
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

        if( te instanceof TEntityMultiblock) {
            MBD = ((TEntityMultiblock)te).getMultiblock();
        } else return;

        //System.out.println("Packet Received: "+ent.getClass().toString());

        if(MBD == null) return;

        try {
            MBD.readPacketData(data);
        } catch (IOException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw ex;
        }
    }

    @Override
    public int getID() { return PacketType.MULTIBLOCK.ordinal(); }
}
