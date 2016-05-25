package com.majorpotato.steampowered.commands;

import com.majorpotato.steampowered.util.DebugType;
import com.majorpotato.steampowered.util.artifice.AuraNodeNet;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class CommandDebug implements ICommand {

    private List typeCompletion;
    private List aliases;
    public CommandDebug() {
        this.aliases = new ArrayList();
        this.aliases.add("spd");
        this.aliases.add("spdebug");

        typeCompletion = new ArrayList();
        for(DebugType type : DebugType.values()) {
            typeCompletion.add(type.getName());
        }
    }

    @Override
    public String getCommandName() { return "spdebug"; }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/spDebug <type> [arg0] [arg1] ...";
    }

    @Override
    public List getCommandAliases() { return this.aliases; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length < 1) {
            sender.addChatMessage(new ChatComponentText("§cInvalid Format; Requires a Debug category"));
            return;
        }

        DebugType debug = DebugType.getDebugTypeFromName(args[0]);
        if(debug == DebugType.UNKNOWN) {
            sender.addChatMessage(new ChatComponentText("§cUnknown Debug category"));
            return;
        }

        switch(debug) {
            case AURA:
                if(sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)sender;
                    int chunkX = ((int)player.posX) >> 4;
                    int chunkZ = ((int)player.posZ) >> 4;
                    System.out.println("ChunkPos:"+chunkX+","+chunkZ);
                    float[] color = AuraNodeNet.getNetForDimension(player.dimension).getAuraAt(chunkX,chunkZ);
                    sender.addChatMessage(new ChatComponentText("Aura @ §e("+player.posX+","+player.posY+","+player.posZ+")§f is §b("+color[0]+","+color[1]+","+color[2]+")§f"));
                    return;
                } else {
                    sender.addChatMessage(new ChatComponentText("Must Be a Player To Use Command Like This"));
                    return;
                }
            default:
                sender.addChatMessage(new ChatComponentText("§cUnknown Debug category"));
                return;
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) { return true; }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 1) return typeCompletion;
        else return null;
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
