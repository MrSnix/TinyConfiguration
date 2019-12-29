package org.tinyconfiguration.ex;

import org.tinyconfiguration.abc.ex.PropertyException;
import org.tinyconfiguration.properties.Property;

public class MalformedConfigurationPropertyException extends PropertyException {

    private Property malformed;

    public MalformedConfigurationPropertyException(String message, Property malformed) {
        super("The following property is malformed: \"" + malformed.getKey() + "\" => " + message);
        this.malformed = malformed;
    }

    public Property getMissingProperty() {
        return malformed;
    }
}