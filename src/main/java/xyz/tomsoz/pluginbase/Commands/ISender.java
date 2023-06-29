package xyz.tomsoz.pluginbase.Commands;

import org.bukkit.command.CommandSender;

public interface ISender extends CommandSender {
    boolean isPlayer();

    User asUser();
}
