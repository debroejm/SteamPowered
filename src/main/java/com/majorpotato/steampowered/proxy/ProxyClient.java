package com.majorpotato.steampowered.proxy;

import com.majorpotato.steampowered.SteamPowered;
import com.majorpotato.steampowered.block.BlockSP;
import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.init.ModItems;
import com.majorpotato.steampowered.item.ItemSP;
import com.majorpotato.steampowered.render.block.RenderMachineBasic;
import com.majorpotato.steampowered.tileentity.machine.basic.TEHotPlate;
import com.majorpotato.steampowered.util.OreMaterial;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ProxyClient extends ProxyCommon {

    @Override
    public void registerRenderThings() {

        OBJLoader.INSTANCE.addDomain(SteamPowered.MODID.toLowerCase());

        // Items
        for(OreMaterial material : OreMaterial.INGOTS) {
            if (material.doAutoCreate() && material.getInstance(OreMaterial.Type.INGOT) != null) {
                registerItemRender((Item)material.getInstance(OreMaterial.Type.INGOT), "ingotBase");
            }
        }

        // Blocks
        for(OreMaterial material : OreMaterial.ORES) {
            if (material.doAutoCreate() && material.getInstance(OreMaterial.Type.ORE) != null) {
                registerBlockRender((Block)material.getInstance(OreMaterial.Type.ORE), "oreBase");
            }
        }
        registerBlockRender(ModBlocks.machineHotPlate);
        registerBlockRender(ModBlocks.machineFireBox);

    }

    @Override
    public void registerTileEntityRenderers() {
        RenderMachineBasic renderMachineBasic = new RenderMachineBasic();
        ClientRegistry.bindTileEntitySpecialRenderer(TEHotPlate.class, renderMachineBasic);
    }

    protected static void registerItemRender(ItemSP item) { registerItemRender(item, item.getName()); }
    protected static void registerItemRender(Item item, String name) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(SteamPowered.MODID+":"+name, "inventory"));
    }

    protected static void registerBlockRender(BlockSP block) { registerBlockRender(block, block.getName()); }
    protected static void registerBlockRender(Block block, String name) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(SteamPowered.MODID+":"+name, "inventory"));
    }
}
