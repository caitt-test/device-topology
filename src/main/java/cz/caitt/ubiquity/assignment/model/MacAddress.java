package cz.caitt.ubiquity.assignment.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value class representing MAC address.
 * <p>
 * New instances are created via {@link #parse(String)} factory method that checks that the address conforms
 * to MAC address format - 6 blocks of two hexadecimal digits separated by colon.
 */
public final class MacAddress implements Comparable<MacAddress> {
    private final static String HEX_DIGIT = "[0-9a-fA-F]{2}";
    private final static Pattern PATTERN = Pattern.compile("(" + HEX_DIGIT + ":" + "){5}" + HEX_DIGIT);

    private final String value;

    private MacAddress(String address) {
        this.value = address.toUpperCase();
    }

    public String getValue() {
        return value;
    }

    /**
     * Factory method to create new instances of MacAddress.
     * <p>
     * It checks that the address is valid MAC address.
     *
     * @param address value to be parsed
     * @return new instance of MacAddress
     * @throws IllegalArgumentException if address is present but not valid MAC address
     * @throws NullPointerException     if address is null
     */
    public static MacAddress parse(String address) {
        Objects.requireNonNull(address);
        if (!PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("Invalid mac address: " + address);
        }
        return new MacAddress(address);
    }

    /**
     * Convenience method that parses the address only if it is not null.
     *
     * @param address (optional) string to convert to MacAddress
     * @return if address is null returns null; otherwise parses the address
     * @throws IllegalArgumentException if address is present but not valid MAC address
     */
    public static MacAddress parseNull(String address) {
        if (address == null) {
            return null;
        }
        return parse(address);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MacAddress that = (MacAddress) obj;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(MacAddress o) {
        return value.compareTo(o.value);
    }
}
