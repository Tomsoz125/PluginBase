package xyz.tomsoz.pluginbase.Exceptions;

import org.jetbrains.annotations.NotNull;

public class ArgumentParseException extends RuntimeException {
    public ArgumentParseException(@NotNull final String message) {
        super(message);
    }
}
