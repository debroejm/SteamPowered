package com.majorpotato.steampowered.multiblock;


import com.majorpotato.steampowered.util.PacketHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class MBInventory extends MultiblockData implements IInventory {

    protected ItemStack[] inventory;

    protected int INPUTS = 1;
    protected int OUTPUTS = 1;

    protected int SLOT_INPUT = 0,
            SLOT_OUTPUT = SLOT_INPUT+INPUTS;

    protected String displayName = "Multiblock";
    protected String IDName = "multiblock";

    public MBInventory(World world, int x, int y, int z, int patternID) {
        super(world, x, y, z, patternID);
        inventory = new ItemStack[INPUTS+OUTPUTS];
    }

    public int getInputCount() { return INPUTS; }
    public int getInputSlot() { return SLOT_INPUT; }
    public int getOutputCount() { return OUTPUTS; }
    public int getOutputSlot() { return SLOT_OUTPUT; }

    @Override
    public int getSizeInventory() { return inventory.length; }

    @Override
    public ItemStack getStackInSlot(int i) {
        //if(i < 0 || i > inventory.length-1) return null;
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        ItemStack itemstack = getStackInSlot(slot);

        if(itemstack != null)
        {
            if(itemstack.stackSize <= count)
            {
                setInventorySlotContents(slot, null);
            }
            else
            {
                itemstack = itemstack.splitStack(count);
                if (itemstack.stackSize == 0)
                {
                    setInventorySlotContents(slot, null);
                }
            }
        }
        return itemstack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack itemstack = getStackInSlot(slot);
        if(itemstack != null)
        {
            setInventorySlotContents(slot, null);
        }
        return itemstack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
        //if(slot < 0 || slot > inventory.length-1) return;
        inventory[slot] = itemstack;

        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack)
    {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public String getInventoryName() {
        return this.isInvNameLocalized() ? this.displayName : "container."+IDName;
    }

    public boolean isInvNameLocalized()
    {
        return this.displayName != null && this.displayName.length() > 0;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public void markDirty() {
        sendUpdateToClient();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
    }

    public int getComparatorOutput() {
        int itemMax = 0;
        int itemCount = 0;
        for(int i = 0; i < inventory.length; i++) {
            if(inventory[i] != null) {
                itemMax += inventory[i].getMaxStackSize();
                itemCount += inventory[i].stackSize;
            }
        }
        return (itemCount * 15) / itemMax;
    }

    protected int getEmptyOutputSlot() {
        int index = SLOT_OUTPUT;
        for(int i = 0; i < OUTPUTS; i++)
        {
            if(inventory[SLOT_OUTPUT+i] == null) return index;
            index++;
        }
        return index-1;
    }

    protected int getEmptyOutputSlot(ItemStack compareItem) {
        int index = SLOT_OUTPUT;
        for(int i = 0; i < OUTPUTS; i++)
        {
            if(inventory[SLOT_OUTPUT+i] == null) return index;
            if(inventory[SLOT_OUTPUT+i].isItemEqual(compareItem)) {
                int result = inventory[SLOT_OUTPUT+i].stackSize+compareItem.stackSize;
                if( result <= getInventoryStackLimit() && result <= compareItem.getMaxStackSize()) return index;
            }
            index++;
        }
        return -1;
    }

    protected ItemStack[] getOutputSlots() {
        ItemStack[] out = new ItemStack[OUTPUTS];
        for(int i = 0; i < OUTPUTS; i++)
            out[i] = inventory[SLOT_OUTPUT+i];
        return out;
    }

    protected ItemStack[] getInputSlots() {
        ItemStack[] out = new ItemStack[INPUTS];
        for(int i = 0; i < INPUTS; i++)
            out[i] = inventory[SLOT_INPUT+i];
        return out;
    }


    // ---------------------
    // | NBT Data Handling |
    // ---------------------

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();

        for(int i = 0; i < inventory.length; i++)
        {
            if(inventory[i] != null)
            {
                NBTTagCompound item = new NBTTagCompound();

                item.setInteger("MBSlot", i);
                inventory[i].writeToNBT(item);
                list.appendTag(item);
            }
        }

        compound.setTag("ItemsMultiblock", list);

        if( isInvNameLocalized())
        {
            compound.setString("MBCustomName", displayName);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagList list = compound.getTagList("ItemsMultiblock", Constants.NBT.TAG_COMPOUND);
        this.inventory = new ItemStack[getSizeInventory()];

        for(int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound item = (NBTTagCompound) list.getCompoundTagAt(i);
            int slot = item.getInteger("MBSlot");

            if(slot >= 0 && slot < inventory.length)
            {
                inventory[slot] = ItemStack.loadItemStackFromNBT(item);
            }
        }

        if( compound.hasKey("MBCustomName"))
        {
            displayName = compound.getString("MBCustomName");
        }
    }


    // -------------------
    // | Packet Handling |
    // -------------------

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeInt(inventory.length);
        for(int i = 0; i < inventory.length; i++)
            PacketHelper.writeItemStack(inventory[i], data);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        int size = data.readInt();
        if(inventory.length != size) inventory = new ItemStack[size];
        for(int i = 0; i < size; i++)
            inventory[i] = PacketHelper.readItemStack(data);
    }
}
