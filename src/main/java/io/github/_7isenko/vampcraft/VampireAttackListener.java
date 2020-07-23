package io.github._7isenko.vampcraft;

import org.bukkit.Color;
import org.bukkit.Material;
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
import org.bukkit.material.MaterialData;
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
                if (r.nextDouble() > 0.6D) vampire.addPotionEffect(PotionHelper.getPotion(PotionEffectType.SLOW, 0));
                if (r.nextDouble() > 0.7D)
                    vampire.addPotionEffect(PotionHelper.getPotion(PotionEffectType.BLINDNESS, 0));
                if (r.nextDouble() > 0.9D) {
                    event.setDamage(event.getDamage() * 2);
                    vampire.getWorld().playSound(vampire.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1, 4);
                    vampire.getWorld().spawnParticle(Particle.SPIT, vampire.getLocation(), 30, 0.5, 2, 0.5);
                }
                if (r.nextDouble() > 0.90D) {
                    for (PotionEffect goodPotionEffect : Vampcraft.goodPotionEffects) {
                        vampire.removePotionEffect(goodPotionEffect.getType());
                    }
                    vampire.getWorld().playSound(vampire.getLocation(), Sound.ITEM_TOTEM_USE, 0.3f, 4);
                    vampire.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, vampire.getLocation(), 60, 0.5, 2, 0.5);
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
                    case 1:
                        victim.addPotionEffect(PotionHelper.getPotion(PotionEffectType.BLINDNESS, 20, 0));
                        victim.addPotionEffect(PotionHelper.getPotion(PotionEffectType.SLOW, 20, 0));
                    case 2:
                        vampire.addPotionEffect(PotionHelper.getPotion(PotionEffectType.REGENERATION, 20, 2));
                        vampire.addPotionEffect(PotionHelper.getPotion(PotionEffectType.SATURATION, 2, 0));
                        victim.getWorld().spawnParticle(Particle.BLOCK_CRACK, victim.getLocation(), 30, 0.5, 2, 0.5, new MaterialData(Material.REDSTONE_BLOCK));
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
