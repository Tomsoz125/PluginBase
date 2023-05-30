package xyz.tomsoz.pluginbase.Locale;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tomsoz.pluginbase.Exceptions.APIException;
import xyz.tomsoz.pluginbase.PluginManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.*;

final class SimpleTranslator implements Translator {
    private static final String DEFAULT_RESOURCE_BUNDLE = "pluginbase";
    private static final LinkedList<LocaleReader> EMPTY_LIST = new LinkedList<>();

    private final Map<Locale, LinkedList<LocaleReader>> registeredBundles = new HashMap<>();
    private volatile Locale locale = Locale.ENGLISH;

    SimpleTranslator() {
        loadDefault();
    }

    public void loadDefault() {
        addResourceBundle(DEFAULT_RESOURCE_BUNDLE);
    }

    @Override
    public void addResourceBundleFromFolder(@NotNull String resourceBundle) {
        final File folder = PluginManager.getPlugin().getDataFolder().toPath().resolve("locales").toFile();

        if (!folder.exists()) return;

        final URL[] urls;

        try {
            urls = new URL[] {folder.toURI().toURL()};
        } catch (final MalformedURLException ex) {
            throw new APIException("Could not load resource bundle from filesystem: " + resourceBundle,ex);
        }

        addResourceBundle(new URLClassLoader(urls), resourceBundle);
    }

    @Override
    public @NotNull String get(@NotNull String key) {
        return get(key, locale);
    }

    @Override
    public @NotNull String get(@NotNull String key, @Nullable Object... objects) {
        return MessageFormat.format(get(key, locale), objects);
    }

    @Override
    public @NotNull String get(@NotNull String key, @NotNull Locale locale) {
        for (final LocaleReader registeredBundle : registeredBundles.getOrDefault(locale, EMPTY_LIST)) {
            if (registeredBundle.containsKey(key)) {
                return registeredBundle.get(key);
            }
        }
        for (final LocaleReader registeredBundle : registeredBundles.getOrDefault(this.locale, EMPTY_LIST)) {
            if (registeredBundle.containsKey(key)) {
                return registeredBundle.get(key);
            }
        }
        return key;
    }

    @Override
    public void add(@NotNull LocaleReader reader) {
        final LinkedList<LocaleReader> list = registeredBundles.computeIfAbsent(reader.getLocale(), v -> new LinkedList<>());
        list.push(reader);
    }

    @Override
    public @NotNull Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(@NotNull Locale locale) {
        this.locale = locale;
    }

    @Override
    public void addResourceBundle(@NotNull ClassLoader loader, @NotNull String resourceBundle, @NotNull Locale... locales) {
        for (final Locale l : locales) {
            try {
                final ResourceBundle bundle = ResourceBundle.getBundle(resourceBundle, l, loader, UTF8Control.INSTANCE);
                add(bundle);
            } catch (final MissingResourceException ignored) {}
        }
    }

    @Override
    public void addResourceBundle(@NotNull ClassLoader loader, @NotNull String resourceBundle) {
        for (final Locale l : Locales.getLocales()) {
            try {
                final ResourceBundle bundle = ResourceBundle.getBundle(resourceBundle, l, loader, UTF8Control.INSTANCE);
                add(bundle);
            } catch (final MissingResourceException ignored) {}
        }
    }

    @Override
    public void clear() {
        registeredBundles.clear();
    }

    @Override
    public void add(@NotNull ResourceBundle resourceBundle) {
        add(LocaleReader.wrap(resourceBundle));
    }
}
