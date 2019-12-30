package org.tinyconfiguration.common.basic.ex;

import org.tinyconfiguration.abc.ex.PropertyException;

public class UnknownConfigurationPropertyException extends PropertyException {

    public UnknownConfigurationPropertyException() {
        super("There is one or more unknown properties inside the configuration file");
    }
}