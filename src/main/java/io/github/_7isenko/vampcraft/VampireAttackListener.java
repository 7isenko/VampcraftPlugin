package io.github._7isenko.vampcraft;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class VampireAttackListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getFinalDamage() == 0.0D) return;

        if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof Player && Vampcraft.vampires.contains(event.getEntity())) {
            EntityEquipment equipment = ((LivingEntity) event.getDamager()).getEquipment();
            if (equipment != null && equipment.getItemInMainHand().getType().name().contains("GOLDEN")) {
                Random r = new Random();
                Player vampire = (Player) event.getEntity();
                if (r.nextDouble() > 0.2D) vampire.addPotionEffect(PotionHelper.getPotion(PotionEffectType.SLOW, 0));
                if (r.nextDouble() > 0.3D)
                    vampire.addPotionEffect(PotionHelper.getPotion(PotionEffectType.BLINDNESS, 0));
                if (r.nextDouble() > 0.5D) {
                    event.setDamage(event.getDamage() * 2);
                    vampire.getWorld().playSound(vampire.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1, 1);
                    vampire.getWorld().spawnParticle(Particle.REDSTONE, vampire.getLocation(), 20, 0.5, 2, 0.5, new Particle.DustOptions(Color.BLUE, 1));
                }
                if (r.nextDouble() > 0.6D) {
                    for (PotionEffect goodPotionEffect : Vampcraft.goodPotionEffects) {
                        vampire.removePotionEffect(goodPotionEffect.getType());
                    }
                    vampire.getWorld().playSound(vampire.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);
                    vampire.getWorld().spawnParticle(Particle.REDSTONE, vampire.getLocation(), 60, 0.5, 2, 0.5, new Particle.DustOptions(Color.YELLOW, 1));
                    Vampcraft.runnable.addToOffList(vampire);
                }
            }
        }

        if (event.getEntity().getType() == EntityType.PLAYER && Vampcraft.vampires.contains(event.getEntity()))
            return;
        if (event.getDamager() instanceof Player && (Vampcraft.edibleTypes.contains(event.getEntityType()))) {
            if (Vampcraft.vampires.contains(event.getDamager())) {
                Player vampire = (Player) event.getDamager();
                LivingEntity victim = (LivingEntity) event.getEntity();

                switch (getGoldenLevel(victim)) {
                    case 0:
                        victim.addPotionEffect(PotionHelper.getPotion(PotionEffectType.WITHER, 100, 0));
                        vampire.addPotionEffect(PotionHelper.getPotion(PotionEffectType.SATURATION, 4, 0));
                    case 1:
                        victim.addPotionEffect(PotionHelper.getPotion(PotionEffectType.BLINDNESS, 20, 0));
                        victim.addPotionEffect(PotionHelper.getPotion(PotionEffectType.SLOW, 20, 0));
                    case 2:
                        vampire.addPotionEffect(PotionHelper.getPotion(PotionEffectType.REGENERATION, 20, 2));
                        vampire.addPotionEffect(PotionHelper.getPotion(PotionEffectType.SATURATION, 2, 0));
                        victim.getWorld().spawnParticle(Particle.REDSTONE, victim.getLocation(), 30, 0.5, 2, 0.5, new Particle.DustOptions(Color.RED, 1));
                    case 3:
                        break;
                    case 4:
                        vampire.damage(1);
                        vampire.setNoDamageTicks(0);
                        break;
                }
            }
        }
    }

    private int getGoldenLevel(LivingEntity entity) {
        int lvl = 0;
        EntityEquipment equipment = entity.getEquipment();
        if (equipment == null) return lvl;
        for (ItemStack armor : equipment.getArmorContents()) {
            if (armor != null && armor.getType().name().contains("GOLDEN"))
                lvl++;
        }
        return lvl;
    }
}
