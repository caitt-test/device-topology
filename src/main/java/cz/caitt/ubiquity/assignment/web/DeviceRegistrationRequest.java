package cz.caitt.ubiquity.assignment.web;

import cz.caitt.ubiquity.assignment.model.DeviceType;
import lombok.Data;

@Data
public class DeviceRegistrationRequest {
    private DeviceType deviceType;
    private String macAddress;
    private String uplinkAddress;
}
