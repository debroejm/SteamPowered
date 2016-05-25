package com.majorpotato.steampowered.client.rendering.item;

import com.majorpotato.steampowered.client.rendering.models.ModelHotPlate;
import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.client.rendering.block.machine.TEntityMachineBasicRenderer;
import com.majorpotato.steampowered.client.rendering.models.ModelAlloyFurnace;
import com.majorpotato.steampowered.client.rendering.models.ModelFirebox;
import com.majorpotato.steampowered.client.rendering.models.ModelFurnaceChimney;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;


public class ItemMachineBasicRenderer implements IItemRenderer {

    private ModelAlloyFurnace mAlloyFurnace = new ModelAlloyFurnace();
    private ModelFirebox mFirebox = new ModelFirebox();
    private ModelFurnaceChimney mChimneyStack = new ModelFurnaceChimney();
    private ModelHotPlate mHotPlate = new ModelHotPlate();

    private RenderBlocks renderBlocksIr = new RenderBlocks();

    public ItemMachineBasicRenderer() { }

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

        if(item.isItemEqual(new ItemStack(ModBlocks.machineAlloyFurnace))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineBasicRenderer.tAlloyFurnace);
            renderModel = mAlloyFurnace;
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineFirebox))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineBasicRenderer.tFirebox_default);
            renderModel = mFirebox;
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineChimneyStack))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineBasicRenderer.tChimneyStack);
            renderModel = mChimneyStack;
        }
        else if(item.isItemEqual(new ItemStack(ModBlocks.machineHotPlate))) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TEntityMachineBasicRenderer.tHotPlate);
            renderModel = mHotPlate;
        }

        Block block = Block.getBlockFromItem(item.getItem());

        if(renderModel != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 1.0F, 0.0F);
            if(type == ItemRenderType.EQUIPPED)
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            else if(type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                GL11.glTranslatef(0.5F, 0.75F, 0.5F);
            }
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            renderModel.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            if(item.isItemEqual(new ItemStack(ModBlocks.machineHotPlate))) {
                if (item != null && block != null && block.getRenderBlockPass() != 0) {
                    GL11.glDepthMask(false);
                    this.renderBlocksIr.renderBlockAsItem(block, item.getItemDamage(), 1.0F);
                    GL11.glDepthMask(true);
                } else this.renderBlocksIr.renderBlockAsItem(block, item.getItemDamage(), 1.0F);
            }
            GL11.glPopMatrix();
        }

        GL11.glPopMatrix();
    }
}
