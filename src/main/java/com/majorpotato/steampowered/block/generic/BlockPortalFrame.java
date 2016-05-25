package com.majorpotato.steampowered.block.generic;

import com.majorpotato.steampowered.block.BlockCTSP;
import com.majorpotato.steampowered.util.TieredMaterial;
import com.majorpotato.steampowered.client.rendering.block.ConnectedTexturesRenderer;
import net.minecraft.block.material.Material;


public class BlockPortalFrame extends BlockCTSP {
    public BlockPortalFrame() {
        super(Material.iron);
        this.setHardness(TieredMaterial.Steel.getHardness());
        this.setResistance(TieredMaterial.Steel.getResistance());
        this.setBlockName("blockPortalFrame");
        this.setBlockTextureName("blockPortalFrame");
        this.setHarvestLevel("pickaxe", TieredMaterial.Steel.getPickLevel());
        this.setStepSound(TieredMaterial.Steel.getStepSound());
        ConnectedTexturesRenderer.registerConnectedTexture("blockPortalFrame", this);
    }
}
