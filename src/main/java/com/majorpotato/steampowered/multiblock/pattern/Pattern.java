package com.majorpotato.steampowered.multiblock.pattern;


import com.majorpotato.steampowered.multiblock.MultiblockData;
import net.minecraft.world.World;

import java.util.ArrayList;

public class Pattern {

    private ArrayList<PatternShape> shapes = new ArrayList<PatternShape>();
    private int ID;

    public Pattern(int ID) {
        this.ID = ID;
    }

    public int getID() { return ID; }

    public Pattern addShape(PatternShape shape) {
        shapes.add(shape);
        return this;
    }

    public boolean validate(World worldObj, int xPos, int yPos, int zPos) {
        for(PatternShape shape : shapes) {
            if(!shape.validate(worldObj, xPos, yPos, zPos)) return false;
        }
        return true;
    }

    public void setAll(World worldObj, int xPos, int yPos, int zPos, MultiblockData data) {
        for(PatternShape shape : shapes) {
            shape.setAll(worldObj, xPos, yPos, zPos, data);
        }
    }
}
