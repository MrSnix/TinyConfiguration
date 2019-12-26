package org.tinyconfiguration.io;

import org.tinyconfiguration.Configuration;
import org.tinyconfiguration.abc.io.components.HandlerIO;
import org.tinyconfiguration.abc.io.utils.FormatType;

import java.util.concurrent.Future;

/**
 * The {@link ConfigurationIO} class contains I/O operations which can be executed on any {@link Configuration} instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public class ConfigurationIO implements HandlerIO<Configuration> {

    /**
     * Private empty constructor
     */
    private ConfigurationIO() {

    }

    /**
     * Delete the configuration file
     *
     * @param instance The configuration instance to delete
     * @throws Exception If the configuration file cannot be deleted
     */
    @Override
    public void delete(Configuration instance) throws Exception {

    }

    /**
     * Delete the configuration file asynchronously
     *
     * @param instance The configuration instance to delete
     * @return Future object representing the deleting task
     */
    @Override
    public Future<Void> deleteAsync(Configuration instance) {
        return null;
    }

    /**
     * Reads the configuration file
     *
     * @param type     The format type to encode the configuration instance
     * @param instance The configuration instance to read and update
     * @throws Exception If anything goes wrong while processing the file
     */
    @Override
    public void read(FormatType type, Configuration instance) throws Exception {

    }

    /**
     * Reads the configuration file asynchronously
     *
     * @param type     The format type to encode the configuration instance
     * @param instance The configuration instance to read
     * @return Future object representing the reading task
     * @throws Exception If anything goes wrong while processing the file
     */
    @Override
    public Future<Void> readAsync(FormatType type, Configuration instance) throws Exception {
        return null;
    }

    /**
     * Write the configuration file
     *
     * @param type     The format type to encode the configuration instance
     * @param instance The configuration instance to write
     * @throws Exception If anything goes wrong while processing the file
     */
    @Override
    public void write(FormatType type, Configuration instance) throws Exception {

    }

    /**
     * Write the configuration file asynchronously
     *
     * @param type     The format type to encode the configuration instance
     * @param instance The configuration instance to write
     * @return Future object representing the writing task
     * @throws Exception If anything goes wrong while processing the file
     */
    @Override
    public Future<Void> writeAsync(FormatType type, Configuration instance) throws Exception {
        return null;
    }
}
