package io.github._7isenko.vampcraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class VampireEatListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onEat(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Vampcraft.vampires.contains(event.getEntity()) && event.getItem() != null) {
                event.setCancelled(true);
                ((Player) event.getEntity()).sendTitle("You need blood", null, 10, 70, 20);
            }
        }
    }

}
