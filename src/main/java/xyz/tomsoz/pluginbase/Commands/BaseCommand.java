package xyz.tomsoz.pluginbase.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.pluginbase.Common;
import xyz.tomsoz.pluginbase.Locale.Translator;
import xyz.tomsoz.pluginbase.Permission;
import xyz.tomsoz.pluginbase.Text.Text;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Extend this on your command classes.
 */
public abstract class BaseCommand extends Command {
    @Getter
    protected Permission perm;
    @Getter
    protected String name;
    @Getter
    protected String description;
    protected String[] aliases;
    @Getter
    protected Arguments args;
    @Getter
    protected JavaPlugin instance;
    @Getter
    protected boolean playerOnly;
    @Getter
    protected boolean consoleOnly;

    public BaseCommand(@NotNull String name, @NotNull String description, @NotNull Permission permission, @NotNull String[] aliases, @NotNull Arguments args, boolean playerOnly) {
        super(name, description, "", Arrays.asList(aliases));
        this.perm = permission;
        this.description = description;
        this.name = name;
        this.aliases = aliases;
        this.args = args;
        this.playerOnly = playerOnly;

        register();
    }

    /**
     * @param sender The sender of the command.
     * @param label  The label of the command executed.
     * @param args   The supplied arguments.
     */
    protected abstract void run(@NotNull Sender sender, @NotNull String label, @NotNull Arguments args);

    public BaseCommand register() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(Common.getName(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        Translator messages = Translator.create();

        if (!label.equalsIgnoreCase(this.name) && !Arrays.asList(this.aliases).contains(label)) {
            return true;
        }

        if (this.playerOnly && !(sender instanceof Player)) {
            Text.tell(sender, messages.get("commands.must-be-player"));
            return true;
        }

        if (this.consoleOnly && (sender instanceof Player)) {
            Text.tell(sender, messages.get("commands.must-be-console"));
            return true;
        }

        if (!(this.perm == null) && !perm.has(sender)) {
            Text.tell(sender, messages.get("commands.no-permission"));
            return true;
        }

        Arguments args1 = this.args;

        int i = 0;
        for (String a : args) {
            if (args1.getIndex(i).validateNoVal()) {
                if (args1.getIndex(i).getType().equals(ArgumentType.EXPONENT)) {
                    args1.getIndex(i).setValue(String.join(" ", Arrays.asList(args).subList(i, args.length - 1)));
                } else {
                    args1.getIndex(i).setValue(a);
                }
                args1.getIndex(i).setArgs(args1);
            }
            i++;
        }

        AtomicBoolean valid = new AtomicBoolean(true);
        args1.forEach((a) -> {
            if (!a.validate()) {
                Text.tell(sender, messages.get("commands.invalid-usage", label + args1.usage()));
                valid.set(false);
            }
        });

        if (valid.get()) {
            run(new Sender(sender), label, args1);
        }

        return true;
    }

    /**
     * @return The builder for this command.
     */
    public static CommandBuilder builder() {
        return null;
    }
}
