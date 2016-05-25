package com.majorpotato.steampowered.block.ore;

import com.majorpotato.steampowered.block.BlockSP;
import net.minecraft.block.material.Material;

public class BlockOre extends BlockSP
{
    public BlockOre()
    {
        super(Material.rock);
        this.setHardness(3.0F);
        this.setHarvestLevel("pickaxe", 2);
    }
}
