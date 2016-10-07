package com.majorpotato.steampowered.init;

import com.majorpotato.steampowered.world.WorldGenSP;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModWorld {

    public static void initWorldGen() {
        GameRegistry.registerWorldGenerator(new WorldGenSP(), 1);
    }
}
