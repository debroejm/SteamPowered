package com.majorpotato.steampowered.block.machine;

import com.majorpotato.steampowered.block.ItemBlockSP;
import com.majorpotato.steampowered.block.machine.BlockMachine;
import net.minecraft.block.Block;

public class ItemMachine extends ItemBlockSP {
    private final BlockMachine machineBlock;

    public ItemMachine(Block block) {
        super(block);
        this.machineBlock = (BlockMachine) block;
        setUnlocalizedName("steampowered.machine");
    }
}
