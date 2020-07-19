package io.github._7isenko.vampcraft;

import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class VampireDeathListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent event) {
        if (Vampcraft.vampires.contains(event.getEntity())) {
            Player player = event.getEntity();
            event.setDeathMessage(player.getName() + " flew away");
            player.getWorld().spawnEntity(player.getLocation(), EntityType.BAT);
            player.getWorld().spawnParticle(Particle.SQUID_INK, player.getLocation(), 100, 1, 1, 1);
        }
    }
}
