package xyz.tomsoz.pluginbase.Commands;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import xyz.tomsoz.pluginbase.Common;
import xyz.tomsoz.pluginbase.Exceptions.ArgumentParseException;

public class Argument {
    @Getter
    private final boolean required;
    @Getter
    private final String name;
    @Getter
    private final ArgumentType type;
    @Getter
    @Setter
    private String value = null;
    @Getter
    @Setter
    private Arguments args = null;

    public Argument(String name, boolean required, ArgumentType type) {
        this.name = name;
        this.required = required;
        this.type = type;
    }

    public boolean validate() {
        if (value == null) return !required;
        if (type.equals(ArgumentType.EXPONENT) && !args.last().equals(this))
            throw new ArgumentParseException("An exponent argument must be the last argument specified.");

        switch (type) {
            case STRING:
            case EXPONENT:
                return true;
            case BOOLEAN:
                return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false") || value.equalsIgnoreCase("no") || value.equalsIgnoreCase("yes");
            case PLAYER:
                return Bukkit.getPlayerExact(value) != null;
            case INTEGER:
                return Common.checkInt(value) != null;
        }

        return false;
    }

    public boolean validateNoVal() {
        if (type.equals(ArgumentType.EXPONENT) && !args.last().equals(this))
            throw new ArgumentParseException("An exponent argument must be the last argument specified.");

        return true;
    }
}
