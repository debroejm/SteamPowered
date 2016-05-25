package com.majorpotato.steampowered.block.generic;


import com.majorpotato.steampowered.block.BlockCTSP;
import com.majorpotato.steampowered.util.TieredMaterial;
import com.majorpotato.steampowered.client.rendering.block.ConnectedTexturesRenderer;
import net.minecraft.block.material.Material;

public class BlockMetalWall extends BlockCTSP {
    TieredMaterial mat;
    public BlockMetalWall(TieredMaterial mat) {
        super(Material.iron);
        this.mat = mat;
        this.setHardness(mat.getHardness());
        this.setResistance(mat.getResistance());
        this.setBlockName("blockWall"+mat.getName());
        this.setBlockTextureName("blockWall"+mat.getName());
        this.setHarvestLevel("pickaxe", mat.getPickLevel());
        this.setStepSound(mat.getStepSound());
        ConnectedTexturesRenderer.registerConnectedTexture("blockWall"+mat.getName(), this);
    }
}
