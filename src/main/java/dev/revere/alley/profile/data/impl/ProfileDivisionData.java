package dev.revere.alley.profile.data.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.var;
import dev.revere.alley.Alley;
import dev.revere.alley.kit.settings.impl.KitSettingRankedImpl;
import dev.revere.alley.profile.Profile;
import dev.revere.alley.profile.division.AbstractDivision;
import dev.revere.alley.profile.division.enums.EnumDivisionLevel;
import dev.revere.alley.profile.division.enums.EnumDivisionTier;

import java.util.stream.Collectors;

/**
 * @author Remi
 * @project Alley
 * @date 6/1/2024
 */
@Getter
@Setter
public class ProfileDivisionData {
    private String division;
    private int globalElo;

    public ProfileDivisionData() {
        this.globalElo = 1000;
        this.division = this.determineDivision(globalElo);
    }

    /**
     * Updates the division of the player
     *
     * @param globalElo the global elo of the player
     */
    public void updateDivision(int globalElo) {
        this.division = determineDivision(globalElo);
    }

    /**
     * Updates the elo and division of the player
     *
     * @param profile the profile of the player
     */
    public void updateEloAndDivision(Profile profile) {
        this.globalElo = calculateGlobalElo(profile);
        this.updateDivision(this.globalElo);
    }

    /**
     * Calculates the global elo of the player
     *
     * @param profile the profile of the player
     * @return the global elo of the player
     */
    public int calculateGlobalElo(Profile profile) {
        var rankedKits = Alley.getInstance().getKitRepository().getKits().stream()
                .filter(kit -> kit.isSettingEnabled(KitSettingRankedImpl.class))
                .collect(Collectors.toList());

        if (rankedKits.isEmpty()) {
            return 0;
        }

        int totalElo = rankedKits.stream()
                .mapToInt(kit -> {
                    ProfileRankedKitData kitData = profile.getProfileData().getRankedKitData().get(kit.getName());
                    return kitData != null ? kitData.getElo() : 0;
                })
                .sum();

        return totalElo / rankedKits.size();
    }

    /**
     * Determines the division of the player based on their elo
     *
     * @param elo the elo of the player
     * @return the division of the player
     */
    private String determineDivision(int elo) {
        for (EnumDivisionTier tier : EnumDivisionTier.values()) {
            for (EnumDivisionLevel level : EnumDivisionLevel.values()) {
                int[] eloRange = tier.getEloRangeForLevel(level);
                if (elo >= eloRange[0] && elo <= eloRange[1]) {
                    return Alley.getInstance().getDivisionRepository().getDivision(tier, level);
                }
            }
        }
        return Alley.getInstance().getDivisionRepository().getDivision(EnumDivisionTier.BRONZE, EnumDivisionLevel.LEVEL_1);
    }

    /**
     * Gets the elo to the next tier or level
     *
     * @return the elo to the next tier or level
     */
    public int getEloToNextTierOrLevel() {
        AbstractDivision division = Alley.getInstance().getDivisionRepository().getDivision(this.division);
        EnumDivisionTier tier = division.getTier();
        EnumDivisionLevel level = division.getLevel();

        int currentElo = this.globalElo;

        EnumDivisionLevel nextLevel = calculateNextLevel(level);
        if (nextLevel != null) {
            int[] eloRange = tier.getEloRangeForLevel(nextLevel);
            return eloRange[0] - currentElo;
        }

        EnumDivisionTier[] tiers = EnumDivisionTier.values();
        for (int i = 0; i < tiers.length; i++) {
            if (tiers[i] == tier) {
                if (i > tiers.length - 1) {
                    return 0;
                }
                EnumDivisionTier nextTier = tiers[i + 1];
                int[] eloRange = nextTier.getEloRangeForLevel(EnumDivisionLevel.LEVEL_1);
                return eloRange[0] - currentElo;
            }
        }
        return 0;
    }

    /**
     * Calculates the next level of the division
     *
     * @param level the current level
     * @return the next level
     */
    private EnumDivisionLevel calculateNextLevel(EnumDivisionLevel level) {
        int ordinal = level.ordinal();
        if (ordinal == EnumDivisionLevel.values().length - 1) {
            return level;
        }
        return EnumDivisionLevel.values()[ordinal + 1];
    }
}