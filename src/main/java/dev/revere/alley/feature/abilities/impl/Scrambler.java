package dev.revere.alley.feature.abilities.impl;

import dev.revere.alley.Alley;
import dev.revere.alley.feature.abilities.AbstractAbility;
import dev.revere.alley.feature.abilities.utils.DurationFormatter;
import dev.revere.alley.profile.Profile;
import dev.revere.alley.util.PlayerUtil;
import dev.revere.alley.util.chat.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Scrambler extends AbstractAbility {
    private final Alley plugin = Alley.getInstance();

    public Scrambler() {
        super("SCRAMBLER");
    }

    @EventHandler
    private void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Profile profile = Alley.getInstance().getProfileService().getProfile(damager.getUniqueId());
            if (!isAbility(damager.getItemInHand())) return;

            if (profile.getScrambler().onCooldown(damager)) {
                damager.sendMessage(CC.translate("&fYou are on &6&lScrambler &7cooldown for &4" + DurationFormatter.getRemaining(profile.getScrambler().getRemainingMillis(damager), true, true)));
                damager.updateInventory();
                return;
            }

            if (profile.getPartneritem().onCooldown(damager)) {
                damager.sendMessage(CC.translate("&fYou are on &6&lPartner Item &fcooldown for &6" + DurationFormatter.getRemaining(profile.getPartneritem().getRemainingMillis(damager), true, true)));
                damager.updateInventory();
                return;
            }

            PlayerUtil.decrement(damager);

            Player victim = (Player) event.getEntity();

            profile.getScrambler().applyCooldown(damager, 60 * 1000);
            profile.getPartneritem().applyCooldown(damager, 10 * 1000);

            this.random(victim);

            plugin.getAbilityService().cooldownExpired(damager, this.getName(), this.getAbility());
            plugin.getAbilityService().playerMessage(damager, this.getAbility());
            plugin.getAbilityService().targetMessage(victim, damager, this.getAbility());
        }
    }

    @EventHandler
    public void checkCooldown(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Profile profile = Alley.getInstance().getProfileService().getProfile(player.getUniqueId());
        if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
            if (!isAbility(player.getItemInHand())) {
                return;
            }
            if (isAbility(player.getItemInHand())) {
                if (this.hasCooldown(player)) {
                    player.sendMessage(CC.translate("&fYou are on cooldown for &4" + DurationFormatter.getRemaining(profile.getCombo().getRemainingMillis(player), true)));
                    event.setCancelled(true);
                    player.updateInventory();
                }
            }
        }
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!isAbility(event.getItem())) return;

        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            event.setCancelled(true);

            if (this.hasCooldown(player)) {
                plugin.getAbilityService().cooldown(player, this.getName(), this.getCooldown(player));
                player.updateInventory();
            }
        }
    }

    private void random(Player victim) {
        Inventory victimInventory = victim.getInventory();

        ItemStack slot1 = victimInventory.getItem(0);
        ItemStack slot2 = victimInventory.getItem(1);
        ItemStack slot3 = victimInventory.getItem(2);
        ItemStack slot4 = victimInventory.getItem(3);
        ItemStack slot5 = victimInventory.getItem(4);
        ItemStack slot6 = victimInventory.getItem(5);
        ItemStack slot7 = victimInventory.getItem(6);
        ItemStack slot8 = victimInventory.getItem(7);
        ItemStack slot9 = victimInventory.getItem(8);

        victimInventory.setItem(0, slot4);
        victimInventory.setItem(1, slot3);
        victimInventory.setItem(2, slot6);
        victimInventory.setItem(3, slot8);
        victimInventory.setItem(4, slot9);
        victimInventory.setItem(5, slot1);
        victimInventory.setItem(6, slot2);
        victimInventory.setItem(7, slot5);
        victimInventory.setItem(8, slot7);

        victim.updateInventory();
    }
}
