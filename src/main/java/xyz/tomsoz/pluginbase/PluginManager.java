package xyz.tomsoz.pluginbase;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.pluginbase.Events.EventManager;
import xyz.tomsoz.pluginbase.Exceptions.APIException;
import xyz.tomsoz.pluginbase.Locale.Translator;

/**
 * A manager containing all other managers associated with the library, as well as the
 * {@link JavaPlugin} the library is working with.
 */
public class PluginManager {

    private static JavaPlugin plugin = null;
    @Getter
    @Setter
    private static EventManager eventManager = null;
    private static Thread mainThread = null;

    /**
     * The translator used for handling localized messages.
     */
    @NotNull @Getter @Setter private static BaseSettings baseSettings = new BaseSettings() {};

    /**
     * The settings the library should use.
     */
    @NotNull @Getter @Setter private static Translator translator;

    /**
     * Gets a never-null instance of the {@link JavaPlugin} the library is currently working with.
     *
     * @return The {@link JavaPlugin} the library is currently working with
     * @throws RuntimeException If the plugin is not set
     */
    @NotNull
    public static JavaPlugin getPlugin() {
        if (plugin == null) {
            throw new APIException("Main class is not set!");
        }
        return plugin;
    }

    /**
     * Sets the {@link JavaPlugin} that the library is currently working with.
     *
     * @param newPlugin The new instance of {@link JavaPlugin}.
     */
    public static void setPlugin(final JavaPlugin newPlugin) {
        plugin = newPlugin;
    }

    /**
     * Gets the main thread of the server that is using the plugin.
     *
     * @return The main thread
     */
    @NotNull
    public static synchronized Thread getMainThread() {
        if (mainThread == null && Bukkit.getServer().isPrimaryThread()) {
            mainThread = Thread.currentThread();
        }

        return mainThread;
    }
}
