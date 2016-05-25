package com.majorpotato.steampowered.multiblock.pattern.alloyfurnace;


import com.majorpotato.steampowered.init.ModBlocks;
import net.minecraft.block.Block;

public class PatternAlloyFurnace3x1x3 extends PatternAlloyFurnace {

    @Override
    protected void setShape() {
        shape = new Block[3][1][3];

        shape[0][0][0] = ModBlocks.machineAlloyFurnace;
        shape[0][0][1] = ModBlocks.machineAlloyFurnace;
        shape[0][0][2] = ModBlocks.machineAlloyFurnace;
        shape[1][0][0] = ModBlocks.machineAlloyFurnace;
        shape[1][0][1] = ModBlocks.machineAlloyFurnace;
        shape[1][0][2] = ModBlocks.machineAlloyFurnace;
        shape[2][0][0] = ModBlocks.machineAlloyFurnace;
        shape[2][0][1] = ModBlocks.machineAlloyFurnace;
        shape[2][0][2] = ModBlocks.machineAlloyFurnace;

        blockTypes.add(ModBlocks.machineAlloyFurnace);
    }

    @Override
    public int getInputCount() { return 9; }

}
