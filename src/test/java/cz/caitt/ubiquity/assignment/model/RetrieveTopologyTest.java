package cz.caitt.ubiquity.assignment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RetrieveTopologyTest {

    private DeviceAPI deviceAPI;

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
                .isThrownBy(() -> deviceAPI.retrieveTopology("NOT-EXIST"));
    }

    @Test
    void retrieveTopologyForTwoTrees() {
        var dev1 = deviceAPI.registerDevice(DeviceType.Switch, "MAC1", null);
        var dev2 = deviceAPI.registerDevice(DeviceType.Switch, "MAC2", null);
        var dev3 = deviceAPI.registerDevice(DeviceType.Gateway, "MAC3", "MAC2");

        var topology = deviceAPI.retrieveAllTopologies();

        assertThat(topology).hasSize(2);

        Map<String, List<Node>> collect = topology.stream().collect(Collectors.groupingBy(Node::getValue));

        assertThat(collect.get("MAC1")).hasSize(1);
        assertThat(collect.get("MAC1").getFirst()).isEqualTo(new Node("MAC1"));

        assertThat(collect.get("MAC2")).hasSize(1);

        var mac2 = new Node("MAC2");
        mac2.getChildren().add(new Node("MAC3"));
        assertThat(collect.get("MAC2").getFirst()).isEqualTo(mac2);
    }
}