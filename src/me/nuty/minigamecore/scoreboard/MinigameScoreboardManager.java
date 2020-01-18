package me.nuty.minigamecore.scoreboard;

import me.nuty.minigamecore.MinigameCore;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class MinigameScoreboardManager {
    private Scoreboard sb;
    private Objective objective;
    private List<Team> scoreboardTexts = new ArrayList<>();


    public MinigameScoreboardManager(String displayName) {
        this.sb = Objects.requireNonNull(MinigameCore.getInstance().getServer().getScoreboardManager()).getNewScoreboard();
        objective = sb.registerNewObjective("new scoreboard", "dummy", displayName);

        for (int i = 0; i < 16; i++) {
            Team team = sb.registerNewTeam(Integer.toString(i, 16));
            String lineSeperator = "§" + Integer.toString(i, 16) + "§r";

            scoreboardTexts.add(team);

            team.addEntry(lineSeperator);
        }
    }

    public void setLines(int line) {
        for (String entry : sb.getEntries()) {
            sb.resetScores(entry);
        }

        if (line > 16)
            line = 16;
        else if (line < 1)
            line = 1;

        for (int i = 0; i < line; i++) {
            scoreboardTexts.get(i).setSuffix("");
        }
    }

    public Scoreboard getScoreboard() {
        return sb;
    }

    public void setText(int line, String text) {
        scoreboardTexts.get(line).setSuffix(text);
    }

    public void display(Player p) {
        p.setScoreboard(sb);
    }
}
