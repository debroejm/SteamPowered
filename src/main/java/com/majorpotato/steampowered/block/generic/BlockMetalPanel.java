package com.majorpotato.steampowered.block.generic;


import com.majorpotato.steampowered.block.BlockSP;
import com.majorpotato.steampowered.util.TieredMaterial;
import net.minecraft.block.material.Material;

public class BlockMetalPanel extends BlockSP {
    TieredMaterial mat;
    public BlockMetalPanel(TieredMaterial mat) {
        super(Material.iron);
        this.mat = mat;
        this.setHardness(mat.getHardness());
        this.setResistance(mat.getResistance());
        this.setBlockName("blockPanel"+mat.getName());
        this.setBlockTextureName("blockPanel"+mat.getName());
        this.setHarvestLevel("pickaxe", mat.getPickLevel());
        this.setStepSound(mat.getStepSound());
    }
}
