package com.majorpotato.steampowered.handler;


import com.majorpotato.steampowered.network.PacketBuilder;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ConnectionHandler {

    @SubscribeEvent
    public void onEntitySpawn(EntityJoinWorldEvent event) {
        Entity ent = event.entity;
        //if(ent instanceof EntityPlayerMP) PacketBuilder.instance().sendAllAuraDataToPlayer((EntityPlayerMP)ent);
    }
}
