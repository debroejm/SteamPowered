package com.majorpotato.steampowered.util.steam;

import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.TieredMaterial;
import com.majorpotato.steampowered.util.machine.MachineType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SteamLogic {
    private MachineType machineType = MachineType.NULL;
    private TieredMaterial material = null;

    private int steamCurrent;

    private int xCoord, yCoord, zCoord;
    private World worldObj;

    private SteamLogic[] connectedSides = new SteamLogic[6];
    private int numSidesConnected = 0;

    private int steamParticleSpawnDelay = 0;

    /**
     * Constructor for a SteamLogic class.
     * @param type of machine this SteamLogic belongs to. (IE Producer, Consumer, Transport)
     * @param material of the machine this SteamLogic belongs to. (IE Iron, Brass, Steel)
     * @param tileEntity that this SteamLogic belongs to.
     */
    public SteamLogic(MachineType type, TieredMaterial material, TileEntity tileEntity) { this(type, material, tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord); }

    /**
     * Secondary Constructor. Used instead of a tileEntity if there is no tileEntity to attach to.
     * @param type of machine this SteamLogic belongs to. (IE Producer, Consumer, Transport)
     * @param material of the machine this SteamLogic belongs to. (IE Iron, Brass, Steel)
     * @param worldObj that this SteamLogic exists in.
     * @param x position of this SteamLogic.
     * @param y position of this SteamLogic.
     * @param z position of this SteamLogic.
     */
    public SteamLogic(MachineType type, TieredMaterial material, World worldObj, int x, int y, int z) {
        this.machineType = type;
        this.steamCurrent = 0;
        this.material = material;
        this.worldObj = worldObj;
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;

        // Setup connection data, and make sure neighboring connection data is accurate as well.
        checkNeighbors();
        if(connectedSides[Direction.NORTH.ID()] != null && connectedSides[Direction.NORTH.ID()].canSideConnect(Direction.SOUTH)) connectedSides[Direction.NORTH.ID()].connectedSides[Direction.SOUTH.ID()] = this;
        if(connectedSides[Direction.SOUTH.ID()] != null && connectedSides[Direction.SOUTH.ID()].canSideConnect(Direction.NORTH)) connectedSides[Direction.SOUTH.ID()].connectedSides[Direction.NORTH.ID()] = this;
        if(connectedSides[Direction.EAST.ID()] != null  && connectedSides[Direction.EAST.ID()].canSideConnect(Direction.WEST))   connectedSides[Direction.EAST.ID()].connectedSides[Direction.WEST.ID()] = this;
        if(connectedSides[Direction.WEST.ID()] != null  && connectedSides[Direction.WEST.ID()].canSideConnect(Direction.EAST))   connectedSides[Direction.WEST.ID()].connectedSides[Direction.EAST.ID()] = this;
        if(connectedSides[Direction.UP.ID()] != null    && connectedSides[Direction.UP.ID()].canSideConnect(Direction.DOWN))     connectedSides[Direction.UP.ID()].connectedSides[Direction.DOWN.ID()] = this;
        if(connectedSides[Direction.DOWN.ID()] != null  && connectedSides[Direction.DOWN.ID()].canSideConnect(Direction.UP))     connectedSides[Direction.DOWN.ID()].connectedSides[Direction.UP.ID()] = this;
    }

    public MachineType getMachineType() { return machineType; }
    public TieredMaterial getMaterial() { return material; }

    public int getSteamSafePressureRate() { return material.getSteamCapacity(); }
    public int getSteamOverloadPressureRate() { return material.getSteamOverload(); }
    public int getSteamPressure() { return steamCurrent; }
    public void setSteamPressure(int amount) {
        if(amount < 0) steamCurrent = 0;
        else steamCurrent = amount;
    }
    public double getSteamPressurePercent() {
        double percent = steamCurrent * 100.0 / material.getSteamCapacity();
        if(percent > 100.0) return 100.0;
        return percent;
    }
    public double getSteamOverloadPercent() {
        double percent = (steamCurrent - material.getSteamCapacity()) * 100.0 / material.getSteamOverload();
        if(percent < 0.0) return 0.0;
        if(percent > 100.0) return 100.0;
        return percent;
    }

    /**
     * Checks neighboring block positions to see if this SteamLogic can connect to other SteamLogics in said positions.
     */
    public void checkNeighbors() {
        numSidesConnected = 0;
        if(worldObj == null) return;

        // EAST
        TileEntity tEnt = this.worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
        if(canSideConnect(Direction.EAST) && tEnt instanceof ISteamUser && ((ISteamUser)tEnt).canSideConnect(Direction.WEST) && (((ISteamUser)tEnt).getSteamLogic() != null) && !(((ISteamUser)tEnt).getSteamLogic().getMachineType() == MachineType.PRODUCER && machineType == MachineType.PRODUCER)) {
            connectedSides[Direction.EAST.ID()] = ((ISteamUser) tEnt).getSteamLogic();
            numSidesConnected++;
        } else connectedSides[Direction.EAST.ID()] = null;

        // WEST
        tEnt = this.worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
        if(canSideConnect(Direction.WEST) && tEnt instanceof ISteamUser && ((ISteamUser)tEnt).canSideConnect(Direction.EAST) && (((ISteamUser)tEnt).getSteamLogic() != null) && !(((ISteamUser)tEnt).getSteamLogic().getMachineType() == MachineType.PRODUCER && machineType == MachineType.PRODUCER)) {
            connectedSides[Direction.WEST.ID()] = ((ISteamUser)tEnt).getSteamLogic();
            numSidesConnected++;
        } else connectedSides[Direction.WEST.ID()] = null;

        // NORTH
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
        if(canSideConnect(Direction.NORTH) && tEnt instanceof ISteamUser && ((ISteamUser)tEnt).canSideConnect(Direction.SOUTH) && (((ISteamUser)tEnt).getSteamLogic() != null) && !(((ISteamUser)tEnt).getSteamLogic().getMachineType() == MachineType.PRODUCER && machineType == MachineType.PRODUCER)) {
            connectedSides[Direction.NORTH.ID()] = ((ISteamUser)tEnt).getSteamLogic();
            numSidesConnected++;
        } else connectedSides[Direction.NORTH.ID()] = null;

        // SOUTH
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
        if(canSideConnect(Direction.SOUTH) && tEnt instanceof ISteamUser && ((ISteamUser)tEnt).canSideConnect(Direction.NORTH) && (((ISteamUser)tEnt).getSteamLogic() != null) && !(((ISteamUser)tEnt).getSteamLogic().getMachineType() == MachineType.PRODUCER && machineType == MachineType.PRODUCER)) {
            connectedSides[Direction.SOUTH.ID()] = ((ISteamUser)tEnt).getSteamLogic();
            numSidesConnected++;
        } else connectedSides[Direction.SOUTH.ID()] = null;

        // TOP
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
        if(canSideConnect(Direction.UP) && tEnt instanceof ISteamUser && ((ISteamUser)tEnt).canSideConnect(Direction.DOWN) && (((ISteamUser)tEnt).getSteamLogic() != null) && !(((ISteamUser)tEnt).getSteamLogic().getMachineType() == MachineType.PRODUCER && machineType == MachineType.PRODUCER)) {
            connectedSides[Direction.UP.ID()] = ((ISteamUser)tEnt).getSteamLogic();
            numSidesConnected++;
        } else connectedSides[Direction.UP.ID()] = null;

        // BOTTOM
        tEnt = this.worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
        if(canSideConnect(Direction.DOWN) && tEnt instanceof ISteamUser && ((ISteamUser)tEnt).canSideConnect(Direction.UP) && (((ISteamUser)tEnt).getSteamLogic() != null) && !(((ISteamUser)tEnt).getSteamLogic().getMachineType() == MachineType.PRODUCER && machineType == MachineType.PRODUCER)) {
            connectedSides[Direction.DOWN.ID()] = ((ISteamUser)tEnt).getSteamLogic();
            numSidesConnected++;
        } else connectedSides[Direction.DOWN.ID()] = null;
    }

    /**
     * Update function to be ran every tick, usually called by the host TileEntity during its own update cycle.
     */
    public void update() {

        // Check to see if the steam pressure is above the overload rate, and explode the pipe if it is.
        if(steamCurrent >= material.getSteamOverload()) {
            if(!worldObj.isRemote)
                worldObj.createExplosion(null, xCoord, yCoord, zCoord, 2F, true);
        }

        // Check to see if the pipe is bursting, and animate accordingly.
        if( steamCurrent > material.getSteamCapacity() )
        {
            if(steamParticleSpawnDelay < 1)
            {
                steamParticleSpawnDelay = 21 - (int)(20*getSteamOverloadPercent());
                float x = (float)Math.random()-0.5F;
                float y = (float)Math.random()-0.5F;
                float z = (float)Math.random()-0.5F;
                worldObj.spawnParticle("cloud", (double)(xCoord+0.5+x*2.0), (double)(yCoord+0.5+y*2.0), (double)(zCoord+0.5+z*2.0), x*0.4, y*0.4, z*0.4);
            }
            else
            {
                steamParticleSpawnDelay--;
            }
        }

        // Balance the steam pressure between this pipe and its neighbors. This will happen twice each, once for this pipe to each neighbor, and once for each neighbor to this pipe.
        balanceSteam(this, connectedSides[Direction.NORTH.ID()]);
        balanceSteam(this, connectedSides[Direction.SOUTH.ID()]);
        balanceSteam(this, connectedSides[Direction.EAST.ID()]);
        balanceSteam(this, connectedSides[Direction.WEST.ID()]);
        balanceSteam(this, connectedSides[Direction.UP.ID()]);
        balanceSteam(this, connectedSides[Direction.DOWN.ID()]);
    }

    /**
     * Checks to see if this SteamLogic can make a connection on the indicated side.
     * @param side of SteamLogic to check, using a Direction enum local to SteamPowered.
     * @return true if indicated side can make a connection.
     */
    public boolean canSideConnect(Direction side) {
        TileEntity tEnt = worldObj.getTileEntity(xCoord, yCoord, zCoord);
        if(tEnt instanceof ISteamUser) return ((ISteamUser)tEnt).canSideConnect(side);
        return false;
    }

    /**
     * Checks to see if this SteamLogic is already connected on the indicated side.
     * @param side of SteamLogic to check, using a Direction enum local to SteamPowered.
     * @return true if indicated side already has a connection.
     */
    public boolean isSideConnected(Direction side)
    {
        if(connectedSides[side.ID()] != null) return true;
        else return false;
    }

    public int getNumSidesConnected() { return numSidesConnected; }

    /**
     * NBTData write function, usually called by the host TileEntity's NBTData write function.
     * @param compound of NBT data to write to.
     */
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("SteamPressure", steamCurrent);
    }

    /**
     * NBTData read function, usually called by the host TileEntity's NBTData read function.
     * @param compound of NBT data to read from.
     */
    public void readFromNBT(NBTTagCompound compound)
    {
        this.steamCurrent = compound.getInteger("SteamPressure");
    }

    /**
     * Static function to balance the steam pressure between two SteamLogics.
     * Will usually be called twice - once from each SteamLogic - but only ran once - from the SteamLogic with higher steam pressure.
     * @param from SteamLogic to transfer from.
     * @param to SteamLogic to transfer to.
     */
    private static void balanceSteam(SteamLogic from, SteamLogic to) {
        if(from == null || to == null) return;
        if(from.getSteamPressure() > to.getSteamPressure()) {
            int amount = from.getSteamPressure() - to.getSteamPressure();
            if(amount > 2) {
                from.setSteamPressure(from.getSteamPressure() - 1);
                to.setSteamPressure(to.getSteamPressure() + 1);
            } else {
                to.setSteamPressure(from.getSteamPressure()-1);
            }
        }
    }
}
