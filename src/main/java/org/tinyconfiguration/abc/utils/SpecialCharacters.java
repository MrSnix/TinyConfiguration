package org.tinyconfiguration.abc.utils;

/**
 * This enum defines a common gateway to escape or unescape all the special characters inside the properties
 *
 * @author G. Baittiner
 * @version 0.1
 */
public enum SpecialCharacters {

    ;

    /**
     * Replace all the special characters inside a given string
     *
     * @param type  The operation to execute on the string
     * @param value The data string
     * @return The processed string
     */
    public static String substitute(Type type, String value) {

        switch (type) {
            case UNESCAPE:
                value = value.
                        replace("\\n", "\n").
                        replace("\\r", "\r").
                        replace("\\t", "\t").
                        replace("\\b", "\b").
                        replace("\\\\\\", "\\\\");

                value = value.
                        replaceAll("\\\\\n", "\\\\n").
                        replaceAll("\\\\\r", "\\\\r").
                        replaceAll("\\\\\b", "\\\\b").
                        replaceAll("\\\\\t", "\\\\t");

                value = value.replace("\\\\", "\\");
                break;

            case ESCAPE:
                value = value.
                        replace("\\", "\\\\").
                        replace("\n", "\\n").
                        replace("\r", "\\r").
                        replace("\t", "\\t").
                        replace("\b", "\\b");
                break;
        }

        return value;
    }

    /**
     * All the processing operations available
     */
    public enum Type {
        UNESCAPE, ESCAPE
    }

}