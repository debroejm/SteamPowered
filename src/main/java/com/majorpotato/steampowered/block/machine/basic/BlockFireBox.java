package com.majorpotato.steampowered.block.machine.basic;

import com.majorpotato.steampowered.block.BlockConnectable;
import com.majorpotato.steampowered.block.BlockTESP;
import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.tileentity.machine.basic.TEFireBox;
import com.majorpotato.steampowered.tileentity.machine.basic.TEHotPlate;
import com.majorpotato.steampowered.util.IDebuggable;
import com.majorpotato.steampowered.util.connect.ConnectLogic;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockFireBox extends BlockConnectable implements IDebuggable {

    protected static final AxisAlignedBB AABB_BASE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public BlockFireBox() {
        super(Material.ROCK);
        setUnlocalizedName("machineFireBox");
        setHarvestLevel("pickaxe",1);
        setHardness(3.5F);
        setResistance(17.0F);
    }

    @Override public boolean canConnectHorizontal() { return true; }
    @Override public boolean canConnectCorners() { return true; }
    @Override public boolean canConnectVertical() { return false; }

    @Override
    public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BASE);
        if(state.getValue(CONNECT_NORTH)) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        if(state.getValue(CONNECT_SOUTH)) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
        if(state.getValue(CONNECT_EAST )) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST );
        if(state.getValue(CONNECT_WEST )) addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST );
    }

    // TODO: Generate Light When Burning
    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return super.getLightValue(state, world, pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tent = worldIn.getTileEntity(pos);
        if(heldItem != null && heldItem.getItem() instanceof ItemFlintAndSteel && tent instanceof TEFireBox) {
            ((TEFireBox)tent).ignite();
            return true;
        } else return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        super.onEntityCollidedWithBlock(world, pos, state, entity);
        if(world.isRemote) return;
        TileEntity tent = world.getTileEntity(pos);
        if(entity instanceof EntityItem && tent instanceof TEFireBox) {
            EntityItem item = (EntityItem)entity;
            ItemStack stack = item.getEntityItem();
            stack = ((TEFireBox)tent).addFuel(stack);
            if(stack == null) item.setDead();
            else item.setEntityItemStack(stack);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TEFireBox();
    }

    @Override
    public String[] getDebugInformation(World world, BlockPos pos) {
        TileEntity tent = world.getTileEntity(pos);
        if(tent instanceof TEFireBox) {
            TEFireBox firebox = (TEFireBox)tent;
            return new String[] {
                    "Fire Box Debug:",
                    "  Burning: " + firebox.isBurning(),
                    "  Fuel: " + firebox.getFuelAmount(),
                    "  Connect-North: " + firebox.isConnected(EnumFacing.NORTH),
                    "  Connect-South: " + firebox.isConnected(EnumFacing.SOUTH),
                    "  Connect-East : " + firebox.isConnected(EnumFacing.EAST),
                    "  Connect-West : " + firebox.isConnected(EnumFacing.WEST)
            };
        } else return null;
    }
}
