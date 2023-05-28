package xyz.tomsoz.pluginbase.Exceptions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tomsoz.pluginbase.Common;

/**
 * An extension of RuntimeException that calls
 * {@link Common#error(Throwable, String, boolean, CommandSender...)}.
 */
public class PluginErrorException extends RuntimeException {
    public PluginErrorException(@NotNull final String description, final boolean disable, final Player... players) {
        Common.error(this, description, disable, players);
    }

    public PluginErrorException(@Nullable final Throwable t, @NotNull final String description, final boolean disable, final Player... players) {
        Common.error(t, description, disable, players);
    }
}
