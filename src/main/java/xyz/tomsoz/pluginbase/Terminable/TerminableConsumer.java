package xyz.tomsoz.pluginbase.Terminable;

import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.pluginbase.Terminable.Module.TerminableModule;

/**
 * Accepts {@link AutoCloseable}s (and by inheritance {@link Terminable}s), as well as
 * {@link TerminableModule}s.
 */
@FunctionalInterface
public interface TerminableConsumer {

    /**
     * Binds with the given terminable.
     *
     * @param terminable the terminable to bind with
     * @param <T>        the terminable type
     * @return the same terminable
     */
    @NotNull <T extends AutoCloseable> T bind(@NotNull T terminable);

    /**
     * Binds with the given terminable module.
     *
     * @param module the module to bind with
     * @param <T>    the module type
     * @return the same module
     */
    @NotNull
    default <T extends TerminableModule> T bindModule(@NotNull final T module) {
        module.setup(this);
        return module;
    }

}