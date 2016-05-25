package com.majorpotato.steampowered.block.machine.mechanical;


import com.majorpotato.steampowered.tileentity.machine.mechanical.TEntityGear;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineGear extends MachineMechanical {
    public MachineGear()
    {
        super();
        this.setBlockName("machineGear");
        this.setBlockTextureName("noTex");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TEntityGear();
    }
}
