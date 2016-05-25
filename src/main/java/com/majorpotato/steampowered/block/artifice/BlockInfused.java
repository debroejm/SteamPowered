package com.majorpotato.steampowered.block.artifice;

import com.majorpotato.steampowered.block.BlockTileSP;
import com.majorpotato.steampowered.tileentity.artifice.TEntityInfused;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInfused extends BlockTileSP {

    public BlockInfused() {
        super(Material.rock);
        this.setHardness(5.0F);
        this.setResistance(50F);
        this.setHarvestLevel("pickaxe", 0);
        this.setBlockName("blockInfused");
        this.setBlockTextureName("blockInfused");
        this.setStepSound(Block.soundTypeStone);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TEntityInfused(Blocks.stone);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity ent = world.getTileEntity(x, y, z);
        if(ent instanceof TEntityInfused) return ((TEntityInfused)ent).getTemplate().getIcon(world, x, y, z, side);
        return null;
    }
}
