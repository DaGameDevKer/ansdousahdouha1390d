package dev.revere.alley.feature.abilities.impl;

import com.google.common.collect.Maps;
import dev.revere.alley.Alley;
import dev.revere.alley.feature.abilities.AbstractAbility;
import dev.revere.alley.feature.abilities.IAbilityService;
import dev.revere.alley.feature.abilities.utils.DurationFormatter;
import dev.revere.alley.profile.IProfileService;
import dev.revere.alley.profile.Profile;
import dev.revere.alley.profile.enums.EnumGlobalCooldown;
import dev.revere.alley.util.PlayerUtil;
import dev.revere.alley.util.chat.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class EffectDisabler extends AbstractAbility {
    private final Alley plugin = Alley.getInstance();

    private final Map<UUID, Integer> HITS = Maps.newHashMap();

    public EffectDisabler() {
        super("EFFECT_DISABLER");
    }

    @EventHandler
    private void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();

            IProfileService profileService = Alley.getInstance().getService(IProfileService.class);
            IAbilityService abilityService = Alley.getInstance().getService(IAbilityService.class);

            Profile profile = profileService.getProfile(damager.getUniqueId());

            if (!isAbility(damager.getItemInHand())) return;

            if (isBard(victim) || isArcher(victim) || isRogue(victim) || isMiner(victim)) return;
            
            if (profile.getCooldown(EffectDisabler.class).onCooldown(damager)) {
                damager.sendMessage(CC.translate("&fYou are on &6&lEffect Disabler &7cooldown for &4" + DurationFormatter.getRemaining(profile.getCooldown(EffectDisabler.class).getRemainingMillis(damager), true, true)));
                damager.updateInventory();
                return;
            }

            if(profile.getGlobalCooldown(EnumGlobalCooldown.PARTNER_ITEM).onCooldown(damager)){
                damager.sendMessage(CC.translate("&fYou are on &6&lPartner Item &fcooldown for &6" + DurationFormatter.getRemaining(profile.getGlobalCooldown(EnumGlobalCooldown.PARTNER_ITEM).getRemainingMillis(damager), true, true)));
                damager.updateInventory();
                return;
            }

            if (!HITS.containsKey(victim.getUniqueId())) {
                HITS.put(victim.getUniqueId(), 0);
            }

            HITS.put(victim.getUniqueId(), HITS.get(victim.getUniqueId()) + 1);

            if (HITS.get(victim.getUniqueId()) != 5) return;

            PlayerUtil.decrement(damager);

            profile.getCooldown(EffectDisabler.class).applyCooldown(damager, 60 * 1000);
            profile.getGlobalCooldown(EnumGlobalCooldown.PARTNER_ITEM).applyCooldown(damager,  10 * 1000);

            HITS.remove(victim.getUniqueId());

            victim.getActivePotionEffects().forEach(potionEffect -> victim.removePotionEffect(potionEffect.getType()));

            abilityService.sendCooldownExpiredMessage(damager, this.getName(), this.getAbility());
            abilityService.sendPlayerMessage(damager, this.getAbility());
            abilityService.sendTargetMessage(victim, damager, this.getAbility());
        }
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (!isAbility(event.getItem())) return;

        event.setCancelled(true);

        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Player player = event.getPlayer();

            if (this.hasCooldown(player)) {
                event.setCancelled(true);
                Alley.getInstance().getService(IAbilityService.class).sendCooldownMessage(player, this.getName(), this.getCooldown(player));
                player.updateInventory();
            }
        }
    }

    @EventHandler
    public void checkCooldown(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Profile profile = Alley.getInstance().getService(IProfileService.class).getProfile(player.getUniqueId());
        if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
            if (!isAbility(player.getItemInHand())) {
                return;
            }
            if (isAbility(player.getItemInHand())) {
                if (this.hasCooldown(player)) {
                    player.sendMessage(CC.translate("&fYou are on cooldown for &4" + DurationFormatter.getRemaining(profile.getCooldown(EffectDisabler.class).getRemainingMillis(player), true)));
                    event.setCancelled(true);
                    player.updateInventory();
                }
            }
        }
    }

    private boolean isBard(Player victim) {
        return victim.getInventory().getHelmet() != null && victim.getInventory().getHelmet().getType().equals(Material.GOLD_HELMET)
                && victim.getInventory().getChestplate() != null && victim.getInventory().getChestplate().getType().equals(Material.GOLD_CHESTPLATE)
                && victim.getInventory().getLeggings() != null && victim.getInventory().getLeggings().getType().equals(Material.GOLD_LEGGINGS)
                && victim.getInventory().getBoots() != null && victim.getInventory().getBoots().getType().equals(Material.GOLD_BOOTS);
    }

    private boolean isArcher(Player victim) {
        return victim.getInventory().getHelmet() != null && victim.getInventory().getHelmet().getType().equals(Material.LEATHER_HELMET)
                && victim.getInventory().getChestplate() != null && victim.getInventory().getChestplate().getType().equals(Material.LEATHER_CHESTPLATE)
                && victim.getInventory().getLeggings() != null && victim.getInventory().getLeggings().getType().equals(Material.LEATHER_LEGGINGS)
                && victim.getInventory().getBoots() != null && victim.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS);
    }

    private boolean isRogue(Player victim) {
        return victim.getInventory().getHelmet() != null && victim.getInventory().getHelmet().getType().equals(Material.CHAINMAIL_HELMET)
                && victim.getInventory().getChestplate() != null && victim.getInventory().getChestplate().getType().equals(Material.CHAINMAIL_CHESTPLATE)
                && victim.getInventory().getLeggings() != null && victim.getInventory().getLeggings().getType().equals(Material.CHAINMAIL_LEGGINGS)
                && victim.getInventory().getBoots() != null && victim.getInventory().getBoots().getType().equals(Material.CHAINMAIL_BOOTS);
    }

    private boolean isMiner(Player victim) {
        return victim.getInventory().getHelmet() != null && victim.getInventory().getHelmet().getType().equals(Material.IRON_HELMET)
                && victim.getInventory().getChestplate() != null && victim.getInventory().getChestplate().getType().equals(Material.IRON_CHESTPLATE)
                && victim.getInventory().getLeggings() != null && victim.getInventory().getLeggings().getType().equals(Material.IRON_LEGGINGS)
                && victim.getInventory().getBoots() != null && victim.getInventory().getBoots().getType().equals(Material.IRON_BOOTS);
    }
}