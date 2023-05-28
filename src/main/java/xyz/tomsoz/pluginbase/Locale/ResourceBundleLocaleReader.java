package xyz.tomsoz.pluginbase.Locale;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Represents a {@link LocaleReader} that fetches its values from a {@link ResourceBundle}.
 */
final class ResourceBundleLocaleReader implements LocaleReader {
    private final ResourceBundle resourceBundle;
    public ResourceBundleLocaleReader(@NotNull final ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public boolean containsKey(@NotNull String key) {
        return resourceBundle.containsKey(key);
    }

    @Override
    public String get(@NotNull String key) {
        return resourceBundle.getString(key);
    }

    @Override
    public String[] getArray(@NotNull String key) {
        return resourceBundle.getStringArray(key);
    }

    @Override
    public @NotNull Locale getLocale() {
        return resourceBundle.getLocale();
    }
}
