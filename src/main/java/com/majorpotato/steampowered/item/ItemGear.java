package com.majorpotato.steampowered.item;


import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.util.Axis;
import com.majorpotato.steampowered.tileentity.machine.mechanical.TEntityAxle;
import com.majorpotato.steampowered.tileentity.machine.mechanical.TEntityGear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemGear extends ItemSP {
    public ItemGear() {
        super();
        this.maxStackSize = 64;
        this.setUnlocalizedName("itemGear");
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float px, float py, float pz)
    {
        TileEntity ent = world.getTileEntity(x, y, z);
        if(ent instanceof TEntityAxle)
        {
            if(placeGear(world, x, y, z, (TEntityAxle)ent)) return true;
        }
        return false;
    }
    
    private boolean placeGear(World world, int x, int y, int z, TEntityAxle axle) {
        Axis axis = axle.getAxis();
        switch(axis) {
            case X:
                if( checkAvailability(world, x, y+1, z-1) &&
                        checkAvailability(world, x, y+1, z) &&
                        checkAvailability(world, x, y+1, z+1) &&
                        checkAvailability(world, x, y, z-1) &&
                        checkAvailability(world, x, y, z+1) &&
                        checkAvailability(world, x, y-1, z-1) &&
                        checkAvailability(world, x, y-1, z) &&
                        checkAvailability(world, x, y-1, z+1) ) {
                    placeGearPart(world, x, y + 1, z - 1, axle);
                    placeGearPart(world, x, y + 1, z, axle);
                    placeGearPart(world, x, y + 1, z + 1, axle);
                    placeGearPart(world, x, y, z - 1, axle);
                    placeGearPart(world, x, y, z + 1, axle);
                    placeGearPart(world, x, y - 1, z - 1, axle);
                    placeGearPart(world, x, y - 1, z, axle);
                    placeGearPart(world, x, y - 1, z + 1, axle);
                    return true;
                } else return false;
            case Y:
                if( checkAvailability(world, x+1, y, z-1) &&
                        checkAvailability(world, x+1, y, z) &&
                        checkAvailability(world, x+1, y, z+1) &&
                        checkAvailability(world, x, y, z-1) &&
                        checkAvailability(world, x, y, z+1) &&
                        checkAvailability(world, x-1, y, z-1) &&
                        checkAvailability(world, x-1, y, z) &&
                        checkAvailability(world, x-1, y, z+1) ) {
                    placeGearPart(world, x+1, y, z - 1, axle);
                    placeGearPart(world, x+1, y, z, axle);
                    placeGearPart(world, x+1, y, z + 1, axle);
                    placeGearPart(world, x, y, z - 1, axle);
                    placeGearPart(world, x, y, z + 1, axle);
                    placeGearPart(world, x-1, y, z - 1, axle);
                    placeGearPart(world, x-1, y, z, axle);
                    placeGearPart(world, x-1, y, z + 1, axle);
                    return true;
                } else return false;
            case Z:
                if( checkAvailability(world, x-1, y+1, z) &&
                        checkAvailability(world, x, y+1, z) &&
                        checkAvailability(world, x+1, y+1, z) &&
                        checkAvailability(world, x-1, y, z) &&
                        checkAvailability(world, x+1, y, z) &&
                        checkAvailability(world, x-1, y-1, z) &&
                        checkAvailability(world, x, y-1, z) &&
                        checkAvailability(world, x+1, y-1, z) ) {
                    placeGearPart(world, x-1, y + 1, z, axle);
                    placeGearPart(world, x, y + 1, z, axle);
                    placeGearPart(world, x+1, y + 1, z, axle);
                    placeGearPart(world, x-1, y, z, axle);
                    placeGearPart(world, x+1, y, z, axle);
                    placeGearPart(world, x-1, y - 1, z, axle);
                    placeGearPart(world, x, y - 1, z, axle);
                    placeGearPart(world, x+1, y - 1, z, axle);
                    return true;
                } else return false;
            default:
                return false;
        }
    }

    private boolean checkAvailability(World world, int x, int y, int z) {
        if (world.getTileEntity(x, y, z) instanceof TEntityGear || world.isAirBlock(x, y, z))
            return true;
        else return false;
    }

    private void placeGearPart(World world, int x, int y, int z, TEntityAxle axle) {
        TileEntity ent = world.getTileEntity(x, y, z);
        if(ent instanceof TEntityGear) ((TEntityGear)ent).addMaster(axle);
        else {
            world.setBlock(x, y, z, ModBlocks.machineGear);
            TileEntity tent = world.getTileEntity(x, y, z);
            if (tent instanceof TEntityGear) ((TEntityGear)tent).addMaster(axle);
        }
    }

    /*
    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    */
}
