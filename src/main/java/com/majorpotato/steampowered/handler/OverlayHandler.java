package com.majorpotato.steampowered.handler;


import com.majorpotato.steampowered.util.IDebuggable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OverlayHandler extends Gui {

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Text event) {

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        RayTraceResult ray = player.rayTrace(100.0, event.getPartialTicks());
        if(ray == null) return;
        String[] debugText = null;
        switch(ray.typeOfHit) {
            case BLOCK:
                Block block = player.worldObj.getBlockState(ray.getBlockPos()).getBlock();
                if(block instanceof IDebuggable) debugText = ((IDebuggable)block).getDebugInformation(player.worldObj, ray.getBlockPos());
                break;
            case ENTITY:
                Entity entity = ray.entityHit;
                if(entity instanceof IDebuggable) debugText = ((IDebuggable)entity).getDebugInformation(entity.worldObj, entity.getPosition());
                break;
            default:
                break;
        }
        if(debugText == null) return;
        ScaledResolution res = event.getResolution();
        FontRenderer FR = Minecraft.getMinecraft().fontRendererObj;
        int yPos = res.getScaledHeight() / 8;
        for(String line : debugText) {
            FR.drawStringWithShadow(line, 5, yPos, -1);
            yPos += 2 + FR.FONT_HEIGHT;
        }
    }
}
