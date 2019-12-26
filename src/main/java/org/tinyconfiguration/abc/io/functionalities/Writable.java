package org.tinyconfiguration.abc.io.functionalities;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.io.utils.FormatType;

import java.util.concurrent.Future;

/**
 * The {@link Writable} interface provides common methods to perform writing operations
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface Writable<C extends AbstractConfiguration> {

    /**
     * Write the configuration file
     *
     * @param type     The format type to encode the configuration instance
     * @param instance The configuration instance to write
     * @throws Exception If anything goes wrong while processing the file
     */
    void write(FormatType type, C instance) throws Exception;

    /**
     * Write the configuration file asynchronously
     *
     * @param type     The format type to encode the configuration instance
     * @param instance The configuration instance to write
     * @return Future object representing the writing task
     * @throws Exception If anything goes wrong while processing the file
     */
    Future<Void> writeAsync(FormatType type, C instance) throws Exception;

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
