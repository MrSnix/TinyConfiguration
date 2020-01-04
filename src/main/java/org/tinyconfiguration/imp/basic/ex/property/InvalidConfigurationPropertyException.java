package org.tinyconfiguration.imp.basic.ex.property;

import org.tinyconfiguration.abc.ex.PropertyException;
import org.tinyconfiguration.imp.basic.Property;

public class InvalidConfigurationPropertyException extends PropertyException {

    private final Property invalid;

    public InvalidConfigurationPropertyException(String message, Property invalid) {
        super("The following property is invalid: \"" + invalid.getKey() + "\" => " + message);
        this.invalid = invalid;
    }

    public Property getInvalidProperty() {
        return invalid;
    }
}