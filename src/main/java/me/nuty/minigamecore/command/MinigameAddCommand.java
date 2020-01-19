package me.nuty.minigamecore.command;

import me.nuty.minigamecore.MinigameCore;
import me.nuty.minigamecore.minigame.IMinigame;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MinigameAddCommand extends AbstractSubCommand {

    private final String usage = "/minigame add <identifier>";

    @Override
    public boolean call(CommandSender sender, Command command, String s, String[] args) {
        IMinigame minigame;

        minigame = MinigameCore.getInstance().getMinigameManager().listMinigame(args[1]); // args[1] id인 미니게임을 가져옴
        if (minigame != null) { // 추가 성공
            sender.sendMessage(ChatColor.GREEN + "성공 테스트 메시지 " + minigame.toString());
        } else { // 실패
            sender.sendMessage(ChatColor.RED + "미니게임 " + args[1] + "을(를) 찾을 수 없습니다.");
        }

        return true;
    }

    @Override
    public String getUsage() {
        return usage;
    }
}
