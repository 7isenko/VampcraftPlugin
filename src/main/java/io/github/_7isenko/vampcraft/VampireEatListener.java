package io.github._7isenko.vampcraft;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class VampireEatListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onEat(PlayerItemConsumeEvent event) {
        if (Vampcraft.vampires.contains(event.getPlayer()) && event.getItem() != null) {
            if (event.getItem().getType().isEdible()) {
                event.setCancelled(true);
                event.getPlayer().sendTitle("You need blood", null, 10, 70, 20);
            }
        }
    }
}
