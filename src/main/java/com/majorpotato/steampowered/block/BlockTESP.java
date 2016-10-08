package com.majorpotato.steampowered.block;


import com.majorpotato.steampowered.block.BlockSP;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;

public abstract class BlockTESP extends BlockSP implements ITileEntityProvider {
    public BlockTESP(Material material) {
        super(material);
    }
}
