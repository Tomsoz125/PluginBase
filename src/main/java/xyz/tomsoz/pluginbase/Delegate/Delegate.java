package xyz.tomsoz.pluginbase.Delegate;

/**
 * Represents a class which delegates calls to a different object.
 *
 * @param <T> The delegate type
 */
public interface Delegate<T> {

    /**
     * Resolves for the provided object.
     *
     * @param obj The object to resolve
     * @return The delegated result
     */
    static Object resolve(Object obj) {
        while (obj instanceof Delegate<?>) {
            final Delegate<?> delegate = (Delegate<?>) obj;
            obj = delegate.getDelegate();
        }

        return obj;
    }

    /**
     * Gets the delegate object.
     *
     * @return The delegate object
     */
    T getDelegate();
}
