package com.majorpotato.steampowered.util.steam;

import com.majorpotato.steampowered.util.Direction;

public interface ISteamUser {

    public abstract SteamLogic getSteamLogic();
    public abstract boolean canSideConnect(Direction side);

}
