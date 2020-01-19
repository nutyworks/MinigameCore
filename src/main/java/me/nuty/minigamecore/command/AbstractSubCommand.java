package me.nuty.minigamecore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class AbstractSubCommand implements SubCommand {

    abstract public boolean call(CommandSender sender, Command command, String s, String[] args);

    abstract public String getUsage();

    @Override
    public int compareTo(Object o) {
        return this.getUsage().compareTo(((SubCommand) o).getUsage());
    }
}
