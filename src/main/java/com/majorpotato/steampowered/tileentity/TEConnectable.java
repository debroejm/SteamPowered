package com.majorpotato.steampowered.tileentity;

import com.majorpotato.steampowered.util.connect.ConnectLogic;
import com.majorpotato.steampowered.util.connect.IConnectable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TEConnectable extends TileEntitySP implements IConnectable {

    protected ConnectLogic connections = null;

    public @Nonnull ConnectLogic getConnectLogic() {
        if(connections == null) updateConnections();
        return connections;
    }

    public boolean isConnected(EnumFacing side) {
        if(connections == null) updateConnections();
        return connections.get(side) != null;
    }
    public @Nullable IConnectable getConnected(EnumFacing side) {
        if(connections == null) updateConnections();
        return connections.get(side);
    }
    public void setConnected(EnumFacing side, @Nullable IConnectable connectable) {
        connections = new ConnectLogic();
        connections.set(side, connectable);
    }

    //@Override public void onLoad() { updateConnections(); }

    public void updateConnections() {
        if(connections == null) connections = new ConnectLogic();
        for(EnumFacing facing : EnumFacing.values()) {

            TileEntity tent = worldObj.getTileEntity(pos.offset(facing));
            if(tent instanceof IConnectable) {
                IConnectable connectable = (IConnectable)tent;
                if(canConnect(facing, connectable) && connectable.canConnect(facing.getOpposite(), this)) {
                    setConnected(facing, connectable);
                    connectable.setConnected(facing.getOpposite(), this);
                    continue;
                }
            }

            // No connection, set it to null
            setConnected(facing, null);
        }
    }
}
