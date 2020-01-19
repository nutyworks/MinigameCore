package me.nuty.minigamecore.event;

import me.nuty.minigamecore.util.Constant;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(event.getTo().getY() < 70)
            event.getPlayer().teleport(Constant.LOBBY);
    }
}
