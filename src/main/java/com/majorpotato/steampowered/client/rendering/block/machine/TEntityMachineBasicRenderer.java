package com.majorpotato.steampowered.client.rendering.block.machine;

import com.majorpotato.steampowered.client.rendering.models.*;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityHotPlate;
import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityAlloyFurnace;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityChimneyStack;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityFirebox;
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


public class TEntityMachineBasicRenderer extends TileEntitySpecialRenderer {

    // Models
    private final ModelFirebox mFirebox = new ModelFirebox();
    private final ModelAlloyFurnace mAlloyFurnace = new ModelAlloyFurnace();
    private final ModelFurnaceChimney mChimneyStack = new ModelFurnaceChimney();
    private final ModelBlock mBlock = new ModelBlock();
    private final ModelHotPlate mHotPlate = new ModelHotPlate();

    // Textures
    public static final ResourceLocation tFirebox_default = new ResourceLocation(Reference.MOD_ID+":textures/models/Firebox.png");
    public static final ResourceLocation tFirebox_burning = new ResourceLocation(Reference.MOD_ID+":textures/models/FireboxBurning.png");
    public static final ResourceLocation tAlloyFurnace = new ResourceLocation(Reference.MOD_ID+":textures/models/AlloyFurnace.png");
    public static final ResourceLocation tAlloyFurnaceUnformed = new ResourceLocation(Reference.MOD_ID+":textures/models/AlloyFurnaceUnformed.png");
    public static final ResourceLocation tChimneyStack = new ResourceLocation(Reference.MOD_ID+":textures/models/ChimneyStack.png");
    public static final ResourceLocation tHotPlate = new ResourceLocation(Reference.MOD_ID+":textures/models/HotPlate.png");

    public TEntityMachineBasicRenderer() { }

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
        if(entity instanceof TEntityFirebox) {
            TEntityFirebox tEnt = (TEntityFirebox)entity;
            mFirebox.setSideRender(Direction.NORTH, tEnt.isSideConnected(Direction.NORTH));
            mFirebox.setSideRender(Direction.SOUTH, tEnt.isSideConnected(Direction.SOUTH));
            mFirebox.setSideRender(Direction.EAST, tEnt.isSideConnected(Direction.EAST));
            mFirebox.setSideRender(Direction.WEST, tEnt.isSideConnected(Direction.WEST));
            mFirebox.setSideRender(Direction.UP, tEnt.isSideConnected(Direction.UP));
            mFirebox.setSideRender(Direction.DOWN, tEnt.isSideConnected(Direction.DOWN));
            mFirebox.setCurrentFuelScale((float)tEnt.getCurrentFuelPercentage());

            if(tEnt.isBurning())
                Minecraft.getMinecraft().renderEngine.bindTexture(tFirebox_burning);
            else
                Minecraft.getMinecraft().renderEngine.bindTexture(tFirebox_default);

            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            return mFirebox;
        }
        else if(entity instanceof TEntityAlloyFurnace) {
            if(((TEntityAlloyFurnace)entity).hasMultiblock())
                Minecraft.getMinecraft().renderEngine.bindTexture(tAlloyFurnace);
            else
                Minecraft.getMinecraft().renderEngine.bindTexture(tAlloyFurnaceUnformed);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            return mAlloyFurnace;
        }
        else if(entity instanceof TEntityChimneyStack) {
            TEntityChimneyStack tEnt = (TEntityChimneyStack)entity;
            mChimneyStack.setSideRender(tEnt.getSideData());
            Minecraft.getMinecraft().renderEngine.bindTexture(tChimneyStack);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            return mChimneyStack;
        }
        else if(entity instanceof TEntityHotPlate) {
            float heatPercent = ((float)((TEntityHotPlate)entity).getHeat()) / ((float)TEntityHotPlate.MAX_HEAT);
            Minecraft.getMinecraft().renderEngine.bindTexture(tHotPlate);
            GL11.glColor3f(1.0F, 1.0F - heatPercent * 0.5F, 1.0F - heatPercent * 0.6F);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            return mHotPlate;
        }

        return null;
    }
}
