package me.nuty.minigamecore.command;

import me.nuty.minigamecore.MinigameCore;
import me.nuty.minigamecore.minigame.IMinigame;
import me.nuty.minigamecore.minigame.MinigameStatus;
import org.bouncycastle.jcajce.provider.symmetric.ChaCha;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class MinigameJoinCommand extends AbstractSubCommand {

    private final String usage = "/minigame join <id>";

    @Override
    public boolean call(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) { // 플레이어면
            if (args.length > 1) { // id를 입력했으면
                if (args[1].matches("^[0-9]+$")) {
                    int id = Integer.parseInt(args[1]);
                    Map<Integer, IMinigame> listed = MinigameCore.getInstance()
                            .getMinigameManager().getListedMinigames();

                    if (listed.containsKey(id)) { // 미니게임이 있으면
                        if (MinigameCore.getInstance().getPlayerManager() // 참여하고 있는 게임이 없으면
                                .getParticipatedGameByPlayer((Player) sender) == -1) {
                            if(MinigameCore.getInstance().getMinigameManager().getListedMinigameByID(id).getStatus().equals(MinigameStatus.WAITING)
                                || MinigameCore.getInstance().getMinigameManager().getListedMinigameByID(id).getStatus().equals(MinigameStatus.READY)) {
                                if(MinigameCore.getInstance().getMinigameManager().getListedMinigameByID(id).getMaxPlayers()
                                        > MinigameCore.getInstance().getMinigameManager().getListedMinigameByID(id).getParticipants().size()) {
                                    sender.sendMessage(ChatColor.GREEN + "게임에 참여했습니다.");
                                    listed.get(id).addParticipant((Player) sender); // 플레이어 추가
                                } else
                                    sender.sendMessage(ChatColor.RED + "인원이 가득 차서 게임에 참여할 수 없습니다.");
                            } else
                                sender.sendMessage(ChatColor.RED + "이미 시작한 게임입니다.");
                        } else
                            sender.sendMessage(ChatColor.RED + "이미 다른 게임에 참여하고 있습니다. 나가려면 '/"
                                    + command.getName() + " leave'를 입력하세요.");
                    } else
                        sender.sendMessage(ChatColor.RED + "존재하지 않는 미니게임 ID입니다.");
                } else
                    sender.sendMessage(ChatColor.RED + args[1] + "은(는) 올바르지 않은 값입니다.");
            } else
                sender.sendMessage(ChatColor.RED + "/" + command.getName() + " join <ID>");
        } else
            sender.sendMessage(ChatColor.RED + "이 명령어는 플레이어만 실행할 수 있습니다.");

        return true;
    }

    @Override
    public String getUsage() {
        return usage;
    }
}
