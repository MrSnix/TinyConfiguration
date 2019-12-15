package org.tinyconfiguration.io.writers.base;

import org.tinyconfiguration.base.Configuration;

/**
 * The {@link Writer} interface provides methods to convert the underlying data representation as common formats
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface Writer<T> {

    /**
     * This method allow to generate an object representation from the configuration instance
     *
     * @param instance The configuration instance
     * @return The object representation of the following instance
     * @throws Exception If something goes wrong during the process
     */
    T toObject(Configuration instance) throws Exception;

    /**
     * This method allow to generate a file given any object representation of the configuration instance
     *
     * @param instance The configuration instance
     * @throws Exception If something goes wrong during the process
     * @see Writer#toObject(Configuration)
     */
    void toFile(Configuration instance) throws Exception;

}
