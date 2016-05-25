package com.majorpotato.steampowered.tileentity.machine.basic;


import com.majorpotato.steampowered.block.machine.basic.MachineFirebox;
import com.majorpotato.steampowered.tileentity.TEntitySP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TEntityHotPlate extends TEntitySP {

    public static final int MAX_HEAT = 2000;


    protected float heat = 0.0f;

    public TEntityHotPlate() { super(); }

    public int getHeat() { return (int)heat; }

    @Override
    public void updateEntity() {
        Block block = worldObj.getBlock(xCoord, yCoord-1, zCoord);
        float incAmnt = 0.0f;
        if(block instanceof BlockLiquid && block.getMaterial() == Material.lava) incAmnt = 0.0055f;
        else if(block instanceof BlockFire) incAmnt = 0.001f;
        else if(block instanceof MachineFirebox) {
            TileEntity tent = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
            if(tent instanceof TEntityFirebox && ((TEntityFirebox)tent).isBurning()) incAmnt = 0.01f;
        } else {
            heat -= 0.05f * 5000.0f;
            if(heat < 0.0f) heat = 0.0f;
        }
        heat += incAmnt * Math.min((MAX_HEAT - heat) / 20.0f, 1.0f) * 5000.0f;
        if(heat > MAX_HEAT) heat = MAX_HEAT;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setFloat("Heat", heat);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        heat = compound.getFloat("Heat");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }

}
