package com.majorpotato.steampowered.block.machine.basic;

import com.majorpotato.steampowered.SteamPowered;
import com.majorpotato.steampowered.block.machine.BlockMachine;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityAlloyFurnace;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineAlloyFurnace extends BlockMachine
{
    public MachineAlloyFurnace()
    {
        super();
        this.setBlockName("machineAlloyFurnace");
        this.setBlockTextureName("noTex");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TEntityAlloyFurnace();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            TileEntity ent = world.getTileEntity(x, y, z);
            if(ent instanceof TEntityAlloyFurnace) {
                if (((TEntityAlloyFurnace)ent).hasMultiblock()) {
                    FMLNetworkHandler.openGui(player, SteamPowered.instance, 0, world, x, y, z);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileEntity ent = world.getTileEntity(x, y, z);
        if(ent instanceof TEntityAlloyFurnace) {
            TEntityAlloyFurnace tAF = (TEntityAlloyFurnace)ent;
            if(tAF.getMultiblock() != null) {
                return tAF.getMultiblock().getComparatorOutput();
            }
        }
        return 0;
    }
}
