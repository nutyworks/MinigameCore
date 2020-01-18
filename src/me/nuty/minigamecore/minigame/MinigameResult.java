package me.nuty.minigamecore.minigame;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MinigameResult {
    private List<Player> winners = new ArrayList<>();
    private long endTime;
    private int exitCode;

    public MinigameResult() {
        this.exitCode = -1;
    }

    public void setWinners(List<Player> winners) {
        this.winners = winners;
    }

    public void addWinners(Player winner) {
        winners.add(winner);
    }

    public List<Player> getWinners() {
        return winners;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }
}
