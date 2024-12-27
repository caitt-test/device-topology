package cz.caitt.ubiquity.assignment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RetrieveAllTest {
    private final static MacAddress MAC1 = MacAddress.parse("00:00:00:00:00:01");
    private final static MacAddress MAC2 = MacAddress.parse("00:00:00:00:00:02");
    private final static MacAddress MAC3 = MacAddress.parse("00:00:00:00:00:03");

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
        var dev1 = deviceAPI.registerDevice(DeviceType.Gateway, MAC1, null);

        assertThat(deviceAPI.retrieveAllDevices()).containsExactly(dev1);
    }

    @Test
    void retrieveAll2() {
        var dev1 = deviceAPI.registerDevice(DeviceType.Gateway, MAC1, null);
        var dev2 = deviceAPI.registerDevice(DeviceType.Gateway, MAC2, null);

        assertThat(deviceAPI.retrieveAllDevices()).containsExactly(dev1, dev2);
    }

    @Test
    void retrieveAll3() {
        var dev1 = deviceAPI.registerDevice(DeviceType.Switch, MAC1, null);
        var dev2 = deviceAPI.registerDevice(DeviceType.AccessPoint, MAC2, null);
        var dev3 = deviceAPI.registerDevice(DeviceType.Gateway, MAC3, null);

        assertThat(deviceAPI.retrieveAllDevices()).containsExactly(dev3, dev1, dev2);
    }
}