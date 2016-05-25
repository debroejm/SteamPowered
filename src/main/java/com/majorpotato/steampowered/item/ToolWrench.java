package com.majorpotato.steampowered.item;


import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.machine.IWrenchUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ToolWrench extends ItemSP {
    public ToolWrench(){
        super();
        this.setUnlocalizedName("toolWrench");
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float px, float py, float pz)
    {
        TileEntity ent = world.getTileEntity(x, y, z);
        if(ent instanceof IWrenchUser && !world.isRemote)
        {
            if(player.isSneaking()) {
                boolean success = ((IWrenchUser) ent).wrenchBlock();
                String message = ((IWrenchUser) ent).getCustomWrenchMessage();
                if(!message.isEmpty() && success)
                    player.addChatComponentMessage(new ChatComponentText(message));
                return success;
            }
            else
                return ((IWrenchUser)ent).rotateBlock(Direction.fromID(side));
        }
        return false;
    }

}
