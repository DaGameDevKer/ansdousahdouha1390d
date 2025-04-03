package dev.revere.alley.tool.logger.command;

import dev.revere.alley.api.command.BaseCommand;
import dev.revere.alley.api.command.CommandArgs;
import dev.revere.alley.api.command.annotation.CommandData;
import dev.revere.alley.tool.logger.Logger;
import dev.revere.alley.util.chat.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Emmy
 * @project Alley
 * @since 03/04/2025
 */
public class ViewErrorCommand extends BaseCommand {
    @CommandData(name = "viewerror", isAdminOnly = true, inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (sender instanceof Player) {
            sender.sendMessage(CC.translate("&cThis command can only be used in console."));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(CC.translate("&cUsage: /viewerror <error>"));
            return;
        }

        try {
            UUID errorId = UUID.fromString(args[0]);
            Logger.viewException(errorId);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(CC.translate("&cInvalid error ID!"));
        }
    }
}