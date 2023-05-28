package xyz.tomsoz.pluginbase.Event.Functional.Single;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.pluginbase.Delegate.Delegates;
import xyz.tomsoz.pluginbase.Event.Functional.FunctionalHandlerList;
import xyz.tomsoz.pluginbase.Event.SingleSubscription;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface SingleHandlerList<T extends Event> extends
        FunctionalHandlerList<T, SingleSubscription<T>> {

    @NotNull
    @Override
    default SingleHandlerList<T> consumer(@NotNull final Consumer<? super T> handler) {
        Objects.requireNonNull(handler, "handler");
        return biConsumer(Delegates.consumerToBiConsumerSecond(handler));
    }

    @NotNull
    @Override
    SingleHandlerList<T> biConsumer(@NotNull BiConsumer<SingleSubscription<T>, ? super T> handler);
}
