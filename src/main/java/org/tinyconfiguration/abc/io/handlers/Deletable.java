package org.tinyconfiguration.abc.io.handlers;

import org.tinyconfiguration.abc.AbstractConfiguration;

import java.util.concurrent.Future;

/**
 * The {@link Deletable} interface provides common methods to perform deleting operations
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface Deletable<C extends AbstractConfiguration<?>> {

    /**
     * Delete the configuration file
     *
     * @param instance The configuration instance to delete
     * @throws Exception If the configuration file cannot be deleted
     */
    void delete(C instance) throws Exception;

    /**
     * Delete the configuration file asynchronously
     *
     * @param instance The configuration instance to delete
     * @return Future object representing the deleting task
     */
    Future<Void> deleteAsync(C instance);

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
