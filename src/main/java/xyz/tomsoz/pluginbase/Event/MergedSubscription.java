package xyz.tomsoz.pluginbase.Event;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Represents a subscription to a set of events.
 *
 * @param <T> the handled type
 */
public interface MergedSubscription<T> extends Subscription {

    /**
     * Gets the handled class
     *
     * @return the handled class
     */
    @NotNull
    Class<? super T> getHandledClass();

    /**
     * Gets a set of the individual event classes being listened to
     *
     * @return the individual classes
     */
    @NotNull
    Set<Class<? extends Event>> getEventClasses();

}
