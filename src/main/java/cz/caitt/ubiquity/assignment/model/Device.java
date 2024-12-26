package cz.caitt.ubiquity.assignment.model;

import lombok.Data;
import lombok.NonNull;

import java.util.Comparator;

@Data
public class Device implements Comparable<Device> {

    private final static Comparator<Device> comparator = Comparator.comparing(Device::getDeviceType).thenComparing(Device::getMacAddress);

    @NonNull
    private final DeviceType deviceType;

    @NonNull
    private final String macAddress;

    @Override
    public int compareTo(Device o) {
        return comparator.compare(this, o);
    }
}
