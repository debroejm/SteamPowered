package com.majorpotato.steampowered.client.container;


import com.majorpotato.steampowered.client.container.slots.SlotAlloyFurnace;
import com.majorpotato.steampowered.network.PacketBuilder;
import com.majorpotato.steampowered.multiblock.MBAlloyFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAlloyFurnace extends Container {
    private MBAlloyFurnace MBAF;

    public ContainerAlloyFurnace(InventoryPlayer invPlayer, MBAlloyFurnace furnace)
    {
        this.MBAF = furnace;

        int inputs = MBAF.getInputCount();
        switch(inputs) {
            case 18:
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot(), 62, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+1, 62, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+2, 62, 53));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+3, 80, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+4, 80, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+5, 80, 53));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+6, 98, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+7, 98, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+8, 98, 53));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+9, 116, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+10, 116, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+11, 116, 53));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+12, 134, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+13, 134, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+14, 134, 53));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+15, 152, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+16, 152, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+17, 152, 53));
                break;
            case 9:
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot(), 62, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+1, 62, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+2, 62, 53));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+3, 80, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+4, 80, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+5, 80, 53));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+6, 98, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+7, 98, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+8, 98, 53));
                break;
            case 4:
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot(), 62, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+1, 62, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+2, 80, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+3, 80, 35));
                break;
            case 8:
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot(), 62, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+1, 62, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+2, 80, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+3, 80, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+4, 98, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+5, 98, 35));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+6, 116, 17));
                this.addSlotToContainer(new SlotAlloyFurnace(invPlayer.player, MBAF, MBAF.getInputSlot()+7, 116, 35));
                break;
            default: break;
        }

        bindPlayerInventory(invPlayer);
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    private void sendUpdateRequest(int slotID) {
        if(slotID < MBAF.getSizeInventory()) PacketBuilder.instance().sendInventoryUpdateRequestToServer(MBAF, slotID, MBAF.getWorldObj(), MBAF.xCoord, MBAF.yCoord, MBAF.zCoord);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return MBAF.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i)
    {
        ItemStack itemstack = null;
        Slot slot = getSlot(i);

        if(slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(i < MBAF.getSizeInventory())
            {
                if(!mergeItemStack(itemstack1, MBAF.getSizeInventory(), MBAF.getSizeInventory()+36, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if(!mergeItemStack(itemstack1, 0, MBAF.getSizeInventory(), false))
            {
                return null;
            }

            if(itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
            return itemstack;
        }
        return null;
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
        super.putStackInSlot(slotID, stack);
        sendUpdateRequest(slotID);
    }

    @Override
    protected boolean mergeItemStack(ItemStack inputStack, int playerInvEnd, int containerInvEnd, boolean reverse)
    {
        boolean success = false;
        int slotID = playerInvEnd;

        if (reverse)
        {
            slotID = containerInvEnd - 1;
        }

        Slot slot;
        ItemStack stack;

        if (inputStack.isStackable())
        {
            while (inputStack.stackSize > 0 && (!reverse && slotID < containerInvEnd || reverse && slotID >= playerInvEnd))
            {
                slot = (Slot)this.inventorySlots.get(slotID);
                stack = slot.getStack();

                if(slot.isItemValid(inputStack)) {
                    if (stack != null && stack.getItem() == inputStack.getItem() && (!inputStack.getHasSubtypes() || inputStack.getItemDamage() == stack.getItemDamage()) && ItemStack.areItemStackTagsEqual(inputStack, stack)) {
                        int var9 = stack.stackSize + inputStack.stackSize;

                        if (var9 <= inputStack.getMaxStackSize()) {
                            inputStack.stackSize = 0;
                            stack.stackSize = var9;
                            slot.onSlotChanged();
                            success = true;
                            sendUpdateRequest(slotID);
                        } else if (stack.stackSize < inputStack.getMaxStackSize()) {
                            inputStack.stackSize -= inputStack.getMaxStackSize() - stack.stackSize;
                            stack.stackSize = inputStack.getMaxStackSize();
                            slot.onSlotChanged();
                            success = true;
                            sendUpdateRequest(slotID);
                        }
                    }
                }

                if (reverse)
                {
                    --slotID;
                }
                else
                {
                    ++slotID;
                }
            }
        }

        if (inputStack.stackSize > 0)
        {
            if (reverse)
            {
                slotID = containerInvEnd - 1;
            }
            else
            {
                slotID = playerInvEnd;
            }

            while (!reverse && slotID < containerInvEnd || reverse && slotID >= playerInvEnd)
            {
                slot = (Slot)this.inventorySlots.get(slotID);
                stack = slot.getStack();

                if (stack == null && slot.isItemValid(inputStack))
                {
                    slot.putStack(inputStack.copy());
                    slot.onSlotChanged();
                    inputStack.stackSize = 0;
                    success = true;
                    sendUpdateRequest(slotID);
                    break;
                }

                if (reverse)
                {
                    --slotID;
                }
                else
                {
                    ++slotID;
                }
            }
        }

        return success;
    }
}