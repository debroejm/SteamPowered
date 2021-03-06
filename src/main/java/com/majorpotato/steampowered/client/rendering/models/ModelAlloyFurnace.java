// Date: 10/27/2014 5:30:32 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package com.majorpotato.steampowered.client.rendering.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAlloyFurnace extends ModelBase
{
    //fields
    ModelRenderer MaterialBoxTop;
    ModelRenderer MaterialBoxLid;
    ModelRenderer MaterialBoxBottom;
    ModelRenderer MaterialBoxMiddle;

    public ModelAlloyFurnace()
    {
        textureWidth = 128;
        textureHeight = 32;

        MaterialBoxTop = new ModelRenderer(this, 0, 0);
        MaterialBoxTop.addBox(0F, 0F, 0F, 18, 5, 18);
        MaterialBoxTop.setRotationPoint(-9F, 8F, -9F);
        MaterialBoxTop.setTextureSize(128, 32);
        MaterialBoxTop.mirror = true;
        setRotation(MaterialBoxTop, 0F, 0F, 0F);
        MaterialBoxLid = new ModelRenderer(this, 64, 7);
        MaterialBoxLid.addBox(0F, 0F, 0F, 16, 1, 16);
        MaterialBoxLid.setRotationPoint(-8F, 7F, -8F);
        MaterialBoxLid.setTextureSize(128, 32);
        MaterialBoxLid.mirror = true;
        setRotation(MaterialBoxLid, 0F, 0F, 0F);
        MaterialBoxBottom = new ModelRenderer(this, 0, 0);
        MaterialBoxBottom.addBox(0F, 0F, 0F, 18, 5, 18);
        MaterialBoxBottom.setRotationPoint(-9F, 19F, -9F);
        MaterialBoxBottom.setTextureSize(128, 32);
        MaterialBoxBottom.mirror = true;
        setRotation(MaterialBoxBottom, 0F, 0F, 0F);
        MaterialBoxMiddle = new ModelRenderer(this, 0, 7);
        MaterialBoxMiddle.addBox(0F, 0F, 0F, 16, 6, 16);
        MaterialBoxMiddle.setRotationPoint(-8F, 13F, -8F);
        MaterialBoxMiddle.setTextureSize(128, 32);
        MaterialBoxMiddle.mirror = true;
        setRotation(MaterialBoxMiddle, 0F, 0F, 0F);
    }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
      super.render(entity, f, f1, f2, f3, f4, f5);
      setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      MaterialBoxTop.render(f5);
      MaterialBoxLid.render(f5);
      MaterialBoxBottom.render(f5);
      MaterialBoxMiddle.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
  }

}
