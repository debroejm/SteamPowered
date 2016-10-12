package com.majorpotato.steampowered.render.block;

import com.majorpotato.steampowered.tileentity.machine.basic.TEHotPlate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderMachineBasic extends TileEntitySpecialRenderer {

    public RenderMachineBasic() {

    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {

        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);

        //adjustLightFixture(te.getWorld(), te.getPos());

        if(te instanceof TEHotPlate) {
            renderHotPlateContents((TEHotPlate)te);
        }

        GlStateManager.popMatrix();
    }

    private void adjustLightFixture(World world, BlockPos pos) {
        float brightness = world.getLightBrightness(pos);
        int skyLight = world.getLightFor(EnumSkyBlock.SKY, pos);
        int modulousModifier = skyLight % 65536;
        int divModifier = skyLight / 65536;
        GlStateManager.color(brightness*200, brightness*200, brightness*200, 1.0F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulousModifier, divModifier);
    }

    private void renderHotPlateContents(TEHotPlate hotPlate) {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        for(int i = 0; i < Math.min(hotPlate.getSizeInventory(), 9); i++) {
            int x = i % 3;
            int y = i / 3;
            ItemStack stack = hotPlate.getStackInSlot(i);
            if(stack != null) {

                EntityItem entityitem = new EntityItem(hotPlate.getWorld(), 0.0D, 0.0D, 0.0D, stack);
                entityitem.getEntityItem().stackSize = 1;
                entityitem.hoverStart = 0.0F;

                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();

                GlStateManager.translate(0.25f*(float)(x+1), 1.01f, 0.25f*(float)(y+1));
                if(!(stack.getItem() instanceof ItemBlock)) {
                    GlStateManager.scale(0.25f,0.25f,0.25f);
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                } else {
                    GlStateManager.translate(0.0f, 0.1f, 0.0f);
                    GlStateManager.scale(0.45f,0.45f,0.45f);
                }

                GlStateManager.pushAttrib();
                RenderHelper.enableStandardItemLighting();
                renderItem.renderItem(entityitem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popAttrib();

                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
            }
        }
    }
}
