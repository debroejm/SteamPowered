package com.majorpotato.steampowered.creativetab;

import com.majorpotato.steampowered.init.ModItems;
import com.majorpotato.steampowered.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabSP
{
    public static final CreativeTabs SP_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase())
    {

        @Override
        public Item getTabIconItem() {
            return ModItems.ingotBrass;
        }
    };
}
