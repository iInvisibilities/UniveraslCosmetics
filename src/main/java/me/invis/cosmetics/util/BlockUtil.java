package me.invis.cosmetics.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockUtil {
    public static Block getFirstBlockUnderLocation(Location location) {
        if(location.getBlock() == null || location.getBlock().getType() == null || location.getBlock().getType() == Material.AIR) {
            return getFirstBlockUnderLocation(location.subtract(0, 1, 0));
        }

        return location.getBlock();
    }
}
