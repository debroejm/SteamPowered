package com.majorpotato.steampowered.multiblock.pattern.alloyfurnace;


import com.majorpotato.steampowered.init.ModBlocks;
import net.minecraft.block.Block;

public class PatternAlloyFurnace3x2x3 extends PatternAlloyFurnace {

    @Override
    protected void setShape() {
        shape = new Block[3][2][3];

        shape[0][0][0] = ModBlocks.machineAlloyFurnace;
        shape[0][0][1] = ModBlocks.machineAlloyFurnace;
        shape[0][0][2] = ModBlocks.machineAlloyFurnace;
        shape[1][0][0] = ModBlocks.machineAlloyFurnace;
        shape[1][0][1] = ModBlocks.machineAlloyFurnace;
        shape[1][0][2] = ModBlocks.machineAlloyFurnace;
        shape[2][0][0] = ModBlocks.machineAlloyFurnace;
        shape[2][0][1] = ModBlocks.machineAlloyFurnace;
        shape[2][0][2] = ModBlocks.machineAlloyFurnace;
        shape[0][1][0] = ModBlocks.machineAlloyFurnace;
        shape[0][1][1] = ModBlocks.machineAlloyFurnace;
        shape[0][1][2] = ModBlocks.machineAlloyFurnace;
        shape[1][1][0] = ModBlocks.machineAlloyFurnace;
        shape[1][1][1] = ModBlocks.machineAlloyFurnace;
        shape[1][1][2] = ModBlocks.machineAlloyFurnace;
        shape[2][1][0] = ModBlocks.machineAlloyFurnace;
        shape[2][1][1] = ModBlocks.machineAlloyFurnace;
        shape[2][1][2] = ModBlocks.machineAlloyFurnace;

        blockTypes.add(ModBlocks.machineAlloyFurnace);
    }

    @Override
    public int getInputCount() { return 18; }

}
