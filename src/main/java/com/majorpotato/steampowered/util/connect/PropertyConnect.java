package com.majorpotato.steampowered.util.connect;


import net.minecraftforge.common.property.IUnlistedProperty;

/**
 * Utility Unlisted Property for Connection data.
 * Mostly used for client-side rendering.
 * @author MajorPotato
 */
public class PropertyConnect implements IUnlistedProperty<ConnectLogic> {

    protected final String name;

    public PropertyConnect(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid(ConnectLogic value) {
        return true;
    }

    @Override
    public Class<ConnectLogic> getType() {
        return ConnectLogic.class;
    }

    @Override
    public String valueToString(ConnectLogic value) {
        return value.toString();
    }
}
