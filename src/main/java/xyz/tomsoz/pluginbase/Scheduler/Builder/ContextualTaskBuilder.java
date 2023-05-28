package xyz.tomsoz.pluginbase.Scheduler.Builder;

import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.pluginbase.Scheduler.Task;

import java.util.function.Consumer;

/**
 * Queues execution of tasks using {@link xyz.tomsoz.pluginbase.Scheduler.Scheduler}, often
 * combining parameters with variables already known by this instance.
 */
public interface ContextualTaskBuilder {
    @NotNull
    Task consume(@NotNull Consumer<Task> consumer);

    @NotNull
    Task run(@NotNull Runnable runnable);
}