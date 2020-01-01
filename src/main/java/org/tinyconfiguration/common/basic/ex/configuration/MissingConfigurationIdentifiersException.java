package org.tinyconfiguration.common.basic.ex.configuration;

import org.tinyconfiguration.abc.ex.ConfigurationException;

public class MissingConfigurationIdentifiersException extends ConfigurationException {

    private String expected;

    public MissingConfigurationIdentifiersException(String expected) {
        super("The configuration is missing the following identifier: " + expected);
        this.expected = expected;
    }

    public String getExpected() {
        return expected;
    }
}