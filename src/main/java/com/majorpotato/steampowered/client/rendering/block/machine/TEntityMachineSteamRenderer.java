package com.majorpotato.steampowered.client.rendering.block.machine;

import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.tileentity.machine.steam.*;
import com.majorpotato.steampowered.util.TieredMaterial;
import com.majorpotato.steampowered.multiblock.MBSteamBoiler;
import com.majorpotato.steampowered.client.rendering.models.*;
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

public class TEntityMachineSteamRenderer extends TileEntitySpecialRenderer {

    // Models
    private final ModelSteamPipe mPipe = new ModelSteamPipe();
    private final ModelSteamBoiler mBoiler = new ModelSteamBoiler();
    private final ModelBlock mBlock = new ModelBlock();
    private final ModelSteamPiston mPiston = new ModelSteamPiston();
    private final ModelSteamBoilerCustom mTest = new ModelSteamBoilerCustom();

    // Textures
    public static final ResourceLocation tPipe = new ResourceLocation(Reference.MOD_ID+":textures/models/SteamPipe.png");
    public static final ResourceLocation tPipeHardened = new ResourceLocation(Reference.MOD_ID+":textures/models/SteamPipeHardened.png");
    public static final ResourceLocation tBoiler = new ResourceLocation(Reference.MOD_ID+":textures/models/SteamBoiler.png");
    public static final ResourceLocation tVent = new ResourceLocation(Reference.MOD_ID+":textures/models/SteamVent.png");
    public static final ResourceLocation tPiston = new ResourceLocation(Reference.MOD_ID+":textures/models/SteamPiston.png");
    public static final ResourceLocation tTest = new ResourceLocation(Reference.MOD_ID+":textures/models/TestTexture.png");

    public TEntityMachineSteamRenderer() {}

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
        if(entity instanceof TEntitySteamPipe) {
            TEntitySteamPipe tEnt = (TEntitySteamPipe)entity;
            float overloadPercent = (float)tEnt.getSteamLogic().getSteamOverloadPercent();
            mPipe.setSideRender(Direction.UP, tEnt.getSteamLogic().isSideConnected(Direction.UP));
            mPipe.setSideRender(Direction.DOWN, tEnt.getSteamLogic().isSideConnected(Direction.DOWN));
            mPipe.setSideRender(Direction.SOUTH, tEnt.getSteamLogic().isSideConnected(Direction.SOUTH));
            mPipe.setSideRender(Direction.NORTH, tEnt.getSteamLogic().isSideConnected(Direction.NORTH));
            mPipe.setSideRender(Direction.WEST, tEnt.getSteamLogic().isSideConnected(Direction.WEST));
            mPipe.setSideRender(Direction.EAST, tEnt.getSteamLogic().isSideConnected(Direction.EAST));
            if(tEnt.getSteamLogic().getMaterial() == TieredMaterial.Steel)
                Minecraft.getMinecraft().renderEngine.bindTexture(tPipeHardened);
            else
                Minecraft.getMinecraft().renderEngine.bindTexture(tPipe);
            //GL11.glPushMatrix();
            if(overloadPercent > 0) {
                GL11.glScalef(1.0F + overloadPercent * 0.5F, 1.0F + overloadPercent * 0.5F, 1.0F + overloadPercent * 0.5F);
                GL11.glColor3f(1.0F, 1.0F - overloadPercent * 0.5F, 1.0F - overloadPercent * 0.5F);
                GL11.glTranslatef(0.0F, overloadPercent * (0.5F - 0.15F*overloadPercent), 0.0F);
            }
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            return mPipe;
            //GL11.glPopMatrix();
        }
        else if(entity instanceof TEntitySteamBoiler) {
            TEntitySteamBoiler tEnt = (TEntitySteamBoiler)entity;
            if(tEnt.hasMultiblock()) {
                mBoiler.setSideRender(tEnt.getSideData());
                float colorPercent = (float)((MBSteamBoiler)tEnt.getMultiblock()).getHeatScaled(100) / 200.0f;
                GL11.glColor3f(1.0F, 1.0F - colorPercent, 1.0F - colorPercent);
            }
            else {
                boolean[] noSides = {false, false, false, false, false, false};
                mBoiler.setSideRender(noSides);
            }
            Minecraft.getMinecraft().renderEngine.bindTexture(tBoiler);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            return mBoiler;
        }
        else if(entity instanceof TEntitySteamVent) {
            Minecraft.getMinecraft().renderEngine.bindTexture(tVent);
            Direction side = ((TEntitySteamVent)entity).getFacing();
            if(side == Direction.UP)
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            if(side == Direction.SOUTH) {
                GL11.glRotatef(270F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -1.0F, -1.0F);
            }
            if(side == Direction.NORTH) {
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -1.0F, 1.0F);
            }
            if(side == Direction.EAST) {
                GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
            }
            if(side == Direction.WEST) {
                GL11.glRotatef(270F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(1.0F, -1.0F, 0.0F);
            }
            if(side == Direction.DOWN)
                GL11.glTranslatef(0.0F, -2.0F, 0.0F);
            return mBlock;
        }
        else if(entity instanceof TEntitySteamPiston) {
            Minecraft.getMinecraft().renderEngine.bindTexture(tPiston);
            Direction side = ((TEntitySteamPiston)entity).getFacing();
            mPiston.setExtensionLength((float)((TEntitySteamPiston)entity).getExtendPercent()*0.75F);
            if(side == Direction.UP)
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            if(side == Direction.SOUTH) {
                GL11.glRotatef(270F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -1.0F, -1.0F);
            }
            if(side == Direction.NORTH) {
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -1.0F, 1.0F);
            }
            if(side == Direction.EAST) {
                GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
            }
            if(side == Direction.WEST) {
                GL11.glRotatef(270F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(1.0F, -1.0F, 0.0F);
            }
            if(side == Direction.DOWN)
                GL11.glTranslatef(0.0F, -2.0F, 0.0F);
            return mPiston;
        }
        return null;
    }
}
