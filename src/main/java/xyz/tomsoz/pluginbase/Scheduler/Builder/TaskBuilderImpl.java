package xyz.tomsoz.pluginbase.Scheduler.Builder;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.pluginbase.Promise.Promise;
import xyz.tomsoz.pluginbase.Promise.ThreadContext;
import xyz.tomsoz.pluginbase.Scheduler.Task;
import xyz.tomsoz.pluginbase.Schedulers;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

class TaskBuilderImpl implements TaskBuilder {

    static final TaskBuilder INSTANCE = new TaskBuilderImpl();

    private final TaskBuilder.ThreadContextual sync;
    private final ThreadContextual async;

    private TaskBuilderImpl() {
        this.sync = new ThreadContextualBuilder(ThreadContext.SYNC);
        this.async = new ThreadContextualBuilder(ThreadContext.ASYNC);
    }

    @NotNull
    @Override
    public TaskBuilder.ThreadContextual sync() {
        return this.sync;
    }

    @NotNull
    @Override
    public TaskBuilder.ThreadContextual async() {
        return this.async;
    }

    private static final class ThreadContextualBuilder implements TaskBuilder.ThreadContextual {

        private final ThreadContext context;
        private final ContextualPromiseBuilder instant;

        ThreadContextualBuilder(final ThreadContext context) {
            this.context = context;
            this.instant = new ContextualPromiseBuilderImpl(context);
        }

        @NotNull
        @Override
        public ContextualPromiseBuilder now() {
            return this.instant;
        }

        @NotNull
        @Override
        public DelayedTick after(final long ticks) {
            return new DelayedTickBuilder(this.context, ticks);
        }

        @NotNull
        @Override
        public DelayedTime after(final long duration, @NotNull final TimeUnit unit) {
            return new DelayedTimeBuilder(this.context, duration, unit);
        }

        @NotNull
        @Override
        public ContextualTaskBuilder afterAndEvery(final long ticks) {
            return new ContextualTaskBuilderTickImpl(this.context, ticks, ticks);
        }

        @NotNull
        @Override
        public ContextualTaskBuilder afterAndEvery(final long duration, @NotNull final TimeUnit unit) {
            return new ContextualTaskBuilderTimeImpl(this.context, duration, unit, duration, unit);
        }

        @NotNull
        @Override
        public ContextualTaskBuilder every(final long ticks) {
            return new ContextualTaskBuilderTickImpl(this.context, 0, ticks);
        }

        @NotNull
        @Override
        public ContextualTaskBuilder every(final long duration, @NotNull final TimeUnit unit) {
            return new ContextualTaskBuilderTimeImpl(this.context, 0, TimeUnit.NANOSECONDS, duration,
                    unit);
        }
    }

    @RequiredArgsConstructor
    private static final class DelayedTickBuilder implements TaskBuilder.DelayedTick {

        private final ThreadContext context;
        private final long delay;

        @NotNull
        @Override
        public <T> Promise<T> supply(@NotNull final Supplier<T> supplier) {
            return Schedulers.get(this.context).supplyLater(supplier, this.delay);
        }

        @NotNull
        @Override
        public <T> Promise<T> call(@NotNull final Callable<T> callable) {
            return Schedulers.get(this.context).callLater(callable, this.delay);
        }

        @NotNull
        @Override
        public Promise<Void> run(@NotNull final Runnable runnable) {
            return Schedulers.get(this.context).runLater(runnable, this.delay);
        }

        @NotNull
        @Override
        public ContextualTaskBuilder every(final long ticks) {
            return new ContextualTaskBuilderTickImpl(this.context, this.delay, ticks);
        }
    }

    @RequiredArgsConstructor
    private static final class DelayedTimeBuilder implements TaskBuilder.DelayedTime {

        private final ThreadContext context;
        private final long delay;
        private final TimeUnit delayUnit;

        @NotNull
        @Override
        public <T> Promise<T> supply(@NotNull final Supplier<T> supplier) {
            return Schedulers.get(this.context).supplyLater(supplier, this.delay, this.delayUnit);
        }

        @NotNull
        @Override
        public <T> Promise<T> call(@NotNull final Callable<T> callable) {
            return Schedulers.get(this.context).callLater(callable, this.delay, this.delayUnit);
        }

        @NotNull
        @Override
        public Promise<Void> run(@NotNull final Runnable runnable) {
            return Schedulers.get(this.context).runLater(runnable, this.delay, this.delayUnit);
        }

        @NotNull
        @Override
        public ContextualTaskBuilder every(final long duration, final TimeUnit unit) {
            return new ContextualTaskBuilderTimeImpl(this.context, this.delay, this.delayUnit, duration,
                    unit);
        }
    }

    private static class ContextualPromiseBuilderImpl implements ContextualPromiseBuilder {

        private final ThreadContext context;

        ContextualPromiseBuilderImpl(final ThreadContext context) {
            this.context = context;
        }

        @NotNull
        @Override
        public <T> Promise<T> supply(@NotNull final Supplier<T> supplier) {
            return Schedulers.get(this.context).supply(supplier);
        }

        @NotNull
        @Override
        public <T> Promise<T> call(@NotNull final Callable<T> callable) {
            return Schedulers.get(this.context).call(callable);
        }

        @NotNull
        @Override
        public Promise<Void> run(@NotNull final Runnable runnable) {
            return Schedulers.get(this.context).run(runnable);
        }
    }

    @RequiredArgsConstructor
    private static class ContextualTaskBuilderTickImpl implements ContextualTaskBuilder {

        private final ThreadContext context;
        private final long delay;
        private final long interval;

        @NotNull
        @Override
        public Task consume(@NotNull final Consumer<Task> consumer) {
            return Schedulers.get(this.context).runRepeating(consumer, this.delay, this.interval);
        }

        @NotNull
        @Override
        public Task run(@NotNull final Runnable runnable) {
            return Schedulers.get(this.context).runRepeating(runnable, this.delay, this.interval);
        }
    }

    @RequiredArgsConstructor
    private static class ContextualTaskBuilderTimeImpl implements ContextualTaskBuilder {

        private final ThreadContext context;
        private final long delay;
        private final TimeUnit delayUnit;
        private final long interval;
        private final TimeUnit intervalUnit;

        @NotNull
        @Override
        public Task consume(@NotNull final Consumer<Task> consumer) {
            return Schedulers.get(this.context)
                    .runRepeating(consumer, this.delay, this.delayUnit, this.interval, this.intervalUnit);
        }

        @NotNull
        @Override
        public Task run(@NotNull final Runnable runnable) {
            return Schedulers.get(this.context)
                    .runRepeating(runnable, this.delay, this.delayUnit, this.interval, this.intervalUnit);
        }
    }
}
