package me.nuty.minigamecore.scoreboard;

import me.nuty.minigamecore.MinigameCore;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class MinigameScoreboardManager {
    private Scoreboard sb;
    private Objective objective;
    private List<Team> scoreboardTexts = new ArrayList<>();
    private int lines;


    public MinigameScoreboardManager(String displayName, int lines) {
        this.sb = Objects.requireNonNull(MinigameCore.getInstance().getServer().getScoreboardManager()).getNewScoreboard();
        objective = sb.registerNewObjective("new scoreboard", "dummy", displayName);

        if (lines > 16)
            lines = 16;
        else if (lines < 1)
            lines = 1;

        this.lines = lines;

        for (int i = 0; i < 16; i++) {
            Team team = sb.registerNewTeam(Integer.toString(i, 16));
            String lineSeperator = "§" + Integer.toString(i, 16) + "§r";

            scoreboardTexts.add(team);

            team.addEntry(lineSeperator);
        }

        for (String entry : sb.getEntries()) {
            sb.resetScores(entry);
        }

        for (int i = 0; i < lines; i++) {
            scoreboardTexts.get(i).setSuffix("");
            objective.getScore("§" + Integer.toString(i, 16) + "§r").setScore(i + 1);
        }
    }

    public Scoreboard getScoreboard() {
        return sb;
    }

    public void setText(int line, String text) {
        scoreboardTexts.get(lines - line).setSuffix(text);
    }

    public void setSlot(DisplaySlot slot) {
        objective.setDisplaySlot(slot);
    }

    public void display(Player p) {
        p.setScoreboard(sb);
    }
}
