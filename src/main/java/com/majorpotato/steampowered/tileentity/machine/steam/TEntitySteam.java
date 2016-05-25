package com.majorpotato.steampowered.tileentity.machine.steam;

import com.majorpotato.steampowered.tileentity.TEntitySP;
import com.majorpotato.steampowered.util.TieredMaterial;
import com.majorpotato.steampowered.util.machine.IDataCollectorUser;
import com.majorpotato.steampowered.util.machine.MachineType;
import com.majorpotato.steampowered.util.steam.ISteamUser;
import com.majorpotato.steampowered.util.steam.SteamLogic;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TEntitySteam extends TEntitySP implements ISteamUser, IDataCollectorUser {

    protected SteamLogic steam;
    protected MachineType machineType = MachineType.TRANSPORT;
    protected TieredMaterial material = TieredMaterial.Iron;

    @Override
    public SteamLogic getSteamLogic() { return steam; }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(steam == null) steam = new SteamLogic(machineType, material, this);
        steam.update();
    }

    @Override
    protected void clock10Tick() {
        super.clock10Tick();
        if(steam.getSteamPressure() > 0) steam.setSteamPressure(steam.getSteamPressure() - 1);
    }

    @Override
    public String getCustomInformationMessage() { return steam.getSteamPressure() + " pU In"; }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        steam.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        steam.readFromNBT(compound);
    }
}
