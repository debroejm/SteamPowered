package com.majorpotato.steampowered.block;

import com.majorpotato.steampowered.util.OreMaterial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

public class ItemBlockOre extends ItemBlockSP implements IItemColor {

    protected BlockOre block;

    public ItemBlockOre(BlockOre block) {
        super(block);
        this.block = block;
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        if(tintIndex == 1) return block.getMaterial().getColor(OreMaterial.Type.ORE);
        else return 0xFFFFFF;
    }
}
