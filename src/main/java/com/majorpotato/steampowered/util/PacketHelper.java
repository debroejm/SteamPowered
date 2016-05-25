package com.majorpotato.steampowered.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class PacketHelper {

    public static ItemStack readItemStack(DataInputStream data) throws IOException
    {
        ItemStack itemstack = null;
        short itemID = data.readShort();

        if (itemID >= 0)
        {
            byte count = data.readByte();
            short meta = data.readShort();
            itemstack = new ItemStack(Item.getItemById(itemID), count, meta);
            if(data.readBoolean())
                itemstack.stackTagCompound = CompressedStreamTools.read(data);
        }

        return itemstack;
    }

    public static void writeItemStack(ItemStack stack, DataOutputStream data) throws IOException
    {
        if (stack == null)
        {
            data.writeShort(-1);
        }
        else
        {
            data.writeShort(Item.getIdFromItem(stack.getItem()));
            data.writeByte(stack.stackSize);
            data.writeShort(stack.getItemDamage());
            NBTTagCompound nbttagcompound = stack.stackTagCompound;
            if(nbttagcompound != null) {
                data.writeBoolean(true);
                CompressedStreamTools.write(nbttagcompound, data);
            } else {
                data.writeBoolean(false);
            }
        }
    }
}
