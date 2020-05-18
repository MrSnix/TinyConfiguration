package org.tinyconfiguration.imp.basic;

import org.tinyconfiguration.abc.io.AbstractHandlerIO;
import org.tinyconfiguration.abc.utils.ExportType;
import org.tinyconfiguration.imp.basic.io.HandlerCSV;
import org.tinyconfiguration.imp.basic.io.HandlerJSON;
import org.tinyconfiguration.imp.basic.io.HandlerXML;
import org.tinyconfiguration.imp.basic.io.HandlerYAML;

/**
 * The {@link ConfigurationIO} class contains {@link AbstractHandlerIO} references which can be used on any {@link Configuration} instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class ConfigurationIO {

    /**
     * Returns an {@link AbstractHandlerIO} implementation for the specified type
     *
     * @param format The format type as specified on {@link ExportType}
     * @return The {@link AbstractHandlerIO} implementation
     */
    public static AbstractHandlerIO<Configuration> as(ExportType format) {

        if (format == null)
            throw new NullPointerException("The export format cannot be null");

        AbstractHandlerIO<Configuration> e;

        switch (format) {
            case XML:
                e = HandlerXML.INSTANCE;
                break;
            case JSON:
                e = HandlerJSON.INSTANCE;
                break;
            case YAML:
                e = HandlerYAML.INSTANCE;
                break;
            case CSV:
                e = HandlerCSV.INSTANCE;
                break;
            default:
                throw new IllegalArgumentException("The following format is not supported");
        }

        return e;
    }

}
