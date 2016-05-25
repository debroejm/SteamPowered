package com.majorpotato.steampowered.block;


import com.majorpotato.steampowered.creativetab.CreativeTabSP;
import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.tileentity.TEntityConnectedTexture;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockCTSP extends BlockTileSP {

    protected BlockCTSP(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabSP.SP_TAB);
    }

    public BlockCTSP()
    {
        this(Material.glass);
        this.setCreativeTab(CreativeTabSP.SP_TAB);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        TileEntity ent = world.getTileEntity(x, y, z);
        if(ent instanceof TEntityConnectedTexture) {
            ((TEntityConnectedTexture)ent).checkNeighbors();
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack itemStack) {
        TileEntity ent = world.getTileEntity(i, j, k);
        if(ent instanceof TEntityConnectedTexture) {
            ((TEntityConnectedTexture)ent).checkNeighbors();
        }
    }


    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TEntityConnectedTexture();
    }

    IIcon[] ctmIcons = new IIcon[16];

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        ctmIcons[0] = iconRegister.registerIcon(String.format("%s/single", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[1] = iconRegister.registerIcon(String.format("%s/middle", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[2] = iconRegister.registerIcon(String.format("%s/edgeLeft", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[3] = iconRegister.registerIcon(String.format("%s/edgeRight", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[4] = iconRegister.registerIcon(String.format("%s/edgeUp", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[5] = iconRegister.registerIcon(String.format("%s/edgeDown", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[6] = iconRegister.registerIcon(String.format("%s/cornerUpLeft", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[7] = iconRegister.registerIcon(String.format("%s/cornerUpRight", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[8] = iconRegister.registerIcon(String.format("%s/cornerDownLeft", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[9] = iconRegister.registerIcon(String.format("%s/cornerDownRight", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[10] = iconRegister.registerIcon(String.format("%s/endLeft", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[11] = iconRegister.registerIcon(String.format("%s/endRight", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[12] = iconRegister.registerIcon(String.format("%s/endUp", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[13] = iconRegister.registerIcon(String.format("%s/endDown", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[14] = iconRegister.registerIcon(String.format("%s/horizontal", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        ctmIcons[15] = iconRegister.registerIcon(String.format("%s/vertical", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));

        blockIcon = ctmIcons[0];
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        Direction frnt = Direction.fromID(side);
        if(world.getBlock(x+frnt.X(),y+frnt.Y(),z+frnt.Z()).isOpaqueCube()) return ctmIcons[0];

        Direction vert, horiz;
        switch(frnt) {
            case UP:
            case DOWN:
            default:
                vert = Direction.NORTH;
                horiz = Direction.WEST;
                break;
            case NORTH:
                vert = Direction.UP;
                horiz = Direction.EAST;
                break;
            case SOUTH:
                vert = Direction.UP;
                horiz = Direction.WEST;
                break;
            case WEST:
                vert = Direction.UP;
                horiz = Direction.NORTH;
                break;
            case EAST:
                vert = Direction.UP;
                horiz = Direction.SOUTH;
                break;
        }

        boolean[] connect = new boolean[4];
        if(!world.getBlock(x + frnt.X() + horiz.X(), y + frnt.Y() + horiz.Y(), z + frnt.Z() + horiz.Z()).isOpaqueCube() &&
                world.getBlock(x + horiz.X(), y + horiz.Y(), z + horiz.Z()).equals(this)) connect[0] = true;
        if(!world.getBlock(x+frnt.X()+vert.X(), y+frnt.Y()+vert.Y(), z+frnt.Z()+vert.Z()).isOpaqueCube() &&
                world.getBlock(x+vert.X(), y+vert.Y(), z+vert.Z()).equals(this)) connect[1] = true;
        if(!world.getBlock(x+frnt.X()+horiz.opposite().X(), y+frnt.Y()+horiz.opposite().Y(), z+frnt.Z()+horiz.opposite().Z()).isOpaqueCube() &&
                world.getBlock(x+horiz.opposite().X(), y+horiz.opposite().Y(), z+horiz.opposite().Z()).equals(this)) connect[2] = true;
        if(!world.getBlock(x+frnt.X()+vert.opposite().X(), y+frnt.Y()+vert.opposite().Y(), z+frnt.Z()+vert.opposite().Z()).isOpaqueCube() &&
                world.getBlock(x + vert.opposite().X(), y + vert.opposite().Y(), z + vert.opposite().Z()).equals(this)) connect[3] = true;

        if(connect[0]) {
            if(connect[2]) {
                if(connect[1]) {
                    if(connect[3]) {
                        return ctmIcons[1];  // Middle
                    } else {
                        return ctmIcons[5];  // EdgeDown
                    }
                } else {
                    if(connect[3]) {
                        return ctmIcons[4];  // EdgeUp
                    } else {
                        return ctmIcons[14]; // Horizontal
                    }
                }
            } else {
                if(connect[1]) {
                    if(connect[3]) {
                        return ctmIcons[3];  // EdgeRight
                    } else {
                        return ctmIcons[9];  // CornerDownRight
                    }
                } else {
                    if(connect[3]) {
                        return ctmIcons[7];  // CornerUpRight
                    } else {
                        return ctmIcons[11]; // EndRight
                    }
                }
            }
        } else {
            if(connect[2]) {
                if(connect[1]) {
                    if(connect[3]) {
                        return ctmIcons[2];  // EdgeLeft
                    } else {
                        return ctmIcons[8];  // CornerDownLeft
                    }
                } else {
                    if(connect[3]) {
                        return ctmIcons[6];  // CornerUpLeft
                    } else {
                        return ctmIcons[10]; // EndLeft
                    }
                }
            } else {
                if(connect[1]) {
                    if(connect[3]) {
                        return ctmIcons[15]; // Vertical
                    } else {
                        return ctmIcons[13]; // EndDown
                    }
                } else {
                    if(connect[3]) {
                        return ctmIcons[12]; // EndUp
                    } else {
                        return ctmIcons[0];  // Single
                    }
                }
            }
        }
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
