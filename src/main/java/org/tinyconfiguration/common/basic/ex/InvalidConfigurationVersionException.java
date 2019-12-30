package org.tinyconfiguration.common.basic.ex;

import org.tinyconfiguration.abc.ex.ConfigurationException;

public class InvalidConfigurationVersionException extends ConfigurationException {

    private String provided;
    private String expected;

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