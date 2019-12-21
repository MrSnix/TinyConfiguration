package org.tinyconfiguration.exceptions;

public class InvalidConfigurationVersionException extends Exception {

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
