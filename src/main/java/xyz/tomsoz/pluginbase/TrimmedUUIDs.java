package xyz.tomsoz.pluginbase;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Utility for converting to and from trimmed UUIDs.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TrimmedUUIDs {

    /**
     * Returns a UUID string without dashes (trimmed).
     *
     * @param uuid The UUID
     * @return The trimmed UUID
     */
    public static String toString(final UUID uuid) {
        return (digits(uuid.getMostSignificantBits() >> 32, 8)
                + digits(uuid.getMostSignificantBits() >> 16, 4)
                + digits(uuid.getMostSignificantBits(), 4)
                + digits(uuid.getLeastSignificantBits() >> 48, 4)
                + digits(uuid.getLeastSignificantBits(), 12));
    }

    /**
     * Parses a UUID from a trimmed string.
     *
     * @param string The trimmed UUID
     * @return The UUID
     */
    public static UUID fromString(final String string) throws IllegalArgumentException {

        if (string.length() != 32) {
            throw new IllegalArgumentException("Invalid length '" + string.length() + "': " + string);
        }

        try {
            return new UUID(
                    Long.parseUnsignedLong(string.substring(0, 16), 16),
                    Long.parseUnsignedLong(string.substring(16), 16)
            );
        } catch (final NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid UUID string: " + string, ex);
        }
    }

    private static String digits(final long val, final int digits) {
        final long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
}