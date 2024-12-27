package cz.caitt.ubiquity.assignment.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MacAddressTest {

    @ParameterizedTest
    @ValueSource(strings = {"00:00:00:00:00:00", "ff:ff:ff:ff:ff:ff"})
    void parseValidAddress(String address) {
        MacAddress macAddress = MacAddress.parse(address);

        assertThat(macAddress.getValue()).isEqualTo(address.toUpperCase());
    }

    @ParameterizedTest
    @ValueSource(strings = {"00:00:00:00:00:0x", "", "0:0:0:0:0:0", "00:00:00:00:00"})
    void parseInvalidAddress(String address) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> MacAddress.parse(address))
                .withMessage("Invalid mac address: " + address);
    }

    @Test
    void testToString() {
        MacAddress macAddress = MacAddress.parse("00:00:00:00:00:00");

        assertThat(macAddress.toString()).isEqualTo("00:00:00:00:00:00");
    }

    @Test
    void testEquals() {
        MacAddress macAddress1 = MacAddress.parse("00:00:00:00:00:ff");
        MacAddress macAddress2 = MacAddress.parse("00:00:00:00:00:FF");

        assertThat(macAddress1).isEqualTo(macAddress2);
    }
}