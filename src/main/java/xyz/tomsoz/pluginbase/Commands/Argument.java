package xyz.tomsoz.pluginbase.Commands;

import lombok.Getter;

public class Argument {
    @Getter private final boolean required;
    @Getter private final String name;
    @Getter private final ArgumentType type;
    public Argument(String name, boolean required, ArgumentType type) {
        this.name = name;
        this.required = required;
        this.type = type;
    }
}
