package com.majorpotato.steampowered.recipe;

import net.minecraft.item.ItemStack;

public class AlloySmeltingRecipe extends GenericRecipe {

    public static final int PRIORITY_SMELTING = 0;
    public static final int PRIORITY_ALLOYS = 10;

    protected int requiredHeat;

    public AlloySmeltingRecipe(RecipeComponentList inputs, RecipeComponentList outputs, int requiredHeat, int priority) {
        super(inputs, outputs, priority);
        this.requiredHeat = requiredHeat;
    }

    /**
     * @return the amount of heat needed in order for the recipe to be valid.
     */
    public int getRequiredHeat() { return requiredHeat; }

    @Override
    public RecipeType getRecipeType() { return RecipeType.ALLOY_SMELTING; }
}
