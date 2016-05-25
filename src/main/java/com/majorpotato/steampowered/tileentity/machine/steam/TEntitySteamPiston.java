package com.majorpotato.steampowered.tileentity.machine.steam;


import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.TieredMaterial;
import com.majorpotato.steampowered.util.machine.IWrenchUser;
import com.majorpotato.steampowered.util.machine.MachineType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class TEntitySteamPiston extends TEntitySteam implements IWrenchUser {

    private enum ExtensionState {
        RETRACTED, EXTENDING, EXTENDED, RETRACTING;
    }

    private int builtUpSteam = 0;
    private static final int MAX_BUILT_UP_STEAM = 5000;
    private Direction facing = Direction.UP;

    private boolean pulsed = false;

    private static final int STEAM_TICK_DELAY_TIME = 60, CHECK_TICK_DELAY_TIME = 10;
    private int steamTickDelay = STEAM_TICK_DELAY_TIME;
    private int checkTickDelay = CHECK_TICK_DELAY_TIME;
    private int steamBlastCount = 0;
    private int extensionTickTime = 0;
    private ExtensionState extState = ExtensionState.RETRACTED;
    private static final int EXTEND_TIME = 5,
                             WAIT_TIME = 40,
                             RETRACT_TIME = 60;

    private boolean autoDetect = true;

    private String customWrenchMessage = "";

    public TEntitySteamPiston() {
        super();
        sendUpdates = true;
        machineType = MachineType.CONSUMER;
        material = TieredMaterial.Steel;
    }

    @Override
    public boolean canSideConnect(Direction side) { return (side == facing.opposite()); }

    public double getExtendPercent()
    {
        switch (extState) {
            case RETRACTED: return 0.0;
            case EXTENDING: return 1.0-(double)extensionTickTime/(double)EXTEND_TIME;
            case EXTENDED: return 1.0;
            case RETRACTING: return (double)extensionTickTime/(double)EXTEND_TIME;
            default: return 0.0;
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if(builtUpSteam < MAX_BUILT_UP_STEAM) {
            if(steam.getSteamPressure() > 5) {
                steam.setSteamPressure(steam.getSteamPressure() - 5);
                builtUpSteam += 5;
            } else {
                builtUpSteam += steam.getSteamPressure();
                steam.setSteamPressure(0);
            }
            if(builtUpSteam > MAX_BUILT_UP_STEAM) builtUpSteam = MAX_BUILT_UP_STEAM;
        }

        if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
            if(!pulsed && builtUpSteam > MAX_BUILT_UP_STEAM/2 && extState == ExtensionState.RETRACTED) {
                steamBlast();
                pulsed = true;
            }
        } else pulsed = false;

        if(builtUpSteam > MAX_BUILT_UP_STEAM/2)
        {
            steamTickDelay--;
            if(steamTickDelay < 1)
            {
                createSteamParticle(false);
                steamTickDelay = STEAM_TICK_DELAY_TIME;
            }

            if(autoDetect)
            {
                checkTickDelay--;
                if(checkTickDelay < 1)
                {
                    if(checkDetection())
                        steamBlast();
                    checkTickDelay = CHECK_TICK_DELAY_TIME;
                }
            }
        }

        if(steamBlastCount > 0)
        {
            steamBlastCount--;
            createSteamParticle(true);
        }

        if(extensionTickTime > 0)
        {
            extensionTickTime--;
            if(extensionTickTime < 1) {
                switch(extState) {
                    case RETRACTED: break;
                    case EXTENDING: extensionTickTime = WAIT_TIME; extState = ExtensionState.EXTENDED; break;
                    case EXTENDED: extensionTickTime = RETRACT_TIME; extState = ExtensionState.RETRACTING; break;
                    case RETRACTING: extensionTickTime = 0; extState = ExtensionState.RETRACTED; break;
                }
            }
        }
    }

    @Override
    public boolean rotateBlock(Direction side)
    {
        facing = side;
        return true;
    }

    @Override
    public boolean wrenchBlock()
    {
        if(autoDetect) {
            autoDetect = false;
            customWrenchMessage = "Auto-Detection: FALSE";
        } else {
            autoDetect = true;
            customWrenchMessage = "Auto-Detection: TRUE";
        }
        return true;
    }

    @Override
    public String getCustomWrenchMessage() {
        String output = customWrenchMessage;
        customWrenchMessage = "";
        return output;
    }

    public boolean isPulsed() { return pulsed; }
    public Direction getFacing() { return facing; }

    @Override
    public String getCustomInformationMessage() { return builtUpSteam + "/" + MAX_BUILT_UP_STEAM + " pU Built Up"; }

    private void createSteamParticle(boolean blast)
    {
        int rand = (int)(Math.random()*4);

        double posSide1 = 0.0;
        double velSide1 = 0.0;
        if(rand == 0 || rand == 2) {
            posSide1 = Math.random();
            if(blast) velSide1 = Math.random() * 3.0 - 1.5;
        } else if(rand == 1) {
            posSide1 = 1.0;
            if(blast) velSide1 = Math.random() * 0.5;
        } else
            if(blast) velSide1 = Math.random() * -0.5;

        double posSide2 = 0.0;
        double velSide2 = 0.0;
        if(rand == 1 || rand == 3) {
            posSide2 = Math.random();
            if(blast) velSide2 = Math.random() * 3.0 - 1.5;
        } else if(rand == 0) {
            posSide2 = 1.0;
            if(blast) velSide2 = Math.random() * 0.5;
        } else
            if(blast) velSide2 = Math.random() * -0.5;

        if (facing == Direction.UP)
            worldObj.spawnParticle("cloud", (xCoord + posSide1), (yCoord + 0.9), (zCoord + posSide2), velSide1, 0.0, velSide2);
        if (facing == Direction.DOWN)
            worldObj.spawnParticle("cloud", (xCoord + posSide1), (yCoord + 0.1), (zCoord + posSide2), velSide1, 0.0, velSide2);
        if (facing == Direction.EAST)
            worldObj.spawnParticle("cloud", (xCoord + 0.9), (yCoord + posSide1), (zCoord + posSide2), 0.0, velSide1, velSide2);
        if (facing == Direction.WEST)
            worldObj.spawnParticle("cloud", (xCoord + 0.1), (yCoord + posSide1), (zCoord + posSide2), 0.0, velSide1, velSide2);
        if (facing == Direction.SOUTH)
            worldObj.spawnParticle("cloud", (xCoord + posSide1), (yCoord + posSide2), (zCoord + 0.9), velSide1, velSide2, 0.0);
        if (facing == Direction.NORTH)
            worldObj.spawnParticle("cloud", (xCoord + posSide1), (yCoord + posSide2), (zCoord + 0.1), velSide1, velSide2, 0.0);
    }

    private void steamBlast()
    {
            double vel = 1.5 * ((double) builtUpSteam / (double) MAX_BUILT_UP_STEAM);
            if (facing == Direction.UP) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1.0, zCoord, xCoord + 1.0, yCoord + 2.0, zCoord + 1.0));
                for (Entity ent : entities)
                    ent.addVelocity(0.0, vel, 0.0);
            }

            if (facing == Direction.DOWN) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord, yCoord - 1.0, zCoord, xCoord + 1.0, yCoord, zCoord + 1.0));
                for (Entity ent : entities)
                    ent.addVelocity(0.0, -1.0 * vel, 0.0);
            }

            if (facing == Direction.EAST) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord + 1.0, yCoord, zCoord, xCoord + 2.0, yCoord + 1.0, zCoord + 1.0));
                for (Entity ent : entities)
                    ent.addVelocity(vel, 0.0, 0.0);
            }

            if (facing == Direction.WEST) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord - 1.0, yCoord, zCoord, xCoord, yCoord + 1.0, zCoord + 1.0));
                for (Entity ent : entities)
                    ent.addVelocity(-1.0 * vel, 0.0, 0.0);
            }

            if (facing == Direction.SOUTH) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord + 1.0, xCoord + 1.0, yCoord + 1.0, zCoord + 2.0));
                for (Entity ent : entities)
                    ent.addVelocity(0.0, 0.0, vel);
            }

            if (facing == Direction.NORTH) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord - 1.0, xCoord + 1.0, yCoord + 1.0, zCoord));
                for (Entity ent : entities)
                    ent.addVelocity(0.0, 0.0, -1.0 * vel);
            }

            builtUpSteam = 0;
            steamBlastCount = 10;
            extensionTickTime = EXTEND_TIME;
            extState = ExtensionState.EXTENDING;
    }

    private boolean checkDetection()
    {
        if (facing == Direction.UP) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1.0, zCoord, xCoord + 1.0, yCoord + 2.0, zCoord + 1.0));
            return entities.size() > 0;
        }

        if (facing == Direction.DOWN) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord, yCoord - 1.0, zCoord, xCoord + 1.0, yCoord, zCoord + 1.0));
            return entities.size() > 0;
        }

        if (facing == Direction.EAST) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord + 1.0, yCoord, zCoord, xCoord + 2.0, yCoord + 1.0, zCoord + 1.0));
            return entities.size() > 0;
        }

        if (facing == Direction.WEST) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord - 1.0, yCoord, zCoord, xCoord, yCoord + 1.0, zCoord + 1.0));
            return entities.size() > 0;
        }

        if (facing == Direction.SOUTH) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord + 1.0, xCoord + 1.0, yCoord + 1.0, zCoord + 2.0));
            return entities.size() > 0;
        }

        if (facing == Direction.NORTH) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord - 1.0, xCoord + 1.0, yCoord + 1.0, zCoord));
            return entities.size() > 0;
        }

        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Facing", facing.ID());
        compound.setInteger("BuiltUp", builtUpSteam);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.facing = Direction.fromID(compound.getInteger("Facing"));
        this.builtUpSteam = compound.getInteger("BuiltUp");
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeInt(facing.ID());
        data.writeInt(builtUpSteam);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        this.facing = Direction.fromID(data.readInt());
        builtUpSteam = data.readInt();
    }
}
