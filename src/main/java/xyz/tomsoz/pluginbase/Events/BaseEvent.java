package xyz.tomsoz.pluginbase.Events;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public abstract class BaseEvent<T extends Event> implements Listener {

    @EventHandler
    public void onEvent(T e) {
        event(e);
    }

    protected abstract void event(T e);
}
