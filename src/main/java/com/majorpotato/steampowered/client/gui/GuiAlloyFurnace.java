package com.majorpotato.steampowered.client.gui;


import com.majorpotato.steampowered.client.container.ContainerAlloyFurnace;
import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.multiblock.MBAlloyFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiAlloyFurnace extends GuiContainer {
    public static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/alloyFurnace.png");

    private MBAlloyFurnace furnace;

    public GuiAlloyFurnace(InventoryPlayer invPlayer, MBAlloyFurnace furnace)
    {
        super(new ContainerAlloyFurnace(invPlayer, furnace));

        xSize = 176;
        ySize = 166;

        this.furnace = furnace;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int j, int i)
    {
        GL11.glColor4f(1F, 1F, 1F, 1F);

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        // Heat Max Bar
        int scale1 = furnace.getHeatMaxScaled(62);
        this.drawTexturedModalRect(x+12, y+12, 184, 31, 8, scale1);

        // Progress Arrow
        int scale2 = furnace.getProgressScaled(25);
        this.drawTexturedModalRect(x+27, y+65-scale2, 192, 56-scale2, scale2, 29);

        // Heat Level
        int scale3 = furnace.getHeatScaled(62);
        this.drawTexturedModalRect(x+12, y+74-scale3, 176, 93-scale3, 8, scale3);

        // Heat Max Indicator
        if( scale1 > 0 )
            this.drawTexturedModalRect(x+11, y+10+scale1, 180, 93, 12, 4);

        // Heat Needed Indicator
        int scale5[] = furnace.getHeatNeededScaled(62);
        for(int s = 0; s < scale5.length; s++) {
            //System.out.println(scale5[s]);
            if (scale5[s] > -1)
                this.drawTexturedModalRect(x + 11, y + 73 - scale5[s], 176, 93, 4, 4);
        }



        int inputs = furnace.getInputCount();
        switch(inputs) {
            default:
                this.drawTexturedModalRect(x + 61, y + 16, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 79, y + 16, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 61, y + 34, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 79, y + 34, 176, 113, 18, 18);
            case 4:
                this.drawTexturedModalRect(x + 97, y + 16, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 115, y + 16, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 97, y + 34, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 115, y + 34, 176, 113, 18, 18);
            case 8:
                this.drawTexturedModalRect(x + 61, y + 52, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 79, y + 52, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 97, y + 52, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 115, y + 52, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 133, y + 16, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 133, y + 34, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 133, y + 52, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 151, y + 16, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 151, y + 34, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 151, y + 52, 176, 113, 18, 18);
                break;
            case 9:
                this.drawTexturedModalRect(x + 115, y + 16, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 115, y + 34, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 115, y + 52, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 133, y + 16, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 133, y + 34, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 133, y + 52, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 151, y + 16, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 151, y + 34, 176, 113, 18, 18);
                this.drawTexturedModalRect(x + 151, y + 52, 176, 113, 18, 18);
                break;
            case 18:
                break;
        }

        this.fontRendererObj.drawStringWithShadow(furnace.getHeat()+"C", x+26, y+21, -1);
    }
}
