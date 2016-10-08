package com.majorpotato.steampowered.recipe;


import com.sun.istack.internal.NotNull;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenericRecipe implements Comparable<GenericRecipe> {

    // ****************
    //  STATIC METHODS
    // ****************

    protected static HashMap<RecipeType,ArrayList<RecipeComponentList.Listing>> validRecipeMap = new HashMap<RecipeType,ArrayList<RecipeComponentList.Listing>>();

    /**
     * Checks if an item is a valid input for any recipe of specified type
     * @param type - RecipeType to check with
     * @param stack - ItemStack representation
     * @return True if the item is valid, False otherwise
     */
    public static boolean isItemValid(@Nonnull RecipeType type, ItemStack stack) {
        ArrayList<RecipeComponentList.Listing> list = validRecipeMap.get(type);
        if(list == null || stack == null) return false;
        for(RecipeComponentList.Listing option : list)
            if(option.matches(stack)) return true;
        return false;
    }

    protected static void registerListing(@Nonnull RecipeType type, @Nonnull RecipeComponentList.Listing listing) {
        ArrayList<RecipeComponentList.Listing> list = validRecipeMap.get(type);
        if(list == null) {
            list = new ArrayList<RecipeComponentList.Listing>();
            validRecipeMap.put(type, list);
        }
        if(!list.contains(listing)) list.add(listing);
    }



    // **********************
    //  CLASS IMPLEMENTATION
    // **********************

    protected RecipeComponentList inputs;
    protected RecipeComponentList outputs;

    protected int priority = 0;

    public GenericRecipe(RecipeComponentList inputs, RecipeComponentList outputs) { this(inputs, outputs, 0); }
    public GenericRecipe(RecipeComponentList inputs, RecipeComponentList outputs, int priority) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.priority = priority;
        for(RecipeComponentList.Listing entry : inputs.getComponents()) registerListing(getRecipeType(), entry);
    }

    public RecipeComponentList getInputs() { return inputs; }
    public RecipeComponentList getOutputs() { return outputs; }

    public RecipeType getRecipeType() { return RecipeType.GENERIC; }
    public String getName() { return getRecipeType().getName(); }

    public int getPriority() { return priority; }

    /**
     * Checks an inventory to see if it has enough items for this recipe to process.
     * @param inventory to check; must implement IInventory
     * @return True if this recipe is processable, False otherwise
     */
    public boolean checkInventory(IInventory inventory) { return checkInventory(inventory, 0, inventory.getSizeInventory(), 0, inventory.getSizeInventory()); }

    /**
     * Checks an inventory to see if it has enough items for this recipe to process.
     * @param inventory to check; must implement IInventory
     * @param startIndex - Index of array to start searching at (Inclusive)
     * @param endIndex - Index of array to stop searching at (Exclusive)
     * @return True if this recipe is processable, False otherwise
     */
    public boolean checkInventory(IInventory inventory, int startIndex, int endIndex) { return checkInventory(inventory, startIndex, endIndex, startIndex, endIndex); }

    /**
     * Checks an inventory to see if it has enough items for this recipe to process.
     * @param inventory to check; must implement IInventory
     * @param startIndexInput - Index of array to start searching at (Input|Inclusive)
     * @param endIndexInput - Index of array to stop searching at (Input|Exclusive)
     * @param startIndexOutput - Index of array to start searching at (Output|Inclusive)
     * @param endIndexOutput - Index of array to stop searching at (Output|Exclusive)
     * @return True if this recipe is processable, False otherwise
     */
    public boolean checkInventory(IInventory inventory, int startIndexInput, int endIndexInput, int startIndexOutput, int endIndexOutput) {
        return inputs.inventoryMatches(inventory, startIndexInput, endIndexInput)
           && outputs.inventoryHasRoom(inventory, startIndexOutput, endIndexOutput);
    }

    /**
     * Processes an inventory by removing required inputs and adding resulting outputs.
     * @param inventory to check; must implement IInventory
     */
    public void processInventory(IInventory inventory) { processInventory(inventory, 0, inventory.getSizeInventory(), 0, inventory.getSizeInventory()); }

    /**
     * Processes an inventory by removing required inputs and adding resulting outputs.
     * @param inventory to check; must implement IInventory
     * @param startIndex - Index of array to start searching at (Inclusive)
     * @param endIndex - Index of array to stop searching at (Exclusive)
     */
    public void processInventory(IInventory inventory, int startIndex, int endIndex) { processInventory(inventory, startIndex, endIndex, startIndex, endIndex); }


    /**
     * Processes an inventory by removing required inputs and adding resulting outputs.
     * @param inventory to check; must implement IInventory
     * @param startIndexInput - Index of array to start searching at (Input|Inclusive)
     * @param endIndexInput - Index of array to stop searching at (Input|Exclusive)
     * @param startIndexOutput - Index of array to start searching at (Output|Inclusive)
     * @param endIndexOutput - Index of array to stop searching at (Output|Exclusive)
     */
    public void processInventory(IInventory inventory, int startIndexInput, int endIndexInput, int startIndexOutput, int endIndexOutput) {
        inputs.removeFromInventory(inventory, startIndexInput, endIndexInput);
        outputs.addToInventory(inventory, startIndexOutput, endIndexOutput);
    }

    @Override
    public int compareTo(@NotNull GenericRecipe other) {
        return priority-other.priority;
    }
}
