package com.majorpotato.steampowered.world;


import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.util.OreMaterial;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenSP implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch(world.provider.getDimension()) {
            case 0: generateOverworld(random, chunkX*16, chunkZ*16, world); break;
            case 1: generateEnd(random, chunkX*16, chunkZ*16, world); break;
            case -1: generateNether(random, chunkX*16, chunkZ*16, world); break;
        }
    }

    protected void generateOverworld(Random random, int xPos, int zPos, World world) {
        this.addOreSpawn(ModBlocks.getOre(OreMaterial.COPPER), world, random, xPos, zPos, 3, 7, 75, 32, 128);
        this.addOreSpawn(ModBlocks.getOre(OreMaterial.TIN), world, random, xPos, zPos, 6, 9, 40, 32, 256);
    }

    protected void generateEnd(Random random, int xPos, int zPos, World world) {

    }

    protected void generateNether(Random random, int xPos, int zPos, World world) {

    }

    protected void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int minVeinSize, int maxVeinSize, int chancesToSpawn, int minY, int maxY )
    {
        WorldGenMinable minable = new WorldGenMinable(block.getDefaultState(), (minVeinSize + random.nextInt(maxVeinSize - minVeinSize)));
        for(int i = 0; i < chancesToSpawn; i++)
        {
            int posX = blockXPos + random.nextInt(16);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(16);
            minable.generate(world, random, new BlockPos(posX, posY, posZ));
        }
    }
}
