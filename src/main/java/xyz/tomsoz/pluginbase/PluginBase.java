package xyz.tomsoz.pluginbase;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tomsoz.pluginbase.Commands.BaseCommand;
import xyz.tomsoz.pluginbase.Events.EventManager;
import xyz.tomsoz.pluginbase.Locale.Translator;

/**
 * An extended version of JavaPlugin. It is recommended that you extend this in your main class, but
 * you can also manually set the values in {@link PluginManager} should you wish to use your own
 * loading system.
 */
public abstract class PluginBase extends JavaPlugin {
    @Override
    public final void onLoad() {
        PluginManager.setPlugin(this);

        load();
    }

    @Override
    public final void onEnable() {
        PluginManager.setTranslator(Translator.create());
        PluginManager.setEventManager(new EventManager(this));

        enable();
    }

    @Override
    public final void onDisable() {
        for (BaseCommand cmd : PluginManager.commands) {
            cmd.unregister();
        }
        PluginManager.commands.clear();

        disable();
    }

    /**
     * Executes at early plugin startup.
     */
    protected void load() {}

    /**
     * Executes on plugin enable.
     */
    protected void enable() {}

    /**
     * Executes on plugin disable.
     */
    protected void disable() {}


    /**
     * Gets if a given plugin is enabled.
     *
     * @param name The name of the plugin
     * @return If the plugin is enabled
     */
    public boolean isPluginPresent(@NotNull final String name) {
        return getServer().getPluginManager().getPlugin(name) != null;
    }

    /**
     * Gets the translator.
     *
     * @return The translator
     */
    public Translator getTranslator() {
        return PluginManager.getTranslator();
    }

    /**
     * Gets the settings the library should use.
     *
     * @return The settings the library should use
     */
    @NotNull
    public BaseSettings getBaseSettings() {
        return PluginManager.getBaseSettings();
    }

    /**
     * Sets the settings the library should use. Default will be used if not set.
     *
     * @param baseSettings The new base settings
     */
    public void setBaseSettings(@Nullable final BaseSettings baseSettings) {

        if (baseSettings == null) {
            PluginManager.setBaseSettings(new BaseSettings() {
            });
            return;
        }

        PluginManager.setBaseSettings(baseSettings);
    }
}
