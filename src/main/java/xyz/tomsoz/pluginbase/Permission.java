package xyz.tomsoz.pluginbase;

import org.bukkit.command.CommandSender;

public class Permission {
    String permission;

    public Permission(String permission) {
        this.permission = permission;
    }

    public static Permission empty() {
        return new Permission(null);
    }

    public boolean has(CommandSender p) {
        if (permission == null) return true;
        return p.hasPermission(permission);
    }
}
