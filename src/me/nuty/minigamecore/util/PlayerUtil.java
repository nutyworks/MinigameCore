package me.nuty.minigamecore.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUtil {
    public static Player getPlayerByUUID(UUID uuid) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId().equals(uuid))
                return player;
        }

        return null;
    }
}
