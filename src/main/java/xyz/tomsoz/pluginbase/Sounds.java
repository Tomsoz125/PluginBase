package xyz.tomsoz.pluginbase;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Various utilities for playing sound effects.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Sounds {

    /**
     * The prefix that the util will search for, used to determine if the plugin should play a vanilla
     * sound or a custom sound (such as one in a resource pack).
     */
    public static final String CUSTOM_PREFIX = "custom:";

    /**
     * Play a sound to a player.
     *
     * @param player    The player that should hear the sound
     * @param soundName The name of the sound, either the enum name for vanilla sounds, or a custom
     *                  sound name prefixed with {@link #CUSTOM_PREFIX}
     * @param volume    The volume of the sound
     * @param pitch     The pitch of the sound
     */
    public static void playToPlayer(@NotNull final Player player, final String soundName,
                                    final float volume,
                                    final float pitch) {

        if (soundName == null || soundName.equalsIgnoreCase("none")) {
            return;
        }

        if (soundName.toLowerCase().startsWith(CUSTOM_PREFIX)) {
            playCustomToPlayer(player, soundName.toLowerCase().replace(CUSTOM_PREFIX, ""), volume, pitch);
            return;
        }

        final Sound sound;

        try {
            sound = Sound.valueOf(soundName);
        } catch (final IllegalArgumentException ex) {
            Common.error(ex, "Invalid sound: " + soundName, false);
            return;
        }

        playVanillaToPlayer(player, sound, volume, pitch);
    }

    /**
     * Plays the sound in the configuration section to the player.
     *
     * @param player  The player that will hear the sound
     * @param section The configuration section containing the sound
     */
    public static void playToPlayer(@NotNull final Player player,
                                    @NotNull final ConfigurationSection section) {
        playToPlayer(
                player,
                Objects.requireNonNull(section.getString("sound"), "Sound to play is null"),
                (float) section.getDouble("volume"),
                (float) section.getDouble("pitch"));
    }

    /**
     * Play a sound to a location.
     *
     * @param loc       The location the sound will be played
     * @param soundName The name of the sound, either the enum name for vanilla sounds, or a custom
     *                  sound name prefixed with {@link #CUSTOM_PREFIX}
     * @param volume    The volume of the sound
     * @param pitch     The pitch of the sound
     */
    public static void playToLocation(@NotNull final Location loc, final String soundName,
                                      final float volume,
                                      final float pitch) {

        if (soundName == null || soundName.equalsIgnoreCase("none")) {
            return;
        }

        if (soundName.toLowerCase().startsWith(CUSTOM_PREFIX)) {
            playCustomToLocation(loc, soundName.toLowerCase().replace(CUSTOM_PREFIX, ""), volume, pitch);
            return;
        }

        final Sound sound;

        try {
            sound = Sound.valueOf(soundName);
        } catch (final IllegalArgumentException ex) {
            Common.error(ex, "Invalid sound: " + soundName, false);
            return;
        }

        playVanillaToLocation(loc, sound, volume, pitch);
    }

    /**
     * Plays the sound in the configuration section to the location.
     *
     * @param loc     The location to play the sound
     * @param section The configuration section containing the sound
     */
    public static void playToLocation(@NotNull final Location loc,
                                      @NotNull final ConfigurationSection section) {
        playToLocation(
                loc,
                Objects.requireNonNull(section.getString("sound"), "Sound to play is null"),
                (float) section.getDouble("volume"),
                (float) section.getDouble("pitch"));
    }

    /**
     * Plays a vanilla sound to a player.
     *
     * @param player The player that will hear the sound
     * @param sound  The vanilla sound to play
     * @param volume The volume of the sound
     * @param pitch  The pitch of the sound
     */
    public static void playVanillaToPlayer(@NotNull final Player player, @NotNull final Sound sound,
                                           final float volume,
                                           final float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    /**
     * Plays a custom sound to a player.
     *
     * @param player The player that will hear the sound
     * @param sound  The custom sound to play
     * @param volume The volume of the sound
     * @param pitch  The pitch of the sound
     */
    public static void playCustomToPlayer(@NotNull final Player player, @NotNull final String sound,
                                          final float volume,
                                          final float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    /**
     * Plays a vanilla sound to a location.
     *
     * @param loc    The location where the sound will be played
     * @param sound  The vanilla sound to play
     * @param volume The volume of the sound
     * @param pitch  The pitch of the sound
     */
    public static void playVanillaToLocation(@NotNull final Location loc, @NotNull final Sound sound,
                                             final float volume, final float pitch) {
        Objects.requireNonNull(loc.getWorld(), "World is null").playSound(loc, sound, volume, pitch);
    }

    /**
     * Plays a custom sound to a location.
     *
     * @param loc    The location where the sound will be played
     * @param sound  The custom sound to play
     * @param volume The volume of the sound
     * @param pitch  The pitch of the sound
     */
    public static void playCustomToLocation(@NotNull final Location loc, @NotNull final String sound,
                                            final float volume, final float pitch) {
        Objects.requireNonNull(loc.getWorld(), "World is null").playSound(loc, sound, volume, pitch);
    }
}
