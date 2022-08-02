package me.invis.cosmetics.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class PlayerUtil implements Listener {

    private final Plugin plugin;
    private final HashMap<Player, Location> lastLoc = new HashMap<>();
    private static final HashMap<Player, Boolean> playerMoveState = new HashMap<>();

    public PlayerUtil(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static boolean isMoving(Player player) {
        return playerMoveState.getOrDefault(player, false);
    }


    public void startChecking() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for(Player p : Bukkit.getOnlinePlayers()) {
                Location lastPlayerLoc = lastLoc.get(p);
                if(lastPlayerLoc != null) {
                    double distance = lastPlayerLoc.distance(p.getLocation());
                    if(distance == 0.0) {
                        playerMoveState.put(p, false);
                        continue;
                    }
                    else playerMoveState.put(p, true);
                }
                lastLoc.put(p, p.getLocation());
            }
        }, 20, 1);
    }

}
