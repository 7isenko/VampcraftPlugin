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
            if (player.getBedSpawnLocation() != null) {
                event.setDeathMessage(player.getName() + " flew away");
                event.setKeepInventory(true);
                event.getDrops().clear();
            }
            player.getWorld().spawnEntity(player.getLocation(), EntityType.BAT);
            player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 100, 1, 1, 1, null);
        }
    }
}
