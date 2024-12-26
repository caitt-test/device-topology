package cz.caitt.ubiquity.assignment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RetrieveByMacTest {

    private DeviceAPI deviceAPI;

    @BeforeEach
    void setUp() {
        deviceAPI = new InMemoryDeviceAPI();
    }

    @Test
    void retrieveByMac() {
        var dev1 = deviceAPI.registerDevice(DeviceType.Switch, "MAC1", null);

        assertNotNull(dev1);
        assertThat(deviceAPI.retrieveByMac("MAC1")).isSameAs(dev1);
    }

    @Test
    void retrieveByMacNotExist() {
        deviceAPI.registerDevice(DeviceType.Switch, "MAC1", null);

        assertThatExceptionOfType(DeviceNotFoundException.class)
                .isThrownBy(() -> deviceAPI.retrieveByMac("MAC2"));

    }
}