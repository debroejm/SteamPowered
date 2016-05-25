package com.majorpotato.steampowered.block.machine.steam;


import com.majorpotato.steampowered.SteamPowered;
import com.majorpotato.steampowered.init.ModItems;
import com.majorpotato.steampowered.tileentity.machine.steam.TEntitySteamBoiler;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineSteamBoilerBurning extends MachineSteam {

    public MachineSteamBoilerBurning()
    {
        super();
        this.setBlockName("machineSteamBoilerBurning");
        this.setBlockTextureName("noTex");
        this.setLightLevel(0.8F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TEntitySteamBoiler();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if(player.getHeldItem() != null && (player.getHeldItem().getItem().equals(ModItems.toolWrench) || player.getHeldItem().getItem().equals(ModItems.toolPressureGauge))) return false;
        if(!world.isRemote)
        {
            TileEntity ent = world.getTileEntity(x, y, z);
            if(ent instanceof TEntitySteamBoiler) {
                FMLNetworkHandler.openGui(player, SteamPowered.instance, 1, world, x, y, z);
                return true;
            }
        }
        return false;
    }
}
