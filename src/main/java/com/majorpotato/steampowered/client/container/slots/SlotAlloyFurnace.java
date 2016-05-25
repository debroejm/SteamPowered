package com.majorpotato.steampowered.client.container.slots;


import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class SlotAlloyFurnace extends Slot {

    private EntityPlayer player;
    private int field_75228_b;

    public SlotAlloyFurnace(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.player = par1EntityPlayer;
    }

    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return this.inventory.isItemValidForSlot(this.getSlotIndex(), par1ItemStack);
    }

    public ItemStack decrStackSize(int par1)
    {
        if(this.getHasStack())
        {
            this.field_75228_b += Math.min(par1, this.getStack().stackSize);
        }

        return super.decrStackSize(par1);
    }

    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    {
        this.onCrafting(par2ItemStack);
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }

    protected void onCrafting(ItemStack par1ItemStack, int par2)
    {
        this.field_75228_b += par2;
        this.onCrafting(par1ItemStack);
    }

    protected void onCrafting(ItemStack par1ItemStack)
    {
        par1ItemStack.onCrafting(this.player.worldObj, this.player, this.field_75228_b);

        if (!this.player.worldObj.isRemote)
        {
            int i = this.field_75228_b;
            float f = 0.0f; // Get Experience from Somewhere
            int j;

            if( f == 0.0F)
            {
                i = 0;
            }
            else if (f < 1.0F)
            {
                j = MathHelper.floor_float((float) i * f);

                if (j < MathHelper.ceiling_float_int((float)i*f) && (float)Math.random() < (float)i*f - (float)j)
                {
                    ++j;
                }

                i = j;
            }

            while (i > 0)
            {
                j = EntityXPOrb.getXPSplit(i);
                i -= j;
                this.player.worldObj.spawnEntityInWorld(new EntityXPOrb(this.player.worldObj, this.player.posX, this.player.posY + 0.5D, this.player.posZ + 0.5D, j));
            }
        }

        this.field_75228_b = 0;
    }
}
