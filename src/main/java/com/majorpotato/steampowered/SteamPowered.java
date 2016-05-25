package com.majorpotato.steampowered;

import com.majorpotato.steampowered.commands.CommandAura;
import com.majorpotato.steampowered.commands.CommandDebug;
import com.majorpotato.steampowered.handler.ConnectionHandler;
import com.majorpotato.steampowered.handler.GuiHandler;
import com.majorpotato.steampowered.client.handler.KeyInputEventHandler;
import com.majorpotato.steampowered.handler.PacketHandler;
import com.majorpotato.steampowered.handler.events.ChunkEventHandler;
import com.majorpotato.steampowered.init.*;
import com.majorpotato.steampowered.proxy.IProxy;
import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.handler.ConfigHandler;
import com.majorpotato.steampowered.util.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;

/*, dependencies = "after:ForgeMultipart"*/

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS, dependencies = "required-after:olympiaAPI;required-before:olympia")
public class SteamPowered
{
    @Mod.Instance(Reference.MOD_ID)
    public static SteamPowered instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());
        MinecraftForge.EVENT_BUS.register(new ConnectionHandler());
        MinecraftForge.EVENT_BUS.register(new ChunkEventHandler.DefaultBus());
        //MinecraftForge.TERRAIN_GEN_BUS.register(new ChunkEventHandler.TerrainBus());

        PacketHandler.init();

        ModItems.init();
        ModBlocks.init();
        ModBlocks.registerTileEntities();
        ModWorld.init();

        proxy.registerKeyBindings();
        proxy.registerRenderThings();

        LogHelper.info("Pre Initialization Complete!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());

        new GuiHandler();

        Recipes.init();

        new ModMultiParts();

        LogHelper.info("Initialization Complete!");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Post Initialization Complete!");

        /*
        for (String oreName : OreDictionary.getOreNames())
        {
            LogHelper.info(oreName);
        }
        */
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandDebug());
        event.registerServerCommand(new CommandAura());
    }
}
