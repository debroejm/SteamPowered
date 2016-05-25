package com.majorpotato.steampowered.multipart;


import codechicken.lib.vec.Cuboid6;
import com.majorpotato.steampowered.block.machine.steam.MachineSteamPipe;
import com.majorpotato.steampowered.util.TieredMaterial;
import net.minecraft.block.Block;

public class SteamPipePart extends SPPart {

    private TieredMaterial mat;
    private MachineSteamPipe pipe;

    public SteamPipePart(TieredMaterial mat, MachineSteamPipe pipe) {
        this.mat = mat;
        this.pipe = pipe;
    }

    public SteamPipePart(TieredMaterial mat, MachineSteamPipe pipe, int meta) {
        super(meta);
        this.mat = mat;
        this.pipe = pipe;
    }

    @Override
    public Block getBlock() {
        return pipe;
    }

    @Override
    public codechicken.lib.vec.Cuboid6 getBounds() {
        return new Cuboid6(0.2,0.2,0.2,0.8,0.8,0.8);
    }

    @Override
    protected String getName() {
        return "steamPipe"+mat.getName();
    }


}
