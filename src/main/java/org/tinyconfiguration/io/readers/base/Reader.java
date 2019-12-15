package org.tinyconfiguration.io.readers.base;

import org.tinyconfiguration.base.Configuration;

/**
 * The {@link Reader} interface provides methods to convert the underlying data representation as common formats
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface Reader {

    /**
     * This method generate an object representation of the configuration from the file
     *
     * @param instance The configuration instance on to perform the loading process
     * @throws Exception If something goes wrong during the process
     */
    void toObject(Configuration instance) throws Exception;

}
