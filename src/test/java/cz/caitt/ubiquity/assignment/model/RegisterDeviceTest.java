package cz.caitt.ubiquity.assignment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegisterDeviceTest {

    private DeviceAPI deviceAPI;

    @BeforeEach
    void setUp() {
        deviceAPI = new InMemoryDeviceAPI();
    }

    @Test
    void registerNullDevice() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> deviceAPI.registerDevice(null, "MAC", null))
                .withMessage("Device type must not be null");
    }

    @Test
    void registerInvalidMacAddress() {
        // TODO:
//      assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy( () ->
        assertNotNull(
                deviceAPI.registerDevice(DeviceType.Gateway, "MAC", null)
        );
    }

    @Test
    void registerInvalidUplinkMacAddress() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> deviceAPI.registerDevice(DeviceType.Gateway, "MAC", "NOT_EXIST"));
    }

    @Test
    void registerLoop() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> deviceAPI.registerDevice(DeviceType.Gateway, "MAC", "MAC"));
    }

    @Test
    void registerLink() {
        deviceAPI.registerDevice(DeviceType.Gateway, "MAC1", null);
        deviceAPI.registerDevice(DeviceType.Gateway, "MAC2", "MAC1");
    }
}