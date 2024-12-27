package cz.caitt.ubiquity.assignment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RetrieveTopologyTest {

    private DeviceAPI deviceAPI;

    private final static MacAddress NOT_EXIST = MacAddress.parse("FF:00:00:00:00:00");
    private final static MacAddress MAC1 = MacAddress.parse("00:00:00:00:00:01");
    private final static MacAddress MAC2 = MacAddress.parse("00:00:00:00:00:02");
    private final static MacAddress MAC3 = MacAddress.parse("00:00:00:00:00:03");

    @BeforeEach
    void setUp() {
        deviceAPI = new InMemoryDeviceAPI();
    }

    @Test
    void retrieveEmptyTopology() {
        var deviceTopology = deviceAPI.retrieveAllTopologies();

        assertNotNull(deviceTopology);
    }

    @Test
    void retrieveTopologyFromNonExistentDevice() {
        assertThatExceptionOfType(DeviceNotFoundException.class)
                .isThrownBy(() -> deviceAPI.retrieveTopology(NOT_EXIST));
    }

    @Test
    void retrieveTopologyForTwoTrees() {
        deviceAPI.registerDevice(DeviceType.Switch, MAC1, null);
        deviceAPI.registerDevice(DeviceType.Switch, MAC2, null);
        deviceAPI.registerDevice(DeviceType.Gateway, MAC3, MAC2);


        var topology = deviceAPI.retrieveAllTopologies();


        assertThat(topology).hasSize(2);

        var collect = topology.stream().collect(Collectors.groupingBy(Node::getAddress));
        assertThat(collect.get(MAC1)).hasSize(1);
        assertThat(collect.get(MAC1).getFirst()).isEqualTo(new Node(MAC1));

        var mac2 = new Node(MAC2);
        mac2.getChildren().add(new Node(MAC3));
        assertThat(collect.get(MAC2)).hasSize(1);
        assertThat(collect.get(MAC2).getFirst()).isEqualTo(mac2);
    }
}