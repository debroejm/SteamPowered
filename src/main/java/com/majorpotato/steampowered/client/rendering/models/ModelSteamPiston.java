// Date: 11/25/2014 6:06:55 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package com.majorpotato.steampowered.client.rendering.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSteamPiston extends ModelBase
{
  //fields
    ModelRenderer Body;
    ModelRenderer Head;
    ModelRenderer Arm;
  
  public ModelSteamPiston()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Body = new ModelRenderer(this, 0, 0);
      Body.addBox(0F, 0F, 0F, 16, 12, 16);
      Body.setRotationPoint(-8F, 12F, -8F);
      Body.setTextureSize(64, 64);
      Body.mirror = true;
      setRotation(Body, 0F, 0F, 0F);
      Head = new ModelRenderer(this, 0, 28);
      Head.addBox(0F, 0F, 0F, 16, 4, 16);
      Head.setRotationPoint(-8F, 8F, -8F);
      Head.setTextureSize(64, 64);
      Head.mirror = true;
      setRotation(Head, 0F, 0F, 0F);
      Arm = new ModelRenderer(this, 0, 0);
      Arm.addBox(0F, 0F, 0F, 4, 12, 4);
      Arm.setRotationPoint(-2F, 12F, -2F);
      Arm.setTextureSize(64, 64);
      Arm.mirror = true;
      setRotation(Arm, 0F, 0F, 0F);
  }

  public void setExtensionLength(float length)
  {
      float val = length;
      if(val > 0.75F) val = 0.75F;
      if(val < 0.0F) val = 0.0F;
      Head.setRotationPoint(-8F, 8F-12F*val, -8F);
      Arm.setRotationPoint(-2F, 12F-12F*val, -2F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(entity, f, f1, f2, f3, f4, f5);
    Body.render(f5);
    Head.render(f5);
    Arm.render(f5);
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
