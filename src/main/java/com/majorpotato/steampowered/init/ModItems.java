package com.majorpotato.steampowered.init;

import com.majorpotato.steampowered.item.*;
import com.majorpotato.steampowered.item.ingot.IngotBrass;
import com.majorpotato.steampowered.item.ingot.IngotCopper;
import com.majorpotato.steampowered.item.ingot.IngotSteel;
import com.majorpotato.steampowered.item.ingot.IngotZinc;
import com.majorpotato.steampowered.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    //public static final Item testItem = new ItemTest();
    public static final Item ingotCopper = new IngotCopper();
    public static final Item ingotZinc = new IngotZinc();
    public static final Item ingotBrass = new IngotBrass();
    public static final Item ingotSteel = new IngotSteel();
    public static final Item itemAsh = new ItemAsh();
    public static final Item itemScrap = new ItemScrap();
    public static final Item itemInstantHeat = new ItemInstantHeat();
    public static final Item itemGear = new ItemGear();

    public static final Item toolPressureGauge = new ToolDataCollector();
    public static final Item toolWrench = new ToolWrench();

    public static void init()
    {
        //GameRegistry.registerItem(testItem, "testItem");
        GameRegistry.registerItem(ingotCopper, "ingotCopper");
        GameRegistry.registerItem(ingotZinc, "ingotZinc");
        GameRegistry.registerItem(ingotBrass, "ingotBrass");
        GameRegistry.registerItem(ingotSteel, "ingotSteel");
        GameRegistry.registerItem(itemAsh, "itemAsh");
        GameRegistry.registerItem(itemScrap, "itemScrap");
        GameRegistry.registerItem(toolPressureGauge, "toolPressureGauge");
        GameRegistry.registerItem(toolWrench, "toolWrench");
        GameRegistry.registerItem(itemInstantHeat, "itemInstantHeat");
        GameRegistry.registerItem(itemGear, "itemGear");

        registerOre("ingotCopper", ingotCopper);
        registerOre("ingotTin", ingotZinc);
        registerOre("ingotBronze", ingotBrass);
        registerOre("ingotSteel", ingotSteel);
    }
}
