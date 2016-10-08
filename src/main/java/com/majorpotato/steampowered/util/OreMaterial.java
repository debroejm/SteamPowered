package com.majorpotato.steampowered.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Utility Class representing possible existing Materials, whether they are simple metals or complex alloys.
 * @author MajorPotato
 */
public class OreMaterial {

    public static OreMaterial COPPER;
    public static OreMaterial TIN;
    public static OreMaterial BRASS;
    public static OreMaterial STEEL;
    public static OreMaterial IRON;
    public static OreMaterial GOLD;

    public static final List<OreMaterial> ALL = new ArrayList<OreMaterial>();
    public static final List<OreMaterial> INGOTS = new ArrayList<OreMaterial>();
    public static final List<OreMaterial> ORES = new ArrayList<OreMaterial>();

    public static void registerMaterials() {

        COPPER  = new OreMaterial("Copper", 1,  3.0F,   15.0F,  0xEF901B)
                .setColor(Type.ORE, 0xCCFFD8);
        TIN     = new OreMaterial("Tin",    1,  2.65F,  8.0F,   0xFFFFFF);
        BRASS   = new OreMaterial("Brass",  2,  2.7F,   12.0F,  0xFFE045);
        STEEL   = new OreMaterial("Steel",  3,  4.5F,   50.0F,  0x626262);
        IRON    = new OreMaterial("Iron",   1,  3.0F,   15.0F)
                .setInstance(Type.ORE, Blocks.IRON_ORE)
                .setInstance(Type.INGOT, Items.IRON_INGOT)
                .setInstance(Type.BLOCK, Blocks.IRON_BLOCK);
        GOLD    = new OreMaterial("Gold",   2,  3.0F,   15.0F)
                .setInstance(Type.ORE, Blocks.GOLD_ORE)
                .setInstance(Type.INGOT, Items.GOLD_INGOT)
                .setInstance(Type.BLOCK, Blocks.GOLD_BLOCK);

        INGOTS.add(COPPER);
        INGOTS.add(TIN);
        INGOTS.add(BRASS);
        INGOTS.add(STEEL);
        INGOTS.add(IRON);
        INGOTS.add(GOLD);

        ORES.add(COPPER);
        ORES.add(TIN);
        ORES.add(IRON);
        ORES.add(GOLD);

    }






    private String name;
    private int harvestLevel;
    private float hardness;
    private float resistance;
    private boolean autoCreate;

    public OreMaterial(String name, int harvestLevel, float hardness, float resistance) { this(name, harvestLevel, hardness, resistance, 0xFFFFFF, false); }
    public OreMaterial(String name, int harvestLevel, float hardness, float resistance, int color) { this(name, harvestLevel, hardness, resistance, color, true); }
    public OreMaterial(String name, int harvestLevel, float hardness, float resistance, int color, boolean autoCreate) {
        this.name = name;
        this.harvestLevel = harvestLevel;
        this.hardness = hardness;
        this.resistance = resistance;
        this.autoCreate = autoCreate;
        ALL.add(this);
        for(Type type : Type.values()) colorMap.put(type, color);
    }

    public String getName() { return name; }
    public int getHarvestLevel() { return harvestLevel; }
    public float getHardness() { return hardness; }
    public float getResistance() { return resistance; }
    public boolean doAutoCreate() { return autoCreate; }

    public String getName(Type type) { return type+name; }

    private HashMap<Type, Integer> colorMap = new HashMap<Type, Integer>();
    public int getColor(Type type) { return colorMap.get(type); }
    public OreMaterial setColor(Type type, int color) {
        colorMap.put(type, color);
        return this;
    }

    private HashMap<Type, Object> instanceMap = new HashMap<Type, Object>();
    public Object getInstance(Type type) { return instanceMap.get(type); }
    public OreMaterial setInstance(Type type, Object instance) {
        switch(type) {
            case ORE:
            case BLOCK:
                if( !(instance instanceof Block) ) throw new IllegalArgumentException("Instance of Type '"+type+"' must be a Block");
                break;
            case INGOT:
            case DUST:
                if( !(instance instanceof Item) ) throw new IllegalArgumentException("Instance of Type '"+type+"' must be an Item");
                break;
            default:
                throw new IllegalArgumentException("Unknown Type");
        }
        instanceMap.put(type, instance);

        return this;
    }

    public enum Type {
        ORE("ore"),
        INGOT("ingot"),
        BLOCK("block"),
        DUST("dust");

        private String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() { return prefix; }
        @Override
        public String toString() { return getPrefix(); }
    }

}
