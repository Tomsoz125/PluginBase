package xyz.tomsoz.pluginbase.Event.Functional.Merged;

import xyz.tomsoz.pluginbase.Delegate.Delegates;
import xyz.tomsoz.pluginbase.Event.MergedSubscription;
import xyz.tomsoz.pluginbase.Event.Functional.FunctionalHandlerList;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public interface MergedHandlerList<T> extends FunctionalHandlerList<T, MergedSubscription<T>> {

    @NotNull
    @Override
    default MergedHandlerList<T> consumer(@NotNull final Consumer<? super T> handler) {
        Objects.requireNonNull(handler, "handler");
        return biConsumer(Delegates.consumerToBiConsumerSecond(handler));
    }

    @NotNull
    @Override
    MergedHandlerList<T> biConsumer(@NotNull BiConsumer<MergedSubscription<T>, ? super T> handler);
}
