package org.tinyconfiguration.imp.basic;

import org.tinyconfiguration.abc.io.AbstractHandlerIO;
import org.tinyconfiguration.abc.utils.ExportType;
import org.tinyconfiguration.imp.basic.io.Handler;

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
                e = Handler.get(ExportType.XML);
                break;
            case JSON:
                e = Handler.get(ExportType.JSON);
                break;
            case YAML:
                e = Handler.get(ExportType.YAML);
                break;
            case CSV:
                e = Handler.get(ExportType.CSV);
                break;
            default:
                throw new IllegalArgumentException("The following format is not supported");
        }

        return e;
    }

}
