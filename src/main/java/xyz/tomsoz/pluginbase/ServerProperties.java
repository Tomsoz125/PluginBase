package xyz.tomsoz.pluginbase;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import xyz.tomsoz.pluginbase.Exceptions.APIException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Utility for getting information from server.properties.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServerProperties {

    private static final Map<String, String> cache = new HashMap<>();
    private static File propertiesFile;

    /**
     * Gets a property from server.properties.
     *
     * @param key The property key
     * @return The property value, or null if failed to retrieve
     */
    public static String getProperty(final String key) {

        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        final Properties properties = new Properties();

        try (final FileInputStream in = new FileInputStream(getPropertiesFile())) {
            properties.load(in);

            final String value = properties.getProperty(key);
            cache.put(key, value);
            return value;

        } catch (final IOException ex) {
            Common.error(ex, "Failed to get server property: " + key, false);
            return null;
        }
    }

    /**
     * Clears the properties cache.
     */
    public static void clearCache() {
        cache.clear();
    }

    private static File getPropertiesFile() {

        if (propertiesFile != null) {
            return propertiesFile;
        }

        propertiesFile = new File("server.properties");

        if (!propertiesFile.exists()) {
            throw new APIException("Could not locate server.properties");
        }

        return propertiesFile;
    }
}
