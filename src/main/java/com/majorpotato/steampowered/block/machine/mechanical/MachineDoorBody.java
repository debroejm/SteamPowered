package com.majorpotato.steampowered.block.machine.mechanical;


import com.majorpotato.steampowered.tileentity.machine.mechanical.TEntityDoorBody;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineDoorBody extends MachineMechanical {
    public MachineDoorBody()
    {
        super();
        this.setBlockName("machineDoorBody");
        this.setBlockTextureName("noTex");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TEntityDoorBody();
    }
}
