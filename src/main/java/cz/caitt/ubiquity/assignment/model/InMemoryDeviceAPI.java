package cz.caitt.ubiquity.assignment.model;

import java.util.*;

/**
 * Simple implementation of Device API that keeps all objects in its internal memory
 */
public class InMemoryDeviceAPI implements DeviceAPI {

    private final Map<MacAddress, Device> map = new HashMap<>();
    private final Map<MacAddress, List<MacAddress>> childrenMap = new HashMap<>();
    private final List<MacAddress> roots = new ArrayList<>();

    @Override
    public synchronized Device registerDevice(DeviceType deviceType, MacAddress macAddress, MacAddress uplinkAddress) {
        Objects.requireNonNull(deviceType, "Device type must not be null");
        if (map.containsKey(macAddress)) {
            throw new IllegalArgumentException("Device mac address " + macAddress + " already exists");
        }
        if (uplinkAddress != null && !map.containsKey(uplinkAddress)) {
            throw new IllegalArgumentException("Device uplink address " + uplinkAddress + " does not exist");
        }
        var device = new Device(deviceType, macAddress);
        map.put(macAddress, device);
        childrenMap.put(macAddress, new ArrayList<>());

        if (uplinkAddress != null) {
            childrenMap.get(uplinkAddress).add(macAddress);
        } else {
            roots.add(macAddress);
        }
        return device;
    }

    @Override
    public synchronized List<Device> retrieveAllDevices() {
        return map.values().stream().sorted().toList();
    }

    @Override
    public synchronized Device retrieveByMac(MacAddress macAddress) {
        var result = map.get(macAddress);
        if (result == null) {
            throw new DeviceNotFoundException("Cannot find device with macAddress: " + macAddress);
        } else {
            return result;
        }
    }

    @Override
    public synchronized List<Node> retrieveAllTopologies() {
        return roots.stream().map(this::retrieveTopology).toList();
    }

    @Override
    public synchronized Node retrieveTopology(MacAddress macAddress) {
        var device = map.get(macAddress);
        if (device == null) {
            throw new DeviceNotFoundException("Cannot find device with macAddress: " + macAddress);
        }
        var root = new Node(macAddress);
        var stack = new ArrayDeque<Node>();

        stack.push(root);

        while (!stack.isEmpty()) {
            var stackElement = stack.pop();
            for (var address : childrenMap.get(stackElement.getAddress())) {
                var nodeChild = new Node(address);
                stackElement.getChildren().add(nodeChild);
                stack.push(nodeChild);
            }
        }

        return root;
    }
}
