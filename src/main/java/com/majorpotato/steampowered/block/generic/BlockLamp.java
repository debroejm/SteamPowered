package com.majorpotato.steampowered.block.generic;


import com.majorpotato.steampowered.block.BlockSP;
import com.majorpotato.steampowered.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class BlockLamp extends BlockSP {
    public BlockLamp () {
        super(Material.redstoneLight);
        this.setStepSound(Block.soundTypeGlass);
        this.setHardness(0.1F);
        this.setResistance(0.5F);
        this.setBlockName("blockLamp");
        this.setBlockTextureName("blockLamp");
        this.setLightLevel(1.0F);
    }

    @Override
    public Item getItemDropped(int parMetadata, Random parRand, int parFortune)
    {
        return Items.glowstone_dust;
    }

    @Override
    public int quantityDropped(int parMetadata, int parFortune, Random parRand)
    {
        int count = parRand.nextInt(2+parFortune)+1;
        if(count < 1) count = 1; if(count > 4) count = 4; if(parFortune == 3) count = 4;
        return count;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
