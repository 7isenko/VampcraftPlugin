package io.github._7isenko.vampcraft;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class PotionHelper {
    static final int period = Vampcraft.period;

    public static ArrayList<PotionEffect> getGoodVampirePotions() {
        ArrayList<PotionEffect> effects = new ArrayList<>();
        effects.add(getPotion(PotionEffectType.NIGHT_VISION, period + 220, 0));
        effects.add(getPotion(PotionEffectType.SPEED, 1));
        effects.add(getPotion(PotionEffectType.INCREASE_DAMAGE, 0));
        effects.add(getPotion(PotionEffectType.DAMAGE_RESISTANCE, 0));
        effects.add(getPotion(PotionEffectType.FAST_DIGGING, 0));
        return effects;
    }

    public static ArrayList<PotionEffect> getBadVampirePotions() {
        ArrayList<PotionEffect> effects = new ArrayList<>();
        effects.add(getPotion(PotionEffectType.SLOW, 0));
        effects.add(getPotion(PotionEffectType.BLINDNESS, 0));
        effects.add(getPotion(PotionEffectType.CONFUSION, period + 40, 0));
        effects.add(getPotion(PotionEffectType.HUNGER, 0));
        effects.add(getPotion(PotionEffectType.SLOW_DIGGING, 0));
        return effects;
    }
    public static PotionEffect getPotion(PotionEffectType pet, int period, int amplifier) {
        return new PotionEffect(pet, period, amplifier, false, false, null);
    }

    public static PotionEffect getPotion(PotionEffectType pet, int amplifier) {
        return PotionHelper.getPotion(pet, (period + 40), amplifier);
    }
}
