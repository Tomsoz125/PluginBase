package xyz.tomsoz.pluginbase.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import xyz.tomsoz.pluginbase.PluginManager;

import java.util.ArrayList;

public class EventManager {
    public static ArrayList<Class<?>> events = new ArrayList<>();

    public void registerAll() {
        for (Class<?> c : events) {
            Object obj = null;
            try {
                obj = c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            if (obj == null) continue;

            if (c.isAssignableFrom(Listener.class)) {
                Bukkit.getPluginManager().registerEvents((Listener) obj, PluginManager.getPlugin());
            }
        }
    }
}
