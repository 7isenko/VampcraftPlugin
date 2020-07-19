package io.github._7isenko.vampcraft;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionHelper {
    public static PotionEffect getPotion(PotionEffectType pet, int period, int amplifier) {
        return new PotionEffect(pet, period, amplifier, false, false, true);
    }
}
