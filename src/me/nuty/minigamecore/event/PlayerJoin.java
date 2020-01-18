package me.nuty.minigamecore.event;

import me.nuty.minigamecore.MinigameCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        MinigameCore.getInstance().getLobbyScoreboard().display(event.getPlayer());
    }
}
