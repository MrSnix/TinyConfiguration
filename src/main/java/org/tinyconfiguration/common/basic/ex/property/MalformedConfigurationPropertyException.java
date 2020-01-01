package org.tinyconfiguration.common.basic.ex.property;

import org.tinyconfiguration.abc.Property;
import org.tinyconfiguration.abc.ex.PropertyException;

public class MalformedConfigurationPropertyException extends PropertyException {

    private final Property malformed;

    public MalformedConfigurationPropertyException(String message, Property malformed) {
        super("The following property is malformed: \"" + malformed.getKey() + "\" => " + message);
        this.malformed = malformed;
    }

    public Property getMissingProperty() {
        return malformed;
    }
}