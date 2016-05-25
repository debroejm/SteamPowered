package com.majorpotato.steampowered.block.machine.basic;


import com.majorpotato.steampowered.SteamPowered;
import com.majorpotato.steampowered.block.machine.BlockMachine;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityAlloyFurnace;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityFirebox;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MachineFirebox extends BlockMachine {

    public MachineFirebox() {
        super();
        this.setBlockName("machineFirebox");
        this.setBlockTextureName("noTex");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TEntityFirebox();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {

            TileEntity ent = world.getTileEntity(x, y, z);
            if(ent instanceof TEntityFirebox && player.getHeldItem() != null) {
                if(!((TEntityFirebox)ent).isBurning() && player.getHeldItem().isItemEqual(new ItemStack(Items.flint_and_steel))) {
                    if(!world.isRemote) ((TEntityFirebox) ent).startBurn();
                    return true;
                }
            }
        return false;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntity entity = world.getTileEntity(x, y, z);
        if(entity instanceof TEntityFirebox) {
            if(((TEntityFirebox)entity).isBurning())
                return 14;
        }
        return 0;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileEntity ent = world.getTileEntity(x, y, z);
        if(ent instanceof TEntityFirebox) {
            TEntityFirebox tFB = (TEntityFirebox)ent;
            return tFB.getComparatorOutput();
        }
        return 0;
    }
}
