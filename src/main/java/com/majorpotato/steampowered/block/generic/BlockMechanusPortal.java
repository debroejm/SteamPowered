package com.majorpotato.steampowered.block.generic;


import com.majorpotato.steampowered.block.BlockSP;
import net.minecraft.block.material.Material;

public class BlockMechanusPortal extends BlockSP {

    public BlockMechanusPortal () {
        super(Material.portal);
        this.setHardness(50.0F);
        this.setResistance(50.0F);
        this.setBlockUnbreakable();
        this.setBlockName("blockMechanusPortal");
        this.setBlockTextureName("blockMechanusPortal");
        this.setLightLevel(0.3F);
        this.setLightOpacity(0);
        this.setBlockBounds(0.0F, 0.2F, 0.0F, 1.0F, 0.8F, 1.0F);
    }

    @Override
    public boolean isOpaqueCube() { return false; }
}
