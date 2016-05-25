package com.majorpotato.steampowered.recipes;


import net.minecraft.item.ItemStack;

public class AlloyFurnaceRecipe extends Recipe {

    private int heatNeeded;

    public AlloyFurnaceRecipe(ItemStack[] input, ItemStack[] output, double xp, int heatNeeded) {
        super(RecipeType.ALLOY_FURNACE, input, output, xp);
        this.heatNeeded = heatNeeded;
    }

    public int getHeatNeeded() { return heatNeeded; }
}
