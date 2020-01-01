package org.tinyconfiguration.common.basic.ex.property;

import org.tinyconfiguration.abc.Property;
import org.tinyconfiguration.abc.ex.PropertyException;

public class InvalidConfigurationPropertyException extends PropertyException {

    private Property invalid;

    public InvalidConfigurationPropertyException(String message, Property invalid) {
        super("The following property is invalid: \"" + invalid.getKey() + "\" => " + message);
        this.invalid = invalid;
    }

    public Property getInvalidProperty() {
        return invalid;
    }
}