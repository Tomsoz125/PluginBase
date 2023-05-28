package xyz.tomsoz.pluginbase;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Utility class for interacting with the Bukkit {@link org.bukkit.plugin.ServicesManager}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Services {

    /**
     * Loads a service instance, throwing a {@link IllegalStateException} if no registration is
     * present.
     *
     * @param clazz the service class
     * @param <T>   the service class type
     * @return the service instance
     */
    @NotNull
    public static <T> T load(@NotNull final Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz");
        return get(clazz).orElseThrow(() -> new IllegalStateException(
                "No registration present for service '" + clazz.getName() + "'"));
    }

    /**
     * Loads a service instance
     *
     * @param clazz the service class
     * @param <T>   the service class type
     * @return the service instance, as an optional
     */
    @NotNull
    public static <T> Optional<T> get(@NotNull final Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz");
        final RegisteredServiceProvider<T> registration = Bukkit.getServicesManager()
                .getRegistration(clazz);
        if (registration == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(registration.getProvider());
    }

    /**
     * Provides a service.
     *
     * @param clazz    the service class
     * @param instance the service instance
     * @param plugin   the plugin to register the service to
     * @param priority the priority to register the service instance at
     * @param <T>      the service class type
     * @return the same service instance
     */
    @NotNull
    public static <T> T provide(@NotNull final Class<T> clazz, @NotNull final T instance,
                                @NotNull final Plugin plugin,
                                @NotNull final ServicePriority priority) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(instance, "instance");
        Objects.requireNonNull(plugin, "plugin");
        Objects.requireNonNull(priority, "priority");
        Bukkit.getServicesManager().register(clazz, instance, plugin, priority);
        return instance;
    }

    /**
     * Provides a service.
     *
     * @param clazz    the service class
     * @param instance the service instance
     * @param priority the priority to register the service instance at
     * @param <T>      the service class type
     * @return the same service instance
     */
    @NotNull
    public static <T> T provide(@NotNull final Class<T> clazz, @NotNull final T instance,
                                @NotNull final ServicePriority priority) {
        return provide(clazz, instance, PluginManager.getPlugin(), priority);
    }

    /**
     * Provides a service.
     *
     * @param clazz    the service class
     * @param instance the service instance
     * @param <T>      the service class type
     * @return the same service instance
     */
    @NotNull
    public static <T> T provide(@NotNull final Class<T> clazz, @NotNull final T instance) {
        return provide(clazz, instance, ServicePriority.Normal);
    }
}
