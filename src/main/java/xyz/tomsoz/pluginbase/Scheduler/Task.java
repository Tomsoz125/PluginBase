package xyz.tomsoz.pluginbase.Scheduler;

import xyz.tomsoz.pluginbase.Terminable.Terminable;

/**
 * Represents a scheduled repeating task.
 */
public interface Task extends Terminable {

    /**
     * Gets the number of times this task ran. The counter is only incremented at the end of
     * execution.
     *
     * @return the number of times this task ran
     */
    int getTimesRan();

    /**
     * Stops the task.
     *
     * @return true if the task wasn't already cancelled
     */
    boolean stop();

    /**
     * Gets the Bukkit ID for this task.
     *
     * @return the bukkit id for this task
     */
    int getBukkitId();

    /**
     * {@link #stop() Stops} the task.
     */
    @Override
    default void close() {
        stop();
    }
}
