package xyz.tomsoz.pluginbase;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.pluginbase.Delegate.Delegate;
import xyz.tomsoz.pluginbase.Exceptions.SchedulerTaskException;
import xyz.tomsoz.pluginbase.Promise.ThreadContext;
import xyz.tomsoz.pluginbase.Scheduler.BaseExecutors;
import xyz.tomsoz.pluginbase.Scheduler.Builder.TaskBuilder;
import xyz.tomsoz.pluginbase.Scheduler.Scheduler;
import xyz.tomsoz.pluginbase.Scheduler.Task;
import xyz.tomsoz.pluginbase.Scheduler.Ticks;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Provides common instances of {@link Scheduler}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Schedulers {

    private static final Scheduler SYNC_SCHEDULER = new SyncScheduler();
    private static final Scheduler ASYNC_SCHEDULER = new AsyncScheduler();

    /**
     * Gets a scheduler for the given context.
     *
     * @param context the context
     * @return a scheduler
     */
    @NotNull
    public static Scheduler get(final ThreadContext context) {
        switch (context) {
            case SYNC:
                return sync();
            case ASYNC:
                return async();
            default:
                throw new AssertionError();
        }
    }

    /**
     * Returns a "sync" scheduler, which executes tasks on the main server thread.
     *
     * @return a sync executor instance
     */
    @NotNull
    public static Scheduler sync() {
        return SYNC_SCHEDULER;
    }

    /**
     * Returns an "async" scheduler, which executes tasks asynchronously.
     *
     * @return an async executor instance
     */
    @NotNull
    public static Scheduler async() {
        return ASYNC_SCHEDULER;
    }

    /**
     * Gets Bukkit's scheduler.
     *
     * @return bukkit's scheduler
     */
    @NotNull
    public static BukkitScheduler bukkit() {
        return Bukkit.getScheduler();
    }

    /**
     * Gets a {@link TaskBuilder} instance
     *
     * @return a task builder
     */
    @NotNull
    public static TaskBuilder builder() {
        return TaskBuilder.newBuilder();
    }

    private static final class SyncScheduler implements Scheduler {

        @Override
        public void execute(final Runnable runnable) {
            BaseExecutors.sync().execute(runnable);
        }

        @NotNull
        @Override
        public ThreadContext getContext() {
            return ThreadContext.SYNC;
        }

        @NotNull
        @Override
        public Task runRepeating(@NotNull final Consumer<Task> consumer, final long delayTicks,
                                 final long intervalTicks) {
            Objects.requireNonNull(consumer, "consumer");
            final BaseTask task = new BaseTask(consumer);
            task.runTaskTimer(PluginManager.getPlugin(), delayTicks, intervalTicks);
            return task;
        }

        @NotNull
        @Override
        public Task runRepeating(@NotNull final Consumer<Task> consumer, final long delay,
                                 @NotNull final TimeUnit delayUnit, final long interval,
                                 @NotNull final TimeUnit intervalUnit) {
            return runRepeating(consumer, Ticks.from(delay, delayUnit),
                    Ticks.from(interval, intervalUnit));
        }
    }

    private static final class AsyncScheduler implements Scheduler {

        @Override
        public void execute(final Runnable runnable) {
            BaseExecutors.asyncBase().execute(runnable);
        }

        @NotNull
        @Override
        public ThreadContext getContext() {
            return ThreadContext.ASYNC;
        }

        @NotNull
        @Override
        public Task runRepeating(@NotNull final Consumer<Task> consumer, final long delayTicks,
                                 final long intervalTicks) {
            Objects.requireNonNull(consumer, "consumer");
            final BaseTask task = new BaseTask(consumer);
            task.runTaskTimerAsynchronously(PluginManager.getPlugin(), delayTicks, intervalTicks);
            return task;
        }

        @NotNull
        @Override
        public Task runRepeating(@NotNull final Consumer<Task> consumer, final long delay,
                                 @NotNull final TimeUnit delayUnit, final long interval,
                                 @NotNull final TimeUnit intervalUnit) {
            Objects.requireNonNull(consumer, "consumer");
            return new BaseAsyncTask(consumer, delay, delayUnit, interval, intervalUnit);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class BaseTask extends BukkitRunnable implements Task, Delegate<Consumer<Task>> {

        private final Consumer<Task> backingTask;

        private final AtomicInteger counter = new AtomicInteger(0);
        private final AtomicBoolean cancelled = new AtomicBoolean(false);

        @Override
        public void run() {
            if (this.cancelled.get()) {
                cancel();
                return;
            }

            try {
                this.backingTask.accept(this);
                this.counter.incrementAndGet();
            } catch (final Throwable e) {
                Common.error(new SchedulerTaskException(e),
                        "Error whilst executing scheduler task.", false);
            }

            if (this.cancelled.get()) {
                cancel();
            }
        }

        @Override
        public int getTimesRan() {
            return this.counter.get();
        }

        @Override
        public boolean stop() {
            return !this.cancelled.getAndSet(true);
        }

        @Override
        public int getBukkitId() {
            return getTaskId();
        }

        @Override
        public boolean isClosed() {
            return this.cancelled.get();
        }

        @Override
        public Consumer<Task> getDelegate() {
            return this.backingTask;
        }
    }

    private static class BaseAsyncTask implements Runnable, Task, Delegate<Consumer<Task>> {

        private final Consumer<Task> backingTask;
        private final ScheduledFuture<?> future;

        private final AtomicInteger counter = new AtomicInteger(0);
        private final AtomicBoolean cancelled = new AtomicBoolean(false);

        private BaseAsyncTask(final Consumer<Task> backingTask, final long delay,
                              final TimeUnit delayUnit,
                              final long interval, final TimeUnit intervalUnit) {
            this.backingTask = backingTask;
            this.future = BaseExecutors.asyncBase()
                    .scheduleAtFixedRate(this, delayUnit.toNanos(delay), intervalUnit.toNanos(interval),
                            TimeUnit.NANOSECONDS);
        }

        @Override
        public void run() {
            if (this.cancelled.get()) {
                return;
            }

            try {
                this.backingTask.accept(this);
                this.counter.incrementAndGet();
            } catch (final Throwable e) {
                Common.error(new SchedulerTaskException(e),
                        "Error whilst executing scheduler task.", false);
            }
        }

        @Override
        public int getTimesRan() {
            return this.counter.get();
        }

        @Override
        public boolean stop() {
            if (!this.cancelled.getAndSet(true)) {
                this.future.cancel(false);
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int getBukkitId() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isClosed() {
            return this.cancelled.get();
        }

        @Override
        public Consumer<Task> getDelegate() {
            return this.backingTask;
        }
    }
}
