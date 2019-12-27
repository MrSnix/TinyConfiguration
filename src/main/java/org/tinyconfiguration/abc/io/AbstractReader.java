package org.tinyconfiguration.abc.io;

import org.tinyconfiguration.abc.AbstractConfiguration;

/**
 * The {@link AbstractReader} interface provides methods to convert the underlying data representation as common formats
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface AbstractReader<C extends AbstractConfiguration> {

    /**
     * This method generate an object representation of the configuration from the file
     *
     * @param configuration The configuration instance
     * @throws Exception If something goes wrong during the process
     */
    void toObject(C configuration) throws Exception;

}