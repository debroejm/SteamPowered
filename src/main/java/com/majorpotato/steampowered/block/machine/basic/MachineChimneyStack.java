package com.majorpotato.steampowered.block.machine.basic;

import com.majorpotato.steampowered.block.machine.BlockMachine;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityChimneyStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class MachineChimneyStack extends BlockMachine {

    public MachineChimneyStack() {
        super();
        this.setBlockName("machineChimneyStack");
        this.setBlockTextureName("noTex");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TEntityChimneyStack();
    }
}
