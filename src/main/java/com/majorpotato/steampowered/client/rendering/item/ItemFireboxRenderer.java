package com.majorpotato.steampowered.client.rendering.item;


import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.client.rendering.models.ModelFirebox;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemFireboxRenderer implements IItemRenderer {

    private ModelFirebox model = new ModelFirebox();
    private static final ResourceLocation tex = new ResourceLocation(Reference.MOD_ID+":textures/models/Firebox.png");

    public ItemFireboxRenderer() { }

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
        /*
        if(type == IItemRenderer.ItemRenderType.ENTITY)
            GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        else if(type == ItemRenderType.INVENTORY)
            GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        */
        GL11.glPushMatrix();
        GL11.glScalef(0.8F, 0.8F, 0.8F);
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
