package dev.revere.alley.feature.layout.menu.button;

import dev.revere.alley.api.menu.Button;
import dev.revere.alley.base.kit.enums.EnumKitCategory;
import dev.revere.alley.feature.layout.menu.LayoutMenu;
import dev.revere.alley.tool.item.ItemBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Emmy
 * @project Alley
 * @since 03/05/2025
 */
@AllArgsConstructor
public class LayoutModeSwitcherButton extends Button {
    private EnumKitCategory kitCategory;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.ARROW)
                .name("&6&l" + this.kitCategory.getName() + " Modes")
                .lore(
                        ("&f" + this.kitCategory.getDescription()),
                        "",
                        "&aClick to view!"
                )
                .hideMeta()
                .build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (clickType != ClickType.LEFT) return;

        if (this.kitCategory == EnumKitCategory.EXTRA) {
            new LayoutMenu(EnumKitCategory.EXTRA).openMenu(player);
            return;
        }

        new LayoutMenu(EnumKitCategory.NORMAL).openMenu(player);
    }
}