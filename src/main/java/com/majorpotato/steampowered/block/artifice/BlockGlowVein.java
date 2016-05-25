package com.majorpotato.steampowered.block.artifice;


import com.majorpotato.steampowered.block.BlockTileSP;
import com.majorpotato.steampowered.tileentity.artifice.TEntityGlowVein;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGlowVein extends BlockTileSP {

    public BlockGlowVein() {
        super(Material.redstoneLight);
        this.setHardness(2.0F);
        this.setResistance(5F);
        this.setHarvestLevel("pickaxe", 0);
        this.setBlockName("blockGlowVein");
        this.setBlockTextureName("blockGlowVein");
        this.setStepSound(Block.soundTypeGlass);
        this.setLightLevel(1.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TEntityGlowVein();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}
