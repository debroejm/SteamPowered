package com.majorpotato.steampowered.handler.events;


import com.majorpotato.steampowered.util.artifice.AuraNode;
import com.majorpotato.steampowered.util.artifice.AuraNodeNet;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;

public class ChunkEventHandler {

    public static class DefaultBus {
        @SubscribeEvent
        public void chunkLoading(ChunkDataEvent.Load event) {
            Chunk chunk = event.getChunk();
            AuraNodeNet worldNet = AuraNodeNet.getNetForDimension(chunk.worldObj.provider.dimensionId);
            AuraNode node = new AuraNode(event.getData(), chunk);
            worldNet.loadNode(node, chunk.xPosition, chunk.zPosition);

            //System.out.println("Chunk Loaded At:" + chunk.xPosition + "," + chunk.zPosition);
        }

        @SubscribeEvent
        public void chunkSaving(ChunkDataEvent.Save event) {
            Chunk chunk = event.getChunk();
            AuraNodeNet worldNet = AuraNodeNet.getNetForDimension(chunk.worldObj.provider.dimensionId);
            AuraNode node = worldNet.getNodeAt(chunk.xPosition, chunk.zPosition);
            if (node != null) node.writeToNBT(event.getData());

            //System.out.println("Chunk Saved At:"+chunk.xPosition+","+chunk.zPosition);
        }

        @SubscribeEvent
        public void chunkUnloading(ChunkEvent.Unload event) {
            Chunk chunk = event.getChunk();
            AuraNodeNet worldNet = AuraNodeNet.getNetForDimension(chunk.worldObj.provider.dimensionId);
            worldNet.unloadNode(chunk.xPosition, chunk.zPosition);

            //System.out.println("Chunk Unloaded At:"+chunk.xPosition+","+chunk.zPosition);
        }
    }

    public static class TerrainBus {
        @SubscribeEvent
        public void chunkPrePopulation(PopulateChunkEvent.Post event) {
            Chunk chunk = event.world.getChunkFromChunkCoords(event.chunkX, event.chunkZ);
            AuraNodeNet worldNet = AuraNodeNet.getNetForDimension(chunk.worldObj.provider.dimensionId);
            AuraNode node = new AuraNode(chunk);
            worldNet.loadNode(node, chunk.xPosition, chunk.zPosition);
        }
    }
}
