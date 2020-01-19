package me.nuty.minigamecore.arena;

import javafx.util.Pair;
import me.nuty.minigamecore.MinigameCore;
import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Arena {

    private Pair<Vector, Vector> allowedArea;
    private File sourceFolder;
    private int id;

    private World world;

    public Arena(File sourceFolder, Pair<Vector, Vector> allowedArea, int id) {
        this.allowedArea = allowedArea;
        this.sourceFolder = sourceFolder;
        this.id = id;
    }

    public Pair<Vector, Vector> getAllowedArea() {
        return allowedArea;
    }

    public void setAllowedArea(Pair<Vector, Vector> allowedArea) {
        this.allowedArea = allowedArea;
    }

    public void create() {

        File targetFolder = new File(MinigameCore.getInstance().getDataFolder().getParentFile().getParentFile(), "mgw" + id);

        copyWorld(sourceFolder, targetFolder);

        world = Bukkit.createWorld(new WorldCreator("mgw" + id));

    }

    private void copyWorld(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("advancements", "datapacks", "DIM1", "DIM-1", "playerdata", "stats", "session.lock", "uid.dat", "session.dat"));
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists())
                        target.mkdirs();
                    String[] files = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        new BukkitRunnable() {
            @Override
            public void run() {
                File path = world.getWorldFolder();
                Bukkit.getServer().unloadWorld(world, true);
                deleteWorld(path);
            }
        }.runTaskLater(MinigameCore.getInstance(), 100L);
    }

    private void deleteWorld(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteWorld(file);
                    file.delete();
                } else {
                    file.delete();
                }
            }
            path.delete();
        }
    }

    public World getWorld() {
        return world;
    }
}
