package com.majorpotato.steampowered.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes
{
    public static void init()
    {
        //GameRegistry.addRecipe(new ItemStack(ModItems.testItem), " s ", "sss", " s ", 's', new ItemStack(Items.stick));
        //GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockTest), new ItemStack(ModItems.testItem), new ItemStack(ModItems.testItem));
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ingotSteel), " c ", "csc", " c ", 's', "ingotIron", 'c', new ItemStack(Items.coal)));
        //GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingotBrass), "ingotCopper", "ingotCopper", "ingotCopper", "ingotTin"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machineFirebox), "scs", "c c", "sss", 's', new ItemStack(Blocks.cobblestone), 'c', "ingotCopper"));
        GameRegistry.addRecipe(new ItemStack(ModBlocks.machineAlloyFurnace), "sis", "d d", "ooo", 's', new ItemStack(Blocks.stone), 'i', new ItemStack(Blocks.iron_bars), 'd', new ItemStack(Blocks.trapdoor), 'o', new ItemStack(Blocks.obsidian));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machineSteamBoiler), "isi", "i i", "bfb", 'i', "ingotIron", 's', new ItemStack(Blocks.iron_bars), 'b', "ingotBronze", 'f', new ItemStack(Blocks.furnace)));
        ItemStack pipeOut = new ItemStack(ModBlocks.machineSteamPipeBrass); pipeOut.stackSize = 4;
        GameRegistry.addRecipe(new ShapedOreRecipe(pipeOut, " c ", "bib", " c ", 'c', "ingotCopper", 'b', new ItemStack(Blocks.iron_bars), 'i', "ingotIron"));
        ItemStack pipeHOut = new ItemStack(ModBlocks.machineSteamPipeSteel); pipeOut.stackSize = 4;
        GameRegistry.addRecipe(new ShapedOreRecipe(pipeHOut, " s ", "bib", " s ", 's', "ingotSteel", 'b', new ItemStack(Blocks.iron_bars), 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockLamp, 2), "dwd", "bgb", "dwd", 'd', "dustGlowstone", 'w', "slabWood", 'b', new ItemStack(Blocks.iron_bars), 'g', "glowstone"));

        // Furnace Recipes
        GameRegistry.addSmelting(new ItemStack(ModBlocks.oreCopper), new ItemStack(ModItems.ingotCopper), 3.5F);
        GameRegistry.addSmelting(new ItemStack(ModBlocks.oreZinc), new ItemStack(ModItems.ingotZinc), 3.5F);
    }
}
