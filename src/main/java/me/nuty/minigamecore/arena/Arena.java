package me.nuty.minigamecore.arena;

import javafx.util.Pair;
import me.nuty.minigamecore.util.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.io.File;

public class Arena {

    private Pair<Location, Vector> totalArea;
    private Pair<Location, Vector> allowedArea;

    public Arena(Pair<Location, Vector> totalArea, Pair<Location, Vector> allowedArea) {
        this.totalArea = totalArea;
        this.allowedArea = allowedArea;
    }

    public Pair<Location, Vector> getTotalArea;

    public Pair<Location, Vector> getAllowedArea() {
        return allowedArea;
    }

    public void setAllowedArea(Pair<Location, Vector> allowedArea) {
        this.allowedArea = allowedArea;
    }

    public void create(File schematicFile, Location loc) {



    }

    public void delete() {
        BlockUtil.fillBlocks(totalArea.getKey(), totalArea.getValue(), Material.AIR);
    }
}
