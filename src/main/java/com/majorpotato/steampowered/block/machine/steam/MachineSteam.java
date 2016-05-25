package com.majorpotato.steampowered.block.machine.steam;

import com.majorpotato.steampowered.block.machine.BlockMachine;
import com.majorpotato.steampowered.tileentity.machine.steam.TEntitySteam;
import com.majorpotato.steampowered.util.TieredMaterial;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;


public abstract class MachineSteam extends BlockMachine {

    public MachineSteam()
    {
        super(Material.iron);
        this.setHarvestLevel("pickaxe", TieredMaterial.Iron.getPickLevel());
        this.setStepSound(TieredMaterial.Iron.getStepSound());
    }

    @Override
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        TileEntity ent = world.getTileEntity(x, y, z);
        if(ent instanceof TEntitySteam) {
            if(((TEntitySteam)ent).getSteamLogic() != null)
                ((TEntitySteam)ent).getSteamLogic().checkNeighbors();
        }
    }

}
