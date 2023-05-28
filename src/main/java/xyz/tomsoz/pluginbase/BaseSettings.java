package xyz.tomsoz.pluginbase;

import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Settings for the plugin base. Most of these methods should be overriden to suite your needs.
 * Apply the settings using {@link PluginBase}'s #setBaseSettings().
 */
public interface BaseSettings {
    /**
     * The prefix for chat and console messages. Not localized. Colorized internally.
     *
     * @return The prefix
     */
    default String prefix() {
        return "&r";
    }

    /**
     * The color scheme of the plugin.
     *
     * @return The color scheme
     */
    default ColourScheme colourScheme() {
        return null;
    }


    // ---------------------------------------------------------------------------------
    // TIME FORMATS
    // ---------------------------------------------------------------------------------

    /**
     * The format for dates and times combined.
     *
     * @return The date and time format
     */
    default String dateTimeFormat() {
        return "HH:mm dd/MM/yyyy";
    }

    /**
     * The format for dates.
     *
     * @return The date format
     */
    default String dateFormat() {
        return "dd/MM/yyyy";
    }

    /**
     * A 3-color color scheme for messages or other text.
     */
    @Data
    class ColourScheme {
        /**
         * The primarily color, used as &p.
         */
        @NotNull private final String primary;

        /**
         * The secondary color, used as &s.
         */
        @NotNull private final String secondary;

        /**
         * The tertiary color, used as &t.
         */
        @NotNull private final String tertiary;

        /**
         * Gets the color scheme from a configuration section.
         *
         * @param section The configuration section containing the color scheme
         * @return The color scheme from the configuration section
         */
        @NotNull
        public static ColourScheme fromConfig(@NotNull final ConfigurationSection section) {
            return new ColourScheme(
                    Objects.requireNonNull(section.getString("primary"), "Primary colour not defined."),
                    Objects.requireNonNull(section.getString("secondary"), "Secondary colour not defined."),
                    Objects.requireNonNull(section.getString("tertiary"), "Tertiary colour not defined.")
            );
        }
    }
}
