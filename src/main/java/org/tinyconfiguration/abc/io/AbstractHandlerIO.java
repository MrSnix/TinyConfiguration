package org.tinyconfiguration.abc.io;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.io.handlers.utils.Deletable;
import org.tinyconfiguration.abc.io.handlers.utils.Readable;
import org.tinyconfiguration.abc.io.handlers.utils.Writable;

/**
 * The {@link AbstractHandlerIO} interface provides common methods to perform I/O operations
 *
 * @author G. Baittiner
 * @version 0.1
 */
public abstract class AbstractHandlerIO<C extends AbstractConfiguration<?>> implements Readable<C>, Writable<C>, Deletable<C> {

    /**
     * Check if the configuration file exists
     *
     * @param instance The configuration instance to check
     * @return True or false
     */
    @Override
    public boolean exist(C instance) {
        return instance.getFile().exists();
    }

}
