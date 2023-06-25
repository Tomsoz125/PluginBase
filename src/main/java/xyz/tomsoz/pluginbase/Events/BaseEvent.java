package xyz.tomsoz.pluginbase.Events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tomsoz.pluginbase.PluginManager;

public abstract class BaseEvent<E extends Event> {
    @Getter
    protected JavaPlugin instance;
    @Getter
    EventPriority priority;
    @Getter
    Class<E> clazz;

    public BaseEvent(JavaPlugin instance, Class<E> clazz) {
        this.instance = instance;
        this.priority = EventPriority.NORMAL;
        this.clazz = clazz;
        register();
    }

    public BaseEvent(JavaPlugin instance, Class<E> clazz, EventPriority priority) {
        this.instance = instance;
        this.priority = priority;
        this.clazz = clazz;
        register();
    }

    protected abstract void event(E e);

    public void register() {
        PluginManager.getEventManager().register(this, clazz, priority, this::event);
    }
}
