package me.nuty.minigamecore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface SubCommand extends Comparable {
    boolean call(CommandSender sender, Command command, String s, String[] args);
    String getUsage();
}
