package xyz.tomsoz.pluginbase.Exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * A custom runtime exception, used for throwing runtime errors related to improper API usage.
 */
public class APIException extends RuntimeException {
    public APIException(@NotNull final String message) {
        super(message);
    }

    public APIException(@NotNull final Throwable cause) {
        super(cause);
    }

    public APIException(@NotNull final String message, @NotNull Throwable cause) {
        super(message, cause);
    }
}
