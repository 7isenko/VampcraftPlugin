package io.github._7isenko.vampcraft;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class VampireAttackListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent event) {
        ArrayList<EntityType> types = new ArrayList<>(Arrays.asList(EntityType.WOLF, EntityType.PLAYER, EntityType.GUARDIAN, EntityType.LLAMA, EntityType.TRADER_LLAMA, EntityType.BAT, EntityType.BEE, EntityType.CAT, EntityType.CHICKEN, EntityType.COW, EntityType.DOLPHIN, EntityType.DONKEY, EntityType.FOX, EntityType.HOGLIN, EntityType.HORSE, EntityType.PILLAGER, EntityType.ILLUSIONER, EntityType.VILLAGER, EntityType.EVOKER, EntityType.LLAMA, EntityType.MULE, EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, EntityType.PIG, EntityType.PIGLIN, EntityType.POLAR_BEAR, EntityType.RABBIT, EntityType.SHEEP, EntityType.SQUID, EntityType.TURTLE, EntityType.VINDICATOR, EntityType.WANDERING_TRADER, EntityType.WITCH));
        if (event.getFinalDamage() == 0.0D) return;
        if (event.getEntity().getType() == EntityType.PLAYER && Vampcraft.vampires.contains(event.getEntity()))
            return;
        if (event.getDamager() instanceof Player && (types.contains(event.getEntityType()))) {
            if (Vampcraft.vampires.contains(event.getDamager())) {
                Player damager = (Player) event.getDamager();
                LivingEntity victim = (LivingEntity) event.getEntity();

                // buff vampire
                damager.addPotionEffect(PotionHelper.getPotion(PotionEffectType.REGENERATION, 20, 2));
                damager.addPotionEffect(PotionHelper.getPotion(PotionEffectType.SATURATION, 2, 0));

                // debuff victim
                victim.addPotionEffect(PotionHelper.getPotion(PotionEffectType.BLINDNESS, 20, 0));
                victim.addPotionEffect(PotionHelper.getPotion(PotionEffectType.SLOW, 20, 0));
                victim.getWorld().spawnParticle(Particle.REDSTONE, victim.getLocation(), 30, 0.5, 2, 0.5, new Particle.DustOptions(Color.RED, 1));
            }
        }
    }
}
