package com.majorpotato.steampowered.client.rendering.models;


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelConnectedTexture extends ModelBase
{
    //fields

    ModelRenderer Single_By_TubeEndLeft;
    ModelRenderer TubeHoriz_By_TubeEndRight;
    ModelRenderer TubeEndUp_By_CornerUpLeft;
    ModelRenderer EdgeUp_By_CornerUpRight;
    ModelRenderer TubeVert_By_EdgeLeft;
    ModelRenderer Middle_By_EdgeRight;
    ModelRenderer TubeEndDown_By_CornerDownLeft;
    ModelRenderer EdgeDown_By_CornerDownRight;
    ModelRenderer TubeCornerUpLeft_By_TubeCornerUpRight;
    ModelRenderer TubeCornerDownLeft_By_TubeCornerDownRight;
    ModelRenderer EdgeLeftAndNookUp_By_EdgeUpAndNookRight;
    ModelRenderer EdgeDownAndNookLeft_By_EdgeRightAndNookDown;
    ModelRenderer EdgeLeftAndNookDouble_By_EdgeUpAndNookDouble;
    ModelRenderer EdgeDownAndNookDouble_By_EdgeRightAndNookDouble;
    ModelRenderer EdgeLeftAndNookDown_By_EdgeUpAndNookLeft;
    ModelRenderer EdgeDownAndNookRight_By_EdgeRightAndNookUp;
    ModelRenderer NookTripleMissingUpRight_By_NookTripleMissingDownRight;
    ModelRenderer NookTripleMissingUpLeft_By_NookTripleMissingDownLeft;
    ModelRenderer NookDownRight_By_NookDownLeft;
    ModelRenderer NookUpRight_By_NookUpLeft;
    ModelRenderer NookDoubleRight_By_NookDoubleDown;
    ModelRenderer NookDoubleUp_By_NookDoubleLeft;
    ModelRenderer NookCrossBack_By_NookCrossForward;
    ModelRenderer NookFull_By_Nothing;

    boolean[][][] neighbors = new boolean[3][3][3];

    public ModelConnectedTexture()
    {
        textureWidth = 192;
        textureHeight = 64;

        Single_By_TubeEndLeft = new ModelRenderer(this, 0, 0).addBox(-8F, -8F, 0F, 16, 16, 0);
        Single_By_TubeEndLeft.setRotationPoint(0F, 16F, 8F);
        Single_By_TubeEndLeft.setTextureSize(192, 64);
        Single_By_TubeEndLeft.mirror = true;
        setRotation(Single_By_TubeEndLeft, 0F, 0F, 0F);

        TubeHoriz_By_TubeEndRight = new ModelRenderer(this, 32, 0).addBox(-8F, -8F, 0F, 16, 16, 0);
        TubeHoriz_By_TubeEndRight .setRotationPoint(0F, 16F, 8F);
        TubeHoriz_By_TubeEndRight .setTextureSize(192, 64);
        TubeHoriz_By_TubeEndRight .mirror = true;
        setRotation(TubeHoriz_By_TubeEndRight , 0F, 0F, 0F);

        TubeEndUp_By_CornerUpLeft = new ModelRenderer(this, 0, 16).addBox(-8F, -8F, 0F, 16, 16, 0);
        TubeEndUp_By_CornerUpLeft.setRotationPoint(0F, 16F, 8F);
        TubeEndUp_By_CornerUpLeft.setTextureSize(192, 64);
        TubeEndUp_By_CornerUpLeft.mirror = true;
        setRotation(TubeEndUp_By_CornerUpLeft, 0F, 0F, 0F);

        EdgeUp_By_CornerUpRight = new ModelRenderer(this, 32, 16).addBox(-8F, -8F, 0F, 16, 16, 0);
        EdgeUp_By_CornerUpRight.setRotationPoint(0F, 16F, 8F);
        EdgeUp_By_CornerUpRight.setTextureSize(192, 64);
        EdgeUp_By_CornerUpRight.mirror = true;
        setRotation(EdgeUp_By_CornerUpRight, 0F, 0F, 0F);

        TubeVert_By_EdgeLeft = new ModelRenderer(this, 0, 32).addBox(-8F, -8F, 0F, 16, 16, 0);
        TubeVert_By_EdgeLeft.setRotationPoint(0F, 16F, 8F);
        TubeVert_By_EdgeLeft.setTextureSize(192, 64);
        TubeVert_By_EdgeLeft.mirror = true;
        setRotation(TubeVert_By_EdgeLeft, 0F, 0F, 0F);

        Middle_By_EdgeRight = new ModelRenderer(this, 32, 32).addBox(-8F, -8F, 0F, 16, 16, 0);
        Middle_By_EdgeRight.setRotationPoint(0F, 16F, 8F);
        Middle_By_EdgeRight.setTextureSize(192, 64);
        Middle_By_EdgeRight.mirror = true;
        setRotation(Middle_By_EdgeRight, 0F, 0F, 0F);

        TubeEndDown_By_CornerDownLeft = new ModelRenderer(this, 0, 48).addBox(-8F, -8F, 0F, 16, 16, 0);
        TubeEndDown_By_CornerDownLeft.setRotationPoint(0F, 16F, 8F);
        TubeEndDown_By_CornerDownLeft.setTextureSize(192, 64);
        TubeEndDown_By_CornerDownLeft.mirror = true;
        setRotation(TubeEndDown_By_CornerDownLeft, 0F, 0F, 0F);

        EdgeDown_By_CornerDownRight = new ModelRenderer(this, 32, 48).addBox(-8F, -8F, 0F, 16, 16, 0);
        EdgeDown_By_CornerDownRight.setRotationPoint(0F, 16F, 8F);
        EdgeDown_By_CornerDownRight.setTextureSize(192, 64);
        EdgeDown_By_CornerDownRight.mirror = true;
        setRotation(EdgeDown_By_CornerDownRight, 0F, 0F, 0F);

        TubeCornerUpLeft_By_TubeCornerUpRight = new ModelRenderer(this, 64, 0).addBox(-8F, -8F, 0F, 16, 16, 0);
        TubeCornerUpLeft_By_TubeCornerUpRight.setRotationPoint(0F, 16F, 8F);
        TubeCornerUpLeft_By_TubeCornerUpRight.setTextureSize(192, 64);
        TubeCornerUpLeft_By_TubeCornerUpRight.mirror = true;
        setRotation(TubeCornerUpLeft_By_TubeCornerUpRight, 0F, 0F, 0F);

        TubeCornerDownLeft_By_TubeCornerDownRight = new ModelRenderer(this, 64, 16).addBox(-8F, -8F, 0F, 16, 16, 0);
        TubeCornerDownLeft_By_TubeCornerDownRight .setRotationPoint(0F, 16F, 8F);
        TubeCornerDownLeft_By_TubeCornerDownRight .setTextureSize(192, 64);
        TubeCornerDownLeft_By_TubeCornerDownRight .mirror = true;
        setRotation(TubeCornerDownLeft_By_TubeCornerDownRight , 0F, 0F, 0F);

        EdgeLeftAndNookUp_By_EdgeUpAndNookRight = new ModelRenderer(this, 64, 32).addBox(-8F, -8F, 0F, 16, 16, 0);
        EdgeLeftAndNookUp_By_EdgeUpAndNookRight.setRotationPoint(0F, 16F, 8F);
        EdgeLeftAndNookUp_By_EdgeUpAndNookRight.setTextureSize(192, 64);
        EdgeLeftAndNookUp_By_EdgeUpAndNookRight.mirror = true;
        setRotation(EdgeLeftAndNookUp_By_EdgeUpAndNookRight, 0F, 0F, 0F);

        EdgeDownAndNookLeft_By_EdgeRightAndNookDown = new ModelRenderer(this, 64, 48).addBox(-8F, -8F, 0F, 16, 16, 0);
        EdgeDownAndNookLeft_By_EdgeRightAndNookDown.setRotationPoint(0F, 16F, 8F);
        EdgeDownAndNookLeft_By_EdgeRightAndNookDown.setTextureSize(192, 64);
        EdgeDownAndNookLeft_By_EdgeRightAndNookDown.mirror = true;
        setRotation(EdgeDownAndNookLeft_By_EdgeRightAndNookDown, 0F, 0F, 0F);

        EdgeLeftAndNookDouble_By_EdgeUpAndNookDouble = new ModelRenderer(this, 96, 0).addBox(-8F, -8F, 0F, 16, 16, 0);
        EdgeLeftAndNookDouble_By_EdgeUpAndNookDouble.setRotationPoint(0F, 16F, 8F);
        EdgeLeftAndNookDouble_By_EdgeUpAndNookDouble.setTextureSize(192, 64);
        EdgeLeftAndNookDouble_By_EdgeUpAndNookDouble.mirror = true;
        setRotation(EdgeLeftAndNookDouble_By_EdgeUpAndNookDouble, 0F, 0F, 0F);

        EdgeDownAndNookDouble_By_EdgeRightAndNookDouble = new ModelRenderer(this, 96, 16).addBox(-8F, -8F, 0F, 16, 16, 0);
        EdgeDownAndNookDouble_By_EdgeRightAndNookDouble .setRotationPoint(0F, 16F, 8F);
        EdgeDownAndNookDouble_By_EdgeRightAndNookDouble .setTextureSize(192, 64);
        EdgeDownAndNookDouble_By_EdgeRightAndNookDouble .mirror = true;
        setRotation(EdgeDownAndNookDouble_By_EdgeRightAndNookDouble , 0F, 0F, 0F);

        EdgeLeftAndNookDown_By_EdgeUpAndNookLeft = new ModelRenderer(this, 96, 32).addBox(-8F, -8F, 0F, 16, 16, 0);
        EdgeLeftAndNookDown_By_EdgeUpAndNookLeft.setRotationPoint(0F, 16F, 8F);
        EdgeLeftAndNookDown_By_EdgeUpAndNookLeft.setTextureSize(192, 64);
        EdgeLeftAndNookDown_By_EdgeUpAndNookLeft.mirror = true;
        setRotation(EdgeLeftAndNookDown_By_EdgeUpAndNookLeft, 0F, 0F, 0F);

        EdgeDownAndNookRight_By_EdgeRightAndNookUp = new ModelRenderer(this, 96, 48).addBox(-8F, -8F, 0F, 16, 16, 0);
        EdgeDownAndNookRight_By_EdgeRightAndNookUp.setRotationPoint(0F, 16F, 8F);
        EdgeDownAndNookRight_By_EdgeRightAndNookUp.setTextureSize(192, 64);
        EdgeDownAndNookRight_By_EdgeRightAndNookUp.mirror = true;
        setRotation(EdgeDownAndNookRight_By_EdgeRightAndNookUp, 0F, 0F, 0F);

        NookTripleMissingUpRight_By_NookTripleMissingDownRight = new ModelRenderer(this, 128, 0).addBox(-8F, -8F, 0F, 16, 16, 0);
        NookTripleMissingUpRight_By_NookTripleMissingDownRight.setRotationPoint(0F, 16F, 8F);
        NookTripleMissingUpRight_By_NookTripleMissingDownRight.setTextureSize(192, 64);
        NookTripleMissingUpRight_By_NookTripleMissingDownRight.mirror = true;
        setRotation(NookTripleMissingUpRight_By_NookTripleMissingDownRight, 0F, 0F, 0F);

        NookTripleMissingUpLeft_By_NookTripleMissingDownLeft = new ModelRenderer(this, 128, 16).addBox(-8F, -8F, 0F, 16, 16, 0);
        NookTripleMissingUpLeft_By_NookTripleMissingDownLeft .setRotationPoint(0F, 16F, 8F);
        NookTripleMissingUpLeft_By_NookTripleMissingDownLeft .setTextureSize(192, 64);
        NookTripleMissingUpLeft_By_NookTripleMissingDownLeft .mirror = true;
        setRotation(NookTripleMissingUpLeft_By_NookTripleMissingDownLeft , 0F, 0F, 0F);

        NookDownRight_By_NookDownLeft = new ModelRenderer(this, 128, 32).addBox(-8F, -8F, 0F, 16, 16, 0);
        NookDownRight_By_NookDownLeft.setRotationPoint(0F, 16F, 8F);
        NookDownRight_By_NookDownLeft.setTextureSize(192, 64);
        NookDownRight_By_NookDownLeft.mirror = true;
        setRotation(NookDownRight_By_NookDownLeft, 0F, 0F, 0F);

        NookUpRight_By_NookUpLeft = new ModelRenderer(this, 128, 48).addBox(-8F, -8F, 0F, 16, 16, 0);
        NookUpRight_By_NookUpLeft.setRotationPoint(0F, 16F, 8F);
        NookUpRight_By_NookUpLeft.setTextureSize(192, 64);
        NookUpRight_By_NookUpLeft.mirror = true;
        setRotation(NookUpRight_By_NookUpLeft, 0F, 0F, 0F);

        NookDoubleRight_By_NookDoubleDown = new ModelRenderer(this, 160, 0).addBox(-8F, -8F, 0F, 16, 16, 0);
        NookDoubleRight_By_NookDoubleDown.setRotationPoint(0F, 16F, 8F);
        NookDoubleRight_By_NookDoubleDown.setTextureSize(192, 64);
        NookDoubleRight_By_NookDoubleDown.mirror = true;
        setRotation(NookDoubleRight_By_NookDoubleDown, 0F, 0F, 0F);

        NookDoubleUp_By_NookDoubleLeft = new ModelRenderer(this, 160, 16).addBox(-8F, -8F, 0F, 16, 16, 0);
        NookDoubleUp_By_NookDoubleLeft.setRotationPoint(0F, 16F, 8F);
        NookDoubleUp_By_NookDoubleLeft.setTextureSize(192, 64);
        NookDoubleUp_By_NookDoubleLeft.mirror = true;
        setRotation(NookDoubleUp_By_NookDoubleLeft , 0F, 0F, 0F);

        NookCrossBack_By_NookCrossForward = new ModelRenderer(this, 160, 32).addBox(-8F, -8F, 0F, 16, 16, 0);
        NookCrossBack_By_NookCrossForward.setRotationPoint(0F, 16F, 8F);
        NookCrossBack_By_NookCrossForward.setTextureSize(192, 64);
        NookCrossBack_By_NookCrossForward.mirror = true;
        setRotation(NookCrossBack_By_NookCrossForward, 0F, 0F, 0F);

        NookFull_By_Nothing = new ModelRenderer(this, 160, 48).addBox(-8F, -8F, 0F, 16, 16, 0);
        NookFull_By_Nothing.setRotationPoint(0F, 16F, 8F);
        NookFull_By_Nothing.setTextureSize(192, 64);
        NookFull_By_Nothing.mirror = true;
        setRotation(NookFull_By_Nothing, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(entity, f, f1, f2, f3, f4, f5);

        boolean[][] blocks = new boolean[3][3];

        GL11.glPushMatrix();
        // FRONT SIDE
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                blocks[x][y] = (neighbors[2-x][2-y][1] && !neighbors[2-x][2-y][0]);
            }
        }
        if(!neighbors[1][1][0]) renderWall(blocks, f5);

        // LEFT SIDE
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                blocks[x][y] = (neighbors[1][2-y][2-x] && !neighbors[2][2-y][2-x]);
            }
        }
        GL11.glRotatef(90.0F, 0, 1, 0);
        if(!neighbors[2][1][1]) renderWall(blocks, f5);

        // BACK SIDE
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                blocks[x][y] = (neighbors[x][2-y][1] && !neighbors[x][2-y][2]);
            }
        }
        GL11.glRotatef(90.0F, 0, 1, 0);
        if(!neighbors[1][1][2]) renderWall(blocks, f5);

        // RIGHT SIDE
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                blocks[x][y] = (neighbors[1][2-y][x] && !neighbors[0][2-y][x]);
            }
        }
        GL11.glRotatef(90.0F, 0, 1, 0);
        if(!neighbors[0][1][1]) renderWall(blocks, f5);

        // BOTTOM SIDE
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                blocks[x][y] = (neighbors[2-x][1][y] && !neighbors[2-x][0][y]);
            }
        }
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.0F, -1.0F);
        GL11.glRotatef(90.0F, 1, 0, 0);
        if(!neighbors[1][0][1]) renderWall(blocks, f5);
        GL11.glPopMatrix();

        // TOP SIDE
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                blocks[x][y] = (neighbors[2-x][1][2-y] && !neighbors[2-x][2][2-y]);
            }
        }
        GL11.glTranslatef(0.0F, 1.0F, 1.0F);
        GL11.glRotatef(-90.0F, 1, 0, 0);
        if(!neighbors[1][2][1]) renderWall(blocks, f5);

    }

    private void flip() {
        GL11.glRotatef(180.0F, 0, 1, 0);
    }

    public void renderWall(boolean[][] blocks, float f5) {
        ModelRenderer used = null;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.0F, -0.5F);
        if(blocks[1][0]) { // Top Edge
            if(blocks[1][2]) { // Bottom Edge
                if(blocks[0][1]) { // Left Edge
                    if(blocks[2][1]) { // Right Edge
                        if(blocks[0][0]) { // Top Left
                            if(blocks[2][0]) { // Top Right
                                if(blocks[0][2]) { // Bottom Left
                                    if(blocks[2][2]) { // Bottom Right
                                        used = Middle_By_EdgeRight;
                                    } else { // Missing Bottom Right
                                        used = NookDownRight_By_NookDownLeft;
                                    }
                                } else {
                                    if(blocks[2][2]) { // Missing Bottom Left
                                        used = NookDownRight_By_NookDownLeft; flip();
                                    } else { // Missing Both Bottom Corners
                                        used = NookDoubleRight_By_NookDoubleDown; flip();
                                    }
                                }
                            } else {
                                if(blocks[0][2]) { // BL
                                    if(blocks[2][2]) { // Missing Top Right
                                        used = NookUpRight_By_NookUpLeft;
                                    } else { // Missing Both Right Corners
                                        used = NookDoubleRight_By_NookDoubleDown;
                                    }
                                } else {
                                    if(blocks[2][2]) { // Missing Top Right and Bottom Left
                                        used = NookCrossBack_By_NookCrossForward; flip();
                                    } else { // Missing All Corners but Top Left
                                        used = NookTripleMissingUpLeft_By_NookTripleMissingDownLeft;
                                    }
                                }
                            }
                        } else {
                            if(blocks[2][0]) { // TR
                                if(blocks[0][2]) { // BL
                                    if(blocks[2][2]) { // Missing Top Left
                                        used = NookUpRight_By_NookUpLeft; flip();
                                    } else { // Missing Top Left and Bottom Right
                                        used = NookCrossBack_By_NookCrossForward;
                                    }
                                } else {
                                    if(blocks[2][2]) { // Missing Both Left Corners
                                        used = NookDoubleUp_By_NookDoubleLeft; flip();
                                    } else { // Missing All but Top Right
                                        used = NookTripleMissingUpRight_By_NookTripleMissingDownRight;
                                    }
                                }
                            } else {
                                if(blocks[0][2]) { // BL
                                    if(blocks[2][2]) { // Missing Both Top Corners
                                        used = NookDoubleUp_By_NookDoubleLeft;
                                    } else { // Missing All but Bottom Left
                                        used = NookTripleMissingUpLeft_By_NookTripleMissingDownLeft; flip();
                                    }
                                } else {
                                    if(blocks[2][2]) { // Missing All but Bottom Right
                                        used = NookTripleMissingUpRight_By_NookTripleMissingDownRight; flip();
                                    } else { // Missing ALL Corners
                                        used = NookFull_By_Nothing;
                                    }
                                }
                            }
                        }
                    } else { // Missing RE
                        if(blocks[0][0]) { // Top Left
                            if(blocks[0][2]) { // Bottom Left
                                used = Middle_By_EdgeRight; flip();
                            } else { // Missing Bottom Left
                                used = EdgeDownAndNookLeft_By_EdgeRightAndNookDown; flip();
                            }
                        } else {
                            if(blocks[0][2]) { // Missing Top Left
                                used = EdgeDownAndNookRight_By_EdgeRightAndNookUp; flip();
                            } else { // Missing Both Left Corners
                                used = EdgeDownAndNookDouble_By_EdgeRightAndNookDouble; flip();
                            }
                        }
                    }
                } else { // Missing LE
                    if(blocks[2][1]) { // Right Edge
                        if(blocks[2][0]) { // Top Right
                            if(blocks[2][2]) { // Bottom Right
                                used = TubeVert_By_EdgeLeft; flip();
                            } else { // Missing Bottom Right
                                used = EdgeLeftAndNookDown_By_EdgeUpAndNookLeft;
                            }
                        } else {
                            if(blocks[2][2]) { // Missing Top Right
                                used = EdgeLeftAndNookUp_By_EdgeUpAndNookRight;
                            } else { // Missing Both Right Corners
                                used = EdgeLeftAndNookDouble_By_EdgeUpAndNookDouble;
                            }
                        }
                    } else { // Missing LE and RE
                        used = TubeVert_By_EdgeLeft;
                    }
                }
            } else { // Missing BE
                if(blocks[0][1]) { // Left Edge
                    if(blocks[2][1]) { // Right Edge
                        if(blocks[0][0]) { // Top Left
                            if(blocks[2][0]) { // Top Right
                                used = EdgeDown_By_CornerDownRight;
                            } else { // Missing Top Right
                                used = EdgeDownAndNookRight_By_EdgeRightAndNookUp;
                            }
                        } else {
                            if(blocks[2][0]) { // Missing Top Left
                                used = EdgeDownAndNookLeft_By_EdgeRightAndNookDown;
                            } else { // Missing Both Top Corners
                                used = EdgeDownAndNookDouble_By_EdgeRightAndNookDouble;
                            }
                        }
                    } else { // Missing BE and RE
                        if(blocks[0][0]) { // Top Left
                            used = EdgeDown_By_CornerDownRight; flip();
                        } else {
                            used = TubeCornerDownLeft_By_TubeCornerDownRight; flip();
                        }
                    }
                } else { // Missing BE and LE
                    if(blocks[2][1]) { // Right Edge
                        if(blocks[2][0]) { // Top Right
                            used = TubeEndDown_By_CornerDownLeft; flip();
                        } else {
                            used = TubeCornerDownLeft_By_TubeCornerDownRight;
                        }
                    } else { // Missing BE, LE, and RE
                        used = TubeEndDown_By_CornerDownLeft;
                    }
                }
            }
        } else { // Missing TE
            if(blocks[1][2]) { // Bottom Edge
                if(blocks[0][1]) { // Left Edge
                    if(blocks[2][1]) { // Right Edge
                        if(blocks[0][2]) { // Bottom Left
                            if(blocks[2][2]) { // Bottom Right
                                used = EdgeUp_By_CornerUpRight;
                            } else { // Missing Bottom Right
                                used = EdgeLeftAndNookUp_By_EdgeUpAndNookRight; flip();
                            }
                        } else {
                            if(blocks[2][2]) { // Missing Bottom Left
                                used = EdgeLeftAndNookDown_By_EdgeUpAndNookLeft; flip();
                            } else { // Missing Both Bottom Corners
                                used = EdgeLeftAndNookDouble_By_EdgeUpAndNookDouble; flip();
                            }
                        }
                    } else { // Missing TE and RE
                        if(blocks[0][2]) { // Bottom Left
                            used = EdgeUp_By_CornerUpRight; flip();
                        } else { // Missing Bottom Left
                            used = TubeCornerUpLeft_By_TubeCornerUpRight; flip();
                        }
                    }
                } else { // Missing TE and LE
                    if(blocks[2][1]) { // Right Edge
                        if(blocks[2][2]) { // Bottom Right
                            used = TubeEndUp_By_CornerUpLeft; flip();
                        } else { // Missing Bottom Right
                            used = TubeCornerUpLeft_By_TubeCornerUpRight;
                        }
                    } else { // Missing TE, LE, and RE
                        used = TubeEndUp_By_CornerUpLeft;
                    }
                }
            } else { // Missing TE and BE
                if(blocks[0][1]) { // Left Edge
                    if(blocks[2][1]) { // Horizontal Tube
                        used = TubeHoriz_By_TubeEndRight;
                    } else { // Missing TE, BE, and RE
                        used = TubeHoriz_By_TubeEndRight; flip();
                    }
                } else {
                    if(blocks[2][1]) { // Missing TE, BE and LE
                        used = Single_By_TubeEndLeft; flip();
                    } else { // Missing ALL
                        used = Single_By_TubeEndLeft;
                    }
                }
            }
        }
        GL11.glTranslatef(0.0F, -1.0F, -0.5F);
        if(used != null) used.render(f5);
        GL11.glPopMatrix();
    }

    public void setNeighbors(boolean[][][] in) { this.neighbors = in; }

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
