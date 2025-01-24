package dev.revere.alley.profile.division.menu;

import dev.revere.alley.Alley;
import dev.revere.alley.api.menu.Button;
import dev.revere.alley.api.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Remi
 * @project Alley
 * @date 6/2/2024
 */
public class DivisionsMenu extends Menu {
    @Override
    public String getTitle(Player player) {
        return "&8Divisions Menu";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();

        //buttons.put(0, new BackButton(new ProfileMenu()));

        Alley.getInstance().getDivisionRepository().getDivisions().forEach(division -> {
            buttons.put(division.getSlot(), new DivisionButton(division));
        });

        addBorder(buttons, (byte) 15, 5);

        return buttons;
    }
}
