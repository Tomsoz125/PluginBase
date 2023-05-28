package xyz.tomsoz.pluginbase.Locale;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Represents a translator. A translator allows localizing messages, adding bundles, registering
 * custom locales, and changing the default locale.
 */
public interface Translator {
    /**
     * Creates a new {@link Translator}
     *
     * @return The newly created translator
     */
    static @NotNull Translator create() {
        return new SimpleTranslator();
    }

    /**
     * Returns the message that corresponds to the given key, using the current {@link #getLocale()}.
     * If no such message is found, the key will be returned.
     *
     * @param key Message key to fetch with
     * @return The translated message, or the key if not found.
     */
    @NotNull String get(@NotNull String key);

    /**
     * Returns the message that corresponds to the given key, using the given locale. If no such
     * message is found, the key will be returned.
     *
     * @param key    Message key to fetch with
     * @param locale Locale to get with
     * @return The translated message, or the key if not found.
     */
    @NotNull String get(@NotNull String key, @NotNull Locale locale);

    /**
     * Adds the given locale reader to this translator.
     *
     * @param reader The locale reader to add
     */
    void add(@NotNull LocaleReader reader);

    /**
     * Registers the given resource bundle
     *
     * @param resourceBundle Resource bundle to register
     */
    void add(@NotNull ResourceBundle resourceBundle);

    /**
     * Adds the given resource bundle from "/locales" in the plugin data folder. This will only
     * register for the given {@link Locale}s.
     * <p>
     * For example, if you have the following files:
     * <ul>
     *     <li>foo_en_US.properties</li>
     *     <li>foo_fr.properties</li>
     *     <li>foo_de.properties</li>
     * </ul>
     * The resource bundle will be "foo", and locales will be
     * each {@link Locales#ENGLISH}, {@link Locales#FRENCH} and {@link Locales#GERMAN}.
     *
     * @param resourceBundle Resource bundle to register
     */
    void addResourceBundleFromFolder(@NotNull final String resourceBundle);

    /**
     * Adds the given resource bundle. This will only register for the given {@link Locale}s.
     * <p>
     * For example, if you have the following files:
     * <ul>
     *     <li>foo_en_US.properties</li>
     *     <li>foo_fr.properties</li>
     *     <li>foo_de.properties</li>
     * </ul>
     * The resource bundle will be "foo", and locales will be
     * each {@link Locales#ENGLISH}, {@link Locales#FRENCH} and {@link Locales#GERMAN}.
     *
     * @param loader         Class loader for the bundle
     * @param resourceBundle Resource bundle to register
     * @param locales        Locales to register for
     */
    void addResourceBundle(@NotNull ClassLoader loader, @NotNull String resourceBundle, @NotNull Locale... locales);

    /**
     * Adds the given resource bundle. This will automatically check for all locales in
     * {@link Locales}.
     * <p>
     * For example, if you have the following files:
     * <ul>
     *     <li>foo_en_US.properties</li>
     *     <li>foo_fr.properties</li>
     *     <li>foo_de.properties</li>
     * </ul>
     * The resource bundle will be "foo", and locales will be
     * each {@link Locales#ENGLISH}, {@link Locales#FRENCH} and {@link Locales#GERMAN}.
     *
     * @param loader         Class loader for the bundle
     * @param resourceBundle Resource bundle to register
     */
    void addResourceBundle(@NotNull ClassLoader loader, @NotNull String resourceBundle);

    /**
     * Clears all currently registered locales.
     */
    void clear();

    /**
     * Gets the current, default locale used by this translator
     *
     * @return The default locale
     */
    @NotNull Locale getLocale();

    /**
     * Sets the locale of this translator.
     *
     * @param locale The locale of this translator
     */
    void setLocale(@NotNull Locale locale);

    /**
     * Adds the given resource bundle from the current class loader.
     *
     * @param resourceBundle Resource bundle to register
     * @see #addResourceBundle(ClassLoader, String, Locale...)
     */
    default void addResourceBundle(@NotNull final String resourceBundle, @NotNull final Locale... locales) {
        addResourceBundle(getClass().getClassLoader(), resourceBundle, locales);
    }

    /**
     * Adds the given resource bundle from the current class loader.
     *
     * @param resourceBundle Resource bundle to register
     * @see #addResourceBundle(ClassLoader, String)
     */
    default void addResourceBundle(@NotNull final String resourceBundle) {
        addResourceBundle(getClass().getClassLoader(), resourceBundle);
    }
}
