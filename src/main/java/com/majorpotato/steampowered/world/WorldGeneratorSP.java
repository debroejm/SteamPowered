package com.majorpotato.steampowered.world;


import com.majorpotato.steampowered.init.ModBlocks;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGeneratorSP implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        switch (world.provider.dimensionId)
        {
            case 0: GenerateOverworld(random, chunkX * 16, chunkZ * 16, world); break;
            case 1: GenerateEnd(random, chunkX * 16, chunkZ * 16, world); break;
            case -1: GenerateNether(random, chunkX * 16, chunkZ * 16, world); break;
        }
    }

    private void GenerateOverworld(Random random, int x, int z, World world)
    {
        this.addOreSpawn(ModBlocks.oreCopper, world, random, x, z, 3, 7, 75, 32, 128);
        this.addOreSpawn(ModBlocks.oreZinc, world, random, x, z, 6, 9, 40, 32, 256);
        GenWorkshop.addWorkshopSpawn(world, x+(int)(Math.random()*16), z+(int)(Math.random()*16), 0.002);
    }

    private void GenerateEnd(Random random, int x, int z, World world)
    {

    }

    private void GenerateNether(Random random, int x, int z, World world)
    {

    }

    public void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int minVeinSize, int maxVeinSize, int chancesToSpawn, int minY, int maxY )
    {
        WorldGenMinable minable = new WorldGenMinable(block, (minVeinSize + random.nextInt(maxVeinSize - minVeinSize)), Blocks.stone);
        for(int i = 0; i < chancesToSpawn; i++)
        {
            int posX = blockXPos + random.nextInt(16);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(16);
            minable.generate(world, random, posX, posY, posZ);
        }
    }
}
