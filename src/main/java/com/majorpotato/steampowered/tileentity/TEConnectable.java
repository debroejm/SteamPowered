package com.majorpotato.steampowered.tileentity;

import com.majorpotato.steampowered.util.connect.ConnectLogic;
import com.majorpotato.steampowered.util.connect.IConnectable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TEConnectable extends TileEntitySP implements IConnectable {

    protected ConnectLogic connections = new ConnectLogic();

    public @Nonnull ConnectLogic getConnectLogic() { return connections; }

    public boolean isConnected(EnumFacing side) { return connections.get(side) != null; }
    public @Nullable IConnectable getConnected(EnumFacing side) { return connections.get(side); }
    public void setConnected(EnumFacing side, @Nullable IConnectable connectable) { connections.set(side, connectable); }

    public void updateConnections() {
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
