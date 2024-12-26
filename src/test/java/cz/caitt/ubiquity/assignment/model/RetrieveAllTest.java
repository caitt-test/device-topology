package cz.caitt.ubiquity.assignment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RetrieveAllTest {

    private DeviceAPI deviceAPI;

    @BeforeEach
    void setUp() {
        deviceAPI = new InMemoryDeviceAPI();
    }

    @Test
    void retrieveAllOnEmptyTopology() {
        assertThat(deviceAPI.retrieveAllDevices()).isEmpty();
    }

    @Test
    void retrieveAll1() {
        var dev1 = deviceAPI.registerDevice(DeviceType.Gateway, "MAC", null);

        assertThat(deviceAPI.retrieveAllDevices()).containsExactly(dev1);
    }

    @Test
    void retrieveAll2() {
        var dev1 = deviceAPI.registerDevice(DeviceType.Gateway, "MAC1", null);
        var dev2 = deviceAPI.registerDevice(DeviceType.Gateway, "MAC2", null);

        // TODO: verify wanted behaviour
        assertThat(deviceAPI.retrieveAllDevices()).containsExactly(dev1, dev2);
    }

    @Test
    void retrieveAll3() {
        var dev1 = deviceAPI.registerDevice(DeviceType.Switch, "MAC1", null);
        var dev2 = deviceAPI.registerDevice(DeviceType.Gateway, "MAC2", null);

        assertThat(deviceAPI.retrieveAllDevices()).containsExactly(dev2, dev1);
    }
}