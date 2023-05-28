package xyz.tomsoz.pluginbase;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Utility for operations involving multiple players.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Players {

    /**
     * Gets all players on the server.
     *
     * @return All players on the server
     */
    @NotNull
    public static Collection<Player> all() {
        //noinspection unchecked
        return (Collection<Player>) Bukkit.getOnlinePlayers();
    }

    /**
     * Gets a stream of all players on the server.
     *
     * @return A stream of all players on the server
     */
    @NotNull
    public static Stream<Player> stream() {
        return all().stream();
    }

    /**
     * Applies a given action to all players on the server.
     *
     * @param consumer The action to apply
     */
    public static void forEach(@NotNull final Consumer<Player> consumer) {
        all().forEach(consumer);
    }

    /**
     * Gets a stream of all players within a given radius of a point.
     *
     * @param center The point
     * @param radius The radius
     * @return A stream of players
     */
    @NotNull
    public static Stream<Player> streamInRange(@NotNull final Location center, final double radius) {
        Objects.requireNonNull(center.getWorld(), "Provided location does not specify world");
        return center.getWorld().getNearbyEntities(center, radius, radius, radius).stream()
                .filter(e -> e.getType() == EntityType.PLAYER)
                .map(Player.class::cast);
    }

    /**
     * Applies an action to all players within a given radius of a point.
     *
     * @param center   The point
     * @param radius   The radius
     * @param consumer The action to apply
     */
    public static void forEachInRange(
            @NotNull final Location center,
            final double radius,
            @NotNull final Consumer<Player> consumer
    ) {
        streamInRange(center, radius).forEach(consumer);
    }
}
