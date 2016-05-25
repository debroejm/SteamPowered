package com.majorpotato.steampowered.util;


import net.minecraft.block.Block;

public class TieredMaterial {

    public static final TieredMaterial Wood = new TieredMaterial("Wood", 1.5F, 1.5F, 1, Block.soundTypeWood);
    public static final TieredMaterial Stone = new TieredMaterial("Stone", 1.0F, 8.0F, 1, Block.soundTypeStone);
    public static final TieredMaterial Rusty = new TieredMaterial("Rusty", 0.5F, 5.0F, 1, Block.soundTypeMetal);
    public static final TieredMaterial Tin = new TieredMaterial("Tin", 1.0F, 6.5F, 1, Block.soundTypeMetal);
    public static final TieredMaterial Copper = new TieredMaterial("Copper", 2.0F, 8.0F, 1, Block.soundTypeMetal, 200, 300);
    public static final TieredMaterial Brass = new TieredMaterial("Brass", 3.5F, 10.0F, 2, Block.soundTypeMetal, 500, 800);
    public static final TieredMaterial Iron = new TieredMaterial("Iron", 5.0F, 10.0F, 1, Block.soundTypeMetal);
    public static final TieredMaterial Steel = new TieredMaterial("Steel", 6.0F, 15.0F, 2, Block.soundTypeMetal, 1000, 1500);

    private String name;
    private float hardness;
    private float resistance;
    private int pickLevel;
    private Block.SoundType stepSound;
    private int steamCapacity;
    private int steamOverload;

    public TieredMaterial(String name, float hardness, float resistance, int pickLevel, Block.SoundType stepSound, int steamCapacity, int steamOverload) {
        this.name = name;
        this.hardness = hardness;
        this.resistance = resistance;
        this.pickLevel = pickLevel;
        this.stepSound = stepSound;
        this.steamCapacity = steamCapacity;
        this.steamOverload = steamOverload;
    }

    public TieredMaterial(String name, float hardness, float resistance, int pickLevel, Block.SoundType stepSound) {
        this(name, hardness, resistance, pickLevel, stepSound, 100, 200);
    }


    public String getName() { return name; }
    public float getHardness() { return hardness; }
    public float getResistance() { return resistance; }
    public int getPickLevel() { return pickLevel; }
    public Block.SoundType getStepSound() { return stepSound; }

    public int getSteamCapacity() { return steamCapacity; }
    public int getSteamOverload() { return steamOverload; }
}
