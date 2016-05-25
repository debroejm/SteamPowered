package com.majorpotato.steampowered.client.rendering.block;

import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.tileentity.TEntityConnectedTexture;
import com.majorpotato.steampowered.client.rendering.models.ModelConnectedTexture;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;


public class ConnectedTexturesRenderer extends TileEntitySpecialRenderer {

    private final ModelConnectedTexture model = new ModelConnectedTexture();
    public static HashMap<Block,ResourceLocation> CTMap = new HashMap<Block,ResourceLocation>();

    public static void registerConnectedTexture(String name, Block type) {
        if(!CTMap.containsKey(type)) {
            CTMap.put(type, new ResourceLocation(Reference.MOD_ID+":textures/models/ConnectedTextures/"+name+".png"));
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        adjustLightFixture(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, te.getBlockType());

        if(te instanceof TEntityConnectedTexture) {
            Block key = te.getWorldObj().getBlock(te.xCoord,te.yCoord,te.zCoord);
            if(CTMap.containsKey(key)) {
                Minecraft.getMinecraft().renderEngine.bindTexture(CTMap.get(key));
                model.setNeighbors(((TEntityConnectedTexture)te).getNeighbors());
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
            }
        }

        GL11.glPopMatrix();
    }

    private void adjustLightFixture(World world, int i, int j, int k, Block block) {
        Tessellator tess = Tessellator.instance;
        float brightness = block.getLightValue(world, i, j, k);
        int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int modulousModifier = skyLight % 65536;
        int divModifier = skyLight / 65536;
        tess.setColorOpaque_F(brightness, brightness, brightness);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulousModifier, divModifier);
    }

}
