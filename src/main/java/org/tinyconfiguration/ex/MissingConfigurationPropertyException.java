package org.tinyconfiguration.ex;

import org.tinyconfiguration.abc.ex.PropertyException;
import org.tinyconfiguration.properties.Property;

public class MissingConfigurationPropertyException extends PropertyException {

    private Property missing;

    public MissingConfigurationPropertyException(Property missing) {
        super("The following property is missed: " + missing.getKey());
        this.missing = missing;
    }

    public Property getMissingProperty() {
        return missing;
    }
}