package com.majorpotato.steampowered.tileentity.artifice;


import com.majorpotato.steampowered.tileentity.TEntitySP;
import net.minecraft.block.Block;

public class TEntityInfused extends TEntitySP {
    Block template;

    public TEntityInfused(Block template) {
        this.template = template;
    }

    public Block getTemplate() { return template; }
    public void setTemplate(Block in) { if(in.isOpaqueCube()) template = in; }
}
