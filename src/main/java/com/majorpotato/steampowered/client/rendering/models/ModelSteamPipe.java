package com.majorpotato.steampowered.client.rendering.models;

import com.majorpotato.steampowered.util.Direction;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelSteamPipe extends ModelBase {
    //fields
    ModelRenderer EndBottom;
    ModelRenderer EndTop;
    ModelRenderer PipeBottom;
    ModelRenderer PipeTop;
    ModelRenderer EndRight;
    ModelRenderer EndLeft;
    ModelRenderer PipeRight;
    ModelRenderer PipeLeft;
    ModelRenderer EndFront;
    ModelRenderer EndBack;
    ModelRenderer PipeBack;
    ModelRenderer PipeFront;
    ModelRenderer PipeHousing;

    boolean[] sides = new boolean[6];

    public ModelSteamPipe()
    {
        textureWidth = 64;
        textureHeight = 32;

        sides[0] = false;
        sides[1] = false;
        sides[2] = true;
        sides[3] = true;
        sides[4] = false;
        sides[5] = false;

        EndBottom = new ModelRenderer(this, 40, 9);
        EndBottom.addBox(0F, 0F, 0F, 6, 1, 6);
        EndBottom.setRotationPoint(-3F, 23F, -3F);
        EndBottom.setTextureSize(64, 32);
        EndBottom.mirror = true;
        setRotation(EndBottom, 0F, 0F, 0F);
        EndTop = new ModelRenderer(this, 40, 9);
        EndTop.addBox(0F, 0F, 0F, 6, 1, 6);
        EndTop.setRotationPoint(-3F, 8F, -3F);
        EndTop.setTextureSize(64, 32);
        EndTop.mirror = true;
        setRotation(EndTop, 0F, 0F, 0F);
        PipeBottom = new ModelRenderer(this, 29, 4);
        PipeBottom.addBox(0F, 0F, 0F, 4, 7, 4);
        PipeBottom.setRotationPoint(-2F, 16F, -2F);
        PipeBottom.setTextureSize(64, 32);
        PipeBottom.mirror = true;
        setRotation(PipeBottom, 0F, 0F, 0F);
        PipeTop = new ModelRenderer(this, 29, 4);
        PipeTop.addBox(0F, 0F, 0F, 4, 7, 4);
        PipeTop.setRotationPoint(-2F, 9F, -2F);
        PipeTop.setTextureSize(64, 32);
        PipeTop.mirror = true;
        setRotation(PipeTop, 0F, 0F, 0F);
        EndRight = new ModelRenderer(this, 40, 20);
        EndRight.addBox(0F, 0F, 0F, 1, 6, 6);
        EndRight.setRotationPoint(-8F, 13F, -3F);
        EndRight.setTextureSize(64, 32);
        EndRight.mirror = true;
        setRotation(EndRight, 0F, 0F, 0F);
        EndLeft = new ModelRenderer(this, 40, 20);
        EndLeft.addBox(0F, 0F, 0F, 1, 6, 6);
        EndLeft.setRotationPoint(7F, 13F, -3F);
        EndLeft.setTextureSize(64, 32);
        EndLeft.mirror = true;
        setRotation(EndLeft, 0F, 0F, 0F);
        PipeRight = new ModelRenderer(this, 24, 16);
        PipeRight.addBox(0F, 0F, 0F, 7, 4, 4);
        PipeRight.setRotationPoint(-7F, 14F, -2F);
        PipeRight.setTextureSize(64, 32);
        PipeRight.mirror = true;
        setRotation(PipeRight, 0F, 0F, 0F);
        PipeLeft = new ModelRenderer(this, 24, 16);
        PipeLeft.addBox(0F, 0F, 0F, 7, 4, 4);
        PipeLeft.setRotationPoint(0F, 14F, -2F);
        PipeLeft.setTextureSize(64, 32);
        PipeLeft.mirror = true;
        setRotation(PipeLeft, 0F, 0F, 0F);
        EndFront = new ModelRenderer(this, 15, 5);
        EndFront.addBox(0F, 0F, 0F, 6, 6, 1);
        EndFront.setRotationPoint(-3F, 13F, -8F);
        EndFront.setTextureSize(64, 32);
        EndFront.mirror = true;
        setRotation(EndFront, 0F, 0F, 0F);
        EndBack = new ModelRenderer(this, 15, 5);
        EndBack.addBox(0F, 0F, 0F, 6, 6, 1);
        EndBack.setRotationPoint(-3F, 13F, 7F);
        EndBack.setTextureSize(64, 32);
        EndBack.mirror = true;
        setRotation(EndBack, 0F, 0F, 0F);
        PipeBack = new ModelRenderer(this, 0, 5);
        PipeBack.addBox(0F, 0F, 0F, 4, 4, 7);
        PipeBack.setRotationPoint(-2F, 14F, 0F);
        PipeBack.setTextureSize(64, 32);
        PipeBack.mirror = true;
        setRotation(PipeBack, 0F, 0F, 0F);
        PipeFront = new ModelRenderer(this, 0, 5);
        PipeFront.addBox(0F, 0F, 0F, 4, 4, 7);
        PipeFront.setRotationPoint(-2F, 14F, -7F);
        PipeFront.setTextureSize(64, 32);
        PipeFront.mirror = true;
        setRotation(PipeFront, 0F, 0F, 0F);
        PipeHousing = new ModelRenderer(this, 0, 20);
        PipeHousing.addBox(0F, 0F, 0F, 6, 6, 6);
        PipeHousing.setRotationPoint(-3F, 13F, -3F);
        PipeHousing.setTextureSize(64, 32);
        PipeHousing.mirror = true;
        setRotation(PipeHousing, 0F, 0F, 0F);
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
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        boolean NS = false;
        boolean EW = false;
        boolean TB = false;

        if(sides[Direction.UP.ID()]) {
            PipeTop.render(f5);
            EndTop.render(f5);
            TB = true;
        }
        if(sides[Direction.DOWN.ID()]) {
            PipeBottom.render(f5);
            EndBottom.render(f5);
            TB = true;
        }
        if(sides[Direction.NORTH.ID()]) {
            PipeFront.render(f5);
            EndFront.render(f5);
            NS = true;
        }
        if(sides[Direction.SOUTH.ID()]) {
            PipeBack.render(f5);
            EndBack.render(f5);
            NS = true;
        }
        if(sides[Direction.EAST.ID()]) {
            PipeRight.render(f5);
            EndRight.render(f5);
            EW = true;
        }
        if(sides[Direction.WEST.ID()]) {
            PipeLeft.render(f5);
            EndLeft.render(f5);
            EW = true;
        }

        if( (NS && EW) || (NS && TB) || (EW && TB) || (!NS && !EW && !TB) )// XOR FTW
            PipeHousing.render(f5);
        else if(NS) {
            if(sides[Direction.NORTH.ID()] ^ sides[Direction.SOUTH.ID()])
                PipeHousing.render(f5);
        } else if(EW) {
            if(sides[Direction.EAST.ID()] ^ sides[Direction.WEST.ID()])
                PipeHousing.render(f5);
        } else if(TB) {
            if(sides[Direction.UP.ID()] ^ sides[Direction.DOWN.ID()])
                PipeHousing.render(f5);
        }
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
