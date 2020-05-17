package org.tinyconfiguration.abc.io.handlers;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.AbstractProperty;

/**
 * The {@link AbstractReader} interface provides methods to convert the underlying data representation as common formats
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface AbstractReader<C extends AbstractConfiguration<?>, P extends AbstractProperty, I> {

    /**
     * This method allow to translate a property object inside an intermediate representation
     *
     * @param property The property instance
     * @throws Exception If the decode process fails
     */
    void decode(P property) throws Exception;

    /**
     * This method generate the final representation of the configuration
     *
     * @param instance The configuration instance
     * @throws Exception If something goes wrong during the process
     */
    void toObject(C instance) throws Exception;

    /**
     * This method generate an intermediate object representation of the configuration from the file
     *
     * @param instance The configuration instance
     * @return The new object representation
     * @throws Exception If something goes wrong during the process
     */
    Object fromFile(C instance) throws Exception;

    /**
     * This method decode object-only property
     *
     * @param property The property instance
     * @param obj      The intermediate object
     * @throws Exception If the decode process fails
     */
    void __decode_obj(P property, I obj) throws Exception;

    /**
     * This method decode array-only property
     *
     * @param property The property instance
     * @param obj      The intermediate array
     * @throws Exception If the decode process fails
     */
    void __decode_array(P property, I obj) throws Exception;
}