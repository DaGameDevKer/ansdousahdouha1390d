package dev.revere.alley.feature.cosmetic.command.impl.admin;

import dev.revere.alley.api.command.BaseCommand;
import dev.revere.alley.api.command.CommandArgs;
import dev.revere.alley.api.command.annotation.CommandData;
import dev.revere.alley.feature.cosmetic.EnumCosmeticType;
import dev.revere.alley.feature.cosmetic.interfaces.ICosmetic;
import dev.revere.alley.feature.cosmetic.repository.BaseCosmeticRepository;
import dev.revere.alley.util.StringUtil;
import dev.revere.alley.util.chat.CC;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * @author Remi
 * @project Alley
 * @date 6/1/2024
 */
public class CosmeticListCommand extends BaseCommand {
    @CommandData(name = "cosmetic.list", isAdminOnly = true)
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        Map<EnumCosmeticType, BaseCosmeticRepository<?>> repositories = this.plugin.getCosmeticRepository().getRepositories();

        player.sendMessage("");

        if (repositories.isEmpty()) {
            player.sendMessage(CC.translate("&cNo cosmetic repositories are registered."));
            player.sendMessage("");
            return;
        }

        for (Map.Entry<EnumCosmeticType, BaseCosmeticRepository<?>> entry : repositories.entrySet()) {
            EnumCosmeticType type = entry.getKey();
            BaseCosmeticRepository<?> repository = entry.getValue();

            List<? extends ICosmetic> cosmetics = repository.getCosmetics();

            if (cosmetics.isEmpty()) {
                continue;
            }

            String friendlyTypeName = StringUtil.formatCosmeticTypeName(type);
            String header = String.format("     &b&l%s &f(%d)", friendlyTypeName, cosmetics.size());
            player.sendMessage(CC.translate(header));

            for (ICosmetic cosmetic : cosmetics) {
                player.sendMessage(CC.translate("      &f● &b" + cosmetic.getName()));
            }
        }


        player.sendMessage("");
    }
}
