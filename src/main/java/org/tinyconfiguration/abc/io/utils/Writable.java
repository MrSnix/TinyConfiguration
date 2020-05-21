package org.tinyconfiguration.abc.io.utils;

import org.tinyconfiguration.abc.utils.FormatType;

import java.util.concurrent.Future;

/**
 * The {@link Writable} interface provides common methods to perform writing operations
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface Writable {

    /**
     * Write the configuration file
     *
     * @param type The configuration instance export type
     * @throws Exception If anything goes wrong while processing the file
     */
    void write(FormatType type) throws Exception;

    /**
     * Write the configuration file asynchronously
     *
     * @param type The configuration instance export type
     * @return Future object representing the writing task
     */
    Future<Void> writeAsync(FormatType type);
}
