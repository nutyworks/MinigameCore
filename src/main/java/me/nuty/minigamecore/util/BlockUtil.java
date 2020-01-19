package me.nuty.minigamecore.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class BlockUtil {

    private static World world = Bukkit.getWorlds().get(0);

    public static void fillBlocks(Location location, Vector vector, Material material) {
        fillBlocks(location.getBlockX(), location.getBlockY(), location.getBlockZ(),
                vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), material);
    }

    public static void fillBlocks(int x, int y, int z, int dx, int dy, int dz, Material material) {
        int i, j, k;

        Location firstLocation = new Location(world, x, y, z);

        for (i = 0; i <= dx; i++) {
            for (j = 0; j <= dy; j++) {
                for (k = 0; k <= dz; k++) {
                    firstLocation.clone().add(i, j, k).getBlock().setType(material);
                }
            }
        }
    }
}
