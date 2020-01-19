package me.nuty.minigamecore;

import me.nuty.minigamecore.command.MinigameCommand;
import me.nuty.minigamecore.event.PlayerJoin;
import me.nuty.minigamecore.event.PlayerMove;
import me.nuty.minigamecore.event.PlayerQuit;
import me.nuty.minigamecore.event.TabComplete;
import me.nuty.minigamecore.minigame.IMinigame;
import me.nuty.minigamecore.minigame.MinigameManager;
import me.nuty.minigamecore.minigame.MinigameStatus;
import me.nuty.minigamecore.player.PlayerManager;
import me.nuty.minigamecore.scoreboard.MinigameScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MinigameCore extends JavaPlugin {

    private static MinigameCore instance;
    private MinigameManager minigameManager;
    private PlayerManager playerManager;
    private MinigameScoreboardManager lobbyScoreboard;

    @Override
    public void onEnable() {
        instance = this;
        this.minigameManager = new MinigameManager();
        this.playerManager = new PlayerManager();

        this.lobbyScoreboard = new MinigameScoreboardManager(ChatColor.YELLOW + "" + ChatColor.BOLD + "1fi.kro.kr");
        lobbyScoreboard.setSlot(DisplaySlot.SIDEBAR);

        new BukkitRunnable() {
            @Override
            public void run() {
                Map<Integer, IMinigame> listedMinigames = minigameManager.getListedMinigames();
                lobbyScoreboard.setLines(5 + (listedMinigames.size() > 0 ? listedMinigames.size() - 1 : 0));
                lobbyScoreboard.setText(2, ChatColor.GREEN + "/minigame join <id>");

                int count = 0;
                if (listedMinigames.size() == 0) {
                    lobbyScoreboard.setText(4, ChatColor.RED + "열린 미니게임이 없음");
                } else {
                    for (int id : listedMinigames.keySet()) {
                        if (count >= 11) break;
                        IMinigame game = listedMinigames.get(id);
                        String text = ChatColor.YELLOW + "" + ChatColor.BOLD + "" + id + ". "
                                + ChatColor.AQUA + game.getName() + " "
                                + ChatColor.GRAY + game.getParticipants().size() + "/"
                                + game.getMaxPlayers() + " ";
                        if(game.getStatus().equals(MinigameStatus.READY))
                            text += ChatColor.YELLOW + "" + game.getStartLeftTime() + "s";
                        else if(game.getStatus().equals(MinigameStatus.STARTED))
                            text += ChatColor.YELLOW + "시작됨";
                        else if(game.getStatus().equals(MinigameStatus.ENDED))
                            text += ChatColor.YELLOW + "종료됨";
                        lobbyScoreboard.setText(4 + count, text);
                        count++;
                    }
                }
            }
        }.runTaskTimer(this, 0, 20L);

        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), instance);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), instance);
        this.getServer().getPluginManager().registerEvents(new PlayerMove(), instance);
        this.getServer().getPluginManager().registerEvents(new TabComplete(), instance);

        PluginCommand minigameCommand = this.getCommand("minigame");
        if (minigameCommand != null)
            minigameCommand.setExecutor(new MinigameCommand());

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(lobbyScoreboard.getScoreboard());
        }

        System.out.println("MinigameCore enabled");
    }

    @Override
    public void onDisable() {
        for (Integer key : minigameManager.getListedMinigames().keySet()) {
            minigameManager.getListedMinigameByID(key).destroy(true);
            System.out.println("Minigame #" + key + " successfully destroyed.");
        }
        System.out.println("MinigameCore disabled");
    }

    public MinigameManager getMinigameManager() {
        return minigameManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public static MinigameCore getInstance() {
        return instance;
    }

    public MinigameScoreboardManager getLobbyScoreboard() {
        return lobbyScoreboard;
    }
}
