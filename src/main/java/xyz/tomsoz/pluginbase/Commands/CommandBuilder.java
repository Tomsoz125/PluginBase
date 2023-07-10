package xyz.tomsoz.pluginbase.Commands;

import lombok.Getter;
import xyz.tomsoz.pluginbase.Permission;

import java.lang.reflect.InvocationTargetException;

public class CommandBuilder {
    @Getter
    boolean playerOnly = false;
    @Getter
    boolean consoleOnly = false;
    private Class<?> command;
    @Getter
    private String description = null;
    @Getter
    private Permission permission = Permission.empty();
    @Getter
    private String name = null;
    @Getter
    private String[] aliases = new String[]{};
    @Getter
    private Arguments args = new Arguments();

    public <C extends BaseCommand> CommandBuilder(Class<C> clazz) {
        this.command = clazz;
    }

    public <C extends BaseCommand> C build() {
        if (!BaseCommand.class.isAssignableFrom(command)) return null;
        try {
            return (C) command.getDeclaredConstructor(String.class, String.class, Permission.class, String[].class, Arguments.class, boolean.class).newInstance(this.name, this.description, this.permission, this.aliases, this.args, this.playerOnly);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public CommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CommandBuilder description(String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder permission(Permission permission) {
        this.permission = permission;
        return this;
    }

    public CommandBuilder aliases(String[] aliases) {
        this.aliases = aliases;
        return this;
    }

    public CommandBuilder args(Arguments args) {
        this.args = args;
        return this;
    }

    public CommandBuilder playerOnly() {
        this.playerOnly = true;
        return this;
    }

    public CommandBuilder playerOnly(boolean playerOnly) {
        this.playerOnly = playerOnly;
        return this;
    }

    public CommandBuilder consoleOnly() {
        this.consoleOnly = true;
        return this;
    }

    public CommandBuilder consoleOnly(boolean consoleOnly) {
        this.consoleOnly = consoleOnly;
        return this;
    }
}
