package me.invis.cosmetics.util;

import me.invis.cosmetics.Cosmetics;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleUtil {
    public static void playCrown(Player player, Particle particle) {

        Location location = player.getLocation();

        for (int degree = 0; degree < 360; degree++) {
            double radians = Math.toRadians(degree);
            double x = Math.cos(radians) * .5;
            double z = Math.sin(radians) * .5;
            location.add(x,0,z);
            player.spawnParticle(particle, location.clone().add(0, 2, 0), 1, 0, 0, 0, 0);
            location.subtract(x,0,z);
        }
    }

    public static <DATA> void createSpiral(Player player, Particle particle, double height, int period, double radius, DATA data) {
        createSpiral(player, particle, null, height, period, radius, data);
    }

    public static <DATA> void createSpiral(Player player, Particle particle, Particle secondParticle, double height, int period, double radius, DATA data) {
        Location loc = player.getLocation();

        new BukkitRunnable() {
            double y = height;

            @Override
            public void run() {
                double x = radius * Math.cos(y);
                double z = radius * Math.sin(y);
                player.spawnParticle(particle, loc.clone().add(x, y, z), 1, 0, 0, 0, 0, data);
                if(secondParticle != null)
                    player.spawnParticle(secondParticle, loc.clone().add(0, y, 0), 1, 0, 0, 0, 0, data);

                y -= .1;
                if (y < 0) this.cancel();
            }
        }.runTaskTimer(Cosmetics.getInstance(), 0, period);
    }
}
