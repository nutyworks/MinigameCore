package me.nuty.minigamecore.command;

import me.nuty.minigamecore.MinigameCore;
import me.nuty.minigamecore.minigame.IMinigame;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MinigameDestroyCommand extends AbstractSubCommand {

    private final String usage = "/minigame destroy <id>";

    @Override
    public boolean call(CommandSender sender, Command command, String s, String[] args) {
        if (args.length > 1) {
            if (args[1].matches("^[0-9]+$")) {
                int id = Integer.parseInt(args[1]);
                IMinigame minigame = MinigameCore.getInstance().getMinigameManager().getListedMinigameByID(id);

                if (minigame != null) {
                    minigame.destroy(true);
                    sender.sendMessage(ChatColor.GREEN + "미니게임을 강제로 종료했습니다.");
                } else {
                    sender.sendMessage(ChatColor.RED + "존재하지 않는 미니게임 ID입니다.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + args[1] + "은(는) 올바르지 않은 값입니다.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "/" + command.getName() + "destroy <ID>");
        }

        return true;
    }

    @Override
    public String getUsage() {
        return usage;
    }
}
