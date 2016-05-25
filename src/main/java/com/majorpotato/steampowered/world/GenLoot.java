package com.majorpotato.steampowered.world;


import com.majorpotato.steampowered.client.container.ContainerAlloyFurnace;
import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.init.ModItems;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityAlloyFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class GenLoot {

    public final static int LOOT_CHEST = 1,
                            LOOT_TRAPPED_CHEST = 2,
                            LOOT_ALLOY_FURNACE = 3;

    public static void createLootContainer(World world, int blockX, int blockY, int blockZ, int type)
    {
        if(type == LOOT_CHEST || type == LOOT_TRAPPED_CHEST)
            createLootContainer(world, blockX, blockY, blockZ, type, (int)(Math.random()*4)+2);
        if(type == LOOT_ALLOY_FURNACE)
            createLootContainer(world, blockX, blockY, blockZ, type, 0);
    }

    public static void createLootContainer(World world, int blockX, int blockY, int blockZ, int type, int meta)
    {
        if(type == LOOT_CHEST)
        {
            world.setBlock(blockX, blockY, blockZ, Blocks.chest, meta, 2);
            TileEntity tEnt = world.getTileEntity(blockX, blockY, blockZ);
            if(tEnt instanceof TileEntityChest)
            {
                int times = (int)(Math.random()*5)+3;
                for(int i = 0; i < times; i++)
                    ((TileEntityChest)tEnt).setInventorySlotContents((int)(Math.random()*25)+2, getLootItemStack(type));
            }
        }
        if(type == LOOT_TRAPPED_CHEST)
        {
            world.setBlock(blockX, blockY, blockZ, Blocks.trapped_chest, meta, 2);
            TileEntity tEnt = world.getTileEntity(blockX, blockY, blockZ);
            if(tEnt instanceof TileEntityChest)
            {
                int times = (int)(Math.random()*4)+2;
                for(int i = 0; i < times; i++)
                    ((TileEntityChest)tEnt).setInventorySlotContents((int)(Math.random()*25)+2, getLootItemStack(type));
            }
        }
        if(type == LOOT_ALLOY_FURNACE)
        {
            world.setBlock(blockX-1, blockY-1, blockZ-1, ModBlocks.machineFirebox, 0, 2);
            world.setBlock(blockX-1, blockY-1, blockZ, ModBlocks.machineFirebox, 0, 2);
            world.setBlock(blockX, blockY-1, blockZ-1, ModBlocks.machineFirebox, 0, 2);
            world.setBlock(blockX, blockY-1, blockZ, ModBlocks.machineFirebox, 0, 2);
            world.setBlock(blockX+1, blockY-1, blockZ+1, ModBlocks.machineFirebox, 0, 2);
            world.setBlock(blockX+1, blockY-1, blockZ, ModBlocks.machineFirebox, 0, 2);
            world.setBlock(blockX, blockY-1, blockZ+1, ModBlocks.machineFirebox, 0, 2);
            world.setBlock(blockX+1, blockY-1, blockZ-1, ModBlocks.machineFirebox, 0, 2);
            world.setBlock(blockX-1, blockY-1, blockZ+1, ModBlocks.machineFirebox, 0, 2);
            world.setBlock(blockX, blockY, blockZ, ModBlocks.machineAlloyFurnace, 0, 2);

            /*
            TileEntity tEnt = world.getTileEntity(blockX, blockY, blockZ);
            if(tEnt instanceof TEntityAlloyFurnace)
            {
                for(int i = ContainerAlloyFurnace.INPUT[0]; i < ContainerAlloyFurnace.INPUT[ContainerAlloyFurnace.INPUT.length-1]; i++)
                    ((TEntityAlloyFurnace)tEnt).setInventorySlotContents(i, getLootItemStack(type));
            }
            */
        }
    }

    public static ItemStack getLootItemStack(int type)
    {
        int chance = (int)(Math.random()*100)+1;
        if(type == LOOT_CHEST)
        {
            if(chance < 15) {ItemStack out = new ItemStack(ModItems.ingotCopper); out.stackSize = (int)(Math.random()*5)+1; return out; }
            if(chance < 30) {ItemStack out = new ItemStack(ModItems.ingotZinc); out.stackSize = (int)(Math.random()*3)+1; return out; }
            if(chance < 40) {ItemStack out = new ItemStack(ModItems.ingotBrass); out.stackSize = (int)(Math.random()*4)+1; return out; }
            if(chance < 50) {ItemStack out = new ItemStack(ModItems.ingotSteel); out.stackSize = (int)(Math.random()*2)+1; return out; }
            if(chance < 54) {ItemStack out = new ItemStack(Items.diamond); out.stackSize = (int)(Math.random()*2)+1; return out; }
            if(chance < 70) {ItemStack out = new ItemStack(Items.bread); out.stackSize = (int)(Math.random()*3)+3; return out; }
            ItemStack out = new ItemStack(ModItems.itemScrap); out.stackSize = (int)(Math.random()*4)+3; return out;
        }
        if(type == LOOT_TRAPPED_CHEST)
        {
            if(chance < 30) {ItemStack out = new ItemStack(ModItems.ingotBrass); out.stackSize = (int)(Math.random()*4)+1; return out; }
            if(chance < 50) {ItemStack out = new ItemStack(ModItems.ingotSteel); out.stackSize = (int)(Math.random()*2)+1; return out; }
            if(chance < 60) {ItemStack out = new ItemStack(Items.diamond); out.stackSize = (int)(Math.random()*2)+1; return out; }
            ItemStack out = new ItemStack(ModItems.itemScrap); out.stackSize = (int)(Math.random()*4)+3; return out;
        }
        if(type == LOOT_ALLOY_FURNACE)
        {
            if(chance < 15) {ItemStack out = new ItemStack(ModItems.ingotCopper); out.stackSize = (int)(Math.random()*5)+1; return out; }
            if(chance < 30) {ItemStack out = new ItemStack(ModItems.ingotZinc); out.stackSize = (int)(Math.random()*3)+1; return out; }
            if(chance < 40) {ItemStack out = new ItemStack(ModItems.ingotBrass); out.stackSize = (int)(Math.random()*4)+1; return out; }
            if(chance < 45) {ItemStack out = new ItemStack(ModItems.ingotSteel); out.stackSize = (int)(Math.random()*2)+1; return out; }
            if(chance < 65) {ItemStack out = new ItemStack(ModItems.itemScrap); out.stackSize = (int)(Math.random()*4)+3; return out; }
            return null;
        }
        return null;
    }
}
