package com.majorpotato.steampowered.util.artifice;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class AuraNode /*implements PacketUser*/ {

    private float[] color;

    public AuraNode(float r, float g, float b) { this(new float[]{r, g, b} ); }
    public AuraNode(float[] color) {
        this.color = color;
    }

    public AuraNode(NBTTagCompound compound, Chunk chunk) {
        if(!readFromNBT(compound)) {
            createFromChunk(chunk);
        }
    }

    public AuraNode(Chunk chunk) {
        createFromChunk(chunk);
    }

    private void createFromChunk(Chunk chunk) {
        BiomeGenBase biome = chunk.worldObj.getBiomeGenForCoords(chunk.xPosition*16+8,chunk.zPosition*16+8);

        float temp = Math.min(biome.temperature,1.0F);
        float rain = Math.min(biome.rainfall,1.0F);
        //float tempRainDiff = Math.abs(temp - rain);

        color = new float[3];

        // Red
        color[0] = Math.min(Math.max(biome.temperature, 0.3F), 1.0F);

        // Green
        color[1] = Math.min(Math.max((biome.temperature+biome.rainfall)/2.0F, 0.3F), 1.0F);
        //color[1] = Math.max(rain, 0.3F);

        // Blue
        color[2] = Math.min(Math.max(1.0F-biome.temperature, 0.3F), 1.0F);

        if(Math.abs(biome.temperature-biome.rainfall) < 0.2F) color[2] = Math.min(color[2]*1.8F,1.0F);
        if(biome.temperature > 1.1F) color[1] = 0.3F;
    }

    //public AuraNode(DataInputStream data) throws IOException { this.readData(data); }

    public float[] getColor() { return color; }
    public void setColor(float r, float g, float b) {
        color = new float[3];
        color[0] = r;
        color[1] = g;
        color[2] = b;
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setFloat("auraRed",color[0]);
        compound.setFloat("auraGreen",color[1]);
        compound.setFloat("auraBlue",color[2]);
    }

    public boolean readFromNBT(NBTTagCompound compound) {
        if(compound == null) return false;
        float[] color = new float[3];
        if(compound.hasKey("auraRed")) color[0] = compound.getFloat("auraRed");
        else return false;
        if(compound.hasKey("auraGreen")) color[1] = compound.getFloat("auraGreen");
        else return false;
        if(compound.hasKey("auraBlue")) color[2] = compound.getFloat("auraBlue");
        else return false;
        this.color = color;
        return true;
    }

    /*
    @Override
    public void writeData(DataOutputStream data) throws IOException {
        data.writeInt(xCoord);
        data.writeInt(yCoord);
        data.writeInt(zCoord);
        data.writeFloat(radius);
        data.writeFloat(color[0]);
        data.writeFloat(color[1]);
        data.writeFloat(color[2]);
        data.writeUTF(nodeID);
    }

    @Override
    public void readData(DataInputStream data) throws IOException  {
        xCoord = data.readInt();
        yCoord = data.readInt();
        zCoord = data.readInt();
        radius = data.readFloat();
        color = new float[3];
        color[0] = data.readFloat();
        color[1] = data.readFloat();
        color[2] = data.readFloat();
        nodeID = data.readUTF();
    }
    */
}
