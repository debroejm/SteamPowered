package com.majorpotato.steampowered.block.machine.steam;


import com.majorpotato.steampowered.tileentity.machine.steam.TEntitySteamEngine;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineSteamEngine extends MachineSteam {

    public MachineSteamEngine()
    {
        super();
        this.setBlockName("machineSteamEngine");
        this.setBlockTextureName("noTex");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TEntitySteamEngine();
    }
}
