package com.majorpotato.steampowered.block.machine;

import com.majorpotato.steampowered.block.BlockTileSP;
import net.minecraft.block.material.Material;

public abstract class BlockMachine extends BlockTileSP {

    public BlockMachine(Material material)
    {
        super(material);
        this.setHardness(3.5F);
        this.setResistance(5.0F);
        this.setHarvestLevel("pickaxe", 1);
    }

    public BlockMachine() {
        super(Material.rock);
        this.setHardness(3.5F);
        this.setResistance(5.0F);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}
