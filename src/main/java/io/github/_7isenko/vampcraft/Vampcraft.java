package io.github._7isenko.vampcraft;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Vampcraft extends JavaPlugin {
    // How to build: Maven/Vampcraft/Lifecycle/package
    public static Plugin plugin;
    public static ArrayList<Player> vampires;
    public static VampireRunnable runnable;

    public static ArrayList<EntityType> edibleTypes = new ArrayList<>(Arrays.asList(EntityType.WOLF, EntityType.PLAYER, EntityType.GUARDIAN, EntityType.LLAMA, EntityType.TRADER_LLAMA, EntityType.BAT, EntityType.BEE, EntityType.CAT, EntityType.CHICKEN, EntityType.COW, EntityType.DOLPHIN, EntityType.DONKEY, EntityType.FOX, EntityType.HOGLIN, EntityType.HORSE, EntityType.PILLAGER, EntityType.ILLUSIONER, EntityType.VILLAGER, EntityType.EVOKER, EntityType.LLAMA, EntityType.MULE, EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, EntityType.PIG, EntityType.PIGLIN, EntityType.POLAR_BEAR, EntityType.RABBIT, EntityType.SHEEP, EntityType.SQUID, EntityType.TURTLE, EntityType.VINDICATOR, EntityType.WANDERING_TRADER, EntityType.WITCH));
    public static ArrayList<PotionEffect> goodPotionEffects = PotionHelper.getGoodVampirePotions();
    public static ArrayList<PotionEffect> badPotionEffects = PotionHelper.getBadVampirePotions();
    public static final int period = 100;

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
        runnable = new VampireRunnable(period);
    }

    @Override
    public void onDisable() {
        // TODO: save vampires to the file
    }
}