package me.nuty.minigamecore;

import me.nuty.minigamecore.command.MinigameCommand;
import me.nuty.minigamecore.event.PlayerJoin;
import me.nuty.minigamecore.event.PlayerQuit;
import me.nuty.minigamecore.minigame.MinigameManager;
import me.nuty.minigamecore.player.PlayerManager;
import me.nuty.minigamecore.scoreboard.MinigameScoreboardManager;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

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

        this.lobbyScoreboard = new MinigameScoreboardManager("Lobby scorebord test", 15);
        lobbyScoreboard.setText(1, "Hello world");
        lobbyScoreboard.setText(10, "this is line 10");
        lobbyScoreboard.setText(7, ChatColor.AQUA  + "chatcolor test");
        lobbyScoreboard.setSlot(DisplaySlot.SIDEBAR);

        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), instance);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), instance);

        PluginCommand minigameCommand = this.getCommand("minigame");
        if (minigameCommand != null)
            minigameCommand.setExecutor(new MinigameCommand());

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
