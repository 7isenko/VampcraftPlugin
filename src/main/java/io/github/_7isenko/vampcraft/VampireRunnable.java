package io.github._7isenko.vampcraft;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class VampireRunnable extends BukkitRunnable {
    private int period;
    private ArrayList<Player> offList = new ArrayList<>();

    public VampireRunnable(int period) {
        this.period = period;
        this.runTaskTimer(Vampcraft.plugin, 0, period);
    }

    @Override
    public void run() {
        if (Vampcraft.vampires.isEmpty())
            return;

        ArrayList<Player> players = Vampcraft.vampires;

        players.forEach(player -> {
            if (!player.isOnline()) return;
            World world = player.getWorld();
            Location location = player.getLocation();
            if (world.getEnvironment() == World.Environment.NORMAL && world.getTime() <= 12566) {
                // day logic
                if (world.getHighestBlockAt(location).getY() <= location.getY()) {
                    player.setFireTicks(period);
                    for (PotionEffect effect : Vampcraft.badPotionEffects)
                        player.addPotionEffect(effect);
                    return;
                }
                // check caving during day
                if (location.getY() > 60 || (location.getY() > 50 && world.getHighestBlockAt(location).getType() == Material.WATER))
                    return;
            }
            if (offList.contains(player)) {
                offList.remove(player);
                return;
            }

            // night or caving logic
            for (PotionEffect effect : Vampcraft.goodPotionEffects)
                player.addPotionEffect(effect);
            if (player.getSaturation() >= 10f) {
                player.addPotionEffect(PotionHelper.getPotion(PotionEffectType.INVISIBILITY, 0));
                for (int i = 0; i < (period + 20); i++) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 10, 2, 3, 2, new Particle.DustOptions(Color.BLACK, 1));
                        }
                    }.runTaskLater(Vampcraft.plugin, i);
                }
            }
        });
    }

    public void addToOffList(Player player) {
        offList.add(player);
    }
}
