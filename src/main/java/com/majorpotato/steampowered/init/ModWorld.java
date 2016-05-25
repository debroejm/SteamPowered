package com.majorpotato.steampowered.init;

import com.majorpotato.steampowered.world.WorldGeneratorSP;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModWorld {

    public static void init()
    {
        GameRegistry.registerWorldGenerator(new WorldGeneratorSP(), 1);
    }
}
