package com.majorpotato.steampowered.client.rendering.block;

import com.majorpotato.steampowered.block.artifice.BlockGlowVein;
import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.client.rendering.models.ModelBlock;
import com.majorpotato.steampowered.tileentity.artifice.TEntityGlowVein;
import com.majorpotato.steampowered.util.artifice.AuraNodeNet;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class TEntityArtificeRenderer extends TileEntitySpecialRenderer {

    // Models
    private final ModelBlock mBlock = new ModelBlock();

    // Textures
    public static final ResourceLocation tBlankCube = new ResourceLocation(Reference.MOD_ID+":textures/models/BlankCube.png");

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        adjustLightFixture(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, te.getBlockType());

        ModelBase renderModel = performPreCalculations(te);
        if(renderModel != null)
            renderModel.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

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

    private ModelBase performPreCalculations(TileEntity entity) {
        if(entity instanceof TEntityGlowVein) {
            Minecraft.getMinecraft().renderEngine.bindTexture(tBlankCube);

            float[] color = AuraNodeNet.getNetForDimension(entity.getWorldObj().provider.dimensionId).getAuraAt(entity.xCoord >> 4,entity.zCoord >> 4);
            GL11.glColor3f(color[0],color[1],color[2]);

            float xSc = 1.0F, ySc = 1.0F, zSc = 1.0F, xTr = 0.0F, yTr = 0.0F, zTr = 0.0F;
            World world = entity.getWorldObj();
            if(!(world.getBlock(entity.xCoord+1,entity.yCoord,entity.zCoord).isOpaqueCube() || world.getBlock(entity.xCoord+1,entity.yCoord,entity.zCoord) instanceof BlockGlowVein)) xSc = 0.8F;
            if(!(world.getBlock(entity.xCoord,entity.yCoord+1,entity.zCoord).isOpaqueCube() || world.getBlock(entity.xCoord,entity.yCoord+1,entity.zCoord) instanceof BlockGlowVein)) ySc = 0.8F;
            if(!(world.getBlock(entity.xCoord,entity.yCoord,entity.zCoord+1).isOpaqueCube() || world.getBlock(entity.xCoord,entity.yCoord,entity.zCoord+1) instanceof BlockGlowVein)) zSc = 0.8F;
            if(!(world.getBlock(entity.xCoord-1,entity.yCoord,entity.zCoord).isOpaqueCube() || world.getBlock(entity.xCoord-1,entity.yCoord,entity.zCoord) instanceof BlockGlowVein)) { xTr = 0.2F; xSc -= xTr; }
            if(!(world.getBlock(entity.xCoord,entity.yCoord-1,entity.zCoord).isOpaqueCube() || world.getBlock(entity.xCoord,entity.yCoord-1,entity.zCoord) instanceof BlockGlowVein)) { yTr = 0.2F; ySc -= yTr; }
            if(!(world.getBlock(entity.xCoord,entity.yCoord,entity.zCoord-1).isOpaqueCube() || world.getBlock(entity.xCoord,entity.yCoord,entity.zCoord-1) instanceof BlockGlowVein)) { zTr = 0.2F; zSc -= zTr; }

            GL11.glTranslatef(xTr,yTr,zTr);
            GL11.glTranslatef(-0.5F*(1.0F-xSc), -1.5F*(1.0F-ySc), -0.5F*(1.0F-zSc));

            //GL11.glTranslatef(xTr,yTr,zTr);
            GL11.glScalef(xSc,ySc,zSc);


            //GL11.glTranslatef(xTr,yTr,zTr);
            //GL11.glScalef(0.2F, 0.2F, 0.2F);

            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

            return mBlock;
        }

        return null;
    }
}
