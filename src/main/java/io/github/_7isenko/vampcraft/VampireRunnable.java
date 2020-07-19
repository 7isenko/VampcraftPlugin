package io.github._7isenko.vampcraft;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class VampireRunnable extends BukkitRunnable {
    int period;

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
            World world = player.getWorld();
            Location location = player.getLocation();
            if (world.getEnvironment() == World.Environment.NORMAL && world.getTime() <= 12566) {
                // day logic
                if (world.getHighestBlockAt(location).getY() <= location.getY()) {
                    player.setFireTicks(period);
                    player.addPotionEffect(getPotion(PotionEffectType.SLOW, 0));
                    player.addPotionEffect(getPotion(PotionEffectType.BLINDNESS, 0));
                    player.addPotionEffect(getPotion(PotionEffectType.CONFUSION, 0));
                    player.addPotionEffect(getPotion(PotionEffectType.HUNGER, 0));
                    player.addPotionEffect(getPotion(PotionEffectType.SLOW_DIGGING, 0));
                    return;
                }
                // check caving during day
                if (location.getY() > 60 || (location.getY() > 50 && world.getHighestBlockAt(location).getType() == Material.WATER)) return;
            }
            // night logic
            player.addPotionEffect(PotionHelper.getPotion(PotionEffectType.NIGHT_VISION, period + 220, 0));
            player.addPotionEffect(getPotion(PotionEffectType.DOLPHINS_GRACE, 0));
            player.addPotionEffect(getPotion(PotionEffectType.SPEED, 1));
            player.addPotionEffect(getPotion(PotionEffectType.INCREASE_DAMAGE, 0));
            player.addPotionEffect(getPotion(PotionEffectType.DAMAGE_RESISTANCE, 0));
            player.addPotionEffect(getPotion(PotionEffectType.FAST_DIGGING, 0));
        });
    }

    private PotionEffect getPotion(PotionEffectType pet, int amplifier) {
        return PotionHelper.getPotion(pet, (period + 20), amplifier);
    }
}
