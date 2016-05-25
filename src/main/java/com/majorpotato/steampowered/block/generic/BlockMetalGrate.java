package com.majorpotato.steampowered.block.generic;


import com.majorpotato.steampowered.block.BlockSP;
import com.majorpotato.steampowered.util.TieredMaterial;
import net.minecraft.block.material.Material;

public class BlockMetalGrate extends BlockSP {
    TieredMaterial mat;
    public BlockMetalGrate(TieredMaterial mat) {
        super(Material.iron);
        this.mat = mat;
        this.setHardness(mat.getHardness());
        this.setResistance(mat.getResistance());
        this.setBlockName("blockGrate"+mat.getName());
        this.setBlockTextureName("blockGrate"+mat.getName());
        this.setHarvestLevel("pickaxe", mat.getPickLevel());
        this.setStepSound(mat.getStepSound());
    }

    @Override
    public boolean isOpaqueCube() { return false; }
}
