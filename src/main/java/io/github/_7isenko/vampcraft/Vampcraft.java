package io.github._7isenko.vampcraft;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Vampcraft extends JavaPlugin {
    // How to build: Maven/Vampcraft/Lifecycle/package
    public static Plugin plugin;
    public static ArrayList<Player> vampires;
    private static VampireRunnable runnable;

    @Override
    public void onEnable() {
        // TODO: enabling logic
        plugin = this;
        // TODO: load from file
        vampires = new ArrayList<>();
        this.getCommand("vamp").setExecutor(new VampireCommandExecutor());
        getServer().getPluginManager().registerEvents(new VampireAttackListener(), plugin);
        getServer().getPluginManager().registerEvents(new VampireEatListener(), plugin);
        getServer().getPluginManager().registerEvents(new VampireDeathListener(), plugin);
        runnable = new VampireRunnable(100);
    }

    @Override
    public void onDisable() {
        // TODO: save vampires to the file
    }
}