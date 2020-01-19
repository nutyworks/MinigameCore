package me.nuty.minigamecore.command;

import me.nuty.minigamecore.MinigameCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MinigameLeaveCommand extends AbstractSubCommand {

    private final String usage = "/minigame leave";

    @Override
    public boolean call(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) { // 플레이어면
            int gameId = MinigameCore.getInstance().getPlayerManager()
                    .getParticipatedGameByPlayer((Player) sender);
            if(gameId != -1) { // 게임에 참여하고 있으면
                MinigameCore.getInstance().getMinigameManager().getListedMinigameByID(gameId)
                        .moveToLobby((Player) sender);
                MinigameCore.getInstance().getMinigameManager().getListedMinigameByID(gameId)
                        .removeParticipant((Player) sender);

                sender.sendMessage(ChatColor.GREEN + "로비로 이동합니다.");
            } else {
                sender.sendMessage(ChatColor.RED + "이미 로비에 있습니다.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "이 명령어는 플레이어만 실행할 수 있습니다.");
        }

        return true;
    }

    @Override
    public String getUsage() {
        return usage;
    }
}
