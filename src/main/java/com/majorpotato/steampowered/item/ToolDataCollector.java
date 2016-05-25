package com.majorpotato.steampowered.item;


import com.majorpotato.steampowered.util.machine.IDataCollectorUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ToolDataCollector extends ItemSP {
    public ToolDataCollector(){
        super();
        this.setUnlocalizedName("toolDataCollector");
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float px, float py, float pz)
    {
        TileEntity ent = world.getTileEntity(x, y, z);
        if(ent instanceof IDataCollectorUser && !world.isRemote)
        {
            player.addChatComponentMessage(new ChatComponentText(((IDataCollectorUser) ent).getCustomInformationMessage()));
            return true;
        }
        return false;
    }
}
