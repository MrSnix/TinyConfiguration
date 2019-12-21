package org.tinyconfiguration.exceptions;

public class InvalidConfigurationNameException extends Exception {

    private String provided;
    private String expected;

    public InvalidConfigurationNameException(String expected, String provided) {
        super("The configuration name does not match, " +
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
