package com.majorpotato.steampowered.item;


import com.majorpotato.steampowered.SteamPowered;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;

public class ItemSP extends Item {

    protected String name = "";
    public final String getName() { return name; }

    public ItemSP() {
        setCreativeTab(SteamPowered.modTab);
    }

    @Override
    public Item setUnlocalizedName(String name) {
        this.name = name;
        setRegistryName(SteamPowered.MODID, name);
        return super.setUnlocalizedName(SteamPowered.MODID+":"+name);
    }
}
