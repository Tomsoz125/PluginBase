package xyz.tomsoz.pluginbase.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import xyz.tomsoz.pluginbase.PluginManager;

public class EventManager {
    public void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, PluginManager.getPlugin());
    }
}
