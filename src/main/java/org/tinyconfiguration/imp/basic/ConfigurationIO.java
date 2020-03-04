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

    public static AbstractHandlerIO<Configuration> export(ExportType format) {

        AbstractHandlerIO<Configuration> e;

        switch (format) {
            case XML:
                e = Handler.as(ExportType.XML);
                break;
            case JSON:
                e = Handler.as(ExportType.JSON);
                break;
            default:
                throw new IllegalArgumentException("The following format is not supported");
        }

        return e;

    }
}
