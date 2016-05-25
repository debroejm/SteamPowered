package com.majorpotato.steampowered.client.rendering.models;


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAxle extends ModelBase {
    ModelRenderer Axle;

    public ModelAxle() {
        textureWidth = 64;
        textureHeight = 32;

        Axle = new ModelRenderer(this, 0, 0);
        Axle.addBox(0F, 0F, 0F, 6, 16, 6);
        Axle.setRotationPoint(-3F, 8F, -3F);
        Axle.setTextureSize(64, 64);
        Axle.mirror = true;
        setRotation(Axle, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(entity, f, f1, f2, f3, f4, f5);
        Axle.render(f5);
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
