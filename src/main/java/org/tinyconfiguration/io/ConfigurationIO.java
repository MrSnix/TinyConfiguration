package org.tinyconfiguration.io;

import org.tinyconfiguration.Configuration;
import org.tinyconfiguration.abc.io.components.HandlerIO;
import org.tinyconfiguration.abc.io.components.writers.WriterJSON;
import org.tinyconfiguration.abc.io.utils.FormatType;
import org.tinyconfiguration.common.Property;

import javax.json.JsonObject;
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

    private static final class Writer implements WriterJSON<Configuration, Property> {

        /**
         * This method allow to generate an object representation from the configuration instance
         *
         * @param instance The configuration instance
         * @return The object representation of the following instance
         * @throws Exception If something goes wrong during the process
         */
        @Override
        public JsonObject toObject(Configuration instance) throws Exception {
            return null;
        }

        /**
         * This method allow to generate a file given any object representation of the configuration instance
         *
         * @param instance The configuration instance
         * @throws Exception If something goes wrong during the process
         */
        @Override
        public void toFile(Configuration instance) throws Exception {

        }

        /**
         * This method allow to insert a property object inside an intermediate representation
         *
         * @param property The property instance
         * @return The new representation
         */
        @Override
        public JsonObject toProperty(Property property) {
            return null;
        }
    }

}
