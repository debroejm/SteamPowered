package com.majorpotato.steampowered.proxy;

import com.majorpotato.steampowered.init.ModBlocks;
import com.majorpotato.steampowered.init.ModItems;
import com.majorpotato.steampowered.client.rendering.block.TEntityArtificeRenderer;
import com.majorpotato.steampowered.tileentity.TEntityConnectedTexture;
import com.majorpotato.steampowered.tileentity.artifice.TEntityGlowVein;
import com.majorpotato.steampowered.tileentity.artifice.TEntityInfused;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityAlloyFurnace;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityChimneyStack;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityFirebox;
import com.majorpotato.steampowered.tileentity.machine.basic.TEntityHotPlate;
import com.majorpotato.steampowered.tileentity.machine.mechanical.*;
import com.majorpotato.steampowered.tileentity.machine.steam.*;
import com.majorpotato.steampowered.tileentity.rubble.TEntityRubble1;
import com.majorpotato.steampowered.tileentity.rubble.TEntityRubble2;
import com.majorpotato.steampowered.tileentity.rubble.TEntityRubble3;
import com.majorpotato.steampowered.tileentity.rubble.TEntityRubble4;
import com.majorpotato.steampowered.client.rendering.block.ConnectedTexturesRenderer;
import com.majorpotato.steampowered.client.rendering.block.TEntityInfusedRenderer;
import com.majorpotato.steampowered.client.rendering.block.machine.TEntityMachineBasicRenderer;
import com.majorpotato.steampowered.client.rendering.block.machine.TEntityMachineMechanicalRenderer;
import com.majorpotato.steampowered.client.rendering.block.TEntityRubbleRenderer;
import com.majorpotato.steampowered.client.rendering.block.machine.TEntityMachineSteamRenderer;
import com.majorpotato.steampowered.client.rendering.item.*;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerKeyBindings()
    {
        //ClientRegistry.registerKeyBinding(Keybindings.charge);
        //ClientRegistry.registerKeyBinding(Keybindings.release);
    }

    @Override
    public void registerRenderThings() {

        TileEntitySpecialRenderer renderRubble = new TEntityRubbleRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityRubble1.class, renderRubble);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityRubble2.class, renderRubble);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityRubble3.class, renderRubble);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityRubble4.class, renderRubble);

        TileEntitySpecialRenderer renderMachineBasic = new TEntityMachineBasicRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityFirebox.class, renderMachineBasic);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityAlloyFurnace.class, renderMachineBasic);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityChimneyStack.class, renderMachineBasic);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityHotPlate.class, renderMachineBasic);
        IItemRenderer renderMachineBasicItem = new ItemMachineBasicRenderer();
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineFirebox), renderMachineBasicItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineAlloyFurnace), renderMachineBasicItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineChimneyStack), renderMachineBasicItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineHotPlate), renderMachineBasicItem);

        TileEntitySpecialRenderer renderMachineSteam = new TEntityMachineSteamRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TEntitySteamPipe.class, renderMachineSteam);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntitySteamBoiler.class, renderMachineSteam);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntitySteamVent.class, renderMachineSteam);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntitySteamPiston.class, renderMachineSteam);
        IItemRenderer renderMachineSteamItem = new ItemMachineSteamRenderer();
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineSteamPipeBrass), renderMachineSteamItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineSteamPipeSteel), renderMachineSteamItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineSteamBoiler), renderMachineSteamItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineSteamBoilerBurning), renderMachineSteamItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineSteamVent), renderMachineSteamItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineSteamPiston), renderMachineSteamItem);

        TileEntitySpecialRenderer renderMachineMechanical = new TEntityMachineMechanicalRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityDoorBody.class, renderMachineMechanical);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityDoorFrame.class, renderMachineMechanical);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityGear.class, renderMachineMechanical);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityAxle.class, renderMachineMechanical);
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityChain.class, renderMachineMechanical);
        IItemRenderer renderMachineMechanicalItem = new ItemMachineMechanicalRenderer();
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineDoorBody), renderMachineMechanicalItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineDoorFrame), renderMachineMechanicalItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineGear), renderMachineMechanicalItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineAxle), renderMachineMechanicalItem);
        MinecraftForgeClient.registerItemRenderer(ModItems.itemGear, renderMachineMechanicalItem);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.machineChain), renderMachineMechanicalItem);

        TileEntitySpecialRenderer renderArtifice = new TEntityArtificeRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityGlowVein.class, renderArtifice);

        TileEntitySpecialRenderer renderInfused = new TEntityInfusedRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityInfused.class, renderInfused);

        TileEntitySpecialRenderer renderConnectedTexture = new ConnectedTexturesRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TEntityConnectedTexture.class, renderConnectedTexture);
    }
}
