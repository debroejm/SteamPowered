package com.majorpotato.steampowered.block;


import com.majorpotato.steampowered.creativetab.CreativeTabSP;
import com.majorpotato.steampowered.reference.Reference;
import com.majorpotato.steampowered.tileentity.TEntitySP;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockTileSP extends BlockContainer {
    protected BlockTileSP(Material material) {
        super(material);
        setHardness(2.5F);
        setResistance(5.0F);
        setCreativeTab(CreativeTabSP.SP_TAB);
    }

    public BlockTileSP()
    {
        this(Material.wood);
        this.setCreativeTab(CreativeTabSP.SP_TAB);
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int metadata);

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta)
    {
        TileEntity ent = world.getTileEntity(x, y, z);
        if(ent instanceof TEntitySP) ((TEntitySP)ent).onDestroy();
    }
}
