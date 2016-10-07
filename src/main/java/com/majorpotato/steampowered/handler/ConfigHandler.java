package com.majorpotato.steampowered.handler;

import com.majorpotato.steampowered.SteamPowered;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigHandler
{
    public static Configuration config;

    public static boolean DEBUG_MODE = false;

    public static void init(File configFile)
    {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }
    private static void loadConfiguration()
    {
        DEBUG_MODE = config.getBoolean("debugMode", Configuration.CATEGORY_GENERAL, false, "Display extra debug information and other useful tools in-game.");

        if (config.hasChanged())
        {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if(event.getModID().equalsIgnoreCase(SteamPowered.MODID))
        {
            loadConfiguration();
        }
    }
}
