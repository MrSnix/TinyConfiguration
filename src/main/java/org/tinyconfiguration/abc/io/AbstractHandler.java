package org.tinyconfiguration.abc.io;

import org.tinyconfiguration.Configuration;
import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.utils.FormatType;

/**
 * The {@link AbstractHandler} provides on any {@link AbstractConfiguration} its own {@link AbstractHandlerIO} for each {@link FormatType}
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface AbstractHandler<C extends AbstractConfiguration> {

    /**
     * Gets the JSON handler
     *
     * @return The {@link AbstractHandlerIO} interface on {@link Configuration} as JSON
     */
    <T extends AbstractHandlerIO<C>> T getHandlerJSON();
}
