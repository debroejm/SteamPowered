package com.majorpotato.steampowered.block.generic.ruined;

import com.majorpotato.steampowered.block.BlockSP;
import com.majorpotato.steampowered.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import java.util.Random;

public abstract class BlockRuined extends BlockSP {

    public BlockRuined() {
        super(Material.iron);
        this.setHardness(3.0F);
        this.setResistance(2.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setStepSound(Block.soundTypeMetal);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random parRand, int parFortune)
    {
        return ModItems.itemScrap;
    }

    @Override
    public int quantityDropped(int parMetadata, int parFortune, Random parRand)
    {
        return (int)(Math.random()*2)+1 + (int)(Math.random()*(parFortune));
    }
}
