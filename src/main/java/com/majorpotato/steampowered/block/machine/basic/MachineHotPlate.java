package com.majorpotato.steampowered.block.machine.basic;

import com.majorpotato.steampowered.block.machine.BlockMachine;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityHotPlate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class MachineHotPlate extends BlockMachine {

    public MachineHotPlate() {
        super();
        this.setBlockName("machineHotPlate");
        this.setBlockTextureName("machineHotPlate");
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("furnace_top");
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity)
    {
        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(0.0, 0.75, 0.0, 1.0, 1.0, 1.0);
        if (aabb != null && mask.intersectsWith(aabb)) list.add(aabb);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TEntityHotPlate();
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileEntity ent = world.getTileEntity(x, y, z);
        if(ent instanceof TEntityHotPlate)
            return ((TEntityHotPlate)ent).getHeat() * 15 / TEntityHotPlate.MAX_HEAT;
        else return 0;
    }

    @Override
    public boolean isOpaqueCube() { return false; }

    @Override
    public int getRenderType() {
        return 0;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return true;
    }
}
