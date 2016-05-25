package com.majorpotato.steampowered.client.rendering.item;

import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.client.rendering.block.machine.TEntityMachineSteamRenderer;
import com.majorpotato.steampowered.client.rendering.models.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemMachineSteamRenderer implements IItemRenderer {

    private ModelSteamPipe mPipe = new ModelSteamPipe();
    private ModelSteamBoiler mBoiler = new ModelSteamBoiler();
    private ModelBlock mBlock = new ModelBlock();
    private ModelSteamPiston mPiston = new ModelSteamPiston();

    public ItemMachineSteamRenderer() { }

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

        ModelBase renderModel = null;

        if(item.isItemEqual(new ItemStack(ModBlocks.machineSteamBoiler))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineSteamRenderer.tBoiler);
            renderModel = mBoiler;
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineSteamBoilerBurning))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineSteamRenderer.tBoiler);
            renderModel = mBoiler;
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineSteamPipeSteel))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineSteamRenderer.tPipeHardened);
            renderModel = mPipe;
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineSteamPipeBrass))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineSteamRenderer.tPipe);
            renderModel = mPipe;
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineSteamVent))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineSteamRenderer.tVent);
            renderModel = mBlock;
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineSteamPiston))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineSteamRenderer.tPiston);
            renderModel = mPiston;
        }

        if(renderModel != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 1.0F, 0.0F);
            if(type == ItemRenderType.EQUIPPED)
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            else if(type == ItemRenderType.EQUIPPED_FIRST_PERSON)
                GL11.glTranslatef(0.5F, 0.75F, 0.5F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            renderModel.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GL11.glPopMatrix();
        }
    }
}
