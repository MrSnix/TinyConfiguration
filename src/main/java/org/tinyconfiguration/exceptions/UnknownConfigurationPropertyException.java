package org.tinyconfiguration.exceptions;

import org.tinyconfiguration.data.Property;

import java.util.List;
import java.util.stream.Collectors;

public class UnknownConfigurationPropertyException extends Exception {

    private List<Property> unknownProperties;

    public UnknownConfigurationPropertyException(List<Property> unknownProperties) {
        super("The following properties are unknown: " + unknownProperties.
                stream().
                map(Property::getKey).
                collect(Collectors.joining()));
        this.unknownProperties = unknownProperties;
    }

    public List<Property> getUnknownProperties() {
        return unknownProperties;
    }
}
