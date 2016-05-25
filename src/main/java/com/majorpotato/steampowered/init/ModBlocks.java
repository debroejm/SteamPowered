package com.majorpotato.steampowered.init;

import com.majorpotato.steampowered.block.BlockCTSP;
import com.majorpotato.steampowered.block.BlockTileSP;
import com.majorpotato.steampowered.block.BlockSP;
import com.majorpotato.steampowered.block.artifice.BlockGlowVein;
import com.majorpotato.steampowered.block.artifice.BlockInfused;
import com.majorpotato.steampowered.block.generic.*;
import com.majorpotato.steampowered.block.generic.ruined.BlockRubble;
import com.majorpotato.steampowered.block.generic.ruined.BlockRuinedScaffold;
import com.majorpotato.steampowered.block.machine.BlockMachine;
import com.majorpotato.steampowered.block.machine.ItemMachine;
import com.majorpotato.steampowered.block.machine.basic.MachineAlloyFurnace;
import com.majorpotato.steampowered.block.machine.basic.MachineChimneyStack;
import com.majorpotato.steampowered.block.machine.basic.MachineFirebox;
import com.majorpotato.steampowered.block.machine.basic.MachineHotPlate;
import com.majorpotato.steampowered.block.machine.mechanical.*;
import com.majorpotato.steampowered.block.machine.steam.*;
import com.majorpotato.steampowered.block.ore.OreCopper;
import com.majorpotato.steampowered.block.ore.OreZinc;
import com.majorpotato.steampowered.reference.Reference;
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
import com.majorpotato.steampowered.util.TieredMaterial;
import cpw.mods.fml.common.registry.GameRegistry;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    // Ores
    public static final BlockSP oreCopper = new OreCopper();
    public static final BlockSP oreZinc = new OreZinc();

    // Generic
    public static final BlockTileSP blockRubble = new BlockRubble();
    public static final BlockSP blockRuinedScaffold = new BlockRuinedScaffold();
    public static final BlockSP blockMechanusPortal = new BlockMechanusPortal();
    public static final BlockSP blockMechanusWorld = new BlockMechanusWorld();
    public static final BlockSP blockLamp = new BlockLamp();

    // Building Blocks - Walls
    public static final BlockCTSP blockWallSteel = new BlockMetalWall(TieredMaterial.Steel);
    public static final BlockCTSP blockWallStone = new BlockMetalWall(TieredMaterial.Stone);

    // Building Blocks - Panels
    public static final BlockSP blockPanelBronze = new BlockMetalPanel(TieredMaterial.Brass);
    public static final BlockSP blockPanelIron = new BlockMetalPanel(TieredMaterial.Iron);

    // Building Blocks - Grates
    public static final BlockSP blockGrateRusty = new BlockMetalGrate(TieredMaterial.Rusty);
    public static final BlockSP blockGrateCopper = new BlockMetalGrate(TieredMaterial.Copper);
    public static final BlockSP blockGrateBrass = new BlockMetalGrate(TieredMaterial.Brass);
    public static final BlockSP blockGrateIron = new BlockMetalGrate(TieredMaterial.Iron);
    public static final BlockSP blockGrateSteel = new BlockMetalGrate(TieredMaterial.Steel);

    // Basic Machine
    public static final BlockMachine machineAlloyFurnace = new MachineAlloyFurnace();
    public static final BlockMachine machineFirebox = new MachineFirebox();
    public static final BlockMachine machineChimneyStack = new MachineChimneyStack();
    public static final BlockMachine machineHotPlate = new MachineHotPlate();

    // Steam Machine
    public static final MachineSteam machineSteamPipeBrass = new MachineSteamPipe(TieredMaterial.Brass);
    public static final MachineSteam machineSteamPipeSteel = new MachineSteamPipe(TieredMaterial.Steel);
    public static final MachineSteam machineSteamBoiler = new MachineSteamBoiler();
    public static final MachineSteam machineSteamBoilerBurning = new MachineSteamBoilerBurning();
    public static final MachineSteam machineSteamVent = new MachineSteamVent();
    public static final MachineSteam machineSteamPiston = new MachineSteamPiston();
    public static final MachineSteam machineSteamEngine = new MachineSteamEngine();

    // Mechanical Machine
    public static final MachineMechanical machineDoorFrame = new MachineDoorFrame();
    public static final MachineMechanical machineDoorBody = new MachineDoorBody();
    public static final MachineMechanical machineGear = new MachineGear();
    public static final MachineMechanical machineAxle = new MachineAxle();
    public static final MachineMechanical machineChain = new MachineChain();

    // Artifice
    public static final BlockGlowVein blockGlowVein = new BlockGlowVein();
    public static final BlockTileSP blockInfused = new BlockInfused();

    public static final BlockPortalFrame blockPortalFrame = new BlockPortalFrame();

    public static void init()
    {
        // Ores
        GameRegistry.registerBlock(oreCopper, "oreCopper");
        GameRegistry.registerBlock(oreZinc, "oreZinc");

        // Generic
        GameRegistry.registerBlock(blockMechanusPortal, "blockMechanusPortal");
        GameRegistry.registerBlock(blockRubble, "blockRubble");
        GameRegistry.registerBlock(blockRuinedScaffold, "blockRuinedScaffold");
        GameRegistry.registerBlock(blockMechanusWorld, "blockMechanusWorld");
        GameRegistry.registerBlock(blockLamp, "blockLamp");

        // Building Blocks - Walls
        GameRegistry.registerBlock(blockWallSteel, "blockWallSteel");
        GameRegistry.registerBlock(blockWallStone, "blockWallStone");

        // Building Blocks - Panels
        GameRegistry.registerBlock(blockPanelBronze, "blockPanelBrass");
        GameRegistry.registerBlock(blockPanelIron, "blockPanelIron");

        // Building Blocks - Grates
        GameRegistry.registerBlock(blockGrateRusty, "blockGrateRusty");
        GameRegistry.registerBlock(blockGrateCopper, "blockGrateCopper");
        GameRegistry.registerBlock(blockGrateBrass, "blockGrateBrass");
        GameRegistry.registerBlock(blockGrateIron, "blockGrateIron");
        GameRegistry.registerBlock(blockGrateSteel, "blockGrateSteel");

        // Basic Machine
        GameRegistry.registerBlock(machineFirebox, ItemMachine.class, "machineFirebox");
        GameRegistry.registerBlock(machineAlloyFurnace, ItemMachine.class, "machineAlloyFurnace");
        GameRegistry.registerBlock(machineChimneyStack, "machineChimneyStack");
        GameRegistry.registerBlock(machineHotPlate, "machineHotPlate");

        // Steam Machine
        GameRegistry.registerBlock(machineSteamPipeBrass, "machineSteamPipeBrass");
        GameRegistry.registerBlock(machineSteamPipeSteel, "machineSteamPipeSteel");
        GameRegistry.registerBlock(machineSteamBoiler, "machineSteamBoiler");
        GameRegistry.registerBlock(machineSteamBoilerBurning, "machineSteamBoilerBurning");
        GameRegistry.registerBlock(machineSteamVent, "machineSteamVent");
        GameRegistry.registerBlock(machineSteamPiston, "machineSteamPiston");
        GameRegistry.registerBlock(machineSteamEngine, "machineSteamEngine");

        // Mechanical Machine
        GameRegistry.registerBlock(machineDoorFrame, "machineDoorFrame");
        GameRegistry.registerBlock(machineDoorBody, "machineDoorBody");
        GameRegistry.registerBlock(machineGear, "machineGear");
        GameRegistry.registerBlock(machineAxle, "machineAxle");
        GameRegistry.registerBlock(machineChain, "machineChain");

        // Artifice
        GameRegistry.registerBlock(blockGlowVein, "blockGlowVein");
        GameRegistry.registerBlock(blockInfused, "blockInfused");

        GameRegistry.registerBlock(blockPortalFrame, "blockPortalFrame");

        // Ore Dictionary Registering
        registerOre("oreCopper", oreCopper);
        registerOre("oreTin", oreZinc);
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TEntityAlloyFurnace.class, "TEAlloyFurnace");
        GameRegistry.registerTileEntity(TEntityFirebox.class, "TEFirebox");
        GameRegistry.registerTileEntity(TEntityRubble1.class, "TERubble1");
        GameRegistry.registerTileEntity(TEntityRubble2.class, "TERubble2");
        GameRegistry.registerTileEntity(TEntityRubble3.class, "TERubble3");
        GameRegistry.registerTileEntity(TEntityRubble4.class, "TERubble4");
        GameRegistry.registerTileEntity(TEntitySteamPipe.class, "TESteamPipe");
        GameRegistry.registerTileEntity(TEntitySteamBoiler.class, "TESteamBoiler");
        GameRegistry.registerTileEntity(TEntitySteamVent.class, "TESteamVent");
        GameRegistry.registerTileEntity(TEntitySteamPiston.class, "TESteamPiston");
        GameRegistry.registerTileEntity(TEntityDoorBody.class, "TEDoorBody");
        GameRegistry.registerTileEntity(TEntityDoorFrame.class, "TEDoorFrame");
        GameRegistry.registerTileEntity(TEntityGear.class, "TEGear");
        GameRegistry.registerTileEntity(TEntityAxle.class, "TEAxle");
        GameRegistry.registerTileEntity(TEntitySteamEngine.class, "TESteamEngine");
        GameRegistry.registerTileEntity(TEntityChimneyStack.class, "TEChimneyStack");
        GameRegistry.registerTileEntity(TEntityChain.class, "TEChain");
        GameRegistry.registerTileEntity(TEntityInfused.class, "TEInfused");
        GameRegistry.registerTileEntity(TEntityConnectedTexture.class, "TECT");
        GameRegistry.registerTileEntity(TEntityGlowVein.class, "TEGlowVein");
        GameRegistry.registerTileEntity(TEntityHotPlate.class, "TEHotPlate");
    }

    private static void registerOlympia() {
        //OlympiaAPI.registry.registerMetal("steel", )
    }
}
