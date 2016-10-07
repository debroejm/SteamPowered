package com.majorpotato.steampowered.block;


import com.majorpotato.steampowered.SteamPowered;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class ItemBlockSP extends ItemBlock {

    protected String name = "";
    public final String getName() { return name; }

    public ItemBlockSP(BlockSP block) {
        super(block);
        setUnlocalizedName(block.getName());
    }

    @Override
    public ItemBlock setUnlocalizedName(String name) {
        this.name = name;
        setRegistryName(SteamPowered.MODID, name);
        setCreativeTab(SteamPowered.modTab);
        return super.setUnlocalizedName(SteamPowered.MODID+":"+name);
    }
}
