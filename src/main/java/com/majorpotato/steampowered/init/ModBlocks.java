package com.majorpotato.steampowered.init;


import com.majorpotato.steampowered.block.BlockOre;
import com.majorpotato.steampowered.block.BlockSP;
import com.majorpotato.steampowered.block.ItemBlockOre;
import com.majorpotato.steampowered.block.ItemBlockSP;
import com.majorpotato.steampowered.block.machine.basic.BlockFireBox;
import com.majorpotato.steampowered.block.machine.basic.BlockHotPlate;
import com.majorpotato.steampowered.tileentity.machine.basic.TEFireBox;
import com.majorpotato.steampowered.tileentity.machine.basic.TEHotPlate;
import com.majorpotato.steampowered.util.OreMaterial;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;

public class ModBlocks {

    // Ore Mapping
    private static HashMap<OreMaterial, BlockOre> ores = new HashMap<OreMaterial, BlockOre>();
    public static Block getOre(OreMaterial material) {
        return ores.get(material);
    }

    // Machines - Basic
    public static BlockHotPlate machineHotPlate = new BlockHotPlate();
    public static BlockFireBox machineFireBox = new BlockFireBox();



    public static void registerBlocks() {

        // Ores
        for(OreMaterial material : OreMaterial.ORES) {
            if(!material.doAutoCreate() || material.getInstance(OreMaterial.Type.ORE) != null) continue;
            BlockOre ore = new BlockOre(material);
            registerBlock(ore);
            ores.put(material, ore);
        }

        // Machines - Basic
        registerBlock(machineHotPlate);
        registerBlock(machineFireBox);

    }

    protected static void registerBlock(BlockSP block) {
        GameRegistry.register(block);
        ItemBlockSP item_block;
        if(block instanceof BlockOre) item_block = new ItemBlockOre((BlockOre)block);
        else item_block = new ItemBlockSP(block);
        GameRegistry.register(item_block);
    }

    public static void registerColorHandlers() {
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(machineHotPlate, machineHotPlate);
        for(BlockOre ore : ores.values()) {
            Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(ore, ore);
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler((ItemBlockOre) Item.getItemFromBlock(ore), (ItemBlockOre)Item.getItemFromBlock(ore));
        }

    }

    public static void registerOreDictionaryEntries() {

        // Register Ores With Ore Dictionary
        for(BlockOre ore : ores.values())
            OreDictionary.registerOre(ore.getName(), ore);

    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TEHotPlate.class, "TileEntityHotPlate");
        GameRegistry.registerTileEntity(TEFireBox.class, "TileEntityFireBox");
    }
}
