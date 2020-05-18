package org.tinyconfiguration.abc.io.utils;

import org.tinyconfiguration.abc.utils.ExportType;

import java.util.concurrent.Future;

/**
 * The {@link Readable} interface provides common methods to perform reading operations
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface Readable {

    /**
     * Reads the configuration file
     *
     * @param type The configuration instance export type
     * @throws Exception If anything goes wrong while processing the file
     */
    void read(ExportType type) throws Exception;

    /**
     * Reads the configuration file asynchronously
     *
     * @param type The configuration instance export type
     * @return Future object representing the reading task
     */
    Future<Void> readAsync(ExportType type);
}
