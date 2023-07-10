package xyz.tomsoz.pluginbase;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tomsoz.pluginbase.Exceptions.PluginErrorException;
import xyz.tomsoz.pluginbase.Text.Text;

import java.util.*;
import java.util.function.IntConsumer;
import java.util.function.UnaryOperator;
import java.util.logging.Level;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Common {
    /**
     * If the server software the plugin is running on is Spigot or a fork of Spigot. Used internally
     * for some Spigot-only features or optimizations.
     */
    public static final boolean SPIGOT = checkClass("net.md_5.bungee.api.ChatColor") != null;

    /**
     * The error message for players when an internal error occurs.
     */
    public static final String PLAYERS_ERROR_MESSAGE = "&cAn internal error occurred in " + Common.getName() + ". Check the console for more information.";

    private static final String STR_DECIMAL_FORMAT = "%.2f";


    // ---------------------------------------------------------------------------------
    // PLUGIN INFORMATION
    // ---------------------------------------------------------------------------------

    /**
     * Gets the name of the plugin, as defined in plugin.yml.
     *
     * @return The name of the plugin
     */
    @NotNull
    public static String getName() {
        return PluginManager.getPlugin().getDescription().getName();
    }

    /**
     * Gets the version string, as defined in plugin.yml.
     *
     * @return The version of the plugin
     */
    @NotNull
    public static String getVersion() {
        return PluginManager.getPlugin().getDescription().getVersion();
    }


    // ---------------------------------------------------------------------------------
    // SERVER INFORMATION
    // ---------------------------------------------------------------------------------

    /**
     * Gets the server's major Minecraft version.
     *
     * <p>For example, if a server is running 1.16.4, this will return 16.
     *
     * @return The server's major Minecraft version.
     */
    public static int getServerMajorVersion() {
        return XMaterial.getVersion();
    }

    /**
     * Checks if the server's major version is at least the specified version.
     *
     * @param version The minimum major version
     * @return True if equal or greater to the provided version, false otherwise
     */
    public static boolean isServerVersionAtLeast(final int version) {
        return getServerMajorVersion() >= version;
    }

    // ---------------------------------------------------------------------------------
    // NUMBER PARSING
    // ---------------------------------------------------------------------------------

    /**
     * Attempts to parse a string into an integer.
     *
     * @param str The string to parse
     * @return The parsed integer, or null if the string is not a valid integer
     */
    @Nullable
    public static Integer checkInt(@NotNull final String str) {
        return Ints.tryParse(str);
    }

    /**
     * Attempts to parse a string into a float.
     *
     * @param str The string to parse
     * @return The parsed float, or null if the string is not a valid float
     */
    @Nullable
    public static Float checkFloat(@NotNull final String str) {
        return Floats.tryParse(str);
    }

    /**
     * Attempts to parse a string into a long.
     *
     * @param str The string to parse
     * @return The parsed long, or null if the string is not a valid long
     */
    @Nullable
    public static Long checkLong(@NotNull final String str) {
        return Longs.tryParse(str);
    }

    /**
     * Attempts to parse a string into a double.
     *
     * @param str The string to parse
     * @return The parsed double, or null if the string is not a valid double
     */
    @Nullable
    public static Double checkDouble(@NotNull final String str) {
        return Doubles.tryParse(str);
    }


    // ---------------------------------------------------------------------------------
    // MISC
    // ---------------------------------------------------------------------------------

    @NotNull
    public static String applyOperator(
            @Nullable final String str,
            @Nullable final UnaryOperator<String> operator) {
        if (str == null || operator == null) {
            return Common.getOrDefault(str, "");
        }

        return operator.apply(str);
    }

    @NotNull
    public static List<String> applyOperator(
            @Nullable final List<String> list,
            @Nullable final UnaryOperator<String> operator) {
        if (list == null || operator == null) {
            return Common.getOrDefault(list, Collections.emptyList());
        }

        final List<String> applied = new LinkedList<>();

        for (final String str : list) {
            applied.add(operator.apply(str));
        }

        return applied;
    }

    @NotNull
    public static ItemStack applyOperator(
            @Nullable final ItemStack stack,
            @Nullable final UnaryOperator<String> operator) {
        if (stack == null || stack.getType() == Material.AIR) {
            return new ItemStack(Material.AIR);
        }

        if (operator == null) return stack;

        final ItemStack replaced = new ItemStack(stack);
        final ItemMeta meta = replaced.getItemMeta();
        Objects.requireNonNull(meta, "Item meta is null");

        meta.setDisplayName(Text.colourize(applyOperator(meta.getDisplayName(), operator)));

        if (meta.getLore() != null && !meta.getLore().isEmpty()) {
            meta.setLore(Text.colourize(applyOperator(meta.getLore(), operator)));
        }

        replaced.setItemMeta(meta);

        return replaced;
    }

    /**
     * Simple method to check if a class exists.
     *
     * @param className The class's package and name (Example: dev.demeng.pluginbase.Validate)
     * @return The actual class if the class exists, null otherwise
     */
    @Nullable
    public static Class<?> checkClass(@NotNull final String className) {
        try {
            return Class.forName(className, false, Common.class.getClassLoader());
        } catch (final ClassNotFoundException ex) {
            return null;
        }
    }

    /**
     * Formats a decimal into a human-friendly string that rounds the double to 2 decimal places.
     *
     * @param d THe double to format
     * @return The formatted double
     */
    @NotNull
    public static String formatDecimal(final double d) {
        return String.format(STR_DECIMAL_FORMAT, d);
    }

    /**
     * Returns the nullable value if not null, or the default value if it is null.
     *
     * @param <T>      The object type being checked and returned
     * @param nullable The nullable value
     * @param def      The default value
     * @return The nullable if not null, default otherwise
     */
    @Nullable
    public static <T> T getOrDefault(@Nullable final T nullable, @NotNull final T def) {
        return nullable != null ? nullable : def;
    }

    /**
     * Returns the nullable value if not null, or throws a runtime exception with error if it is
     * null.
     *
     * @param <T>         The object type being checked and returned
     * @param nullable    The nullable value
     * @param description The error description if the value is null
     * @param disable     If the plugin should disable if the value is null
     * @return The nullable if not null
     */
    @NotNull
    private static <T> T getOrError(
            @Nullable final T nullable,
            @NotNull final String description,
            final boolean disable) {
        if (nullable == null) {
            throw new PluginErrorException(description, disable);
        }

        return nullable;
    }

    /**
     * Checks if the specified command sender has the permission node. If the permission node is null,
     * empty, or equal to "none", the method will return true.
     *
     * @param sender     The command sender to check
     * @param permission The permission to check
     * @return True if the command sender has the permission, false otherwise
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean hasPermission(
            @NotNull final CommandSender sender,
            @NotNull final String permission) {
        return permission == null
                || permission.isEmpty()
                || permission.equalsIgnoreCase("none")
                || sender.hasPermission(permission);
    }

    /**
     * Reports an error in the plugin.
     *
     * @param throwable   The throwable
     * @param description A brief description of the error
     * @param disable     If the plugin should be disabled
     * @param players     Any players associated with this error, a message will be sent to them
     */
    public static void error(
            @Nullable final Throwable throwable,
            @NotNull final String description,
            final boolean disable,
            @NotNull final CommandSender... players) {
        if (throwable != null) throwable.printStackTrace();

        Text.colouredConsole("&4" + Text.CONSOLE_LINE);
        Text.colouredConsole("&cAn internal error has occurred in " + Common.getName() + ".");
        Text.colouredConsole("&cDescription: &6" + description);
        Text.colouredConsole("&cContact the plugin author if you cannot fix this issue.");
        Text.colouredConsole("&4" + Text.CONSOLE_LINE);

        Arrays.stream(players).filter(Player.class::isInstance)
                .forEach(p -> Text.colouredTell(p, PLAYERS_ERROR_MESSAGE));

        if (disable && Bukkit.getPluginManager().isPluginEnabled(PluginManager.getPlugin())) {
            Bukkit.getPluginManager().disablePlugin(PluginManager.getPlugin());
        }
    }

    /**
     * Parses a string sequence of integers and accepts a consumer for each integer. The sequence can
     * either be a single integer (1), a range of integers (1-10), or a list of integers (1,2,3).
     *
     * @param str      The integer sequence to parse
     * @param consumer The consumer to accept for each integer
     * @throws IllegalArgumentException If the sequenece is invalid
     */
    public static void forEachInt(final String str, final IntConsumer consumer) throws IllegalArgumentException {
        final Integer singleNum = Ints.tryParse(str);

        if (singleNum != null) {
            consumer.accept(singleNum);
            return;
        }

        final String[] listArr = str.split(",");

        if (listArr.length > 1) {
            for (final String s : listArr) {
                forEachInt(s, consumer);
            }
            return;
        }

        final String[] rangeArr = str.split("-");

        if (rangeArr.length == 2) {
            final Integer start = Ints.tryParse(rangeArr[0]);
            final Integer end = Ints.tryParse(rangeArr[1]);

            if (start != null && end != null) {
                for (int i = start; i <= end; i++) {
                    consumer.accept(i);
                }
                return;
            }
        }

        throw new IllegalArgumentException("Invalid integer sequence: " + str);
    }

    public static boolean isPluginEnabled(String name) {
        return Bukkit.getPluginManager().isPluginEnabled(name);
    }

    public static boolean isPluginEnabled(String name, boolean disable) {
        if (Bukkit.getPluginManager().isPluginEnabled(name)) return true;
        else {
            Text.log(Level.SEVERE, "The required plugin " + name + " was not found!");
            if (disable) Bukkit.getPluginManager().disablePlugin(PluginManager.getPlugin());
            return false;
        }
    }

    public static String secondsToString(int pTime) {
        if ((pTime % 3600) > 0) return String.format("%02d:%02d:%02d", pTime / 3600, (pTime % 3600) / 60, pTime % 60);
        else return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }
}
