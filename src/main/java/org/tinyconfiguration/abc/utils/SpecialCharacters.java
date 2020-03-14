package org.tinyconfiguration.abc.utils;

import static org.tinyconfiguration.abc.utils.SpecialCharacters.Type.STR_DECODE;
import static org.tinyconfiguration.abc.utils.SpecialCharacters.Type.STR_ENCODE;

/**
 * This enum defines a common gateway to encode or decode all the special characters inside the properties
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
            case STR_DECODE:
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

                value = value.
                        replace("\\\\", "\\").
                        replace("\\\"", "\"");
                break;

            case STR_ENCODE:
                value = value.
                        replace("\\", "\\\\").
                        replace("\n", "\\n").
                        replace("\r", "\\r").
                        replace("\t", "\\t").
                        replace("\b", "\\b").
                        replace("\"", "\\\"");
                break;
            case ARR_ENCODE:
                value = substitute(STR_ENCODE, value).replace(",", "\\,");
                break;
            case ARR_DECODE:
                value = substitute(STR_DECODE, value).replace("\\,", ",");
                break;
        }

        return value;
    }

    /**
     * All the processing operations available
     */
    public enum Type {
        STR_ENCODE, STR_DECODE, ARR_ENCODE, ARR_DECODE
    }

}