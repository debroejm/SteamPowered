package com.majorpotato.steampowered.block.machine.mechanical;


import com.majorpotato.steampowered.block.machine.BlockMachine;
import net.minecraft.block.material.Material;

public abstract class MachineMechanical extends BlockMachine {
    public MachineMechanical()
    {
        super(Material.iron);
        this.setHarvestLevel("pickaxe", 2);
    }
}
