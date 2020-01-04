package org.tinyconfiguration.imp.basic.ex.property;

import org.tinyconfiguration.abc.ex.PropertyException;
import org.tinyconfiguration.imp.basic.Property;

public class DuplicatedConfigurationPropertyException extends PropertyException {

    private final Property duplicated;

    public DuplicatedConfigurationPropertyException(Property duplicated) {
        super("The following property is duplicated: " + duplicated.getKey());
        this.duplicated = duplicated;
    }

    public Property getDuplicatedProperty() {
        return duplicated;
    }
}