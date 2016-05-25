package com.majorpotato.steampowered.block.generic;


import com.majorpotato.steampowered.block.BlockSP;
import net.minecraft.block.material.Material;

public class BlockMechanusWorld extends BlockSP {

    public BlockMechanusWorld () {
        super(Material.iron);
        this.setHardness(50.0F);
        this.setResistance(50.0F);
        this.setBlockUnbreakable();
        this.setBlockName("blockMechanusWorld");
        this.setBlockTextureName("blockMechanusWorld");
    }
}
