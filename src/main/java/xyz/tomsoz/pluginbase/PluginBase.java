package xyz.tomsoz.pluginbase;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
        PluginManager.setAdventure(BukkitAudiences.create(this));

        enable();
    }

    @Override
    public final void onDisable() {
        disable();

        if (getAdventure() != null) {
            getAdventure().close();
            PluginManager.setAdventure(null);
        }
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
     * Gets the BukkitAudiences instance to use for Adventure.
     *
     * @return The BukkitAudiences instance to use for Adventure
     */
    public BukkitAudiences getAdventure() {
        return PluginManager.getAdventure();
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
