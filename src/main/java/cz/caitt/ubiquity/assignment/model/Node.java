package cz.caitt.ubiquity.assignment.model;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class Node {
    @NonNull
    private final String value;

    private final List<Node> children = new ArrayList<>();
}
