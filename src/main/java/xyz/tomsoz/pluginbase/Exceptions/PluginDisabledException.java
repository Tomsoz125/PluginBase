package xyz.tomsoz.pluginbase.Exceptions;

import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.pluginbase.Common;

public class PluginDisabledException extends RuntimeException {
    public PluginDisabledException(@NotNull final String name, boolean disable) {
        Common.error(this, "The required plugin " + name + " was not found!", disable);
    }
}