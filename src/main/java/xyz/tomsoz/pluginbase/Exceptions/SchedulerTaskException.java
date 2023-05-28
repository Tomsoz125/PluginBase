package xyz.tomsoz.pluginbase.Exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.pluginbase.Common;
import xyz.tomsoz.pluginbase.Delegate.Delegate;

/**
 * A general exception caused by a scheduler task.
 */
public class SchedulerTaskException extends APIException {

    public SchedulerTaskException(@NotNull final Throwable cause) {
        super("scheduler task", cause);
    }

    /**
     * Wraps a runnable into a runnable that catches its own exceptions and logs them using
     * {@link Common#error(Throwable, String, boolean, CommandSender...)}.
     *
     * @param runnable The runnable
     * @return The wrapped runnable
     */
    @NotNull
    public static Runnable wrap(@NotNull final Runnable runnable) {
        return new SchedulerWrappedRunnable(runnable);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class SchedulerWrappedRunnable implements Runnable, Delegate<Runnable> {

        @Getter @NotNull private final Runnable delegate;

        @Override
        public void run() {
            try {
                this.delegate.run();
            } catch (final Throwable t) {
                Common.error(new SchedulerTaskException(t),
                        "Error whilst executing scheduler task.", false);
            }
        }
    }
}
