package com.majorpotato.steampowered.client.rendering.models;


import com.majorpotato.steampowered.util.Direction;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelFurnaceChimney extends ModelBase
{
    //fields
    ModelRenderer SingleBackWall;
    ModelRenderer SingleFrontWall;
    ModelRenderer SingleLeftWall;
    ModelRenderer SingleRightWall;
    ModelRenderer SingleMiddle;
    ModelRenderer CornerRightWall;
    ModelRenderer CornerFrontWall;
    ModelRenderer CornerMiddle;
    ModelRenderer EdgeFrontWall;
    ModelRenderer EdgeMiddle;
    ModelRenderer MiddleMiddle;

    boolean[] sides = {false, false, false, false, false, false};

    public ModelFurnaceChimney()
    {
        textureWidth = 128;
        textureHeight = 128;

        SingleBackWall = new ModelRenderer(this, 0, 110);
        SingleBackWall.addBox(0F, 0F, 0F, 14, 16, 2);
        SingleBackWall.setRotationPoint(-7F, 8F, 5F);
        SingleBackWall.setTextureSize(128, 128);
        SingleBackWall.mirror = true;
        setRotation(SingleBackWall, 0F, 0F, 0F);
        SingleFrontWall = new ModelRenderer(this, 0, 110);
        SingleFrontWall.addBox(0F, 0F, 0F, 14, 16, 2);
        SingleFrontWall.setRotationPoint(-7F, 8F, -7F);
        SingleFrontWall.setTextureSize(128, 128);
        SingleFrontWall.mirror = true;
        setRotation(SingleFrontWall, 0F, 0F, 0F);
        SingleLeftWall = new ModelRenderer(this, 32, 102);
        SingleLeftWall.addBox(0F, 0F, 0F, 2, 16, 10);
        SingleLeftWall.setRotationPoint(5F, 8F, -5F);
        SingleLeftWall.setTextureSize(128, 128);
        SingleLeftWall.mirror = true;
        setRotation(SingleLeftWall, 0F, 0F, 0F);
        SingleRightWall = new ModelRenderer(this, 32, 102);
        SingleRightWall.addBox(0F, 0F, 0F, 2, 16, 10);
        SingleRightWall.setRotationPoint(-7F, 8F, -5F);
        SingleRightWall.setTextureSize(128, 128);
        SingleRightWall.mirror = true;
        setRotation(SingleRightWall, 0F, 0F, 0F);
        SingleMiddle = new ModelRenderer(this, 0, 86);
        SingleMiddle.addBox(0F, 0F, 0F, 10, 14, 10);
        SingleMiddle.setRotationPoint(-5F, 10F, -5F);
        SingleMiddle.setTextureSize(128, 128);
        SingleMiddle.mirror = true;
        setRotation(SingleMiddle, 0F, 0F, 0F);
        CornerRightWall = new ModelRenderer(this, 52, 35);
        CornerRightWall.addBox(0F, 0F, 0F, 2, 16, 15);
        CornerRightWall.setRotationPoint(-7F, 8F, -7F);
        CornerRightWall.setTextureSize(128, 128);
        CornerRightWall.mirror = true;
        setRotation(CornerRightWall, 0F, 0F, 0F);
        CornerFrontWall = new ModelRenderer(this, 58, 66);
        CornerFrontWall.addBox(0F, 0F, 0F, 13, 16, 2);
        CornerFrontWall.setRotationPoint(-5F, 8F, -7F);
        CornerFrontWall.setTextureSize(128, 128);
        CornerFrontWall.mirror = true;
        setRotation(CornerFrontWall, 0F, 0F, 0F);
        CornerMiddle = new ModelRenderer(this, 0, 30);
        CornerMiddle.addBox(0F, 0F, 0F, 13, 14, 13);
        CornerMiddle.setRotationPoint(-5F, 10F, -5F);
        CornerMiddle.setTextureSize(128, 128);
        CornerMiddle.mirror = true;
        setRotation(CornerMiddle, 0F, 0F, 0F);
        EdgeFrontWall = new ModelRenderer(this, 40, 84);
        EdgeFrontWall.addBox(0F, 0F, 0F, 16, 16, 2);
        EdgeFrontWall.setRotationPoint(-8F, 8F, -7F);
        EdgeFrontWall.setTextureSize(128, 128);
        EdgeFrontWall.mirror = true;
        setRotation(EdgeFrontWall, 0F, 0F, 0F);
        EdgeMiddle = new ModelRenderer(this, 0, 57);
        EdgeMiddle.addBox(0F, 0F, 0F, 16, 14, 13);
        EdgeMiddle.setRotationPoint(-8F, 10F, -5F);
        EdgeMiddle.setTextureSize(128, 128);
        EdgeMiddle.mirror = true;
        setRotation(EdgeMiddle, 0F, 0F, 0F);
        MiddleMiddle = new ModelRenderer(this, 0, 0);
        MiddleMiddle.addBox(0F, 0F, 0F, 16, 14, 16);
        MiddleMiddle.setRotationPoint(-8F, 10F, -8F);
        MiddleMiddle.setTextureSize(128, 128);
        MiddleMiddle.mirror = true;
        setRotation(MiddleMiddle, 0F, 0F, 0F);
    }

    public void setSideRender(int side, boolean value) {
        if(side < 0 || side > sides.length-1) return;
        sides[side] = value;
    }

    public void setSideRender(Direction side, boolean value) {
        sides[side.ID()] = value;
    }

    public void setSideRender(boolean[] values) {
        if(values.length > 5)
        {
            sides[0] = values[0];
            sides[1] = values[1];
            sides[2] = values[2];
            sides[3] = values[3];
            sides[4] = values[4];
            sides[5] = values[5];
        }
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(entity, f, f1, f2, f3, f4, f5);
        
        GL11.glPushMatrix();
        
        if( sides[Direction.NORTH.ID()] && sides[Direction.SOUTH.ID()] && sides[Direction.EAST.ID()] && sides[Direction.WEST.ID()] ) {
            MiddleMiddle.render(f5);
        }
        else if( sides[Direction.NORTH.ID()] && sides[Direction.EAST.ID()] && !sides[Direction.WEST.ID()] && !sides[Direction.SOUTH.ID()]) {
            GL11.glRotatef(180.0f, 0, 1, 0);
            CornerRightWall.render(f5);
            CornerFrontWall.render(f5);
            CornerMiddle.render(f5);
        }
        else if( sides[Direction.NORTH.ID()] && !sides[Direction.EAST.ID()] && sides[Direction.WEST.ID()] && !sides[Direction.SOUTH.ID()]) {
            GL11.glRotatef(90.0f, 0, 1, 0);
            CornerRightWall.render(f5);
            CornerFrontWall.render(f5);
            CornerMiddle.render(f5);
        }
        else if( !sides[Direction.NORTH.ID()] && sides[Direction.EAST.ID()] && !sides[Direction.WEST.ID()] && sides[Direction.SOUTH.ID()]) {
            GL11.glRotatef(-90.0f, 0, 1, 0);
            CornerRightWall.render(f5);
            CornerFrontWall.render(f5);
            CornerMiddle.render(f5);
        }
        else if( !sides[Direction.NORTH.ID()] && !sides[Direction.EAST.ID()] && sides[Direction.WEST.ID()] && sides[Direction.SOUTH.ID()]) {
            CornerRightWall.render(f5);
            CornerFrontWall.render(f5);
            CornerMiddle.render(f5);
        }
        else if( !sides[Direction.NORTH.ID()] && sides[Direction.EAST.ID()] && sides[Direction.WEST.ID()] && sides[Direction.SOUTH.ID()]) {
            EdgeFrontWall.render(f5);
            EdgeMiddle.render(f5);
        }
        else if( sides[Direction.NORTH.ID()] && sides[Direction.EAST.ID()] && sides[Direction.WEST.ID()] && !sides[Direction.SOUTH.ID()]) {
            GL11.glRotatef(180.0f, 0, 1, 0);
            EdgeFrontWall.render(f5);
            EdgeMiddle.render(f5);
        }
        else if( sides[Direction.NORTH.ID()] && !sides[Direction.EAST.ID()] && sides[Direction.WEST.ID()] && sides[Direction.SOUTH.ID()]) {
            GL11.glRotatef(90.0f, 0, 1, 0);
            EdgeFrontWall.render(f5);
            EdgeMiddle.render(f5);
        }
        else if( sides[Direction.NORTH.ID()] && sides[Direction.EAST.ID()] && !sides[Direction.WEST.ID()] && sides[Direction.SOUTH.ID()]) {
            GL11.glRotatef(-90.0f, 0, 1, 0);
            EdgeFrontWall.render(f5);
            EdgeMiddle.render(f5);
        }
        else {
            SingleBackWall.render(f5);
            SingleFrontWall.render(f5);
            SingleLeftWall.render(f5);
            SingleRightWall.render(f5);
            SingleMiddle.render(f5);
        }
        
        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
