package com.majorpotato.steampowered.client.rendering.item;

import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.init.ModItems;
import com.majorpotato.steampowered.client.rendering.block.machine.TEntityMachineMechanicalRenderer;
import com.majorpotato.steampowered.client.rendering.models.ModelAxle;
import com.majorpotato.steampowered.client.rendering.models.ModelBlock;
import com.majorpotato.steampowered.client.rendering.models.ModelChainLink;
import com.majorpotato.steampowered.client.rendering.models.ModelGear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;


public class ItemMachineMechanicalRenderer implements IItemRenderer {

    private ModelBlock mBlock = new ModelBlock();
    private ModelGear mGear = new ModelGear();
    private ModelAxle mAxle = new ModelAxle();
    private ModelChainLink mChain = new ModelChainLink();

    public ItemMachineMechanicalRenderer() { }

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

        GL11.glPushMatrix();

        if(item.isItemEqual(new ItemStack(ModBlocks.machineDoorBody))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineMechanicalRenderer.tDoorBody);
            renderModel = mBlock;
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineDoorFrame))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineMechanicalRenderer.tDoorFrame);
            renderModel = mBlock;
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineGear))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineMechanicalRenderer.tGearWood);
            renderModel = mGear;
            //GL11.glScalef(0.75F, 0.75F, 0.75F);
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineAxle))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineMechanicalRenderer.tGearWood);
            renderModel = mAxle;
            //GL11.glScalef(1.0F, 1.5F, 1.0F);
        }
        else if(item.isItemEqual(new ItemStack(ModItems.itemGear))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineMechanicalRenderer.tGearWood);
            renderModel = mGear;
            GL11.glScalef(0.6F, 0.6F, 0.6F);
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineChain))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineMechanicalRenderer.tGearWood);
            renderModel = mChain;
        }

        if(renderModel != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 1.0F, 0.0F);
            if(type == ItemRenderType.EQUIPPED)
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            else if(type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                GL11.glTranslatef(0.5F, 0.75F, 0.5F);
                if(renderModel == mGear)
                    GL11.glTranslatef(0.25F, 0.25F, 0.25F);
            }
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            renderModel.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GL11.glPopMatrix();
        }

        GL11.glPopMatrix();
    }
}
