package dev.revere.alley.feature.match.model.internal;

import dev.revere.alley.feature.match.model.GamePlayer;
import dev.revere.alley.feature.match.model.MatchGamePlayerData;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * @author Remi
 * @project Alley
 * @date 5/21/2024
 */
@Setter
@Getter
public class MatchGamePlayer extends GamePlayer {
    private final MatchGamePlayerData data;
    private int elo;

    /**
     * Constructor for the MatchGamePlayerImpl class.
     *
     * @param uuid     The UUID of the model.
     * @param username The username of the model.
     * @param elo      The elo of the model.
     */
    public MatchGamePlayer(UUID uuid, String username, int elo) {
        super(uuid, username);
        this.data = new MatchGamePlayerData();
        this.elo = elo;
    }

    /**
     * Constructor for the MatchGamePlayerImpl class.
     *
     * @param uuid     The UUID of the model.
     * @param username The username of the model.
     */
    public MatchGamePlayer(UUID uuid, String username) {
        this(uuid, username, 0);
    }
}
