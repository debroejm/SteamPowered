package com.majorpotato.steampowered.client.rendering.models;


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSteamBoilerCustom extends ModelBase {

    ModelPlane north, south, east, west, top, bottom;

    public ModelSteamBoilerCustom() {
        textureWidth = 16;
        textureHeight = 16;

        /*
        north = new ModelPlane(
                0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 0.0f);
        south = new ModelPlane(
                0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f, 1.0f);
        east = new ModelPlane(
                1.0f, 0.0f, 0.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 1.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 0.0f, 1.0f);
        west = new ModelPlane(
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f, 1.0f);
        top = new ModelPlane(
                1.0f, 1.0f, 0.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f, 1.0f);
        bottom = new ModelPlane(
                1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
                1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 1.0f);
                */
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(entity, f, f1, f2, f3, f4, f5);
        //Base.render(f5);
        //Boiler.render(f5);

        //north.render();
        //south.render();
        //east.render();
        //west.render();
        //top.render();
        //bottom.render();
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
