package xyz.tomsoz.pluginbase.Events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BaseEvent<E extends Event> implements Listener {
    @Getter
    protected JavaPlugin instance;

    public BaseEvent(JavaPlugin instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onEvent(E e) {
        event(e);
    }

    protected abstract void event(E e);
}
