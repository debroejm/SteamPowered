package com.majorpotato.steampowered.tileentity.machine.steam;


import com.majorpotato.steampowered.util.Direction;
import com.majorpotato.steampowered.util.TieredMaterial;
import com.majorpotato.steampowered.util.machine.IWrenchUser;
import com.majorpotato.steampowered.util.machine.MachineType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class TEntitySteamVent extends TEntitySteam implements IWrenchUser {

    private int builtUpSteam = 0;
    private static final int MAX_BUILT_UP_STEAM = 5000;
    private Direction facing = Direction.UP;

    private boolean pulsed = false;

    private static final int STEAM_TICK_DELAY_TIME = 60, CHECK_TICK_DELAY_TIME = 10;
    private int steamTickDelay = STEAM_TICK_DELAY_TIME;
    private int checkTickDelay = CHECK_TICK_DELAY_TIME;
    private int steamBlastCount = 0;

    private static final int BLAST_DAMAGE = 16;

    private boolean autoDetect = true;

    private String customWrenchMessage = "";

    public TEntitySteamVent() {
        super();
        sendUpdates = true;
        machineType = MachineType.CONSUMER;
        material = TieredMaterial.Steel;
    }

    @Override
    public boolean canSideConnect(Direction side) { return true; }

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
            if(!pulsed && builtUpSteam > MAX_BUILT_UP_STEAM/2) {
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
            createSteamParticle(true);
            createSteamParticle(true);
        }
    }

    @Override
    public boolean rotateBlock(Direction side)
    {
        facing = side;
        return true;
    }

    @Override
    public String getCustomWrenchMessage() {
        String output = customWrenchMessage;
        customWrenchMessage = "";
        return output;
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

    public boolean isPulsed() { return pulsed; }
    public Direction getFacing() { return facing; }

    @Override
    public String getCustomInformationMessage() { return builtUpSteam + "/" + MAX_BUILT_UP_STEAM + " pU Built Up"; }

    private void createSteamParticle(boolean blast)
    {
            double velFor = 0.0, velSide1 = 0.0, velSide2 = 0.0;
            if (blast) {
                velFor = Math.random() / 2;
                velSide1 = Math.random() * 0.4 - 0.2;
                velSide2 = Math.random() * 0.4 - 0.2;
            }

            if (facing == Direction.UP)
                worldObj.spawnParticle("cloud", (xCoord + Math.random()), (yCoord + 1.0), (zCoord + Math.random()), velSide1, velFor, velSide2);
            if (facing == Direction.DOWN)
                worldObj.spawnParticle("cloud", (xCoord + Math.random()), (double) (yCoord), (zCoord + Math.random()), velSide1, velFor * -1.0, velSide2);
            if (facing == Direction.EAST)
                worldObj.spawnParticle("cloud", (xCoord + 1.0), (yCoord + Math.random()), (zCoord + Math.random()), velFor, velSide1, velSide2);
            if (facing == Direction.WEST)
                worldObj.spawnParticle("cloud", (double) (xCoord), (yCoord + Math.random()), (zCoord + Math.random()), velFor * -1.0, velSide1, velSide2);
            if (facing == Direction.SOUTH)
                worldObj.spawnParticle("cloud", (xCoord + Math.random()), (yCoord + Math.random()), (zCoord + 1.0), velSide1, velSide2, velFor);
            if (facing == Direction.NORTH)
                worldObj.spawnParticle("cloud", (xCoord + Math.random()), (yCoord + Math.random()), (double) (zCoord), velSide1, velSide2, velFor * -1.0);
    }

    private void steamBlast()
    {
            double vel = 1.5 * ((double) builtUpSteam / (double) MAX_BUILT_UP_STEAM);
            if (facing == Direction.UP) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1.0, zCoord, xCoord + 1.0, yCoord + 3.0, zCoord + 1.0));
                for (Entity ent : entities)
                    ent.attackEntityFrom(DamageSource.generic, BLAST_DAMAGE);
            }

            if (facing == Direction.DOWN) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord, yCoord - 2.0, zCoord, xCoord + 1.0, yCoord, zCoord + 1.0));
                for (Entity ent : entities)
                    ent.attackEntityFrom(DamageSource.generic, BLAST_DAMAGE);
            }

            if (facing == Direction.EAST) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord + 1.0, yCoord, zCoord, xCoord + 3.0, yCoord + 1.0, zCoord + 1.0));
                for (Entity ent : entities)
                    ent.attackEntityFrom(DamageSource.generic, BLAST_DAMAGE);
            }

            if (facing == Direction.WEST) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord - 2.0, yCoord, zCoord, xCoord, yCoord + 1.0, zCoord + 1.0));
                for (Entity ent : entities)
                    ent.attackEntityFrom(DamageSource.generic, BLAST_DAMAGE);
            }

            if (facing == Direction.SOUTH) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord + 1.0, xCoord + 1.0, yCoord + 1.0, zCoord + 3.0));
                for (Entity ent : entities)
                    ent.attackEntityFrom(DamageSource.generic, BLAST_DAMAGE);
            }

            if (facing == Direction.NORTH) {
                List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                        AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord - 2.0, xCoord + 1.0, yCoord + 1.0, zCoord));
                for (Entity ent : entities)
                    ent.attackEntityFrom(DamageSource.generic, BLAST_DAMAGE);
            }

            builtUpSteam = 0;
            steamBlastCount = 10;
    }

    private boolean checkDetection()
    {
        if (facing == Direction.UP) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1.0, zCoord, xCoord + 1.0, yCoord + 3.0, zCoord + 1.0));
            return entities.size() > 0;
        }

        if (facing == Direction.DOWN) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord, yCoord - 2.0, zCoord, xCoord + 1.0, yCoord, zCoord + 1.0));
            return entities.size() > 0;
        }

        if (facing == Direction.EAST) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord + 1.0, yCoord, zCoord, xCoord + 3.0, yCoord + 1.0, zCoord + 1.0));
            return entities.size() > 0;
        }

        if (facing == Direction.WEST) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord - 2.0, yCoord, zCoord, xCoord, yCoord + 1.0, zCoord + 1.0));
            return entities.size() > 0;
        }

        if (facing == Direction.SOUTH) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord + 1.0, xCoord + 1.0, yCoord + 1.0, zCoord + 3.0));
            return entities.size() > 0;
        }

        if (facing == Direction.NORTH) {
            List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class,
                    AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord - 2.0, xCoord + 1.0, yCoord + 1.0, zCoord));
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
