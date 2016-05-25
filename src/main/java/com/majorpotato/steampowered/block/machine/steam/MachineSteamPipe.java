package com.majorpotato.steampowered.block.machine.steam;


import com.majorpotato.steampowered.tileentity.machine.steam.TEntitySteamPipe;
import com.majorpotato.steampowered.util.TieredMaterial;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineSteamPipe extends MachineSteam {

    private TieredMaterial material;

    public MachineSteamPipe(TieredMaterial material)
    {
        super();
        this.setBlockName("machineSteamPipe"+material.getName());
        this.setBlockTextureName("noTex");
        this.setBlockBounds(0.2F, 0.2F, 0.2F, 0.8F, 0.8F, 0.8F);
        this.material = material;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TEntitySteamPipe(material);
    }

}
