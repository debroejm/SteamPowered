package com.majorpotato.steampowered.recipes;


import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Recipe {
    private ItemStack[] input;
    private ItemStack[] output;
    private ItemStack[] byproduct;
    private double xp;
    private RecipeType recipeType;

    public Recipe(RecipeType type, ItemStack[] input, ItemStack[] output) { this(type, input, output, null, 0.0); }
    public Recipe(RecipeType type, ItemStack[] input, ItemStack output[], ItemStack[] byproduct) { this(type, input, output, byproduct, 0.0); }
    public Recipe(RecipeType type, ItemStack[] input, ItemStack output[], double xp) { this(type, input, output, null, xp); }
    public Recipe(RecipeType type, ItemStack[] input, ItemStack output[], ItemStack[] byproduct, double xp) {
        this.input = input;
        this.output = output;
        this.byproduct = byproduct;
        this.xp = xp;
        this.recipeType = type;
    }

    /**
     * Checks to see if target inventory contains the necessary ingredients for the recipe.
     * @param inventory to check.
     * @return true if all of the ingredients exist in the inventory.
     */
    public boolean checkInput(ItemStack[] inventory)
    {
        for (ItemStack ingredient : input) {
            int inputAmount = 0;
            for (ItemStack slot : inventory) {
                if (slot == null) continue;
                if (ingredient.isItemEqual(slot) || OreDictionary.itemMatches(ingredient, slot, false)) {
                    inputAmount += slot.stackSize;
                }
            }
            if (inputAmount < ingredient.stackSize) return false;
        }
        return true;
    }

    /**
     * Checks to see if target inventory contains enough space for the recipes result/s.
     * @param inventory to check.
     * @return true if there is enough space to place the results.
     */
    public boolean checkOutput(ItemStack[] inventory) {
        int emptySlotsTaken = 0;
        for (ItemStack result : output) {
            int outputAmount = 0;
            int emptySlots = 0;
            for( ItemStack slot : inventory) {
                if (slot == null) { emptySlots++; continue; }
                if (result.isItemEqual(slot) || OreDictionary.itemMatches(result, slot, false)) {
                    outputAmount += (slot.getMaxStackSize() - slot.stackSize);
                }
            }
            if (outputAmount < result.stackSize) {
                emptySlotsTaken++;
                if(emptySlotsTaken > emptySlots) return false;
            }
        }
        return true;
    }

    public ItemStack[] getInput() { return input; }
    public ItemStack[] getOutput() { return output; }
    public ItemStack[] getByProduct() { return byproduct; }
    public double getXP() { return xp; }
    public RecipeType getRecipeType() { return recipeType; }

}
