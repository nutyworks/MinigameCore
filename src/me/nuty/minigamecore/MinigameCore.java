package me.nuty.minigamecore;

import me.nuty.minigamecore.command.MinigameCommand;
import me.nuty.minigamecore.event.PlayerQuit;
import me.nuty.minigamecore.minigame.MinigameManager;
import me.nuty.minigamecore.player.PlayerManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MinigameCore extends JavaPlugin {

    private static MinigameCore instance;
    private MinigameManager minigameManager;
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        instance = this;
        this.minigameManager = new MinigameManager();
        this.playerManager = new PlayerManager();

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
}
