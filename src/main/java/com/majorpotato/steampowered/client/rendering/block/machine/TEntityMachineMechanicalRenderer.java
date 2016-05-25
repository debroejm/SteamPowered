package com.majorpotato.steampowered.client.rendering.block.machine;

import com.majorpotato.steampowered.util.Axis;
import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.tileentity.machine.mechanical.*;
import com.majorpotato.steampowered.client.rendering.models.ModelAxle;
import com.majorpotato.steampowered.client.rendering.models.ModelBlock;
import com.majorpotato.steampowered.client.rendering.models.ModelChainLink;
import com.majorpotato.steampowered.client.rendering.models.ModelGear;
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


public class TEntityMachineMechanicalRenderer extends TileEntitySpecialRenderer {

    // Models
    private final ModelBlock mBlock = new ModelBlock();
    private final ModelGear mGear = new ModelGear();
    private final ModelAxle mAxle = new ModelAxle();
    private final ModelChainLink mChain = new ModelChainLink();

    // Textures
    public static final ResourceLocation tDoorBody = new ResourceLocation(Reference.MOD_ID+":textures/models/SteelDoorBody.png");
    public static final ResourceLocation tDoorFrame = new ResourceLocation(Reference.MOD_ID+":textures/models/DoorFrame.png");
    public static final ResourceLocation tGearWood = new ResourceLocation(Reference.MOD_ID+":textures/models/GearWood.png");

    public TEntityMachineMechanicalRenderer() { }

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

    private ModelBase performPreCalculations(TileEntity entity)
    {
        if(entity instanceof TEntityDoorBody) {
            TEntityDoorBody tEnt = (TEntityDoorBody)entity;

            if(tEnt.getFacing() == Direction.NORTH) {
                GL11.glScalef(0.5F + 0.5F * (float) tEnt.extendPercent(), 0.95F + 0.05F * (float) tEnt.extendPercent(), 1.0F);
                GL11.glTranslatef(0.0F, 0.0F, 1.0F - 1.0F * (float) tEnt.extendPercent());
            }
            if(tEnt.getFacing() == Direction.SOUTH) {
                GL11.glScalef(0.5F + 0.5F * (float) tEnt.extendPercent(), 0.95F + 0.05F * (float) tEnt.extendPercent(), 1.0F);
                GL11.glTranslatef(0.0F, 0.0F, -1.0F + 1.0F * (float) tEnt.extendPercent());
            }
            if(tEnt.getFacing() == Direction.WEST) {
                GL11.glScalef(1.0F, 0.95F + 0.05F * (float) tEnt.extendPercent(), 0.5F + 0.5F * (float) tEnt.extendPercent());
                GL11.glTranslatef(1.0F - 1.0F * (float) tEnt.extendPercent(), 0.0F, 0.0F);
            }
            if(tEnt.getFacing() == Direction.EAST) {
                GL11.glScalef(1.0F, 0.95F + 0.05F * (float) tEnt.extendPercent(), 0.5F + 0.5F * (float) tEnt.extendPercent());
                GL11.glTranslatef(-1.0F + 1.0F * (float) tEnt.extendPercent(), 0.0F, 0.0F);
            }

            Minecraft.getMinecraft().renderEngine.bindTexture(tDoorBody);

            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            return mBlock;
        }
        else if(entity instanceof TEntityDoorFrame) {
            Minecraft.getMinecraft().renderEngine.bindTexture(tDoorFrame);
            Direction side = ((TEntityDoorFrame)entity).getFacing();
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

            if(side == Direction.SOUTH)
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            if(side == Direction.EAST)
                GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            if(side == Direction.WEST)
                GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);

            return mBlock;
        }
        else if(entity instanceof TEntityAxle) {
            Minecraft.getMinecraft().renderEngine.bindTexture(tGearWood);
            Axis axis = Axis.UNKNOWN;
            float anglePercent = 0.0F;
            axis = ((TEntityAxle) entity).getAxis();
            anglePercent = (float) ((TEntityAxle) entity).getRotationAnglePercent();
            if(axis == Axis.UNKNOWN) return null;
            if(axis == Axis.Z) { GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F); GL11.glTranslatef(0.0F, 1.0F, 1.0F); }
            if(axis == Axis.X) { GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F); GL11.glTranslatef(-1.0F, 1.0F, 0.0F); }
            GL11.glRotatef(360F*anglePercent, 0.0F, 1.0F, 0.0F);
            //GL11.glScalef(0.8F, 1.6F, 0.8F);
            //GL11.glTranslatef(0.0F, 0.7F, 0.0F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

            boolean gearFormed = ((TEntityAxle)entity).isGear();
            //System.out.println(gearFormed);
            if(gearFormed) return mGear;
            else return mAxle;
        }
        else if(entity instanceof TEntityChain) {
            Minecraft.getMinecraft().renderEngine.bindTexture(tGearWood);
            GL11.glTranslatef(0.0f, 0.0f, -0.25f);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            return mChain;
        }

        return null;
    }
}
