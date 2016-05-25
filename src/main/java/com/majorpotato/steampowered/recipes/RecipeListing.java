package com.majorpotato.steampowered.recipes;

import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeListing {
    private static HashMap<RecipeType, HashMap<Item, List<Recipe>>> recipesByInput = new HashMap<RecipeType, HashMap<Item, List<Recipe>>>();
    private static HashMap<RecipeType, HashMap<Item, List<Recipe>>> recipesByOutput = new HashMap<RecipeType, HashMap<Item, List<Recipe>>>();

    static {
        // 3 Copper Ingots + 1 Zinc Ingot = 4 Brass Ingots
        addRecipe( new AlloyFurnaceRecipe(
                new ItemStack[]{
                        new ItemStack(ModItems.ingotCopper, 3),
                        new ItemStack(ModItems.ingotZinc, 1)
                },
                new ItemStack[]{
                        new ItemStack(ModItems.ingotBrass, 4)
                },
                0.8,
                1000 )
        );

        // 1 Iron Ingot + 1 Ash = 1 Steel Ingot
        addRecipe( new AlloyFurnaceRecipe(
                new ItemStack[]{
                        new ItemStack(Items.iron_ingot, 1),
                        new ItemStack(ModItems.itemAsh, 1)
                },
                new ItemStack[]{
                        new ItemStack(ModItems.ingotSteel, 1)
                },
                1.0,
                1800 )
        );

        // 8 Scrap = 1 Iron Ingot
        addRecipe( new AlloyFurnaceRecipe(
                new ItemStack[]{
                        new ItemStack(ModItems.itemScrap, 8)
                },
                new ItemStack[]{
                        new ItemStack(Items.iron_ingot, 1)
                },
                0.4,
                1500 )
        );

        // Copper Ore Doubling
        addRecipe( new AlloyFurnaceRecipe(
                new ItemStack[]{
                        new ItemStack(ModBlocks.oreCopper, 1)
                },
                new ItemStack[]{
                        new ItemStack(ModItems.ingotCopper, 2)
                },
                0.6,
                1000 )
        );

        // Zinc Ore Doubling
        addRecipe( new AlloyFurnaceRecipe(
                new ItemStack[]{
                        new ItemStack(ModBlocks.oreZinc, 1)
                },
                new ItemStack[]{
                        new ItemStack(ModItems.ingotZinc, 2)
                },
                0.6,
                500 )
        );

        // Iron Ore Doubling
        addRecipe( new AlloyFurnaceRecipe(
                new ItemStack[]{
                        new ItemStack(Blocks.iron_ore, 1)
                },
                new ItemStack[]{
                        new ItemStack(Items.iron_ingot, 2)
                },
                0.7,
                1500 )
        );

        // Gold Ore Doubling
        addRecipe( new AlloyFurnaceRecipe(
                new ItemStack[]{
                        new ItemStack(Blocks.gold_ore, 1)
                },
                new ItemStack[]{
                        new ItemStack(Items.gold_ingot, 2)
                },
                1.0,
                1000 )
        );
    }

    /**
     * Adds a new recipe to the internal recipe mapping.
     * @param newRecipe to add.
     */
    public static void addRecipe(Recipe newRecipe)
    {
        RecipeType type = newRecipe.getRecipeType();

        // Grab the listing of Recipes By Input for Alloy Furnaces
        HashMap<Item, List<Recipe>> recipeListByInput;
        if(recipesByInput.containsKey(type))
            recipeListByInput = recipesByInput.get(type);
        else {
            recipeListByInput = new HashMap<Item, List<Recipe>>();
            recipesByInput.put(type, recipeListByInput);
        }

        // Grab the listing of Recipes By Output for Alloy Furnaces
        HashMap<Item, List<Recipe>> recipeListByOutput;
        if(recipesByOutput.containsKey(type))
            recipeListByOutput = recipesByOutput.get(type);
        else {
            recipeListByOutput = new HashMap<Item, List<Recipe>>();
            recipesByOutput.put(type, recipeListByOutput);
        }

        // Index the new Recipe by its Inputs
        for(ItemStack stack : newRecipe.getInput()) {
            if(recipeListByInput.containsKey(stack.getItem()))
                recipeListByInput.get(stack.getItem()).add(newRecipe);
            else {
                List<Recipe> tList = new ArrayList<Recipe>();
                tList.add(newRecipe);
                recipeListByInput.put(stack.getItem(), tList);
            }
        }

        // Index the new Recipe by its Outputs
        for(ItemStack stack : newRecipe.getOutput()) {
            if(recipeListByOutput.containsKey(stack.getItem()))
                recipeListByOutput.get(stack.getItem()).add(newRecipe);
            else {
                List<Recipe> tList = new ArrayList<Recipe>();
                tList.add(newRecipe);
                recipeListByOutput.put(stack.getItem(), tList);
            }
        }
    }

    /**
     * Returns a list of recipes of RecipeType that match the specified input.
     * @param type of recipe to retrieve.
     * @param input item to match
     * @return List of recipes that match the input item.
     */
    public static List<Recipe> getRecipesByInput(RecipeType type, Item input) {
        HashMap<Item, List<Recipe>> inputMap = recipesByInput.get(type);
        if(inputMap == null) return null;
        return inputMap.get(input);
    }

    /**
     * Returns a list of recipes of RecipeType that match the specified output.
     * @param type of recipe to retrieve.
     * @param output item to match
     * @return list of recipes that match the output item.
     */
    public static List<Recipe> getRecipesByOutput(RecipeType type, Item output) {
        HashMap<Item, List<Recipe>> outputMap = recipesByOutput.get(type);
        if(outputMap == null) return null;
        return outputMap.get(output);
    }
}
