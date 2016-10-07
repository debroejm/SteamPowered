package com.majorpotato.steampowered.init;

import com.majorpotato.steampowered.item.*;
import com.majorpotato.steampowered.util.OreMaterial;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;

public class ModItems {

    // Ingot Mapping
    private static HashMap<OreMaterial, ItemIngot> ingots = new HashMap<OreMaterial, ItemIngot>();
    public static Item getIngot(OreMaterial material) {
        return ingots.get(material);
    }

    public static void registerItems() {

        // Ingots
        for(OreMaterial material : OreMaterial.INGOTS) {
            if(!material.doAutoCreate() || material.getInstance(OreMaterial.Type.INGOT) != null) continue;
            ItemIngot ingot = new ItemIngot(material);
            GameRegistry.register(ingot);
            ingots.put(material, ingot);
        }
    }

    public static void registerColorHandlers() {
        for(ItemIngot ingot : ingots.values()) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ingot, ingot);
        }
    }

    public static void registerOreDictionaryEntries() {

        // Register Ingots With Ore Dictionary
        for(ItemIngot ingot : ingots.values()) {
            OreDictionary.registerOre(ingot.getName(), ingot);
        }
    }
}
