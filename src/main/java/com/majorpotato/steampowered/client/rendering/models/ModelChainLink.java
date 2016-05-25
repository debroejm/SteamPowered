package com.majorpotato.steampowered.client.rendering.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelChainLink extends ModelBase
{
    //fields
    ModelRenderer Rod;
    ModelRenderer SideRight;
    ModelRenderer SideLeft;

    public ModelChainLink()
    {
        textureWidth = 64;
        textureHeight = 32;

        Rod = new ModelRenderer(this, 35, 3);
        Rod.addBox(0F, 0F, 0F, 4, 2, 4);
        Rod.setRotationPoint(-2F, 15F, -1F);
        Rod.setTextureSize(64, 32);
        Rod.mirror = true;
        setRotation(Rod, 0F, 0F, 0F);
        SideRight = new ModelRenderer(this, 42, 0);
        SideRight.addBox(0F, 0F, 0F, 2, 4, 9);
        SideRight.setRotationPoint(2F, 14F, -8F);
        SideRight.setTextureSize(64, 32);
        SideRight.mirror = true;
        setRotation(SideRight, 0F, 0F, 0F);
        SideLeft = new ModelRenderer(this, 42, 0);
        SideLeft.mirror = true;
        SideLeft.addBox(0F, 0F, 0F, 2, 4, 9);
        SideLeft.setRotationPoint(-4F, 14F, -8F);
        SideLeft.setTextureSize(64, 32);
        SideLeft.mirror = true;
        setRotation(SideLeft, 0F, 0F, 0F);
        SideLeft.mirror = false;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Rod.render(f5);
        SideRight.render(f5);
        SideLeft.render(f5);
    }

    public void renderEdge(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        SideRight.render(f5);
        SideLeft.render(f5);
    }

    public void renderMiddle(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Rod.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
