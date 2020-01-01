package org.tinyconfiguration.common.basic.ex.io;

import org.tinyconfiguration.abc.ex.ConfigurationException;

public class ParsingProcessException extends ConfigurationException {

    public ParsingProcessException(String message) {
        super("The configuration is not well formed: => " + message);
    }
}