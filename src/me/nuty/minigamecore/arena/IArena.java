package me.nuty.minigamecore.arena;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public interface IArena {

    /**
     * Returns the start location of arena.
     *
     * @return location
     */
    Location getLocation();

    /**
     * Returns the size of arena from start location
     *
     * @return size
     */
    Vector getSize();

    /**
     * Deletes the arena.
     */
    void delete();


}
