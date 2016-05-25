package com.majorpotato.steampowered.commands;

import com.majorpotato.steampowered.util.FormatHelper;
import com.majorpotato.steampowered.util.artifice.AuraNode;
import com.majorpotato.steampowered.util.artifice.AuraNodeNet;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class CommandAura implements ICommand {

    private List aliases;
    public CommandAura() {
        this.aliases = new ArrayList();
        this.aliases.add("aura");
        this.aliases.add("spaura");
    }

    @Override
    public String getCommandName() { return "aura"; }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        if(sender.canCommandSenderUseCommand(2, sender.getCommandSenderName())) {
            return "/aura [chunkX] [chunkZ] [set <auraR> <auraG> <auraB>]";
        }

        return "/aura [chunkX] [chunkZ]";
    }

    @Override
    public List getCommandAliases() { return this.aliases; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        boolean set = false;
        boolean invalid = false;
        int chunkX = 0, chunkZ = 0;
        float r = 0.0F, g = 0.0F, b = 0.0F;

        if(sender instanceof EntityPlayer) {
            if (args.length == 0) {
                    chunkX = ((int) ((EntityPlayer) sender).posX) >> 4;
                    chunkZ = ((int) ((EntityPlayer) sender).posZ) >> 4;
            } else if (args.length == 2) {
                chunkX = Integer.parseInt(args[0]);
                chunkZ = Integer.parseInt(args[1]);
            } else if (args.length == 4) {
                    if (sender.canCommandSenderUseCommand(2, sender.getCommandSenderName())) {
                        if (args[0].equals("set")) {
                            set = true;
                            chunkX = ((int) ((EntityPlayer) sender).posX) >> 4;
                            chunkZ = ((int) ((EntityPlayer) sender).posZ) >> 4;
                            r = Float.parseFloat(args[1]);
                            g = Float.parseFloat(args[2]);
                            b = Float.parseFloat(args[3]);
                        } else {
                            invalid = true;
                        }
                    } else {
                        invalid = true;
                    }
            } else if (args.length == 6) {
                if (sender.canCommandSenderUseCommand(2, sender.getCommandSenderName())) {
                    if (args[2].equals("set")) {
                        set = true;
                        chunkX = Integer.parseInt(args[0]);
                        chunkZ = Integer.parseInt(args[1]);
                        r = Float.parseFloat(args[3]);
                        g = Float.parseFloat(args[4]);
                        b = Float.parseFloat(args[5]);
                    } else {
                        invalid = true;
                    }
                } else {
                    invalid = true;
                }
            } else {
                invalid = true;
            }
        } else {
            sender.addChatMessage(new ChatComponentText("§cUsage of the command like this is client-side only"));
            return;
        }

        if(invalid) {
            sender.addChatMessage(new ChatComponentText("§cInvalid Format;"));
            sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
            return;
        }

        AuraNode node = AuraNodeNet.getNetForDimension(((EntityPlayer)sender).dimension).getNodeAt(chunkX, chunkZ);

        if(set) {
            node.setColor(r,g,b);
            sender.addChatMessage(new ChatComponentText("Aura at Chunk §e( "+chunkX+", "+chunkZ+" )§f Set to " + FormatHelper.formatColor3f(r, g, b) ));
        } else {
            float[] color = node.getColor();
            sender.addChatMessage(new ChatComponentText("Aura at Chunk §e( "+chunkX+", "+chunkZ+" )§f is " + FormatHelper.formatColor3f(color[0], color[1], color[2]) ));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.canCommandSenderUseCommand(2, sender.getCommandSenderName());
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 0 || args.length == 2) {
            ArrayList<String> tList = new ArrayList<String>();
            tList.add("set");
            return tList;
        }
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int i) { return false; }

    @Override
    public int compareTo(Object o) {
        if(o instanceof ICommand) {
            return getCommandName().compareTo(((ICommand)o).getCommandName());
        } else return 0;
    }
}
