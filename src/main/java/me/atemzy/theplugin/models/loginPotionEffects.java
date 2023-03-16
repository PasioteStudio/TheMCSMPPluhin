package me.atemzy.theplugin.models;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class loginPotionEffects {

    private static PotionEffect peB = new PotionEffect(PotionEffectType.REGENERATION, 1999999999, 250, false, false, false);
    private static PotionEffect peW = new PotionEffect(PotionEffectType.WEAKNESS, 1999999999, 250, false, false, false);
    private static PotionEffect peS = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1999999999, 250, false, false, false);
    private static PotionEffect peSD = new PotionEffect(PotionEffectType.SLOW_DIGGING, 1999999999, 250, false, false, false);
    private static PotionEffect peD = new PotionEffect(PotionEffectType.SPEED, 1999999999, 2, false, false, false);
    private static PotionEffect peF = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1999999999, 1, false, false, false);
    private static PotionEffect peH = new PotionEffect(PotionEffectType.HEAL, 1999999999, 1, false, false, false);




    private static PotionEffect[] poe = new PotionEffect[]{peB,peW,peS,peSD,peD, peH, peF};

    public static PotionEffect[] getPotionEffect() {
        return poe;
    }
}
