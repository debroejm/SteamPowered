package com.majorpotato.steampowered.world;

import com.majorpotato.steampowered.init.ModBlocks;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockSand;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GenWorkshop {

    private static final int DIRECT_X_POS = 1, DIRECT_X_NEG = 2,
                             DIRECT_Z_POS = 3, DIRECT_Z_NEG = 4;

    public static void addWorkshopSpawn(World world, int blockXPos, int blockZPos, double percentChanceToSpawn)
    {
        double randChance = (double)(Math.random()*100.0);
        double possChance = (double)(percentChanceToSpawn*100.0);
        if(randChance < possChance)
        {
            for(int i = 150; i > 60; i--)
            {
                if(world.getBlock(blockXPos, i, blockZPos) instanceof BlockGrass || world.getBlock(blockXPos, i, blockZPos) instanceof BlockSand)
                {

                    // ----------------
                    // | Debug Marker |
                    // ----------------

                    //for(int y = 90; y < 120; y++)
                    //    world.setBlock(blockXPos, y, blockZPos, Blocks.glowstone, 0, 2);


                    // -----------------
                    // | Entrance Hole |
                    // -----------------

                    createWorkshopEntranceLayer(world, blockXPos, i+3, blockZPos, false, false);
                    createWorkshopEntranceLayer(world, blockXPos, i+2, blockZPos, false, true);
                    createWorkshopEntranceLayer(world, blockXPos, i+1, blockZPos, false, false);

                    for(int y = i; y > 55; y--)
                        createWorkshopEntranceLayer(world, blockXPos, y, blockZPos, true, false);


                    // ------------------
                    // | Elevator Shaft |
                    // ------------------

                    createWorkshopElevatorCeiling(world, blockXPos, 55, blockZPos);

                    createWorkshopElevatorLayer(world, blockXPos, 54, blockZPos, false);
                    createWorkshopElevatorLayer(world, blockXPos, 53, blockZPos, false);
                    int times = (int)(Math.random()*6)+2;
                    for(int t = 0; t < times; t++)
                    {
                        int y = 52-(t)*5;

                        createWorkshopElevatorLayer(world, blockXPos, y, blockZPos, true);
                        createWorkshopElevatorLayer(world, blockXPos, y-1, blockZPos, false);
                        createWorkshopElevatorLayer(world, blockXPos, y-2, blockZPos, false);
                        if(t != times-1) {
                            createWorkshopElevatorLayer(world, blockXPos, y - 3, blockZPos, false);
                            createWorkshopElevatorLayer(world, blockXPos, y - 4, blockZPos, false);
                        }
                    }
                    int floorBottom = 54-(times)*5;
                    createWorkshopElevatorFloor(world, blockXPos, floorBottom, blockZPos);


                    // -----------------
                    // | Side Passages |
                    // -----------------

                    int chance = (int)(Math.random()*100)+1;
                    boolean portalCreated = false;
                    if(chance < 40) {
                        portalCreated = createWorkshopChamber(world, blockXPos+22, floorBottom, blockZPos, portalCreated);
                        createWorkshopPassage(world, blockXPos, floorBottom, blockZPos, DIRECT_X_POS);
                    }
                    else if(chance < 60)
                        createWorkshopAlcove(world, blockXPos, floorBottom, blockZPos, DIRECT_X_POS);

                    chance = (int)(Math.random()*100)+1;
                    if(chance < 40) {
                        portalCreated = createWorkshopChamber(world, blockXPos-22, floorBottom, blockZPos, portalCreated);
                        createWorkshopPassage(world, blockXPos, floorBottom, blockZPos, DIRECT_X_NEG);
                    }
                    else if(chance < 60)
                        createWorkshopAlcove(world, blockXPos, floorBottom, blockZPos, DIRECT_X_NEG);

                    chance = (int)(Math.random()*100)+1;
                    if(chance < 40) {
                        portalCreated = createWorkshopChamber(world, blockXPos, floorBottom, blockZPos+22, portalCreated);
                        createWorkshopPassage(world, blockXPos, floorBottom, blockZPos, DIRECT_Z_POS);
                    }
                    else if(chance < 60)
                        createWorkshopAlcove(world, blockXPos, floorBottom, blockZPos, DIRECT_Z_POS);

                    chance = (int)(Math.random()*100)+1;
                    if(chance < 40) {
                        portalCreated = createWorkshopChamber(world, blockXPos, floorBottom, blockZPos-22, portalCreated);
                        createWorkshopPassage(world, blockXPos, floorBottom, blockZPos, DIRECT_Z_NEG);
                    }
                    else if(chance < 60)
                        createWorkshopAlcove(world, blockXPos, floorBottom, blockZPos, DIRECT_Z_NEG);

                    return;
                }
            }
        }
    }

    private static void createWorkshopEntranceLayer(World world, int blockXPos, int blockYPos, int blockZPos, boolean dirt, boolean bars)
    {
        for(int x = blockXPos-2; x < blockXPos+4; x++)
        {
            for(int z = blockZPos-2; z < blockZPos+4; z++)
            {
                if(x == blockXPos-2 || x == blockXPos+3 || z == blockZPos-2 || z == blockZPos+3)
                {
                    if(bars) world.setBlock(x, blockYPos, z, Blocks.iron_bars);
                    else setBrickTypeAt(world, x, blockYPos, z, dirt);
                }
                else world.setBlockToAir(x, blockYPos, z);
            }
        }
    }

    private static void createWorkshopElevatorLayer(World world, int blockXPos, int blockYPos, int blockZPos, boolean lamp)
    {
        for(int x = blockXPos-6; x < blockXPos+8; x++)
        {
            for(int z = blockZPos-6; z < blockZPos+8; z++)
            {
                if(x == blockXPos-6 || x == blockXPos+7)
                {
                    if(z == blockZPos-2 || z == blockZPos+3) {
                        if(lamp) {
                            world.setBlock(x, blockYPos, z, Blocks.redstone_lamp);
                            int chance = (int)(Math.random()*10);
                            if(chance == 4 && x == blockXPos-6) world.setBlock(x-1, blockYPos, z, Blocks.redstone_block, 0, 2);
                            if(chance == 4 && x == blockXPos+7) world.setBlock(x+1, blockYPos, z, Blocks.redstone_block, 0, 2);
                        }
                        else world.setBlock(x, blockYPos, z, ModBlocks.blockRuinedScaffold);
                    }
                    else if(z > blockZPos-2 && z < blockZPos+3)
                        setBrickTypeAt(world, x, blockYPos, z, true);
                    else {
                        if(lamp) world.setBlock(x, blockYPos, z, ModBlocks.blockRuinedScaffold);
                        else setBrickTypeAt(world, x, blockYPos, z, true);
                    }
                }
                else if(z == blockZPos-6 || z == blockZPos+7)
                {
                    if(x == blockXPos-2 || x == blockXPos+3) {
                        if(lamp) {
                            world.setBlock(x, blockYPos, z, Blocks.redstone_lamp);
                            int chance = (int)(Math.random()*10);
                            if(chance == 4 && z == blockZPos-6) world.setBlock(x, blockYPos, z-1, Blocks.redstone_block, 0, 2);
                            if(chance == 4 && z == blockZPos+7) world.setBlock(x, blockYPos, z+1, Blocks.redstone_block, 0, 2);
                        }
                        else world.setBlock(x, blockYPos, z, ModBlocks.blockRuinedScaffold);
                    }
                    else if(x > blockXPos-2 && x < blockXPos+3)
                        setBrickTypeAt(world, x, blockYPos, z, true);
                    else {
                        if(lamp) world.setBlock(x, blockYPos, z, ModBlocks.blockRuinedScaffold);
                        else setBrickTypeAt(world, x, blockYPos, z, true);
                    }
                }
                else world.setBlockToAir(x, blockYPos, z);
            }
        }
    }

    private static void createWorkshopElevatorFloor(World world, int blockXPos, int blockYPos, int blockZPos)
    {
        for(int x = blockXPos-6; x < blockXPos+8; x++)
        {
            for(int z = blockZPos-6; z < blockZPos+8; z++)
            {
                if(x == blockXPos-6 || x == blockXPos+7 || z == blockZPos-6 || z == blockZPos+7)
                    world.setBlock(x, blockYPos, z, Blocks.double_stone_slab, 0, 2);
                else {
                    setFloorTypeAt(world, x, blockYPos, z, true, true);
                }
            }
        }
    }

    private static void createWorkshopElevatorCeiling(World world, int blockXPos, int blockYPos, int blockZPos)
    {
        for(int x = blockXPos-6; x < blockXPos+8; x++)
        {
            for(int z = blockZPos-6; z < blockZPos+8; z++)
            {
                if(x == blockXPos-6 || x == blockXPos+7 || z == blockZPos-6 || z == blockZPos+7)
                    setBrickTypeAt(world, x, blockYPos, z, true);
                else if(x > blockXPos-3 && x < blockXPos+4 && z > blockZPos-3 && z < blockZPos+4)
                {
                    if(x > blockXPos-2 && x < blockXPos+3 && z > blockZPos-2 && z < blockZPos+3)
                        world.setBlockToAir(x, blockYPos, z);
                    else setBrickTypeAt(world, x, blockYPos, z, true);
                }
                else world.setBlock(x, blockYPos, z, Blocks.stained_hardened_clay, 9, 2);
            }
        }
    }

    private static boolean createWorkshopChamber(World world, int blockXPos, int blockYPos, int blockZPos, boolean portalCreated)
    {
        for(int x = blockXPos-6; x < blockXPos+8; x++)
        {
            for(int y = blockYPos+5; y > blockYPos-1; y--)
            {
                for(int z = blockZPos-6; z < blockZPos+8; z++)
                {
                    if(y == blockYPos)
                    {
                        if(x == blockXPos-6 || x == blockXPos+7 || z == blockZPos-6 || z == blockZPos+7)
                            world.setBlock(x, y, z, Blocks.double_stone_slab, 0, 2);
                        else setFloorTypeAt(world, x, y, z, false, true);
                    }
                    else if(y == blockYPos+1 || y == blockYPos+5)
                    {
                        if(x == blockXPos-6 || x == blockXPos+7 || z == blockZPos-6 || z == blockZPos+7)
                        {
                            if(x == blockXPos-2 || x == blockXPos+3 || z == blockZPos-2 || z == blockZPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else if((x == blockXPos-6 && z == blockZPos-6) || (x == blockXPos-6 && z == blockZPos+7) || (x == blockXPos+7 && z == blockZPos-6) || (x == blockXPos+7 && z == blockZPos+7))
                                setBrickTypeAt(world, x, y, z, false);
                            else world.setBlock(x, y, z, Blocks.brick_block);
                        }
                        else
                        {
                            if(y == blockYPos+5)
                            {
                                if(x == blockXPos-2 || x == blockXPos+3 || z == blockZPos-2 || z == blockZPos+3) {
                                    if( (x == blockXPos-2 && z == blockZPos-2) || (x == blockXPos-2 && z == blockZPos+3) || (x == blockXPos+3 && z == blockZPos-2) || (x == blockXPos+3 && z == blockZPos+3) ) {
                                        world.setBlock(x, y, z, Blocks.redstone_lamp, 0, 2);
                                        int chance = (int)(Math.random()*100)+1;
                                        if(chance < 40) world.setBlock(x, y+1, z, Blocks.redstone_block, 0, 2);
                                    }
                                    else world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                                }
                                else world.setBlockToAir(x, y, z);
                            }
                            else world.setBlockToAir(x, y, z);
                        }
                    }
                    else if(y == blockYPos+2)
                    {
                        if(x == blockXPos-6 || x == blockXPos+7 || z == blockZPos-6 || z == blockZPos+7)
                        {
                            if(x == blockXPos-2 || x == blockXPos+3 || z == blockZPos-2 || z == blockZPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else if((x == blockXPos-6 && z == blockZPos-6) || (x == blockXPos-6 && z == blockZPos+7) || (x == blockXPos+7 && z == blockZPos-6) || (x == blockXPos+7 && z == blockZPos+7))
                                setBrickTypeAt(world, x, y, z, false);
                            else
                            {
                                if(x == blockXPos-6)
                                    world.setBlock(x, y, z, Blocks.brick_stairs, 1, 2);
                                if(x == blockXPos+7)
                                    world.setBlock(x, y, z, Blocks.brick_stairs, 0, 2);
                                if(z == blockZPos-6)
                                    world.setBlock(x, y, z, Blocks.brick_stairs, 3, 2);
                                if(z == blockZPos+7)
                                    world.setBlock(x, y, z, Blocks.brick_stairs, 2, 2);
                            }
                        }
                        else world.setBlockToAir(x, y, z);
                    }
                    else if(y == blockYPos+4)
                    {
                        if(x == blockXPos-6 || x == blockXPos+7 || z == blockZPos-6 || z == blockZPos+7)
                        {
                            if(x == blockXPos-2 || x == blockXPos+3 || z == blockZPos-2 || z == blockZPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else if((x == blockXPos-6 && z == blockZPos-6) || (x == blockXPos-6 && z == blockZPos+7) || (x == blockXPos+7 && z == blockZPos-6) || (x == blockXPos+7 && z == blockZPos+7))
                                setBrickTypeAt(world, x, y, z, false);
                            else
                            {
                                if(x == blockXPos-6)
                                    world.setBlock(x, y, z, Blocks.brick_stairs, 5, 2);
                                if(x == blockXPos+7)
                                    world.setBlock(x, y, z, Blocks.brick_stairs, 4, 2);
                                if(z == blockZPos-6)
                                    world.setBlock(x, y, z, Blocks.brick_stairs, 7, 2);
                                if(z == blockZPos+7)
                                    world.setBlock(x, y, z, Blocks.brick_stairs, 6, 2);
                            }
                        }
                        else world.setBlockToAir(x, y, z);
                    }
                    else
                    {
                        if(x == blockXPos-6 || x == blockXPos+7 || z == blockZPos-6 || z == blockZPos+7)
                        {
                            if(x == blockXPos-2 || x == blockXPos+3 || z == blockZPos-2 || z == blockZPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else setBrickTypeAt(world, x, y, z, false);
                        }
                        else world.setBlockToAir(x, y, z);
                    }
                }
            }
        }

        int chance = (int)(Math.random()*100)+1;
        if(chance < 26 && !portalCreated)
        {
            for(int x = blockXPos-2; x < blockXPos+4; x++)
            {
                for(int y = blockYPos-2; y < blockYPos+2; y++)
                {
                    for (int z = blockZPos - 2; z < blockZPos + 4; z++)
                    {
                        if(y == blockYPos-2)
                            world.setBlock(x, y, z, Blocks.bedrock);
                        else if(y == blockYPos-1)
                        {
                            if(x == blockXPos-2 || x == blockXPos+3 || z == blockZPos-2 || z == blockZPos+3)
                                world.setBlock(x, y, z, Blocks.bedrock);
                            else world.setBlock(x, y, z, ModBlocks.blockMechanusPortal, 0, 2);
                        }
                        else if(y == blockYPos)
                        {
                            if(x == blockXPos-2 || x == blockXPos+3 || z == blockZPos-2 || z == blockZPos+3)
                                world.setBlock(x, y, z, Blocks.stone_slab, 0, 2);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else world.setBlockToAir(x, y, z);
                    }
                }
            }
            return true;
        }
        if(chance > 70)
        {
            int corner = (int)(Math.random()*100)+1;
            if(corner < 26)
                GenLoot.createLootContainer(world, blockXPos-2, blockYPos+2, blockZPos-2, GenLoot.LOOT_ALLOY_FURNACE);
            else if(corner < 51)
                GenLoot.createLootContainer(world, blockXPos+3, blockYPos+2, blockZPos-2, GenLoot.LOOT_ALLOY_FURNACE);
            else if(corner < 76)
                GenLoot.createLootContainer(world, blockXPos+3, blockYPos+2, blockZPos+3, GenLoot.LOOT_ALLOY_FURNACE);
            else
                GenLoot.createLootContainer(world, blockXPos-2, blockYPos+2, blockZPos+3, GenLoot.LOOT_ALLOY_FURNACE);
        }
        else if(chance > 40)
        {
            int times = (int)(Math.random()*4)+1;
            for(int i = 0; i < times; i++)
            {
                int x = (blockXPos-5)+(int)(Math.random()*12);
                int z = (blockZPos-5)+(int)(Math.random()*12);
                GenLoot.createLootContainer(world, x, blockYPos+1, z, GenLoot.LOOT_CHEST);
                world.setBlock(x, blockYPos, z, Blocks.double_stone_slab, 0, 2);
            }
        }

        return portalCreated;
    }

    private static void createWorkshopAlcove(World world, int blockXPos, int blockYPos, int blockZPos, int direction)
    {
        if(direction == DIRECT_X_POS)
        {
            for(int x = blockXPos+7; x < blockXPos+10; x++)
            {
                for(int y = blockYPos; y < blockYPos+5; y++)
                {
                    for (int z = blockZPos - 2; z < blockZPos + 4; z++)
                    {
                        if(x == blockXPos+7)
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(z == blockZPos || z == blockZPos+1)
                                    world.setBlock(x, y, z, Blocks.redstone_lamp, 0, 2);
                                else
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            }
                            else if (z == blockZPos-2 || z == blockZPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else if(x == blockXPos+8)
                        {
                            if (y == blockYPos) {
                                if(z == blockZPos || z == blockZPos+1)
                                    world.setBlock(x, y, z, Blocks.tnt, 0, 2);
                                else world.setBlock(x, y, z, Blocks.double_stone_slab, 0, 2);
                            }
                            else if (y == blockYPos + 4 || z == blockZPos-2 || z == blockZPos+3)
                                setBrickTypeAt(world, x, y, z, false);
                            else if (y == blockYPos+1 && (z == blockZPos || z == blockZPos+1))
                                GenLoot.createLootContainer(world, x, y, z, GenLoot.LOOT_TRAPPED_CHEST, 5);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else setBrickTypeAt(world, x, y, z, false);
                    }
                }
            }
        }

        if(direction == DIRECT_X_NEG)
        {
            for(int x = blockXPos-6; x > blockXPos-9; x--)
            {
                for(int y = blockYPos; y < blockYPos+5; y++)
                {
                    for (int z = blockZPos - 2; z < blockZPos + 4; z++)
                    {
                        if(x == blockXPos-6)
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(z == blockZPos || z == blockZPos+1)
                                    world.setBlock(x, y, z, Blocks.redstone_lamp, 0, 2);
                                else
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            }
                            else if (z == blockZPos-2 || z == blockZPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else if(x == blockXPos-7)
                        {
                            if (y == blockYPos) {
                                if(z == blockZPos || z == blockZPos+1)
                                    world.setBlock(x, y, z, Blocks.tnt, 0, 2);
                                else world.setBlock(x, y, z, Blocks.double_stone_slab, 0, 2);
                            }
                            else if (y == blockYPos + 4 || z == blockZPos-2 || z == blockZPos+3)
                                setBrickTypeAt(world, x, y, z, false);
                            else if (y == blockYPos+1 && (z == blockZPos || z == blockZPos+1))
                                GenLoot.createLootContainer(world, x, y, z, GenLoot.LOOT_TRAPPED_CHEST, 4);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else setBrickTypeAt(world, x, y, z, false);
                    }
                }
            }
        }

        if(direction == DIRECT_Z_POS)
        {
            for(int x = blockXPos-2; x < blockXPos+4; x++)
            {
                for(int y = blockYPos; y < blockYPos+5; y++)
                {
                    for (int z = blockZPos+7; z < blockZPos+10; z++)
                    {
                        if(z == blockZPos+7)
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(x == blockXPos || x == blockXPos+1)
                                    world.setBlock(x, y, z, Blocks.redstone_lamp, 0, 2);
                                else
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            }
                            else if (x == blockXPos-2 || x == blockXPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else if(z == blockZPos+8)
                        {
                            if (y == blockYPos) {
                                if(x == blockXPos || x == blockXPos+1)
                                    world.setBlock(x, y, z, Blocks.tnt, 0, 2);
                                else world.setBlock(x, y, z, Blocks.double_stone_slab, 0, 2);
                            }
                            else if (y == blockYPos + 4 || x == blockXPos-2 || x == blockXPos+3)
                                setBrickTypeAt(world, x, y, z, false);
                            else if (y == blockYPos+1 && (x == blockXPos || x == blockXPos+1))
                                GenLoot.createLootContainer(world, x, y, z, GenLoot.LOOT_TRAPPED_CHEST, 3);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else setBrickTypeAt(world, x, y, z, false);
                    }
                }
            }
        }

        if(direction == DIRECT_Z_NEG)
        {
            for(int x = blockXPos-2; x < blockXPos+4; x++)
            {
                for(int y = blockYPos; y < blockYPos+5; y++)
                {
                    for (int z = blockZPos-6; z > blockZPos-9; z--)
                    {
                        if(z == blockZPos-6)
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(x == blockXPos || x == blockXPos+1)
                                    world.setBlock(x, y, z, Blocks.redstone_lamp, 0, 2);
                                else
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            }
                            else if (x == blockXPos-2 || x == blockXPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else if(z == blockZPos-7)
                        {
                            if (y == blockYPos) {
                                if(x == blockXPos || x == blockXPos+1)
                                    world.setBlock(x, y, z, Blocks.tnt, 0, 2);
                                else world.setBlock(x, y, z, Blocks.double_stone_slab, 0, 2);
                            }
                            else if (y == blockYPos + 4 || x == blockXPos-2 || x == blockXPos+3)
                                setBrickTypeAt(world, x, y, z, false);
                            else if (y == blockYPos+1 && (x == blockXPos || x == blockXPos+1))
                                GenLoot.createLootContainer(world, x, y, z, GenLoot.LOOT_TRAPPED_CHEST, 2);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else setBrickTypeAt(world, x, y, z, false);
                    }
                }
            }
        }
    }

    private static void createWorkshopPassage(World world, int blockXPos, int blockYPos, int blockZPos, int direction)
    {
        if(direction == DIRECT_X_POS)
        {
            for(int x = blockXPos+7; x < blockXPos+17; x++)
            {
                for(int y = blockYPos; y < blockYPos+5; y++)
                {
                    for (int z = blockZPos - 2; z < blockZPos + 4; z++)
                    {
                        if(x == blockXPos+7 || x == blockXPos+16)
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(z == blockZPos || z == blockZPos+1)
                                    world.setBlock(x, y, z, Blocks.redstone_lamp, 0, 2);
                                else
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            }
                            else if (z == blockZPos-2 || z == blockZPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(z == blockZPos || z == blockZPos+1)
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                                else
                                    setBrickTypeAt(world, x, y, z, false);
                            }
                            else if (z == blockZPos-2) {
                                if (y == blockYPos+1) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 3, 2);
                                else if(y == blockYPos+3) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 7, 2);
                                else world.setBlock(x, y, z, Blocks.brick_block, 0, 2);
                            }
                            else if (z == blockZPos+3) {
                                if (y == blockYPos+1) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 2, 2);
                                else if(y == blockYPos+3) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 6, 2);
                                else world.setBlock(x, y, z, Blocks.brick_block, 0, 2);
                            }
                            else
                                world.setBlockToAir(x, y, z);
                        }
                    }
                }
            }
        }

        if(direction == DIRECT_X_NEG)
        {
            for(int x = blockXPos-6; x > blockXPos-16; x--)
            {
                for(int y = blockYPos; y < blockYPos+5; y++)
                {
                    for (int z = blockZPos - 2; z < blockZPos + 4; z++)
                    {
                        if(x == blockXPos-6 || x == blockXPos-15)
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(z == blockZPos || z == blockZPos+1)
                                    world.setBlock(x, y, z, Blocks.redstone_lamp, 0, 2);
                                else
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            }
                            else if (z == blockZPos-2 || z == blockZPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(z == blockZPos || z == blockZPos+1)
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                                else
                                    setBrickTypeAt(world, x, y, z, false);
                            }
                            else if (z == blockZPos-2) {
                                if (y == blockYPos+1) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 3, 2);
                                else if(y == blockYPos+3) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 7, 2);
                                else world.setBlock(x, y, z, Blocks.brick_block, 0, 2);
                            }
                            else if (z == blockZPos+3) {
                                if (y == blockYPos+1) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 2, 2);
                                else if(y == blockYPos+3) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 6, 2);
                                else world.setBlock(x, y, z, Blocks.brick_block, 0, 2);
                            }
                            else
                                world.setBlockToAir(x, y, z);
                        }
                    }
                }
            }
        }

        if(direction == DIRECT_Z_POS)
        {
            for(int x = blockXPos-2; x < blockXPos+4; x++)
            {
                for(int y = blockYPos; y < blockYPos+5; y++)
                {
                    for (int z = blockZPos+7; z < blockZPos+17; z++)
                    {
                        if(z == blockZPos+7 || z == blockZPos+16)
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(x == blockXPos || x == blockXPos+1)
                                    world.setBlock(x, y, z, Blocks.redstone_lamp, 0, 2);
                                else
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            }
                            else if (x == blockXPos-2 || x == blockXPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(x == blockXPos || x == blockXPos+1)
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                                else
                                    setBrickTypeAt(world, x, y, z, false);
                            }
                            else if (x == blockXPos-2) {
                                if (y == blockYPos+1) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 1, 2);
                                else if(y == blockYPos+3) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 5, 2);
                                else world.setBlock(x, y, z, Blocks.brick_block, 0, 2);
                            }
                            else if (x == blockXPos+3) {
                                if (y == blockYPos+1) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 0, 2);
                                else if(y == blockYPos+3) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 4, 2);
                                else world.setBlock(x, y, z, Blocks.brick_block, 0, 2);
                            }
                            else
                                world.setBlockToAir(x, y, z);
                        }
                    }
                }
            }
        }

        if(direction == DIRECT_Z_NEG)
        {
            for(int x = blockXPos-2; x < blockXPos+4; x++)
            {
                for(int y = blockYPos; y < blockYPos+5; y++)
                {
                    for (int z = blockZPos-6; z > blockZPos-16; z--)
                    {
                        if(z == blockZPos-6 || z == blockZPos-15)
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(x == blockXPos || x == blockXPos+1)
                                    world.setBlock(x, y, z, Blocks.redstone_lamp, 0, 2);
                                else
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            }
                            else if (x == blockXPos-2 || x == blockXPos+3)
                                world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                            else
                                world.setBlockToAir(x, y, z);
                        }
                        else
                        {
                            if (y == blockYPos)
                                setFloorTypeAt(world, x, y, z, false, false);
                            else if (y == blockYPos + 4) {
                                if(x == blockXPos || x == blockXPos+1)
                                    world.setBlock(x, y, z, ModBlocks.blockRuinedScaffold);
                                else
                                    setBrickTypeAt(world, x, y, z, false);
                            }
                            else if (x == blockXPos-2) {
                                if (y == blockYPos+1) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 1, 2);
                                else if(y == blockYPos+3) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 5, 2);
                                else world.setBlock(x, y, z, Blocks.brick_block, 0, 2);
                            }
                            else if (x == blockXPos+3) {
                                if (y == blockYPos+1) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 0, 2);
                                else if(y == blockYPos+3) world.setBlock(x, y, z, Blocks.stone_brick_stairs, 4, 2);
                                else world.setBlock(x, y, z, Blocks.brick_block, 0, 2);
                            }
                            else
                                world.setBlockToAir(x, y, z);
                        }
                    }
                }
            }
        }
    }

    private static void setBrickTypeAt(World world, int blockXPos, int blockYPos, int blockZPos, boolean dirtPossible)
    {
        int many = 8; if(dirtPossible) many = 9;
        int chance = (int)(Math.random()*many)+1;
        switch (chance) {
            case 1:
            case 2:
            case 3:
            case 4:
                world.setBlock(blockXPos, blockYPos, blockZPos, Blocks.stonebrick, 0, 2);
                return;
            case 5:
            case 6:
                world.setBlock(blockXPos, blockYPos, blockZPos, Blocks.stonebrick, 2, 2);
                return;
            case 7:
            case 8:
                world.setBlock(blockXPos, blockYPos, blockZPos, Blocks.stone, 0, 2);
                return;
            case 9:
                world.setBlock(blockXPos, blockYPos, blockZPos, Blocks.dirt, 0, 2);
                return;
            default:
                return;
        }
    }

    private static void setFloorTypeAt(World world, int blockXPos, int blockYPos, int blockZPos, boolean dirtPossible, boolean rubblePossible)
    {
        int chance = (int)(Math.random()*10);
        if( chance == 4 ) world.setBlock(blockXPos, blockYPos, blockZPos, Blocks.stone_slab, 0, 2);
        else if( chance == 6 && dirtPossible ) world.setBlock(blockXPos, blockYPos, blockZPos, Blocks.dirt, 0, 2);
        else world.setBlock(blockXPos, blockYPos, blockZPos, Blocks.double_stone_slab, 0, 2);
        if( chance == 8 || chance == 2 && rubblePossible) world.setBlock(blockXPos, blockYPos+1, blockZPos, ModBlocks.blockRubble, 0, 2);
        if( chance == 6 && dirtPossible ) world.setBlock(blockXPos, blockYPos+1, blockZPos, Blocks.tallgrass, 1, 2);
    }
}
