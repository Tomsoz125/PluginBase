package xyz.tomsoz.pluginbase.Locale.Reader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.pluginbase.Locale.LocaleReader;

import java.util.Locale;

/**
 * A locale reader that uses Bukkit config files (such as .yml) instead of standard resource bundles
 * (.properties). This should typically only be used if you want to have a singular locale file,
 * such as a "messages.yml".
 *
 * @see xyz.tomsoz.pluginbase.Locale.Translator#add(LocaleReader)
 */
@RequiredArgsConstructor
public class ConfigLocaleReader implements LocaleReader {

    @NotNull private final FileConfiguration config;
    @Getter @NotNull private final Locale locale;

    @Override
    public boolean containsKey(@NotNull final String key) {
        return config.contains(key);
    }

    @Override
    public String get(@NotNull final String key) {
        return config.getString(key);
    }

    @Override
    public String[] getArray(@NotNull final String key) {
        return config.getStringList(key).toArray(new String[0]);
    }
}