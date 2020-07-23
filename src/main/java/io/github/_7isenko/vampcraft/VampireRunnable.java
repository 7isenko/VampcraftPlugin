package io.github._7isenko.vampcraft;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.glow.GlowAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
                        player.addPotionEffect(effect, true);
                    return;
                }
            }

            if (offList.contains(player)) {
                offList.remove(player);
                return;
            }

            // night or caving logic
            List<Entity> entities = player.getNearbyEntities(40, 40, 40);
            removeNotLivingEntities(entities);
            GlowAPI.setGlowing(entities, GlowAPI.Color.RED, player);

            for (PotionEffect effect : Vampcraft.goodPotionEffects)
                player.addPotionEffect(effect, true);

            for (int i = 0; i < (period + 20); i++) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation(), 30, 3, 2, 3, 0.01);
                    }
                }.runTaskLater(Vampcraft.plugin, i);
            }
        });
    }

    public void addToOffList(Player player) {
        offList.add(player);
    }

    private void removeNotLivingEntities(List<Entity> entities) {
        entities.removeIf(entity -> !(entity instanceof LivingEntity));
    }
}
