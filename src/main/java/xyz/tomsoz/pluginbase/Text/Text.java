package xyz.tomsoz.pluginbase.Text;

import com.cryptomorin.xseries.messages.Titles;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tomsoz.pluginbase.BaseSettings;
import xyz.tomsoz.pluginbase.Common;
import xyz.tomsoz.pluginbase.Locale.Locales;
import xyz.tomsoz.pluginbase.PluginManager;

import javax.annotation.Nonnull;
import java.awt.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Text {
    private static final String IGNORE_MESSAGE_VALUE = "ignore";
    private static final String MINI_PREFIX = "mini:";
    public static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]){6}");
    public static final Pattern GRADIENT_PATTERN = Pattern.compile("<GRADIENT:([0-9A-Fa-f]{6})>(.*?)</GRADIENT:([0-9A-Fa-f]{6})>");
    public static final Pattern RAINBOW_PATTERN = Pattern.compile("<RAINBOW>(.*?)</RAINBOW>");
    public static final String CHAT_LINE = "&m-----------------------------------------------------";
    public static final String CONSOLE_LINE = "*-----------------------------------------------------*";
    public static final MiniMessage MINI_MESSAGE = MiniMessage.builder().build();

    private static final List<String> SPECIAL_COLORS = Arrays.asList("&l", "&n", "&o", "&k", "&m", "§l", "§n", "§o", "§k", "§m");

    /**
     * Cached result of all legacy colors.
     *
     * @since 1.0.0
     */
    private static final Map<Color, net.md_5.bungee.api.ChatColor> COLORS = ImmutableMap.<Color, net.md_5.bungee.api.ChatColor>builder()
            .put(new Color(0), net.md_5.bungee.api.ChatColor.getByChar('0'))
            .put(new Color(170), net.md_5.bungee.api.ChatColor.getByChar('1'))
            .put(new Color(43520), net.md_5.bungee.api.ChatColor.getByChar('2'))
            .put(new Color(43690), net.md_5.bungee.api.ChatColor.getByChar('3'))
            .put(new Color(11141120), net.md_5.bungee.api.ChatColor.getByChar('4'))
            .put(new Color(11141290), net.md_5.bungee.api.ChatColor.getByChar('5'))
            .put(new Color(16755200), net.md_5.bungee.api.ChatColor.getByChar('6'))
            .put(new Color(11184810), net.md_5.bungee.api.ChatColor.getByChar('7'))
            .put(new Color(5592405), net.md_5.bungee.api.ChatColor.getByChar('8'))
            .put(new Color(5592575), net.md_5.bungee.api.ChatColor.getByChar('9'))
            .put(new Color(5635925), net.md_5.bungee.api.ChatColor.getByChar('a'))
            .put(new Color(5636095), net.md_5.bungee.api.ChatColor.getByChar('b'))
            .put(new Color(16733525), net.md_5.bungee.api.ChatColor.getByChar('c'))
            .put(new Color(16733695), net.md_5.bungee.api.ChatColor.getByChar('d'))
            .put(new Color(16777045), net.md_5.bungee.api.ChatColor.getByChar('e'))
            .put(new Color(16777215), net.md_5.bungee.api.ChatColor.getByChar('f')).build();


    // ---------------------------------------------------------------------------------
    // LOCALE
    // ---------------------------------------------------------------------------------

    /**
     * Gets the locale for the specified console or player. If the player's locale cannot be resolved,
     * the sender is a console, or the sender is null, the default locale is returned.
     *
     * @param sender The sender to get the locale of
     * @return The sender's locale or the default locale if the sender's locale could not be resolved
     */
    @NotNull
    public static Locale getLocale(@NotNull final CommandSender sender) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            String playerLocale;

            try {
                playerLocale = player.getLocale();
            } catch (final NoSuchMethodError ex) {
                try {
                    final Player.Spigot spigotPlayer = player.spigot();
                    playerLocale = (String) spigotPlayer.getClass().getDeclaredMethod("getLocale").invoke(spigotPlayer);
                } catch (final Exception ex1) {
                    return PluginManager.getTranslator().getLocale();
                }
            }

            final Locale locale = Locales.get(playerLocale);
            return locale == null ? PluginManager.getTranslator().getLocale() : locale;
        }

        return PluginManager.getTranslator().getLocale();
    }

    /**
     * Gets the localized string with the given key using the provided sender's locale. Uses the
     * default locale if the localized string could not be resolved with the provided sender's
     * locale.
     *
     * @param key    The key of the message
     * @param sender The sender to get the locale of
     * @param args   Arguments for positioned placeholders ({n})
     * @return The localized message, or key if unable to resolve
     * @see #getLocale(CommandSender)
     */
    @NotNull
    public static String localized(
            @Nullable final String key,
            @Nullable final CommandSender sender,
            @NotNull final Object... args) {
        if (key == null) return "";

        return MessageFormat.format(PluginManager.getTranslator().get(key, getLocale(sender)), args);
    }

    /**
     * Gets the localized string with the given key using the provided locale. Uses the default locale
     * if the localized string could not be resolved with the provided locale.
     *
     * @param key    The key of the message
     * @param locale The locale
     * @param args   Arguments for positioned placeholders ({n})
     * @return The localized message, or key if unable to resolve
     */
    @NotNull
    public static String localized(
            @Nullable final String key,
            @Nullable final Locale locale,
            @NotNull final Object... args) {
        if (key == null) return "";

        if (locale == null) {
            return localized(key, PluginManager.getTranslator().getLocale());
        }

        return MessageFormat.format(PluginManager.getTranslator().get(key, locale), args);
    }

    @NotNull
    public static String tl(
            @Nullable final String key,
            @Nullable final CommandSender sender,
            @NotNull final Object... args) {
        return localized(key, sender ,args);
    }

    @NotNull
    public static String tl(
            @Nullable final String key,
            @Nullable final Locale locale,
            @NotNull final Object... args) {
        return localized(key, locale ,args);
    }


    // ---------------------------------------------------------------------------------
    // FORMATTING
    // ---------------------------------------------------------------------------------

    /**
     * Convenience method to get the current prefix of the plugin.
     *
     * @return The prefix
     */
    @NotNull
    public static String getPrefix() {
        return PluginManager.getBaseSettings().prefix();
    }

    /**
     * Gets either {@link #CHAT_LINE} or {@link #CONSOLE_LINE}, depending on if the
     * {@link CommandSender} is a player or console.
     *
     * @param sender The command sender
     * @return The separation line
     */
    @NotNull
    public static String line(@NotNull final CommandSender sender) {
        return sender instanceof Player ? CHAT_LINE : CONSOLE_LINE;
    }

    /**
     * Returns a gradient array of chat colors.
     *
     * @param start The starting color.
     * @param end   The ending color.
     * @param step  How many colors we return.
     * @author TheViperShow
     * @since 1.0.0
     */
    @Nonnull
    private static net.md_5.bungee.api.ChatColor[] createGradient(@Nonnull Color start, @Nonnull Color end, int step) {
        net.md_5.bungee.api.ChatColor[] colors = new net.md_5.bungee.api.ChatColor[step];
        int stepR = Math.abs(start.getRed() - end.getRed()) / (step - 1);
        int stepG = Math.abs(start.getGreen() - end.getGreen()) / (step - 1);
        int stepB = Math.abs(start.getBlue() - end.getBlue()) / (step - 1);
        int[] direction = new int[] {
                start.getRed() < end.getRed() ? +1 : -1,
                start.getGreen() < end.getGreen() ? +1 : -1,
                start.getBlue() < end.getBlue() ? +1 : -1
        };

        for (int i = 0; i < step; i++) {
            Color color = new Color(start.getRed() + ((stepR * i) * direction[0]), start.getGreen() + ((stepG * i) * direction[1]), start.getBlue() + ((stepB * i) * direction[2]));
            if (Common.SPIGOT && Common.isServerVersionAtLeast(16)) {
                colors[i] = net.md_5.bungee.api.ChatColor.of(color);
            } else {
                colors[i] = getClosestColor(color);
            }
        }

        return colors;
    }

    /**
     * Returns the closest legacy color from an rgb color
     *
     * @param color The color we want to transform
     * @since 1.0.0
     */
    @Nonnull
    private static net.md_5.bungee.api.ChatColor getClosestColor(Color color) {
        Color nearestColor = null;
        double nearestDistance = Integer.MAX_VALUE;

        for (Color constantColor : COLORS.keySet()) {
            double distance = Math.pow(color.getRed() - constantColor.getRed(), 2) + Math.pow(color.getGreen() - constantColor.getGreen(), 2) + Math.pow(color.getBlue() - constantColor.getBlue(), 2);
            if (nearestDistance > distance) {
                nearestColor = constantColor;
                nearestDistance = distance;
            }
        }
        return COLORS.get(nearestColor);
    }

    @Nonnull
    private static String withoutSpecialChar(@Nonnull String source) {
        String workingString = source;
        for (String color : SPECIAL_COLORS) {
            if (workingString.contains(color)) {
                workingString = workingString.replace(color, "");
            }
        }
        return workingString;
    }

    /**
     * Colors a String with a gradiant.
     *
     * @param string The string we want to color
     * @param start  The starting gradiant
     * @param end    The ending gradiant
     * @since 1.0.0
     */
    @Nonnull
    private static String color(@Nonnull String string, @Nonnull Color start, @Nonnull Color end) {
        String originalString = string;

        net.md_5.bungee.api.ChatColor[] colors = createGradient(start, end, withoutSpecialChar(string).length());
        return apply(originalString, colors);
    }

    @Nonnull
    private static String apply(@Nonnull String source, net.md_5.bungee.api.ChatColor[] colors) {
        StringBuilder specialColors = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        String[] characters = source.split("");
        int outIndex = 0;
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].equals("&") || characters[i].equals("§")) {
                if (i + 1 < characters.length) {
                    if (characters[i + 1].equals("r")) {
                        specialColors.setLength(0);
                    } else {
                        specialColors.append(characters[i]);
                        specialColors.append(characters[i + 1]);
                    }
                    i++;
                } else
                    stringBuilder.append(colors[outIndex++]).append(specialColors).append(characters[i]);
            } else
                stringBuilder.append(colors[outIndex++]).append(specialColors).append(characters[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Converts plain text string into a gradient string.
     * e.g. <GRADIENT:2C08BA>Cool string with a gradient</GRADIENT:028A97>
     *
     * @param str The string to translate gradient on
     * @return String with gradient applied or an empty string if str is null.
     */
    @NotNull
    public static String gradient(@Nullable String str) {
        if (str == null) return "";
        Matcher matcher = GRADIENT_PATTERN.matcher(str);
        while (matcher.find()) {
            String start = matcher.group(1);
            String end = matcher.group(3);
            String content = matcher.group(2);
            str = str.replace(matcher.group(), color(content, new Color(Integer.parseInt(start, 16)), new Color(Integer.parseInt(end, 16))));
        }
        return str;
    }

    /**
     * Colors a String with rainbow colors.
     *
     * @param str     The string which should have rainbow colors
     * @param saturation The saturation of the rainbow colors
     * @since 1.0.3
     */
    @Nonnull
    public static String rainbow(@Nullable String str, float saturation) {
        String originalString = str;
        if (str == null) return "";

        Matcher matcher = RAINBOW_PATTERN.matcher(str);
        while (matcher.find()) {
            String content = matcher.group();
            Bukkit.broadcastMessage(content);
            net.md_5.bungee.api.ChatColor[] colors = createRainbow(withoutSpecialChar(content).length(), saturation);
            originalString = originalString.replace(matcher.group(), apply(content, colors));
        }
        return str;
    }

    /**
     * Returns a rainbow array of chat colors.
     *
     * @param step       How many colors we return
     * @param saturation The saturation of the rainbow
     * @return The array of colors
     * @since 1.0.3
     */
    @Nonnull
    private static net.md_5.bungee.api.ChatColor[] createRainbow(int step, float saturation) {
        net.md_5.bungee.api.ChatColor[] colors = new net.md_5.bungee.api.ChatColor[step];
        double colorStep = (1.00 / step);

        for (int i = 0; i < step; i++) {
            Color color = Color.getHSBColor((float) (colorStep * i), saturation, saturation);
            if (Common.SPIGOT && Common.isServerVersionAtLeast(16)) {
                colors[i] = net.md_5.bungee.api.ChatColor.of(color);
            } else {
                colors[i] = getClosestColor(color);
            }
        }

        return colors;
    }

    /**
     * Converts plain string with color codes into a colorized message. Supports HEX colors in the
     * format of {@code &#HEX}. 1.16+ HEX support requires the server software to be Spigot, or a
     * fork of Spigot.
     *
     * @param str The plain string
     * @return Colorized string, or empty if the provided string is null
     */
    @NotNull
    public static String colourize(@Nullable final String str) {
        if (str == null) return "";

        String message = str;

        final BaseSettings.ColourScheme scheme = PluginManager.getBaseSettings().colourScheme();
        if (scheme != null) {
            message = message.replace("&p", scheme.getPrimary())
                    .replace("&s", scheme.getSecondary())
                    .replace("&t", scheme.getTertiary());
        }

        message = gradient(message);

        if (Common.SPIGOT && Common.isServerVersionAtLeast(16)) {
            Matcher matcher = HEX_PATTERN.matcher(message);

            while (matcher.find()) {
                final net.md_5.bungee.api.ChatColor hexColour = net.md_5.bungee.api.ChatColor.of(matcher.group().substring(1, matcher.group().length()));
                final String before = message.substring(0, matcher.start());
                final String after = message.substring(matcher.end());

                message = before + hexColour + after;
                matcher = HEX_PATTERN.matcher(message);
            }
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Colorizes a list of plain strings.
     *
     * @param strList The plain strings
     * @return Colorized strings, or an empty collection if the provided list is null
     * @see #colourize(String)
     */
    @NotNull
    public static List<String> colourize(@Nullable final List<String> strList) {
        if (strList == null) return Collections.emptyList();

        return strList.stream().map(Text::colourize).collect(Collectors.toList());
    }

    /**
     * Appends the prefix, and then colorizes.
     *
     * @param str The plain, non-prefixed string
     * @return Formatted string, or empty if the provided string is null
     */
    @NotNull
    public static String format(@Nullable final String str) {
        if (str == null) return "";

        return colourize(getPrefix() + str);
    }

    /**
     * Appends the prefix, a red chat color, and the message, and then colorizes.
     *
     * @param str The plain string
     * @return Formatted string with red message
     */
    @NotNull
    public static String error(@Nullable final String str) {
        if (str == null) return "";

        return colourize(getPrefix() + "&c" + str);
    }

    /**
     * Parses the string using the MiniMessage library. Format:
     * https://docs.advntr.dev/minimessage/format.html
     *
     * @param str The raw string
     * @return The result component for the string, or empty if the provided string is null
     */
    @NotNull
    public static Component parseMini(@Nullable final String str) {
        if (str == null) return Component.empty();

        return MINI_MESSAGE.deserialize(str);
    }

    /**
     * Parses the string using the MiniMessage library, and then uses the legacy Bukkit Component
     * Serializer to return a String rather than a Component. Format:
     * https://docs.advntr.dev/minimessage/format.html
     *
     * @param str The raw string(s)
     * @return The serialized component for the string, or empty if the provided string is null
     */
    @NotNull
    public static String legacyParseMini(@Nullable final String str) {
        return legacySerialize(parseMini(str));
    }

    /**
     * Serializes an Adventure {@link Component} using the legacy Bukkit Component Serializer, which
     * can be useful for displaying components in areas other than the chat (ex. item names).
     *
     * @param component The component to serialize
     * @return The serialized component
     * @see #legacyParseMini(String)
     */
    @NotNull
    public static String legacySerialize(@NotNull final Component component) {
        return BukkitComponentSerializer.legacy().serialize(component);
    }

    /**
     * Fully strip all color from the string.
     *
     * @param str The string to strip
     * @return The stripped string, or an empty string if the provided one is null
     */
    @NotNull
    public static String strip(@Nullable final String str) {
        if (str == null) return "";

        return ChatColor.stripColor(colourize(str));
    }

    /**
     * Fully strip all color from strings.
     *
     * @param strList The list of string to strip
     * @return The stripped string list, or an empty list if the provided one is null
     * @see #strip(String)
     */
    @NotNull
    public static List<String> strip(@Nullable final List<String> strList) {
        if (strList == null) return Collections.emptyList();

        return strList.stream().map(Text::strip).collect(Collectors.toList());
    }

    /**
     * Makes the first character uppercase and the rest lowercase.
     *
     * @param str The string
     * @return The string with the first letter in upper case and the rest in lower case
     */
    @NotNull
    public static String capitalizeFirst(@NotNull final String str) {
        if (str.isEmpty()) return str;

        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    /**
     * Makes a string Title Case.
     *
     * @param str       The string
     * @param delimeter The delimeter to use for splitting the string
     * @return The string in Title Case
     */
    @NotNull
    public static String titleCase(@NotNull final String str, @NotNull final String delimeter) {
        final StringBuilder builder = new StringBuilder();
        final Iterator<String> iterator = Arrays.stream(str.split(delimeter)).iterator();

        while (iterator.hasNext()) {
            builder.append(capitalizeFirst(iterator.next().toLowerCase()));
            if (iterator.hasNext()) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }


    // ---------------------------------------------------------------------------------
    // CONSOLE MESSAGES
    // ---------------------------------------------------------------------------------

    /**
     * Sends a formatted console message. Any message equaling null or {@link #IGNORE_MESSAGE_VALUE}
     * (ignore case) will be ignored.
     *
     * @param str The message to send
     */
    public static void console(@Nullable final String str) {
        if (str == null || str.equalsIgnoreCase(IGNORE_MESSAGE_VALUE)) return;

        Bukkit.getConsoleSender().sendMessage(format(str));
    }

    /**
     * Sends a colored console message. Any message equaling null or {@link #IGNORE_MESSAGE_VALUE}
     * (ignore case) will be ignored.
     *
     * @param str The message to send
     */
    public static void colouredConsole(@Nullable final String str) {
        if (str == null || str.equalsIgnoreCase(IGNORE_MESSAGE_VALUE)) return;

        Bukkit.getConsoleSender().sendMessage(colourize(str));
    }

    /**
     * Logs a plain message into the console.
     *
     * @param str The message to send
     */
    public static void log(@Nullable final String str) {
        if (str == null) return;

        PluginManager.getPlugin().getLogger().info(str);
    }

    /**
     * Logs a plain message into the console.
     *
     * @param str   The message to send
     * @param level The logging level
     */
    public static void log(final Level level, final String str) {
        if (str == null) return;

        PluginManager.getPlugin().getLogger().log(level, str);
    }


    // ---------------------------------------------------------------------------------
    // PLAYER MESSAGES
    // ---------------------------------------------------------------------------------

    /**
     * Sends a colored and prefixed message to the command sender. Any message equaling null or
     * {@link #IGNORE_MESSAGE_VALUE} (ignore case) will be ignored.
     *
     * @param sender The command sender that will receive the message
     * @param str    The message to send
     */
    public static void tell(@NotNull final CommandSender sender, @Nullable final String str) {
        if (str == null || str.equalsIgnoreCase(IGNORE_MESSAGE_VALUE)) return;

        if (!attemptTellMini(sender, str)) {
            sender.sendMessage(format(str));
        }
    }

    /**
     * Sends a {@link #localized(String, CommandSender, Object...)}, colored, and prefixed message to the command
     * sender. Any key equaling null or any message equaling to {@link #IGNORE_MESSAGE_VALUE} (ignore
     * case) will be ignored.
     *
     * @param sender The command sender that will receive the message
     * @param key    The key of the localized string
     * @param args   Arguments to replace in the localized string
     * @see #tell(CommandSender, String)
     */
    public static void tellLocalized(
            @NotNull final CommandSender sender,
            @Nullable final String key,
            final Object... args) {
        if (key == null) return;

        final String message = MessageFormat.format(localized(key, sender), args);

        if (message.equalsIgnoreCase(IGNORE_MESSAGE_VALUE)) return;

        if (!attemptTellMini(sender, message)) {
            sender.sendMessage(format(message));
        }
    }

    /**
     * Does the same thing as {@link #tell(CommandSender, String)}, but without the prefix. Any
     * message equaling null or IGNORE_MESSAGE_VALUE (ignore case) will be ignored.
     *
     * @param sender The command sender that will receive the message
     * @param str    The message to send
     */
    public static void colouredTell(@NotNull final CommandSender sender, @Nullable final String str) {
        if (str == null || str.equalsIgnoreCase(IGNORE_MESSAGE_VALUE)) return;

        if (!attemptTellMini(sender, str)) {
            sender.sendMessage(colourize(str));
        }
    }

    /**
     * Does the same thing as {@link #tellLocalized(CommandSender, String, Object...)}, but without
     * the prefix. Any key equaling null or any message equaling to {@link #IGNORE_MESSAGE_VALUE}
     * (ignore case) will be ignored.
     *
     * @param sender The command sender that will receive the message
     * @param key    The key of the localized string
     * @param args   Arguments to replace in the localized string
     * @see #tellLocalized(CommandSender, String, Object...)
     */
    public static void colouredTellLocalized(
            @NotNull final CommandSender sender,
            @Nullable final String key,
            final Object... args) {
        if (key == null) return;

        final String message = MessageFormat.format(localized(key, sender), args);

        if (message.equalsIgnoreCase(IGNORE_MESSAGE_VALUE)) return;

        if (!attemptTellMini(sender, message)) {
            sender.sendMessage(colourize(message));
        }
    }

    /**
     * Sends the {@link Component} to the player as a chat message.
     *
     * @param player    The player who should receive the component
     * @param component The component to send
     * @see #parseMini(String)
     */
    public static void tellComponent(@NotNull final Player player, @NotNull final Component component) {
        PluginManager.getAdventure().player(player).sendMessage(component);
    }

    /**
     * Sends a colored and centered message. May not work if the player has changed their chat size,
     * used a custom font (resource pack), or if the message contains HEX colors. Any message equaling
     * null or IGNORE_MESSAGE_VALUE (ignore case) will be ignored.
     *
     * @param player The player that will receive the message
     * @param str    The message to send
     */
    public static void tellCentered(@NotNull final Player player, @Nullable final String str) {
        if (str == null || str.equalsIgnoreCase(IGNORE_MESSAGE_VALUE)) return;

        if (str.isEmpty()) player.sendMessage("");

        final String colourized = colourize(str);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (final char c : colourized.toCharArray()) {
            if (c == ChatColor.COLOR_CHAR) {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                final DefaultFontInfo dfi = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dfi.getBoldLength() : dfi.getLength();
                messagePxSize++;
            }
        }

        final int halvedMessageSize = messagePxSize / 2;
        final int toCompensate = 154 - halvedMessageSize;
        final int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;

        final StringBuilder sb = new StringBuilder();

        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }

        player.sendMessage(sb + colourized);
    }

    /**
     * Broadcasts the message after coloring and formatting it. Any message equaling null or
     * {@link #IGNORE_MESSAGE_VALUE} (ignore case) will be ignored.
     *
     * @param permission The permission players must have in order to see this broadcast, or null if
     *                   the broadcast should be seen by everyone
     * @param str        The message to send
     */
    public static void broadcast(@Nullable final String permission, @Nullable final String str) {
        if (str == null || str.equalsIgnoreCase(IGNORE_MESSAGE_VALUE)) return;

        if (permission == null) {
            Bukkit.broadcastMessage(format(str));
            return;
        }

        Bukkit.broadcast(format(str), permission);
    }

    /**
     * Same thing as {@link #broadcast(String, String)}, but without the prefix. Any message equaling
     * null or IGNORE_MESSAGE_VALUE (ignore case) will be ignored.
     *
     * @param permission The permission players must have in order to see this broadcast, or null if
     *                   the broadcast should be seen by everyone
     * @param str        The message to send
     * @see #broadcast(String, String)
     */
    public static void broadcastColourized(@Nullable final String permission, @Nullable final String str) {
        if (str == null || str.equalsIgnoreCase(IGNORE_MESSAGE_VALUE)) return;

        if (permission == null) {
            Bukkit.broadcastMessage(colourize(str));
            return;
        }

        Bukkit.broadcast(colourize(str), permission);
    }


    // ---------------------------------------------------------------------------------
    // TITLES
    // ---------------------------------------------------------------------------------

    /**
     * Sends the title to the player.
     *
     * @param p        The player who should receive the title
     * @param title    The title to send, or null for none
     * @param subtitle The subtitle to send, or null for none
     */
    public static void sendTitle(
            @NotNull final Player p,
            @Nullable final String title,
            @Nullable final String subtitle) {
        Titles.sendTitle(p, Text.colourize(title), Text.colourize(subtitle));
    }

    /**
     * Sends the title to the player.
     *
     * @param p        The player who should receive the title
     * @param title    The title to send, or null for none
     * @param subtitle The subtitle to send, or null for none
     * @param fadeIn   The fade in duration, in ticks
     * @param stay     The stay duration, in ticks
     * @param fadeOut  The fade out duration, in ticks
     */
    public static void sendTitle(
            @NotNull final Player p,
            @Nullable final String title,
            @Nullable final String subtitle,
            final int fadeIn, final int stay, final int fadeOut) {
        Titles.sendTitle(p, fadeIn, stay, fadeOut, Text.colourize(title), Text.colourize(subtitle));
    }

    /**
     * Clears the current title and subtitle of the player.
     *
     * @param p The player who should have thier title and subtitle cleared
     */
    public static void clearTitle(@NotNull final Player p) {
        Titles.clearTitle(p);
    }


    // ---------------------------------------------------------------------------------
    // INTERNAL
    // ---------------------------------------------------------------------------------

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean attemptTellMini(final CommandSender sender, final String str) {
        if (!(sender instanceof Player)) return false;
        if (!str.startsWith(MINI_PREFIX)) return false;

        tellComponent((Player)sender, parseMini(str.replaceFirst(MINI_PREFIX, "")));
        return true;
    }
}
