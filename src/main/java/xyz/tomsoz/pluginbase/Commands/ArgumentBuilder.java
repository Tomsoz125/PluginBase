package xyz.tomsoz.pluginbase.Commands;

import lombok.Getter;

public class ArgumentBuilder {
    @Getter
    private boolean required = false;
    @Getter
    private String name = null;
    @Getter
    private ArgumentType type = ArgumentType.STRING;

    public Argument build() {
        return new Argument(name, required, type);
    }

    public ArgumentBuilder required() {
        this.required = true;
        return this;
    }

    public ArgumentBuilder required(boolean required) {
        this.required = required;
        return this;
    }

    public ArgumentBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ArgumentBuilder type(ArgumentType type) {
        this.type = type;
        return this;
    }
}
