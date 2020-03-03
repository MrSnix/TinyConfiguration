package org.tinyconfiguration.imp.basic.ex.property;

import org.tinyconfiguration.abc.ex.PropertyException;
import org.tinyconfiguration.imp.basic.Property;

public class MissingConfigurationPropertyException extends PropertyException {

    private final Property missing;

    public MissingConfigurationPropertyException(Property missing) {
        super("The following property is missing: " + missing.getKey());
        this.missing = missing;
    }

    public Property getMissingProperty() {
        return missing;
    }
}