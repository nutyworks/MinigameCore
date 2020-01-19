package me.nuty.minigamecore.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

/*
 /game add <minigame name>: int return gameid;
 /game join <game id>: void
 /game forcestop <game id>
 /game forcestart <game id>
*/

public class MinigameCommand implements CommandExecutor {


    private final Map<String, SubCommand> commandMap = new HashMap<String, SubCommand>() {
        {
            put("add", new MinigameAddCommand());
            put("destroy", new MinigameDestroyCommand());
            put("help", new MinigameHelpCommand());
            put("join", new MinigameJoinCommand());
            put("leave", new MinigameLeaveCommand());
            put("list", new MinigameListCommand());
        }
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "알 수 없는 명령입니다. 도움말을 보려면 '/"
                    + command.getName() + " help'를 입력하세요.");
            return true;
        }

        SubCommand subCommand = commandMap.getOrDefault(args[0], null);

        if (subCommand != null)
            subCommand.call(sender, command, s, args);
        else
            sender.sendMessage(ChatColor.RED + "알 수 없는 명령입니다. 도움말을 보려면 '/"
                    + command.getName() + " help'를 입력하세요.");

        return true;
    }

    public Map<String, SubCommand> getCommandMap() {
        return commandMap;
    }
}
