package com.majorpotato.steampowered.client.rendering.item;


import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.client.rendering.models.ModelAlloyFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemAlloyFurnaceRenderer implements IItemRenderer {

    private ModelAlloyFurnace model = new ModelAlloyFurnace();
    private static final ResourceLocation tex = new ResourceLocation(Reference.MOD_ID+":textures/models/AlloyFurnace.png");

    public ItemAlloyFurnaceRenderer() { }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Minecraft.getMinecraft().renderEngine.bindTexture(tex);

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        if(type == ItemRenderType.EQUIPPED)
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        else if(type == ItemRenderType.EQUIPPED_FIRST_PERSON)
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
