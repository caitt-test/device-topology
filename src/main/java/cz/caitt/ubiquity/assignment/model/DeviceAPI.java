package cz.caitt.ubiquity.assignment.model;

import java.util.List;

public interface DeviceAPI {
    /**
     * Registering a device to a network deployment
     *
     * @param deviceType    type of the added device
     * @param macAddress    unique identification of the device
     * @param uplinkAddress (optional) link to the upstream network device (must exist if provided)
     * @return instance of newly created Device
     * @throws NullPointerException     if mandatory arguments are null
     * @throws IllegalArgumentException if macAddress already exists
     * @throws IllegalArgumentException if uplinkAddress is provided, but it does not exist (or points to the same device)
     */
    Device registerDevice(DeviceType deviceType, String macAddress, String uplinkAddress);

    /**
     * Return all devices as list
     *
     * @return list of devices (sorting order: Gateway, then Switch, then Access Point)
     */
    List<Device> retrieveAllDevices();

    /**
     * Retrieving network deployment device by MAC address
     *
     * @param macAddress address identifying a device
     * @return Device with given address
     * @throws DeviceNotFoundException if device with given address was not registered
     */
    Device retrieveByMac(String macAddress);

    /**
     * Retrieving all registered network device topologies.
     *
     * @return topologies as tree structure, node is represented as macAddress
     */
    List<Node> retrieveAllTopologies();

    /**
     * Retrieving network device topology starting from a specific device.
     *
     * @param macAddress address identifying a device
     * @return Device topology where root node is device with matching macAddress
     * @throws DeviceNotFoundException if device with given address was not registered
     */
    Node retrieveTopology(String macAddress);
}
