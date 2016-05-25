package com.majorpotato.steampowered.client.rendering.block;

import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.tileentity.rubble.TEntityRubble1;
import com.majorpotato.steampowered.tileentity.rubble.TEntityRubble2;
import com.majorpotato.steampowered.tileentity.rubble.TEntityRubble3;
import com.majorpotato.steampowered.client.rendering.models.ModelRubble1;
import com.majorpotato.steampowered.client.rendering.models.ModelRubble2;
import com.majorpotato.steampowered.client.rendering.models.ModelRubble3;
import com.majorpotato.steampowered.client.rendering.models.ModelRubble4;
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

public class TEntityRubbleRenderer extends TileEntitySpecialRenderer {
    private final ModelRubble1 rubble1;
    private final ModelRubble2 rubble2;
    private final ModelRubble3 rubble3;
    private final ModelRubble4 rubble4;
    public static final ResourceLocation rubbleTex = new ResourceLocation(Reference.MOD_ID+":textures/models/Rubble.png");

    public TEntityRubbleRenderer() {
        this.rubble1 = new ModelRubble1();
        this.rubble2 = new ModelRubble2();
        this.rubble3 = new ModelRubble3();
        this.rubble4 = new ModelRubble4();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        adjustLightFixture(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, te.getBlockType());

        Minecraft.getMinecraft().renderEngine.bindTexture(rubbleTex);
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        if(te instanceof TEntityRubble1)
            this.rubble1.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        else if(te instanceof TEntityRubble2)
            this.rubble2.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        else if(te instanceof TEntityRubble3)
            this.rubble3.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        else
            this.rubble4.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
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
