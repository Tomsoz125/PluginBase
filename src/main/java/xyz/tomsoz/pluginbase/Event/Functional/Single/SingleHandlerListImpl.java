package xyz.tomsoz.pluginbase.Event.Functional.Single;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.pluginbase.Event.SingleSubscription;
import xyz.tomsoz.pluginbase.PluginManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

class SingleHandlerListImpl<T extends Event> implements SingleHandlerList<T> {

    private final SingleSubscriptionBuilderImpl<T> builder;
    private final List<BiConsumer<SingleSubscription<T>, ? super T>> handlers = new ArrayList<>(1);

    SingleHandlerListImpl(@NotNull final SingleSubscriptionBuilderImpl<T> builder) {
        this.builder = builder;
    }

    @NotNull
    @Override
    public SingleHandlerList<T> biConsumer(
            @NotNull final BiConsumer<SingleSubscription<T>, ? super T> handler) {
        Objects.requireNonNull(handler, "handler");
        this.handlers.add(handler);
        return this;
    }

    @NotNull
    @Override
    public SingleSubscription<T> register() {
        if (this.handlers.isEmpty()) {
            throw new IllegalStateException("No handlers have been registered");
        }

        final BaseEventListener<T> listener = new BaseEventListener<>(this.builder, this.handlers);
        listener.register(PluginManager.getPlugin());
        return listener;
    }
}
