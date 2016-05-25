package com.majorpotato.steampowered.client.rendering.models;


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPlane extends ModelBase
{
    //fields
    ModelRenderer Box;

    public ModelPlane()
    {
        textureWidth = 16;
        textureHeight = 16;

        Box = new ModelRenderer(this, -16, -16);
        Box.addBox(0F, 0F, 0F, 16, 16, 0);
        Box.setRotationPoint(-8F, 8F, -8F);
        Box.setTextureSize(16, 16);
        Box.mirror = true;
        setRotation(Box, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Box.render(f5);
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

/*
public class ModelPlane {

    private float[] vertex1 = new float[3];
    private float[] uv1 = new float[2];
    private float[] vertex2 = new float[3];
    private float[] uv2 = new float[2];
    private float[] vertex3 = new float[3];
    private float[] uv3 = new float[2];
    private float[] vertex4 = new float[3];
    private float[] uv4 = new float[2];

    public ModelPlane(float x1, float y1, float z1, float uvx1, float uvy1,
                      float x2, float y2, float z2, float uvx2, float uvy2,
                      float x3, float y3, float z3, float uvx3, float uvy3,
                      float x4, float y4, float z4, float uvx4, float uvy4) {
        vertex1[0] = x1; vertex1[1] = y1; vertex1[2] = z1; uv1[0] = uvx1; uv1[1] = uvy1;
        vertex2[0] = x2; vertex2[1] = y2; vertex2[2] = z2; uv2[0] = uvx2; uv2[1] = uvy2;
        vertex3[0] = x3; vertex3[1] = y3; vertex3[2] = z3; uv3[0] = uvx3; uv3[1] = uvy3;
        vertex4[0] = x4; vertex4[1] = y4; vertex4[2] = z4; uv4[0] = uvx4; uv4[1] = uvy4;
    }

    public void render() {
        GL11.glPushMatrix();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(vertex1[0], vertex1[1], vertex1[2]);
        GL11.glTexCoord2f(uv1[0], uv1[1]);
        GL11.glVertex3f(vertex2[0], vertex2[1], vertex2[2]);
        GL11.glTexCoord2f(uv2[0], uv2[1]);
        GL11.glVertex3f(vertex3[0], vertex3[1], vertex3[2]);
        GL11.glTexCoord2f(uv3[0], uv3[1]);
        GL11.glVertex3f(vertex4[0], vertex4[1], vertex4[2]);
        GL11.glTexCoord2f(uv4[0], uv4[1]);
        GL11.glEnd();

        GL11.glPopMatrix();
    }
}
*/