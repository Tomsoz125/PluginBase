package xyz.tomsoz.pluginbase;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.Nullable;
import xyz.tomsoz.pluginbase.Text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Simple utility for checking for resource updates on SpigotMC.
 */
public class UpdateChecker {

    @Getter private final int resourceId;
    @Getter private final String latestVersion;
    @Getter private final UpdateChecker.Result result;

    /**
     * Creates a new update checker instance and caches the latest version from Spigot. Should be
     * called asynchronously.
     *
     * @param resourceId The resource ID on SpigotMC
     */
    public UpdateChecker(final int resourceId) {
        this.resourceId = resourceId;
        this.latestVersion = retrieveVersionFromSpigot();

        if (latestVersion == null) {
            this.result = UpdateChecker.Result.ERROR;
        } else if (Common.getVersion().equals(latestVersion)) {
            this.result = UpdateChecker.Result.UP_TO_DATE;
        } else {
            this.result = UpdateChecker.Result.OUTDATED;
        }
    }

    /**
     * Notifies the console or a player about a new update. Calling this method if the
     * {@link #getResult()} is anything other than {@link UpdateChecker.Result#OUTDATED} will do
     * nothing.
     *
     * @param sender The console or player to notify, defaults to console if null
     */
    public void notifyResult(@Nullable final CommandSender sender) {

        if (getResult() != Result.OUTDATED) {
            return;
        }

        final CommandSender cs = Common.getOrDefault(sender, Bukkit.getConsoleSender());
        final String line = cs instanceof ConsoleCommandSender
                ? Text.CONSOLE_LINE : Text.CHAT_LINE;

        Text.colouredTell(cs, "&2" + line);
        Text.colouredTell(cs, "&aA new update for " + Common.getName() + " is available!");
        Text.colouredTell(cs, "&aYour version: &f" + Common.getVersion());
        Text.colouredTell(cs, "&aLatest version: &f" + latestVersion);
        Text.colouredTell(cs, "&aDownload: &fhttps://spigotmc.org/resources/" + resourceId);
        Text.colouredTell(cs, "&2" + line);
    }

    private String retrieveVersionFromSpigot() {

        try (final InputStream inputStream = new URL(
                "https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
             final Scanner scanner = new Scanner(inputStream)) {

            if (scanner.hasNext()) {
                return scanner.next();
            }

        } catch (final IOException ex) {
            return null;
        }

        return null;
    }

    /**
     * The result of an update check.
     */
    public enum Result {
        /**
         * The plugin version matches the one on the resource.
         */
        UP_TO_DATE,
        /**
         * The plugin version does not match the one on the resource.
         */
        OUTDATED,
        /**
         * There was an error whilst checking for updates.
         */
        ERROR
    }
}
