package org.tinyconfiguration.exceptions;

import org.tinyconfiguration.data.Property;

import java.util.List;
import java.util.stream.Collectors;

public class MalformedConfigurationPropertyException extends Exception {

    private List<Property> malformedProperty;

    public MalformedConfigurationPropertyException(List<Property> malformedProperty) {
        super("The following properties are malformed: " + malformedProperty.
                stream().
                map(Property::getKey).
                collect(Collectors.joining()));
        this.malformedProperty = malformedProperty;
    }

    public List<Property> getMalformedProperty() {
        return malformedProperty;
    }
}
