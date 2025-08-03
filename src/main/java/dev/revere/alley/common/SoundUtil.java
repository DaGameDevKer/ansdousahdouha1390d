package dev.revere.alley.common;

import lombok.experimental.UtilityClass;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Alley
 * @date 25/05/2024 - 17:11
 */
@UtilityClass
public class SoundUtil {
    /**
     * Play a custom sound to the model
     *
     * @param player the model to play the sound to
     * @param sound  the sound to play
     * @param volume the volume of the sound
     * @param pitch  the pitch of the sound
     */
    public void playCustomSound(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    /**
     * Play a ban hammer sound to the model
     *
     * @param player the model to play the sound to
     */
    public void playBanHammer(Player player) {
        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 2.0F, 1.5F);
    }

    /**
     * Play a sound to the model based on the success boolean
     *
     * @param player  the model to play the sound to
     * @param success the boolean to determine the sound
     */
    public void playSound(Player player, boolean success) {
        if (success) {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 2F, 2F);
        } else {
            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2F, 2F);
        }
    }

    /**
     * Play a fail sound to the model (Note Bass)
     *
     * @param player the model to play the sound to
     */
    public void playFail(Player player) {
        player.playSound(player.getLocation(), Sound.NOTE_BASS, 20F, 0.1F);

    }

    /**
     * Play a success sound to the model (Note Pling)
     *
     * @param player the model to play the sound to
     */
    public void playSuccess(Player player) {
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 20F, 15F);
    }

    /**
     * Play a click sound to the model (Click)
     *
     * @param player the model to play the sound to
     */
    public void playClick(Player player) {
        player.playSound(player.getLocation(), Sound.CLICK, 20F, 15F);
    }

    /**
     * Play a neutral sound to the model (Note Sticks)
     *
     * @param player the model to play the sound to
     */
    public void playNeutral(Player player) {
        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 20F, 15F);
    }

    /**
     * Play an Explode sound to the model (Explosion)
     *
     * @param player the model to play the sound to
     */
    public void playExplode(Player player) {
        player.playSound(player.getLocation(), Sound.EXPLODE, 2.0F, 1.5F);
    }

    /**
     * Play a blast sound to the model (Firework Blast)
     *
     * @param player the model to play the sound to
     */
    public void playBlast(Player player) {
        player.playSound(player.getLocation(), Sound.FIREWORK_BLAST, 20F, 15F);
    }
}