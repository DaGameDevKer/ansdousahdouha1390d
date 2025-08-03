package dev.revere.alley.adapter.knockback;

import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Alley
 * @since 26/04/2025
 */
public interface Knockback {
    /**
     * Retrieves the bootstrap name of the knockback implementation.
     *
     * @return The bootstrap name as a String.
     */
    KnockbackType getType();

    void applyKnockback(Player player, String profile);
}