package xyz.tomsoz.pluginbase.Events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BaseEvent<T extends Event> implements Listener {
    @Getter
    JavaPlugin i;

    @EventHandler
    public void onEvent(T e) {
        event(e);
    }

    protected abstract void event(T e);

    public BaseEvent<T> register(JavaPlugin plugin) {
        i = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        return this;
    }
}
