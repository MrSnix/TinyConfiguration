package org.tinyconfiguration.common.basic.ex.property;

import org.tinyconfiguration.abc.Property;
import org.tinyconfiguration.abc.ex.PropertyException;

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