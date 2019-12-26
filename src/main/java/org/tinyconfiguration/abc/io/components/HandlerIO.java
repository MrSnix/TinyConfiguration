package org.tinyconfiguration.abc.io.components;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.io.functionalities.Deletable;
import org.tinyconfiguration.abc.io.functionalities.Readable;
import org.tinyconfiguration.abc.io.functionalities.Writable;

/**
 * The {@link HandlerIO} interface provides common methods to perform I/O operations
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface HandlerIO<C extends AbstractConfiguration> extends Readable<C>, Writable<C>, Deletable<C> {

    /**
     * Check if the configuration file exists
     *
     * @param instance The configuration instance to check
     * @return True or false
     */
    @Override
    default boolean exist(C instance) {
        return instance.getFile().exists();
    }

}
