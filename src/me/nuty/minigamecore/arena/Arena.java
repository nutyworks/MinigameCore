package me.nuty.minigamecore.arena;

import me.nuty.minigamecore.util.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Arena implements IArena {

    private Location location;
    private Vector vector;

    public Arena(Location location, Vector vector) {
        this.location = location;
        this.vector = vector;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Vector getSize() {
        return vector;
    }

    @Override
    public void delete() {
        BlockUtil.fillBlocks(location, vector, Material.AIR);
    }
}
