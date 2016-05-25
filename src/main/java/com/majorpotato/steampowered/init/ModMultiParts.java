package com.majorpotato.steampowered.init;


import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import com.majorpotato.steampowered.block.machine.steam.MachineSteamPipe;
import com.majorpotato.steampowered.multipart.SteamPipePart;
import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.util.TieredMaterial;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Arrays;

public class ModMultiParts implements MultiPartRegistry.IPartFactory, MultiPartRegistry.IPartConverter {

    //SteamPipePart steamPipeBrass = new SteamPipePart(TieredMaterial.Brass, (MachineSteamPipe)ModBlocks.machineSteamPipeBrass);

    public ModMultiParts() {
        init();
    }


    @Override
    public TMultiPart createPart(String name, boolean client)
    {
        if(name.equals(Reference.MOD_ID.toLowerCase()+":steamPipeBrass")) return new SteamPipePart(TieredMaterial.Brass, (MachineSteamPipe)ModBlocks.machineSteamPipeBrass);
        if(name.equals(Reference.MOD_ID.toLowerCase()+":steamPipeSteel")) return new SteamPipePart(TieredMaterial.Steel, (MachineSteamPipe)ModBlocks.machineSteamPipeSteel);

        return null;
    }

    public void init()
    {
        MultiPartRegistry.registerConverter(this);
        MultiPartRegistry.registerParts(this, new String[] {
                Reference.MOD_ID.toLowerCase()+":steamPipeBrass",
                Reference.MOD_ID.toLowerCase()+":steamPipeSteel"
        });
    }

    @Override
    public Iterable<Block> blockTypes() {
        return Arrays.asList(
                (Block)ModBlocks.machineSteamPipeBrass,
                (Block)ModBlocks.machineSteamPipeSteel
        );
    }

    @Override
    public TMultiPart convert(World world, BlockCoord pos)
    {
        Block b = world.getBlock(pos.x, pos.y, pos.z);
        int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
        if(b == ModBlocks.machineSteamPipeBrass)
            return new SteamPipePart(TieredMaterial.Brass, (MachineSteamPipe)ModBlocks.machineSteamPipeBrass, meta);
        if(b == ModBlocks.machineSteamPipeSteel)
            return new SteamPipePart(TieredMaterial.Steel, (MachineSteamPipe)ModBlocks.machineSteamPipeSteel, meta);

        return null;
    }

}
