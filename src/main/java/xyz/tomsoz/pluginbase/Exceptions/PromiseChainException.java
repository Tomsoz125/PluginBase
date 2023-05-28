package xyz.tomsoz.pluginbase.Exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * A general exception caused by a {@link xyz.tomsoz.pluginbase.Promise.Promise} chain.
 */
public class PromiseChainException extends APIException {

    public PromiseChainException(@NotNull final Throwable cause) {
        super("promise chain", cause);
    }
}
