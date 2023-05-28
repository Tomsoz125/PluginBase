package xyz.tomsoz.pluginbase;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Utilities for {@link org.bukkit.Location}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Locations {

    /**
     * Rounds a location to the center of a block.
     *
     * @param loc The location to center
     * @return The centered location
     */
    @NotNull
    public static Location center(@NotNull final Location loc) {
        return new Location(loc.getWorld(),
                loc.getBlockX() + 0.5,
                loc.getBlockY(),
                loc.getBlockZ() + 0.5,
                loc.getYaw(),
                loc.getPitch());
    }

    /**
     * Converts a location to a block location (world and integer coordinates).
     *
     * @param loc The location to convert to a block location
     * @return The block position
     */
    @NotNull
    public static Location toBlockLocation(@NotNull final Location loc) {
        return new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
}
