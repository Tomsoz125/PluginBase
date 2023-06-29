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

    public BaseCommand build() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!BaseCommand.class.isAssignableFrom(command)) return null;
        return (BaseCommand) command.getDeclaredConstructor(new Class[6]).newInstance(name, description, permission, aliases, args, playerOnly);
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
