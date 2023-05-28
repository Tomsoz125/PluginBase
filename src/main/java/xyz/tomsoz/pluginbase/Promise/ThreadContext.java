package xyz.tomsoz.pluginbase.Promise;

import xyz.tomsoz.pluginbase.PluginManager;

/**
 * Represents the two main types of {@link Thread} on the server.
 */
public enum ThreadContext {

    SYNC, ASYNC;

    /**
     * Gets the thread context of the current thread.
     *
     * @return The threat context of the current thread
     */
    public static ThreadContext forCurrentThread() {
        return forThread(Thread.currentThread());
    }

    /**
     * Gets the thread context of the specified thread.
     *
     * @param thread The thread to get the context of
     * @return The thread context of the specified thread
     */
    public static ThreadContext forThread(final Thread thread) {
        return thread == PluginManager.getMainThread() ? SYNC : ASYNC;
    }
}
