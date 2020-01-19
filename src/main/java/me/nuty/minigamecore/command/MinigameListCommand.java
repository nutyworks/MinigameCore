package me.nuty.minigamecore.command;

import me.nuty.minigamecore.MinigameCore;
import me.nuty.minigamecore.minigame.IMinigame;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class MinigameListCommand extends AbstractSubCommand {

    private final String usage = "/minigame list";

    @Override
    public boolean call(CommandSender sender, Command command, String s, String[] args) {
        Map<Integer, IMinigame> listedMinigames = MinigameCore.getInstance()
                .getMinigameManager().getListedMinigames(); // 미니게임 목록 가져옴

        if (listedMinigames.size() > 0) { // 있으면
            sender.sendMessage(ChatColor.GREEN + "====== 미니게임 목록 ======");
            for (int key : listedMinigames.keySet()) { // 각각 출력
                IMinigame minigameListIter = MinigameCore.getInstance()
                        .getMinigameManager().getListedMinigameByID(key);
                sender.sendMessage(ChatColor.AQUA + "" + key + ". " + ChatColor.YELLOW + minigameListIter);
            }
        } else { // 없으면
            sender.sendMessage(ChatColor.RED + "현재 열려 있는 미니게임이 없습니다.");
        }

        return true;
    }

    @Override
    public String getUsage() {
        return usage;
    }
}
