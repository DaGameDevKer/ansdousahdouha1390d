package dev.revere.alley.cosmetic.impl.killeffects.impl;

import dev.revere.alley.cosmetic.impl.killeffects.AbstractKillEffect;
import dev.revere.alley.cosmetic.impl.killeffects.annotation.KillEffectData;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author Remi
 * @project Alley
 * @date 01/06/2024
 */
@KillEffectData(name = "None", description = "Remove your kill effect", icon = Material.BARRIER, slot = 10)
public class NoneKillEffect extends AbstractKillEffect {

    @Override
    public void spawnEffect(Player player) {

    }
}
