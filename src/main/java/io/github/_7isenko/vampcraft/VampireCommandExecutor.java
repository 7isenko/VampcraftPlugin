package io.github._7isenko.vampcraft;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inventivetalent.glow.GlowAPI;

public class VampireCommandExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            Player player = Bukkit.getPlayer(strings[0]);
            if (Vampcraft.vampires.contains(player)) {
                Vampcraft.vampires.remove(player);
                player.sendMessage("Now you are a human");
                player.getNearbyEntities(80, 60, 80).forEach(entity -> GlowAPI.setGlowing(entity, false, player));
            } else {
                Vampcraft.vampires.add(player);
                player.sendMessage("You become a vampire");
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
