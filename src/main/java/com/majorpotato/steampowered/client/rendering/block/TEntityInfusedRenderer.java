package com.majorpotato.steampowered.client.rendering.block;


import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.client.rendering.models.ModelPlane;
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

public class TEntityInfusedRenderer extends TileEntitySpecialRenderer {

    // Models
    private final ModelPlane mPlane = new ModelPlane();

    // Textures
    public static final ResourceLocation tInfused_lineStraight = new ResourceLocation(Reference.MOD_ID+":textures/models/Infused/lineStraight.png");
    public static final ResourceLocation tInfused_lineCorner = new ResourceLocation(Reference.MOD_ID+":textures/models/Infused/lineCorner.png");
    public static final ResourceLocation tInfused_lineT = new ResourceLocation(Reference.MOD_ID+":textures/models/Infused/lineT.png");
    public static final ResourceLocation tInfused_lineIntersection = new ResourceLocation(Reference.MOD_ID+":textures/models/Infused/lineIntersection.png");

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.51F, (float) z + 0.5F);

        adjustLightFixture(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, te.getBlockType());

        Minecraft.getMinecraft().renderEngine.bindTexture(tInfused_lineIntersection);

        GL11.glScalef(1.05F, 1.05F, 1.05F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        GL11.glColor4f(0.8F, 0.8F, 1.0F, 0.5F);
        mPlane.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        mPlane.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        mPlane.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        mPlane.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0.0F, -1.0F, -1.0F);
        mPlane.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0.0F, -2.0F, 0.0f);
        mPlane.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

    private void adjustLightFixture(World world, int i, int j, int k, Block block) {
        Tessellator tess = Tessellator.instance;
        float brightness = 15; //block.getLightValue(world, i, j, k);
        int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int modulousModifier = skyLight % 65536;
        int divModifier = skyLight / 65536;
        tess.setColorOpaque_F(brightness, brightness, brightness);
        //OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulousModifier, divModifier);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0, 240);
    }
}
