package me.nuty.minigamecore.player;

import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {
    private Map<Player, Integer> participated = new HashMap<>();

    public void addParticipated(Player player, int game) {
        participated.put(player, game);
    }
    public void removeParticipated(Player player) {
        participated.remove(player);
    }

    public Map<Player, Integer> getParticipated() {
        return participated;
    }

    public int getParticipatedGameByPlayer(Player player) {
        return participated.getOrDefault(player, -1);
    }
}
