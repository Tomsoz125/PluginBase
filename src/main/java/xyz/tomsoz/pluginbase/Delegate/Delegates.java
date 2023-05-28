package xyz.tomsoz.pluginbase.Delegate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import xyz.tomsoz.pluginbase.Exceptions.APIException;

import java.util.concurrent.Callable;
import java.util.function.*;

/**
 * A collection of utility methods for delegating Java 8 functions.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Delegates {

    public static <T> Consumer<T> runnableToConsumer(final Runnable runnable) {
        return new RunnableToConsumer<>(runnable);
    }

    public static Supplier<Void> runnableToSupplier(final Runnable runnable) {
        return new RunnableToSupplier<>(runnable);
    }

    public static <T> Supplier<T> callableToSupplier(final Callable<T> callable) {
        return new CallableToSupplier<>(callable);
    }

    public static <T, U> BiConsumer<T, U> consumerToBiConsumerFirst(final Consumer<T> consumer) {
        return new ConsumerToBiConsumerFirst<>(consumer);
    }

    public static <T, U> BiConsumer<T, U> consumerToBiConsumerSecond(final Consumer<U> consumer) {
        return new ConsumerToBiConsumerSecond<>(consumer);
    }

    public static <T, U> BiPredicate<T, U> predicateToBiPredicateFirst(final Predicate<T> predicate) {
        return new PredicateToBiPredicateFirst<>(predicate);
    }

    public static <T, U> BiPredicate<T, U> predicateToBiPredicateSecond(
            final Predicate<U> predicate) {
        return new PredicateToBiPredicateSecond<>(predicate);
    }

    public static <T, U> Function<T, U> consumerToFunction(final Consumer<T> consumer) {
        return new ConsumerToFunction<>(consumer);
    }

    public static <T, U> Function<T, U> runnableToFunction(final Runnable runnable) {
        return new RunnableToFunction<>(runnable);
    }

    private abstract static class AbstractDelegate<T> implements Delegate<T> {

        final T delegate;

        AbstractDelegate(final T delegate) {
            this.delegate = delegate;
        }

        @Override
        public T getDelegate() {
            return delegate;
        }
    }

    private static final class RunnableToConsumer<T> extends AbstractDelegate<Runnable> implements
            Consumer<T> {

        RunnableToConsumer(final Runnable delegate) {
            super(delegate);
        }

        @Override
        public void accept(final T t) {
            delegate.run();
        }
    }

    private static final class CallableToSupplier<T> extends AbstractDelegate<Callable<T>> implements
            Supplier<T> {

        CallableToSupplier(final Callable<T> delegate) {
            super(delegate);
        }

        @Override
        public T get() {
            try {
                return delegate.call();
            } catch (final RuntimeException ex) {
                throw ex;
            } catch (final Exception ex) {
                throw new APIException(ex);
            }
        }
    }

    private static final class RunnableToSupplier<T> extends AbstractDelegate<Runnable> implements
            Supplier<T> {

        RunnableToSupplier(final Runnable delegate) {
            super(delegate);
        }

        @Override
        public T get() {
            delegate.run();
            return null;
        }
    }

    private static final class ConsumerToBiConsumerFirst<T, U> extends
            AbstractDelegate<Consumer<T>> implements BiConsumer<T, U> {

        ConsumerToBiConsumerFirst(final Consumer<T> delegate) {
            super(delegate);
        }

        @Override
        public void accept(final T t, final U u) {
            delegate.accept(t);
        }
    }

    private static final class ConsumerToBiConsumerSecond<T, U> extends
            AbstractDelegate<Consumer<U>> implements BiConsumer<T, U> {

        ConsumerToBiConsumerSecond(final Consumer<U> delegate) {
            super(delegate);
        }

        @Override
        public void accept(final T t, final U u) {
            delegate.accept(u);
        }
    }

    private static final class PredicateToBiPredicateFirst<T, U> extends
            AbstractDelegate<Predicate<T>> implements BiPredicate<T, U> {

        PredicateToBiPredicateFirst(final Predicate<T> delegate) {
            super(delegate);
        }

        @Override
        public boolean test(final T t, final U u) {
            return delegate.test(t);
        }
    }

    private static final class PredicateToBiPredicateSecond<T, U> extends
            AbstractDelegate<Predicate<U>> implements BiPredicate<T, U> {

        PredicateToBiPredicateSecond(final Predicate<U> delegate) {
            super(delegate);
        }

        @Override
        public boolean test(final T t, final U u) {
            return delegate.test(u);
        }
    }

    private static final class ConsumerToFunction<T, R> extends
            AbstractDelegate<Consumer<T>> implements Function<T, R> {

        ConsumerToFunction(final Consumer<T> delegate) {
            super(delegate);
        }

        @Override
        public R apply(final T t) {
            delegate.accept(t);
            return null;
        }
    }

    private static final class RunnableToFunction<T, R> extends AbstractDelegate<Runnable> implements
            Function<T, R> {

        RunnableToFunction(final Runnable delegate) {
            super(delegate);
        }

        @Override
        public R apply(final T t) {
            delegate.run();
            return null;
        }
    }
}
