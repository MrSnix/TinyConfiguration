package org.tinyconfiguration.imp.basic.ex.configuration;

import org.tinyconfiguration.abc.ex.ConfigurationException;

public class InvalidConfigurationVersionException extends ConfigurationException {

    private final String provided;
    private final String expected;

    public InvalidConfigurationVersionException(String expected, String provided) {
        super("The configuration version does not match, " +
                "expecting \"" + expected + "\" but \"" + provided + "\" was provided");
        this.expected = expected;
        this.provided = provided;
    }

    public String getExpected() {
        return expected;
    }

    public String getProvided() {
        return provided;
    }
}