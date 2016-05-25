// Date: 11/19/2014 1:13:30 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package com.majorpotato.steampowered.client.rendering.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGear extends ModelBase
{
  //fields
  ModelRenderer Axle;
    ModelRenderer BodyR1;
    ModelRenderer BodyR2;
    ModelRenderer BodyR3;
    ModelRenderer BodyR4;
    ModelRenderer BodyR5;
    ModelRenderer BodyR6;
    ModelRenderer BodyR7;
    ModelRenderer BodyR9;
    ModelRenderer BodyR8;
    ModelRenderer EdgeR1;
    ModelRenderer EdgeR2;
    ModelRenderer EdgeR3;
    ModelRenderer EdgeR4;
    ModelRenderer EdgeR5;
    ModelRenderer EdgeR6;
    ModelRenderer EdgeR7;
    ModelRenderer EdgeR8;
    ModelRenderer EdgeR9;
  
  public ModelGear()
  {
      textureWidth = 64;
      textureHeight = 32;

      Axle = new ModelRenderer(this, 0, 0);
      Axle.addBox(0F, 0F, 0F, 6, 16, 6);
      Axle.setRotationPoint(-3F, 8F, -3F);
      Axle.setTextureSize(64, 32);
      Axle.mirror = true;
      setRotation(Axle, 0F, 0F, 0F);
      BodyR1 = new ModelRenderer(this, 7, 5);
      BodyR1.addBox(-2F, 0F, 0F, 4, 2, 17);
      BodyR1.setRotationPoint(0F, 15F, 0F);
      BodyR1.setTextureSize(64, 32);
      BodyR1.mirror = true;
      setRotation(BodyR1, 0F, 0F, 0F);
      BodyR2 = new ModelRenderer(this, 7, 5);
      BodyR2.addBox(-2F, 0F, 0F, 4, 2, 17);
      BodyR2.setRotationPoint(0F, 15F, 0F);
      BodyR2.setTextureSize(64, 32);
      BodyR2.mirror = true;
      setRotation(BodyR2, 0F, 0.6981317F, 0F);
      BodyR3 = new ModelRenderer(this, 7, 5);
      BodyR3.addBox(-2F, 0F, 0F, 4, 2, 17);
      BodyR3.setRotationPoint(0F, 15F, 0F);
      BodyR3.setTextureSize(64, 32);
      BodyR3.mirror = true;
      setRotation(BodyR3, 0F, 1.396263F, 0F);
      BodyR4 = new ModelRenderer(this, 7, 5);
      BodyR4.addBox(-2F, 0F, 0F, 4, 2, 17);
      BodyR4.setRotationPoint(0F, 15F, 0F);
      BodyR4.setTextureSize(64, 32);
      BodyR4.mirror = true;
      setRotation(BodyR4, 0F, 2.094395F, 0F);
      BodyR5 = new ModelRenderer(this, 7, 5);
      BodyR5.addBox(-2F, 0F, 0F, 4, 2, 17);
      BodyR5.setRotationPoint(0F, 15F, 0F);
      BodyR5.setTextureSize(64, 32);
      BodyR5.mirror = true;
      setRotation(BodyR5, 0F, 2.792527F, 0F);
      BodyR6 = new ModelRenderer(this, 7, 5);
      BodyR6.addBox(-2F, 0F, 0F, 4, 2, 17);
      BodyR6.setRotationPoint(0F, 15F, 0F);
      BodyR6.setTextureSize(64, 32);
      BodyR6.mirror = true;
      setRotation(BodyR6, 0F, 3.490659F, 0F);
      BodyR7 = new ModelRenderer(this, 7, 5);
      BodyR7.addBox(-2F, 0F, 0F, 4, 2, 17);
      BodyR7.setRotationPoint(0F, 15F, 0F);
      BodyR7.setTextureSize(64, 32);
      BodyR7.mirror = true;
      setRotation(BodyR7, 0F, 4.18879F, 0F);
      BodyR9 = new ModelRenderer(this, 7, 5);
      BodyR9.addBox(-2F, 0F, 0F, 4, 2, 17);
      BodyR9.setRotationPoint(0F, 15F, 0F);
      BodyR9.setTextureSize(64, 32);
      BodyR9.mirror = true;
      setRotation(BodyR9, 0F, 5.585053F, 0F);
      BodyR8 = new ModelRenderer(this, 7, 5);
      BodyR8.addBox(-2F, 0F, 0F, 4, 2, 17);
      BodyR8.setRotationPoint(0F, 15F, 0F);
      BodyR8.setTextureSize(64, 32);
      BodyR8.mirror = true;
      setRotation(BodyR8, 0F, 4.886922F, 0F);
      EdgeR1 = new ModelRenderer(this, 32, 14);
      EdgeR1.addBox(-5F, 0F, 9F, 10, 4, 4);
      EdgeR1.setRotationPoint(0F, 14F, 0F);
      EdgeR1.setTextureSize(64, 32);
      EdgeR1.mirror = true;
      setRotation(EdgeR1, 0F, 0.3490659F, 0F);
      EdgeR2 = new ModelRenderer(this, 32, 14);
      EdgeR2.addBox(-5F, 0F, 9F, 10, 4, 4);
      EdgeR2.setRotationPoint(0F, 14F, 0F);
      EdgeR2.setTextureSize(64, 32);
      EdgeR2.mirror = true;
      setRotation(EdgeR2, 0F, 1.047198F, 0F);
      EdgeR3 = new ModelRenderer(this, 32, 14);
      EdgeR3.addBox(-5F, 0F, 9F, 10, 4, 4);
      EdgeR3.setRotationPoint(0F, 14F, 0F);
      EdgeR3.setTextureSize(64, 32);
      EdgeR3.mirror = true;
      setRotation(EdgeR3, 0F, 1.745329F, 0F);
      EdgeR4 = new ModelRenderer(this, 32, 14);
      EdgeR4.addBox(-5F, 0F, 9F, 10, 4, 4);
      EdgeR4.setRotationPoint(0F, 14F, 0F);
      EdgeR4.setTextureSize(64, 32);
      EdgeR4.mirror = true;
      setRotation(EdgeR4, 0F, 2.443461F, 0F);
      EdgeR5 = new ModelRenderer(this, 32, 14);
      EdgeR5.addBox(-5F, 0F, 9F, 10, 4, 4);
      EdgeR5.setRotationPoint(0F, 14F, 0F);
      EdgeR5.setTextureSize(64, 32);
      EdgeR5.mirror = true;
      setRotation(EdgeR5, 0F, 3.141593F, 0F);
      EdgeR6 = new ModelRenderer(this, 32, 14);
      EdgeR6.addBox(-5F, 0F, 9F, 10, 4, 4);
      EdgeR6.setRotationPoint(0F, 14F, 0F);
      EdgeR6.setTextureSize(64, 32);
      EdgeR6.mirror = true;
      setRotation(EdgeR6, 0F, 3.839724F, 0F);
      EdgeR7 = new ModelRenderer(this, 32, 14);
      EdgeR7.addBox(-5F, 0F, 9F, 10, 4, 4);
      EdgeR7.setRotationPoint(0F, 14F, 0F);
      EdgeR7.setTextureSize(64, 32);
      EdgeR7.mirror = true;
      setRotation(EdgeR7, 0F, 4.537856F, 0F);
      EdgeR8 = new ModelRenderer(this, 32, 14);
      EdgeR8.addBox(-5F, 0F, 9F, 10, 4, 4);
      EdgeR8.setRotationPoint(0F, 14F, 0F);
      EdgeR8.setTextureSize(64, 32);
      EdgeR8.mirror = true;
      setRotation(EdgeR8, 0F, 5.235988F, 0F);
      EdgeR9 = new ModelRenderer(this, 32, 14);
      EdgeR9.addBox(-5F, 0F, 9F, 10, 4, 4);
      EdgeR9.setRotationPoint(0F, 14F, 0F);
      EdgeR9.setTextureSize(64, 32);
      EdgeR9.mirror = true;
      setRotation(EdgeR9, 0F, 5.93412F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
      Axle.render(f5);
      BodyR1.render(f5);
      BodyR2.render(f5);
      BodyR3.render(f5);
      BodyR4.render(f5);
      BodyR5.render(f5);
      BodyR6.render(f5);
      BodyR7.render(f5);
      BodyR9.render(f5);
      BodyR8.render(f5);
      EdgeR1.render(f5);
      EdgeR2.render(f5);
      EdgeR3.render(f5);
      EdgeR4.render(f5);
      EdgeR5.render(f5);
      EdgeR6.render(f5);
      EdgeR7.render(f5);
      EdgeR8.render(f5);
      EdgeR9.render(f5);
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