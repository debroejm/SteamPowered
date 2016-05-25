package com.majorpotato.steampowered.block.generic.ruined;

import com.majorpotato.steampowered.tileentity.rubble.TEntityRubble1;
import com.majorpotato.steampowered.tileentity.rubble.TEntityRubble2;
import com.majorpotato.steampowered.tileentity.rubble.TEntityRubble3;
import com.majorpotato.steampowered.tileentity.rubble.TEntityRubble4;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRubble extends BlockTileRuined {

    public BlockRubble()
    {
        this.setBlockName("blockRubble");
        this.setBlockTextureName("noTex");
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3F, 1.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        int rand = (int)(Math.random()*4);
        switch (rand) {
            case 0:
                return new TEntityRubble1();
            case 1:
                return new TEntityRubble2();
            case 2:
                return new TEntityRubble3();
            default:
                return new TEntityRubble4();
        }
    }
}
