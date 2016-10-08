package com.majorpotato.steampowered.init;

import com.majorpotato.steampowered.recipe.*;
import com.majorpotato.steampowered.util.OreMaterial;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class ModRecipes {

    protected static HashMap<RecipeType, ArrayList<GenericRecipe>> recipesMap = new HashMap<RecipeType, ArrayList<GenericRecipe>>();

    public static void initRecipes() {

        // ************************
        //  Alloy Smelting Recipes
        // ************************

        ArrayList<GenericRecipe> recipes_alloySmelting = new ArrayList<GenericRecipe>();

        // Ore Duplication Recipes
        for(OreMaterial material : OreMaterial.ORES) {
            if(material.getInstance(OreMaterial.Type.INGOT) != null) {
                recipes_alloySmelting.add(new AlloySmeltingRecipe(
                        new RecipeComponentList(material.getName(OreMaterial.Type.ORE),1),
                        new RecipeComponentList(material.getInstance(OreMaterial.Type.INGOT),2),
                        700,
                        AlloySmeltingRecipe.PRIORITY_SMELTING));
                if(material.doAutoCreate() && material.getInstance(OreMaterial.Type.ORE) != null)
                    GameRegistry.addSmelting(new ItemStack((Block)material.getInstance(OreMaterial.Type.ORE)), new ItemStack((Item)material.getInstance(OreMaterial.Type.INGOT)), 0.5f);
            }
        }

        // Alloy Recipes
        recipes_alloySmelting.add(new AlloySmeltingRecipe(
                new RecipeComponentList(OreMaterial.COPPER.getName(OreMaterial.Type.INGOT),3,OreMaterial.TIN.getName(OreMaterial.Type.INGOT),1),
                new RecipeComponentList(OreMaterial.BRASS.getName(OreMaterial.Type.INGOT),4),
                1250,
                AlloySmeltingRecipe.PRIORITY_ALLOYS ));
        recipes_alloySmelting.add(new AlloySmeltingRecipe(
                new RecipeComponentList(OreMaterial.IRON.getName(OreMaterial.Type.INGOT),1, new ItemStack(Items.COAL,4,0)),
                new RecipeComponentList(OreMaterial.STEEL.getName(OreMaterial.Type.INGOT),1),
                1800,
                AlloySmeltingRecipe.PRIORITY_ALLOYS ));
        recipes_alloySmelting.add(new AlloySmeltingRecipe(
                new RecipeComponentList(OreMaterial.IRON.getName(OreMaterial.Type.INGOT),1, new ItemStack(Items.COAL,8,1)),
                new RecipeComponentList(OreMaterial.STEEL.getName(OreMaterial.Type.INGOT),1),
                1800,
                AlloySmeltingRecipe.PRIORITY_ALLOYS ));

        recipesMap.put(RecipeType.ALLOY_SMELTING, recipes_alloySmelting);
    }

    public static void postInitRecipes() {
        // Sort Custom Recipe Listings by Priority
        RecipeComparator comparator = new RecipeComparator();
        for(ArrayList<GenericRecipe> list : recipesMap.values()) list.sort(comparator);
    }

    public static GenericRecipe getValidRecipe(RecipeType type, IInventory inventory) { return getValidRecipe(type, inventory, 0, inventory.getSizeInventory(), 0, inventory.getSizeInventory()); }
    public static GenericRecipe getValidRecipe(RecipeType type, IInventory inventory, int startIndex, int endIndex) { return getValidRecipe(type, inventory, startIndex, endIndex, startIndex, endIndex); }
    public static GenericRecipe getValidRecipe(RecipeType type, IInventory inventory, int startIndexInput, int endIndexInput, int startIndexOutput, int endIndexOutput) {
        ArrayList<GenericRecipe> recipes = recipesMap.get(type);
        if(recipes == null) return null;
        for(GenericRecipe recipe : recipes) {
            if(recipe.checkInventory(inventory, startIndexInput, endIndexInput, startIndexOutput, endIndexOutput)) return recipe;
        }
        return null;
    }

    protected static class RecipeComparator implements Comparator<GenericRecipe> {

        @Override
        public int compare(GenericRecipe a, GenericRecipe b) {
            return a.compareTo(b);
        }
    }
}
