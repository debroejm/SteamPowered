package com.majorpotato.steampowered.network.packets;


import com.majorpotato.steampowered.util.PacketHelper;
import com.majorpotato.steampowered.multiblock.IMultiblockTileEntity;
import com.majorpotato.steampowered.multiblock.MultiblockData;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketInventoryUpdate extends SteamPoweredPacket {

    IInventory inventory;
    int slotID;
    World worldObj;
    int x, y, z;

    public PacketInventoryUpdate(IInventory inventory, int slotID, World worldObj, int x, int y, int z) {
        this.inventory = inventory;
        this.slotID = slotID;
        this.worldObj = worldObj;
        this.x = x; this.y = y; this.z = z;
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException {
        data.writeInt(worldObj.provider.dimensionId);
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
        data.writeInt(slotID);
        ItemStack stack = inventory.getStackInSlot(slotID);
        PacketHelper.writeItemStack(stack, data);

        if(stack != null) System.out.println("Inventory Update Packet Sent [SlotID:"+slotID+"|StackName:"+stack.getDisplayName()+"|StackSize:"+stack.stackSize+"]");
        else System.out.println("Inventory Update Packet Sent [SlotID:"+slotID+"|NULL]");
    }

    @Override
    public void readData(DataInputStream data) throws IOException {
        worldObj = DimensionManager.getWorld(data.readInt());
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();

        inventory = null;
        TileEntity tEnt = worldObj.getTileEntity(x, y, z);
        if(tEnt instanceof IInventory) {
            inventory = (IInventory)tEnt;
        } else if(tEnt instanceof IMultiblockTileEntity) {
            MultiblockData mb = ((IMultiblockTileEntity)tEnt).getMultiblock();
            if(mb instanceof IInventory) {
                inventory = (IInventory)mb;
            }
        }
        if(inventory == null) return;

        slotID = data.readInt();
        ItemStack stack = PacketHelper.readItemStack(data);

        if(stack != null) System.out.println("Inventory Update Packet Received [SlotID:"+slotID+"|StackName:"+stack.getDisplayName()+"|StackSize:"+stack.stackSize+"]");
        else System.out.println("Inventory Update Packet Received [SlotID:"+slotID+"|NULL]");

        inventory.setInventorySlotContents(slotID, stack);
    }

    @Override
    public int getID() {
        return PacketType.INVENTORY_UPDATE.ordinal();
    }
}
