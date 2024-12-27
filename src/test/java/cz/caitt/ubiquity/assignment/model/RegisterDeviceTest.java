package cz.caitt.ubiquity.assignment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RegisterDeviceTest {

    private DeviceAPI deviceAPI;

    private final static MacAddress NOT_EXIST = MacAddress.parse("FF:00:00:00:00:00");
    private final static MacAddress MAC1 = MacAddress.parse("00:00:00:00:00:01");
    private final static MacAddress MAC2 = MacAddress.parse("00:00:00:00:00:02");

    @BeforeEach
    void setUp() {
        deviceAPI = new InMemoryDeviceAPI();
    }

    @Test
    void registerNullDevice() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> deviceAPI.registerDevice(null, MAC1, null))
                .withMessage("Device type must not be null");
    }

    @Test
    void registerNullMacAddress() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> deviceAPI.registerDevice(DeviceType.Gateway, null, null));
    }

    @Test
    void registerInvalidUplinkMacAddress() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> deviceAPI.registerDevice(DeviceType.Gateway, MAC1, NOT_EXIST));
    }

    @Test
    void registerLoop() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> deviceAPI.registerDevice(DeviceType.Gateway, MAC1, MAC1));
    }

    @Test
    void registerLink() {
        deviceAPI.registerDevice(DeviceType.Gateway, MAC1, null);
        deviceAPI.registerDevice(DeviceType.Gateway, MAC2, MAC1);
    }
}