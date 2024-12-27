package cz.caitt.ubiquity.assignment.web;

import cz.caitt.ubiquity.assignment.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/api/v1/device")
public class DeviceController {

    private final DeviceAPI deviceAPI = new InMemoryDeviceAPI();

    @GetMapping("")
    public List<Device> getAllDevices() {
        return deviceAPI.retrieveAllDevices();
    }

    @GetMapping("/macAddress/{macAddress}")
    public Device getMacAddress(@PathVariable String macAddress) {
        return deviceAPI.retrieveByMac(MacAddress.parse(macAddress));
    }

    @GetMapping("/topology/{macAddress}")
    public Node getTopology(@PathVariable String macAddress) {
        return deviceAPI.retrieveTopology(MacAddress.parse(macAddress));
    }

    @GetMapping("/topology")
    public List<Node> getTopology() {
        return deviceAPI.retrieveAllTopologies();
    }

    @PostMapping
    public void createDevice(@RequestBody DeviceRegistrationRequest device) {
        var macAddress = MacAddress.parseNull(device.getMacAddress());
        var uplinkAddress = MacAddress.parseNull(device.getUplinkAddress());
        deviceAPI.registerDevice(device.getDeviceType(), macAddress, uplinkAddress);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(DeviceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

