package xyz.tomsoz.pluginbase.Event.Functional.Merged;

import xyz.tomsoz.pluginbase.Event.MergedSubscription;
import xyz.tomsoz.pluginbase.PluginManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

class MergedHandlerListImpl<T> implements MergedHandlerList<T> {

    private final MergedSubscriptionBuilderImpl<T> builder;
    private final List<BiConsumer<MergedSubscription<T>, ? super T>> handlers = new ArrayList<>(1);

    MergedHandlerListImpl(@NotNull final MergedSubscriptionBuilderImpl<T> builder) {
        this.builder = builder;
    }

    @NotNull
    @Override
    public MergedHandlerList<T> biConsumer(
            @NotNull final BiConsumer<MergedSubscription<T>, ? super T> handler) {
        Objects.requireNonNull(handler, "handler");
        this.handlers.add(handler);
        return this;
    }

    @NotNull
    @Override
    public MergedSubscription<T> register() {
        if (this.handlers.isEmpty()) {
            throw new IllegalStateException("No handlers have been registered");
        }

        final BaseMergedEventListener<T> listener = new BaseMergedEventListener<>(this.builder,
                this.handlers);
        listener.register(PluginManager.getPlugin());
        return listener;
    }
}
