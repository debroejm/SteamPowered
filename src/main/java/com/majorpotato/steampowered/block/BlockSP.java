package com.majorpotato.steampowered.block;


import com.majorpotato.steampowered.SteamPowered;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockSP extends Block {

    protected String name = "";
    public final String getName() { return name; }

    public BlockSP(Material material) {
        super(material);
        setCreativeTab(SteamPowered.modTab);
    }

    @Override
    public Block setUnlocalizedName(String name) {
        this.name = name;
        setRegistryName(SteamPowered.MODID, name);
        return super.setUnlocalizedName(SteamPowered.MODID+":"+name);
    }
}
