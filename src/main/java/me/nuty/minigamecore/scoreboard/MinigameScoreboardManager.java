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


    public MinigameScoreboardManager(String displayName) {
        this.sb = Objects.requireNonNull(MinigameCore.getInstance().getServer().getScoreboardManager()).getNewScoreboard();
        objective = sb.registerNewObjective("new scoreboard", "dummy", displayName);
        this.lines = 1;

        for (int i = 0; i < 16; i++) {
            Team team = sb.registerNewTeam(Integer.toString(i, 15));
            String lineSeperator = "§" + Integer.toString(i, 15) + "§r";

            scoreboardTexts.add(team);

            team.addEntry(lineSeperator);
        }
    }

    public void setLines(int lines) {
        if (lines > 15)
            lines = 15;
        else if (lines < 1)
            lines = 1;

        for (int i = 0; i < lines; i++) {
            scoreboardTexts.get(i).setSuffix("");
            objective.getScore("§0§r").setScore(1);
        }

        if (this.lines > lines) { // 기존보다 작으면 라인 제거 8, 6 -> remove 7 to 8
            for(int i = lines; i < this.lines; i++) {
                sb.resetScores("§" + Integer.toString(i, 16) + "§r");
            }
        } else if(this.lines < lines) { // 기존보다 크면 라인 추가 8, 6 -> add 7 to 8
            for(int i = this.lines; i < lines; i++) {
                objective.getScore("§" + Integer.toString(i, 16) + "§r").setScore(i + 1);
            }
        }

        this.lines = lines;
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
