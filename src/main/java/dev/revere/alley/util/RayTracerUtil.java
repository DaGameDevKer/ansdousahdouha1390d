package dev.revere.alley.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

/**
 * @author Emmy
 * @project Alley
 * @date 10/06/2024 - 20:50
 */
@UtilityClass
public class RayTracerUtil {
    /**
     * Ray trace from a location in a direction.
     *
     * @param startLocation The location to start the ray trace from.
     * @param direction     The direction to ray trace.
     * @return The location of the ray trace.
     */
    public Location rayTrace(Location startLocation, Vector direction) {
        Location currentLocation = startLocation.clone();
        while (currentLocation.getBlock().getType() == Material.AIR) {
            currentLocation.add(direction);
        }
        return currentLocation;
    }
}