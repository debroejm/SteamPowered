package com.majorpotato.steampowered.tileentity.machine.basic;

import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.tileentity.TEntitySP;


public class TEntityChimneyStack extends TEntitySP {

    private boolean[] sides = {false, false, false, false, false, false};

    @Override
    public void updateEntity() {
        super.updateEntity();

        // Reset
        sides[Direction.NORTH.ID()] = false;
        sides[Direction.SOUTH.ID()] = false;
        sides[Direction.WEST.ID()] = false;
        sides[Direction.EAST.ID()] = false;

        // EAST
        if (this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord) instanceof TEntityChimneyStack) {
            // NORTH EAST
            if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) instanceof TEntityChimneyStack && this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord - 1) instanceof TEntityChimneyStack) {
                sides[Direction.EAST.ID()] = true;
                sides[Direction.NORTH.ID()] = true;
            }
            // SOUTH EAST
            if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) instanceof TEntityChimneyStack && this.worldObj.getTileEntity(xCoord + 1, yCoord, zCoord + 1) instanceof TEntityChimneyStack) {
                sides[Direction.EAST.ID()] = true;
                sides[Direction.SOUTH.ID()] = true;
            }
        }
        // WEST
        if (this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord) instanceof TEntityChimneyStack) {
            // NORTH WEST
            if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord - 1) instanceof TEntityChimneyStack && this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord - 1) instanceof TEntityChimneyStack) {
                sides[Direction.WEST.ID()] = true;
                sides[Direction.NORTH.ID()] = true;
            }
            // SOUTH WEST
            if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord + 1) instanceof TEntityChimneyStack && this.worldObj.getTileEntity(xCoord - 1, yCoord, zCoord + 1) instanceof TEntityChimneyStack) {
                sides[Direction.WEST.ID()] = true;
                sides[Direction.SOUTH.ID()] = true;
            }
        }

        // TOP
        if (this.worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) instanceof TEntityChimneyStack)
            sides[Direction.UP.ID()] = true;
        else
            sides[Direction.UP.ID()] = false;

        // BOTTOM
        if (this.worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof TEntityChimneyStack)
            sides[Direction.DOWN.ID()] = true;
        else
            sides[Direction.DOWN.ID()] = false;
    }

    public boolean[] getSideData() { return sides; }
    public boolean isSideConnected(Direction direct) {
        //System.out.println(sides[0] + ", " + sides[1] + ", " + sides[2] + ", " + sides[3] + ", " + sides[4] + ", " + sides[5]);
        return sides[direct.ID()];
    }
}
