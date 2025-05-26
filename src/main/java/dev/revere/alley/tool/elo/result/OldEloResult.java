package dev.revere.alley.tool.elo.result;

import lombok.Getter;

/**
 * @author Emmy
 * @project Alley
 * @date 09/11/2024 - 15:23
 */
@Getter
public class OldEloResult {
    private final int oldWinnerElo;
    private final int oldLoserElo;

    /**
     * Constructor for the OldEloResult class.
     *
     * @param oldWinnerElo The old elo of the winner.
     * @param oldLoserElo  The old elo of the loser.
     */
    public OldEloResult(int oldWinnerElo, int oldLoserElo) {
        this.oldWinnerElo = oldWinnerElo;
        this.oldLoserElo = oldLoserElo;
    }
}