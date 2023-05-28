package xyz.tomsoz.pluginbase.Locale;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A locale reader is a source in which messages for a specific locale are fetched. This can be a
 * file, a resource bundle, or even a remote source.
 */
public interface LocaleReader {
    /**
     * Returns whether this reader contains a mapping for the given key.
     *
     * @param key Key to check for
     * @return {@code true} if this reader has a mapping for the key
     */
    boolean containsKey(@NotNull final String key);

    /**
     * Returns the mapping value for this key. It is recommended that this only return values if
     * {@link #containsKey(String)} is true, otherwise throwing an exception to avoid confusion.
     *
     * @param key Key to fetch for
     * @return The string value
     */
    String get(@NotNull String key);

    /**
     * Returns the mapping value for this key. It is recommended that this only return values if
     * {@link #containsKey(String)} is true, otherwise throwing an exception to avoid confusion.
     *
     * @param key Key to fetch for
     * @return The string array value
     */
    String[] getArray(@NotNull final String key);

    /**
     * Returns the locale of by this reader
     *
     * @return The reader's locale
     */
    @NotNull Locale getLocale();

    /**
     * Wraps a {@link ResourceBundle} in a {@link LocaleReader}.
     *
     * @param bundle Bundle to wrap
     * @return The locale reader
     */
    static @NotNull LocaleReader wrap(@NotNull final ResourceBundle bundle) {
        return new ResourceBundleLocaleReader(bundle);
    }
}
