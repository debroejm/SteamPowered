package com.majorpotato.steampowered.client.rendering.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHotPlate extends ModelBase {

    ModelRenderer plate;

    public ModelHotPlate()
    {
        textureWidth = 64;
        textureHeight = 32;

        plate = new ModelRenderer(this, 0, 0);
        plate.addBox(0F, 0F, 0F, 16, 4, 16);
        plate.setRotationPoint(-8F, 8F, -8F);
        plate.setTextureSize(64, 32);
        plate.mirror = true;
        setRotation(plate, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(entity, f, f1, f2, f3, f4, f5);
        plate.render(f5);
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
