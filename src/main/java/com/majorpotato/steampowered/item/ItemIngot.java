package com.majorpotato.steampowered.item;

import com.majorpotato.steampowered.util.OreMaterial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemIngot extends ItemSP implements IItemColor {

    protected OreMaterial material;
    public OreMaterial getMaterial() { return material; }

    public ItemIngot(OreMaterial material) {
        super();
        this.material = material;
        setUnlocalizedName(material.getName(OreMaterial.Type.INGOT));
        material.setInstance(OreMaterial.Type.INGOT, this);
    }


    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        if(tintIndex == 0) return material.getColor(OreMaterial.Type.INGOT);
        else return 0xFFFFFF;
    }
}
