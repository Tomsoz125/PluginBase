package xyz.tomsoz.pluginbase.Scheduler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import xyz.tomsoz.pluginbase.Exceptions.SchedulerTaskException;
import xyz.tomsoz.pluginbase.PluginManager;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Provides common {@link Executor} instances.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BaseExecutors {

    private static final Executor SYNC_BUKKIT = new BukkitSyncExecutor();
    private static final Executor ASYNC_BUKKIT = new BukkitAsyncExecutor();
    private static final BaseAsyncExecutor ASYNC_BASE = new BaseAsyncExecutor();

    public static Executor sync() {
        return SYNC_BUKKIT;
    }

    public static ScheduledExecutorService asyncBase() {
        return ASYNC_BASE;
    }

    public static Executor asyncBukkit() {
        return ASYNC_BUKKIT;
    }

    public static void shutdown() {
        ASYNC_BASE.cancelRepeatingTasks();
    }

    private static final class BukkitSyncExecutor implements Executor {

        @Override
        public void execute(final Runnable runnable) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(PluginManager.getPlugin(),
                    SchedulerTaskException.wrap(runnable));
        }
    }

    private static final class BukkitAsyncExecutor implements Executor {

        @Override
        public void execute(final Runnable runnable) {
            Bukkit.getScheduler().runTaskAsynchronously(PluginManager.getPlugin(),
                    SchedulerTaskException.wrap(runnable));
        }
    }

    @Deprecated
    public static Runnable wrapRunnable(final Runnable runnable) {
        return SchedulerTaskException.wrap(runnable);
    }
}
