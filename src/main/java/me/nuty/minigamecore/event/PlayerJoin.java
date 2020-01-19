package me.nuty.minigamecore.event;

import me.nuty.minigamecore.MinigameCore;
import me.nuty.minigamecore.util.Constant;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        event.getPlayer().setInvulnerable(true);
        event.getPlayer().setFlying(false);
        event.getPlayer().setAllowFlight(false);
        event.getPlayer().setHealth(20);
        event.getPlayer().setSaturation(20);
        event.getPlayer().teleport(Constant.LOBBY);
        event.getPlayer().setGameMode(GameMode.ADVENTURE);

        MinigameCore.getInstance().getLobbyScoreboard().display(event.getPlayer());
    }
}
