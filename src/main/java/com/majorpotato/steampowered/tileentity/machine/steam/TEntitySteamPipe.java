package com.majorpotato.steampowered.tileentity.machine.steam;


import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.TieredMaterial;
import com.majorpotato.steampowered.util.machine.MachineType;

public class TEntitySteamPipe extends TEntitySteam {

    public TEntitySteamPipe(TieredMaterial material)
    {
        super();
        machineType = MachineType.TRANSPORT;
        this.material = material;
    }

    @Override
    public boolean canSideConnect(Direction side) { return true; }

}
