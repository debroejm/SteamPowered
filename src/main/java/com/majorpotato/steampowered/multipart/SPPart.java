package com.majorpotato.steampowered.multipart;


import codechicken.multipart.minecraft.McMetaPart;
import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.reference.Reference;
import net.minecraft.block.Block;

public abstract class SPPart extends McMetaPart {

    public SPPart() { }
    public SPPart(int meta) { super(meta); }

    @Override
    public String getType() {
        return Reference.MOD_ID.toLowerCase() + ":" + getName();
    }

    protected abstract String getName();
}
