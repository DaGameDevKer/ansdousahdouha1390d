package dev.revere.alley.feature.cosmetic.internal.repository.impl.killmessage;

import dev.revere.alley.feature.cosmetic.model.CosmeticType;
import dev.revere.alley.feature.cosmetic.annotation.CosmeticData;
import org.bukkit.Material;

/**
 * @author Remi
 * @project alley-practice
 * @date 27/06/2025
 */
@CosmeticData(
        type = CosmeticType.KILL_MESSAGE,
        name = "None",
        description = "Remove your kill message",
        icon = Material.BARRIER,
        slot = 10,
        price = 0
)
public class NoneKillMessages extends KillMessagePack {
    @Override
    protected String getResourceFileName() {
        return null;
    }
}