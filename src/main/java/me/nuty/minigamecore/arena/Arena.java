package me.nuty.minigamecore.arena;

import com.sk89q.jnbt.NBTInputStream;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.MCEditSchematicReader;
import com.sk89q.worldedit.math.BlockVector3;
import javafx.util.Pair;
import me.nuty.minigamecore.MinigameCore;
import me.nuty.minigamecore.util.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

        Vector v = new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()); // loc is your location variable.
        MCEditSchematicReader schemaReader;
        Clipboard clipboard;

        //EditSession es = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new (loc.getWorld()), WorldEdit.getInstance().getConfiguration().maxChangeLimit)
        try {
            schemaReader = new MCEditSchematicReader(new NBTInputStream(new FileInputStream(MinigameCore.getInstance().getDataFolder() + "\\schemaTest.schem")));
            clipboard = schemaReader.read();
            clipboard.setOrigin(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ()));
            clipboard.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
                /*
        SchematicFormat format = SchematicFormat.getFormat(file);
        CuboidClipboard cc = format.load(file);
        cc.paste(es, v, false);

                 */

    }

    public void delete() {
        BlockUtil.fillBlocks(totalArea.getKey(), totalArea.getValue(), Material.AIR);
    }
}
