package me.nuty.minigamecore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.*;

public class MinigameHelpCommand extends AbstractSubCommand {
    private final String usage = "/minigame help";

    @Override
    public boolean call(CommandSender sender, Command command, String s, String[] args) {

        List<SubCommand> subCommands = new ArrayList<>(new MinigameCommand().getCommandMap().values());

        subCommands.sort(null);

        for(SubCommand cmd : subCommands) {
            sender.sendMessage(cmd.getUsage());
        }

        return true;
    }

    @Override
    public String getUsage() {
        return usage;
    }
}
