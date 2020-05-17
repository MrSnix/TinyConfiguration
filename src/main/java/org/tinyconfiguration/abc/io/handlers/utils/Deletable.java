package org.tinyconfiguration.abc.io.handlers.utils;

import org.tinyconfiguration.abc.AbstractConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
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
     * @throws IOException If the configuration file cannot be deleted
     */
    default void delete(C instance) throws IOException {
        Files.delete(instance.getFile().toPath());
    }

    /**
     * Delete the configuration file asynchronously
     *
     * @param instance The configuration instance to delete
     * @return Future object representing the deleting task
     */
    default Future<Void> deleteAsync(C instance) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                delete(instance);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
            return null;
        });
    }

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
