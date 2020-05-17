package org.tinyconfiguration.imp.basic.ex.configuration;

import org.tinyconfiguration.abc.ex.ConfigurationException;

public class MissingConfigurationIdentifiersException extends ConfigurationException {

    private final String expected;

    public MissingConfigurationIdentifiersException(String expected) {
        super("The configuration is missing the following identifier: " + expected);
        this.expected = expected;
    }

    public String getExpected() {
        return expected;
    }
}