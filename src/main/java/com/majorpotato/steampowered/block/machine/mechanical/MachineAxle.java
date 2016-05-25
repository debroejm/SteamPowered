package com.majorpotato.steampowered.block.machine.mechanical;


import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.tileentity.machine.mechanical.TEntityAxle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineAxle extends MachineMechanical {
    public MachineAxle()
    {
        super();
        this.setBlockName("machineAxle");
        this.setBlockTextureName("noTex");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TEntityAxle();
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack itemStack)
    {
        int rotation = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        int pitch = MathHelper.floor_double((double) ((entityliving.rotationPitch * 4F) / 360F) + 0.5D) & 3;
        int facing = 0;
        if(rotation == 0) facing = 2;
        if(rotation == 1) facing = 5;
        if(rotation == 2) facing = 3;
        if(rotation == 3) facing = 4;
        if(pitch == 1) facing = 1;
        if(pitch == 3) facing = 0;
        TileEntity ent = world.getTileEntity(i, j, k);
        if(ent instanceof TEntityAxle) {
            ((TEntityAxle)ent).rotateBlock(Direction.fromID(facing));
            ((TEntityAxle)ent).sendUpdateToClient();
        }

    }
}
