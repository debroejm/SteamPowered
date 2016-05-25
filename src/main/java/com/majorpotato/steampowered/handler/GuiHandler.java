package com.majorpotato.steampowered.handler;


import com.majorpotato.steampowered.SteamPowered;
import com.majorpotato.steampowered.client.container.ContainerAlloyFurnace;
import com.majorpotato.steampowered.client.gui.GuiAlloyFurnace;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityAlloyFurnace;
import com.majorpotato.steampowered.multiblock.MBAlloyFurnace;
import com.majorpotato.steampowered.multiblock.MultiblockData;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public GuiHandler()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(SteamPowered.instance, this);
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity entity = world.getTileEntity(x, y, z);
        switch(id)
        {
            case 0:
                if(entity != null && entity instanceof TEntityAlloyFurnace) {
                    MultiblockData TMB = ((TEntityAlloyFurnace)entity).getMultiblock();
                    if(TMB instanceof MBAlloyFurnace)
                        return new ContainerAlloyFurnace(player.inventory, (MBAlloyFurnace)TMB);
                    else return null;
                }
                else return null;
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity entity = world.getTileEntity(x, y, z);
        switch(id)
        {
            case 0:
                if(entity != null && entity instanceof TEntityAlloyFurnace) {
                    MultiblockData TMB = ((TEntityAlloyFurnace)entity).getMultiblock();
                    if(TMB instanceof MBAlloyFurnace)
                        return new GuiAlloyFurnace(player.inventory, (MBAlloyFurnace)TMB);
                    else return null;
                }
                else return null;
            default:
                return null;
        }
    }
}
