package com.majorpotato.steampowered.block;


import com.majorpotato.steampowered.SteamPowered;
import com.majorpotato.steampowered.util.OreMaterial;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockOre extends BlockSP implements IBlockColor {

    protected OreMaterial material;
    public OreMaterial getMaterial() { return material; }

    public BlockOre(OreMaterial material) {
        super(Material.ROCK);
        this.material = material;
        setUnlocalizedName(material.getName(OreMaterial.Type.ORE));
        material.setInstance(OreMaterial.Type.ORE, this);
        setHarvestLevel("pickaxe", material.getHarvestLevel());
        setHardness(material.getHardness());
        setResistance(material.getResistance());
    }

    @Override
    @ParametersAreNonnullByDefault
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        return tintIndex == 0 ? material.getColor(OreMaterial.Type.ORE) : 0xFFFFFF;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

}
