package org.tinyconfiguration.utils;

/**
 * This enum describes all the delimiters used to defines a well-made syntax for I/O processing
 *
 * @author G.Baittiner
 * @version 0.1
 */
public enum Delimiters {

    ASSIGNMENT("="), DESCRIPTION("#"), ESCAPE("\\"), PROPERTY(";"),
    GROUP_OPEN("["), GROUP_CLOSE("]"), GROUP_NAME_SEPARATOR(".");

    private final String value;

    /**
     * This constructor assign the character identifier on each delimiter
     *
     * @param value The identifier
     */
    Delimiters(String value) {
        this.value = value;
    }

    /**
     * @return The character identifier
     */
    public String value() {
        return value;
    }

    /**
     * @return The character identifier
     */
    @Override
    public String toString() {
        return value;
    }
}
