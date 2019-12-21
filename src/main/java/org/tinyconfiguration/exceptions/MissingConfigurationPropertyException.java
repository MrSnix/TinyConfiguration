package org.tinyconfiguration.exceptions;

import org.tinyconfiguration.data.Property;

import java.util.List;
import java.util.stream.Collectors;

public class MissingConfigurationPropertyException extends Exception {

    private List<Property> missingProperty;

    public MissingConfigurationPropertyException(List<Property> missingProperty) {
        super("The following properties are missed: " + missingProperty.
                stream().
                map(Property::getKey).
                collect(Collectors.joining()));
        this.missingProperty = missingProperty;
    }

    public List<Property> getMissingProperty() {
        return missingProperty;
    }
}
