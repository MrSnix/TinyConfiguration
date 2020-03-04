package org.tinyconfiguration.abc.io.handlers.utils;

import org.tinyconfiguration.abc.AbstractConfiguration;

import java.util.concurrent.Future;

/**
 * The {@link Readable} interface provides common methods to perform reading operations
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface Readable<C extends AbstractConfiguration<?>> {

    /**
     * Reads the configuration file
     *
     * @param instance The configuration instance to read and update
     * @throws Exception If anything goes wrong while processing the file
     */
    void read(C instance) throws Exception;

    /**
     * Reads the configuration file asynchronously
     *
     * @param instance The configuration instance to read
     * @return Future object representing the reading task
     */
    Future<Void> readAsync(C instance);

    /**
     * Check if the configuration file exists
     *
     * @param instance The configuration instance to check
     * @return True or false
     */
    default boolean exist(C instance) {
        return instance.getFile().exists();
    }

}