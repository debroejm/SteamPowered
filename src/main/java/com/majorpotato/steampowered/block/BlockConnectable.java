package com.majorpotato.steampowered.block;


import com.majorpotato.steampowered.tileentity.TEConnectable;
import com.majorpotato.steampowered.util.connect.IConnectable;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

public abstract class BlockConnectable extends BlockTESP {

    public static final PropertyBool CONNECT_NORTH = PropertyBool.create("connect_north");
    public static final PropertyBool CONNECT_SOUTH = PropertyBool.create("connect_south");
    public static final PropertyBool CONNECT_EAST  = PropertyBool.create("connect_east");
    public static final PropertyBool CONNECT_WEST  = PropertyBool.create("connect_west");

    public static final PropertyBool CONNECT_NORTH_EAST = PropertyBool.create("connect_north_east");
    public static final PropertyBool CONNECT_NORTH_WEST = PropertyBool.create("connect_north_west");
    public static final PropertyBool CONNECT_SOUTH_EAST = PropertyBool.create("connect_south_east");
    public static final PropertyBool CONNECT_SOUTH_WEST = PropertyBool.create("connect_south_west");

    public static final PropertyBool CONNECT_UP   = PropertyBool.create("connect_up");
    public static final PropertyBool CONNECT_DOWN = PropertyBool.create("connect_down");

    public abstract boolean canConnectHorizontal();
    public abstract boolean canConnectCorners();
    public abstract boolean canConnectVertical();

    public BlockConnectable(Material material) {
        super(material);
        setDefaultState(getDefaultConnectedState());
    }

    protected final @Nonnull IBlockState getDefaultConnectedState() {
        IBlockState state = blockState.getBaseState();
        if(canConnectHorizontal()) {
            state = state
                    .withProperty(CONNECT_NORTH, false)
                    .withProperty(CONNECT_SOUTH, false)
                    .withProperty(CONNECT_EAST, false)
                    .withProperty(CONNECT_WEST, false);
        }
        if(canConnectCorners()) {
            state = state
                    .withProperty(CONNECT_NORTH_EAST, false)
                    .withProperty(CONNECT_NORTH_WEST, false)
                    .withProperty(CONNECT_SOUTH_EAST, false)
                    .withProperty(CONNECT_SOUTH_WEST, false);
        }
        if(canConnectVertical()) {
            state = state
                    .withProperty(CONNECT_UP, false)
                    .withProperty(CONNECT_DOWN, false);
        }
        return state;
    }


    @Override
    @MethodsReturnNonnullByDefault
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, getConnectedProperties());
    }

    protected final @Nonnull IProperty[] getConnectedProperties() {
        int count = (canConnectHorizontal() ? 4 : 0) + (canConnectCorners() ? 4 : 0) + (canConnectVertical() ? 2 : 0);
        IProperty[] properties = new IProperty[count];
        int i = 0;
        if(canConnectHorizontal()) {
            properties[i] = CONNECT_NORTH; i++;
            properties[i] = CONNECT_SOUTH; i++;
            properties[i] = CONNECT_EAST; i++;
            properties[i] = CONNECT_WEST; i++;
        }
        if(canConnectCorners()) {
            properties[i] = CONNECT_NORTH_EAST; i++;
            properties[i] = CONNECT_NORTH_WEST; i++;
            properties[i] = CONNECT_SOUTH_EAST; i++;
            properties[i] = CONNECT_SOUTH_WEST; i++;
        }
        if(canConnectVertical()) {
            properties[i] = CONNECT_UP; i++;
            properties[i] = CONNECT_DOWN; i++;
        }
        return properties;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tent = worldIn.getTileEntity(pos);
        if(tent instanceof IConnectable) {
            IConnectable connectable = (IConnectable)tent;
            if(canConnectHorizontal()) {
                state = state
                        .withProperty(CONNECT_NORTH, connectable.isConnected(EnumFacing.NORTH))
                        .withProperty(CONNECT_SOUTH, connectable.isConnected(EnumFacing.SOUTH))
                        .withProperty(CONNECT_EAST,  connectable.isConnected(EnumFacing.EAST))
                        .withProperty(CONNECT_WEST,  connectable.isConnected(EnumFacing.WEST));
            }
            if(canConnectVertical()) {
                state = state
                        .withProperty(CONNECT_UP, connectable.isConnected(EnumFacing.UP))
                        .withProperty(CONNECT_DOWN, connectable.isConnected(EnumFacing.DOWN));
            }
            if(canConnectCorners()) {
                TileEntity ne = worldIn.getTileEntity(pos.offset(EnumFacing.NORTH).offset(EnumFacing.EAST));
                TileEntity nw = worldIn.getTileEntity(pos.offset(EnumFacing.NORTH).offset(EnumFacing.WEST));
                TileEntity se = worldIn.getTileEntity(pos.offset(EnumFacing.SOUTH).offset(EnumFacing.EAST));
                TileEntity sw = worldIn.getTileEntity(pos.offset(EnumFacing.SOUTH).offset(EnumFacing.WEST));
                state = state
                        .withProperty(CONNECT_NORTH_EAST, ne instanceof IConnectable && connectable.canConnect(EnumFacing.NORTH, (IConnectable)ne))
                        .withProperty(CONNECT_NORTH_WEST, nw instanceof IConnectable && connectable.canConnect(EnumFacing.NORTH, (IConnectable)nw))
                        .withProperty(CONNECT_SOUTH_EAST, se instanceof IConnectable && connectable.canConnect(EnumFacing.SOUTH, (IConnectable)se))
                        .withProperty(CONNECT_SOUTH_WEST, sw instanceof IConnectable && connectable.canConnect(EnumFacing.SOUTH, (IConnectable)sw));
            }
        }
        return state;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(world, pos, neighbor);
        TileEntity tent = world.getTileEntity(pos);
        if(tent instanceof TEConnectable) ((TEConnectable)tent).updateConnections();
    }
}
