package xyz.tomsoz.pluginbase.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tomsoz.pluginbase.Common;
import xyz.tomsoz.pluginbase.Locale.Translator;
import xyz.tomsoz.pluginbase.Text.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Extend this on your command classes.
 */
public abstract class BaseCommand extends Command {
    @Getter
    protected String permission;
    @Getter
    protected String name;
    @Getter
    protected String description;
    protected String[] aliases;
    @Getter
    protected Argument[] args;
    @Getter
    protected JavaPlugin instance;
    @Getter
    protected boolean playerOnly;
    @Getter
    protected boolean consoleOnly;

    public BaseCommand(@NotNull JavaPlugin instance, @NotNull String description, @Nullable String permission, @NotNull String name, @Nullable String[] aliases, @Nullable Argument[] args, boolean playerOnly) {
        super(name, description, "", Arrays.asList(aliases));
        this.permission = permission;
        this.description = description;
        this.name = name;
        this.aliases = aliases;
        this.args = args;
        this.instance = instance;
        this.playerOnly = playerOnly;
    }

    public BaseCommand(@NotNull String description, @Nullable String permission, @NotNull String name, @Nullable String[] aliases, @Nullable Argument[] args, boolean playerOnly) {
        super(name, description, "", Arrays.asList(aliases));
        this.permission = permission;
        this.description = description;
        this.name = name;
        this.aliases = aliases;
        this.args = args;
        this.playerOnly = playerOnly;
    }

    public BaseCommand(@NotNull String description, @NotNull String name, @Nullable String[] aliases, @Nullable Argument[] args, boolean playerOnly) {
        super(name, description, "", Arrays.asList(aliases));
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.args = args;
        this.playerOnly = playerOnly;
    }

    public BaseCommand(@NotNull String description, @NotNull String name, @Nullable String[] aliases, boolean playerOnly) {
        super(name, description, "", Arrays.asList(aliases));
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.playerOnly = playerOnly;
    }

    public BaseCommand(@NotNull String description, @NotNull String name, boolean playerOnly) {
        super(name, description, "", new ArrayList<>());
        this.name = name;
        this.description = description;
        this.playerOnly = playerOnly;
    }

    public BaseCommand(@NotNull String description, @NotNull String name, @Nullable String permission, boolean playerOnly) {
        super(name, description, "", new ArrayList<>());
        this.name = name;
        this.description = description;
        this.playerOnly = playerOnly;
        this.permission = permission;
    }

    public BaseCommand(@NotNull String description, @NotNull String name) {
        super(name, description, "", new ArrayList<>());
        this.name = name;
        this.description = description;
        this.playerOnly = false;
    }

    protected abstract void run(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args);

    public void register() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(Common.getName(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        Translator messages = Translator.create();

        if (!s.equalsIgnoreCase(this.name) && !Arrays.asList(this.aliases).contains(this.name)) {
            return true;
        }

        if (this.playerOnly && !(commandSender instanceof Player)) {
            Text.tell(commandSender, messages.get("commands.must-be-player"));
            return true;
        }

        if (this.consoleOnly && (commandSender instanceof Player)) {
            Text.tell(commandSender, messages.get("commands.must-be-console"));
            return true;
        }

        if (!(this.permission == null) && !commandSender.hasPermission(this.permission)) {
            Text.tell(commandSender, messages.get("commands.no-permission"));
            return true;
        }

        if (this.args != null) {
            int required = 0;

            StringBuilder usageStr = new StringBuilder();
            usageStr.append(s);

            boolean valid = true;
            int i = 0;
            for (Argument a : this.args) {
                if (a.isRequired()) {
                    required++;
                    usageStr.append(" <").append(a.getName()).append(">");
                } else {
                    usageStr.append(" [").append(a.getName()).append("]");
                }

                switch (a.getType()) {
                    case PLAYER:
                        valid = Bukkit.getPlayerExact(strings[i]) != null;
                    case STRING:
                        valid = true;
                    case BOOLEAN:
                        valid = strings[i].equalsIgnoreCase("true") || strings[i].equalsIgnoreCase("false");
                    case INTEGER:
                        valid = Common.checkInt(strings[i]) != null;
                }
                i++;
            }

            if (!(args.length >= required) || !valid) {
                Text.tell(commandSender, messages.get("commands.invalid-usage", usageStr.toString()));
                return true;
            }
        }

        run(commandSender, s, strings);
        return true;
    }
}
