package com.majorpotato.steampowered.util.artifice;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

import java.util.HashMap;

public class AuraNodeNet {

    private static HashMap<Integer, AuraNodeNet> worldMap = new HashMap<Integer, AuraNodeNet>();

    public static AuraNodeNet getNetForDimension(int dimID) {
        if(worldMap.containsKey(dimID)) return worldMap.get(dimID);
        else {
            AuraNodeNet newNet = new AuraNodeNet(DimensionManager.getWorld(dimID));
            worldMap.put(dimID, newNet);
            return newNet;
        }
    }

    /*
    private static HashMap<Integer, AuraNodeNet> worldNetMap = new HashMap<Integer, AuraNodeNet>();

    public static void addNode(AuraNode node, int worldID) {
        if(worldNetMap.containsKey(worldID)) {
            worldNetMap.get(worldID).addNode(node);
        } else {
            AuraNodeNet newNet = new AuraNodeNet();
            newNet.addNode(node);
            worldNetMap.put(worldID, newNet);
        }
    }
    public static AuraNodeNet getWorldNet(int worldID) {
        if(worldNetMap.containsKey(worldID)) return worldNetMap.get(worldID);
        else {
            worldNetMap.put(worldID, new AuraNodeNet());
            return worldNetMap.get(worldID);
        }
    }

    */

    private HashMap<Integer, HashMap<Integer, AuraNode>> nodeMap = new HashMap<Integer, HashMap<Integer, AuraNode>>();
    private World worldObj;

    public AuraNodeNet(World world) {
        worldObj = world;
    }

    public void loadNode(AuraNode node, int chunkX, int chunkZ) {

        HashMap<Integer, AuraNode> zMap = null;
        if(!nodeMap.containsKey(chunkX)) {
            zMap = new HashMap<Integer, AuraNode>();
            nodeMap.put(chunkX, zMap);
        } else zMap = nodeMap.get(chunkX);

        if(!zMap.containsKey(chunkZ)) {
            zMap.put(chunkZ, node);
        }
    }

    public void unloadNode(int chunkX, int chunkZ) {
        HashMap<Integer, AuraNode> zMap = null;
        if(!nodeMap.containsKey(chunkX)) {
            zMap = new HashMap<Integer, AuraNode>();
            nodeMap.put(chunkX, zMap);
        } else zMap = nodeMap.get(chunkX);

        if(zMap.containsKey(chunkZ)) {
            zMap.remove(chunkZ);
            if(zMap.isEmpty()) {
                nodeMap.remove(chunkX);
            }
        }
    }

    /*
    public void addNode(AuraNode node) {
        int leastX = (int)(node.getXCoord()-node.getSize()) >> 4;
        int leastY = (int)(node.getYCoord()-node.getSize()) >> 4;
        int leastZ = (int)(node.getZCoord()-node.getSize()) >> 4;
        int greatestX = (int)(node.getXCoord()+node.getSize()) >> 4;
        int greatestY = (int)(node.getYCoord()+node.getSize()) >> 4;
        int greatestZ = (int)(node.getZCoord()+node.getSize()) >> 4;

        nodeList.add(node);
        if(node.getID() == null) node.setID("node"+(nodeList.size()-1));

        for(int x = leastX; x <= greatestX; x++) {
            HashMap<Integer, HashMap<Integer, List<AuraNode>>> yMap = null;
            if(nodeMap.containsKey(x)) yMap = nodeMap.get(x);
            else {
                yMap = new HashMap<Integer, HashMap<Integer, List<AuraNode>>>();
                nodeMap.put(x, yMap);
            }
            for(int y = leastY; y <= greatestY; y++) {
                HashMap<Integer, List<AuraNode>> zMap = null;
                if(yMap.containsKey(y)) zMap = yMap.get(y);
                else {
                    zMap = new HashMap<Integer, List<AuraNode>>();
                    yMap.put(y, zMap);
                }
                for(int z = leastZ; z <= greatestZ; z++) {
                    List<AuraNode> nodeList = null;
                    if (zMap.containsKey(z)) nodeList = zMap.get(z);
                    else {
                        nodeList = new ArrayList<AuraNode>();
                        zMap.put(z, nodeList);
                    }
                    nodeList.add(node);
                }
            }
        }
    }
    */

    public float[] getAuraAt(int chunkX, int chunkZ) {
        return getNodeAt(chunkX, chunkZ).getColor();
    }

    public AuraNode getNodeAt(int chunkX, int chunkZ) {
        HashMap<Integer, AuraNode> zMap = null;
        if(!nodeMap.containsKey(chunkX)) {
            zMap = new HashMap<Integer, AuraNode>();
            nodeMap.put(chunkX, zMap);
        } else zMap = nodeMap.get(chunkX);

        if(zMap.containsKey(chunkZ)) {
            return zMap.get(chunkZ);
        } else {
            Chunk chunk = worldObj.getChunkFromChunkCoords(chunkX, chunkZ);
            AuraNode node = new AuraNode(chunk);
            this.loadNode(node, chunk.xPosition, chunk.zPosition);
            return node;
        }
    }
}
