package com.majorpotato.steampowered;

import com.majorpotato.steampowered.handler.ConfigHandler;
import com.majorpotato.steampowered.handler.OverlayHandler;
import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.init.ModItems;
import com.majorpotato.steampowered.init.ModRecipes;
import com.majorpotato.steampowered.init.ModWorld;
import com.majorpotato.steampowered.proxy.ProxyCommon;
import com.majorpotato.steampowered.util.OreMaterial;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = SteamPowered.MODID, version = SteamPowered.VERSION)
public class SteamPowered
{
    public static final String MODID = "steampowered";
    public static final String NAME = "Steam Powered";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static SteamPowered instance;

    @SidedProxy(modId=MODID, clientSide="com.majorpotato.steampowered.proxy.ProxyClient", serverSide="com.majorpotato.steampowered.proxy.ProxyServer")
    public static ProxyCommon proxy;

    public static CreativeTabs modTab = new CreativeTabs(SteamPowered.NAME) {
        @Override
        @SideOnly(Side.CLIENT)
        @MethodsReturnNonnullByDefault
        public Item getTabIconItem() { return ModItems.getIngot(OreMaterial.BRASS); }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        OreMaterial.registerMaterials();
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        ModBlocks.registerTileEntities();
        ModWorld.initWorldGen();
        proxy.registerRenderThings();

    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {

        FMLCommonHandler.instance().bus().register(new ConfigHandler());
        MinecraftForge.EVENT_BUS.register(new OverlayHandler());

        ModItems.registerOreDictionaryEntries();
        ModBlocks.registerOreDictionaryEntries();
        ModItems.registerColorHandlers();
        ModBlocks.registerColorHandlers();
        ModRecipes.initRecipes();
        proxy.registerTileEntityRenderers();

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ModRecipes.postInitRecipes();
    }
}
