package dev.revere.alley.cosmetic.impl.killeffects;

import lombok.Data;
import dev.revere.alley.cosmetic.impl.killeffects.annotation.KillEffectData;
import dev.revere.alley.cosmetic.interfaces.ICosmetic;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author Remi
 * @project Alley
 * @date 01/06/2024
 */
@Data
public abstract class AbstractKillEffect implements ICosmetic {
    private final String name;
    private final String description;
    private final String permission;
    private final Material icon;
    private final int slot;
    private final int price;

    public AbstractKillEffect() {
        KillEffectData data = getClass().getAnnotation(KillEffectData.class);
        if (data != null) {
            this.name = data.name();
            this.description = data.description();
            this.permission = data.permission();
            this.icon = data.icon();
            this.slot = data.slot();
            this.price = data.price();
        } else {
            throw new IllegalStateException("KillEffectData annotation missing");
        }
    }

    /**
     * Spawns the effect at the given player location
     *
     * @param player the player to spawn the effect for
     */
    public abstract void spawnEffect(Player player);

    /**
     * Gets the permission for this kill effect
     *
     * @return the permission for this kill effect
     */
    public String getPermission() {
        return "alley.kill.effects." + this.permission;
    }
}