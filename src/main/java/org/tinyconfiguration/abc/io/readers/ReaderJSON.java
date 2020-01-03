package org.tinyconfiguration.abc.io.readers;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.io.AbstractReader;

import javax.json.JsonObject;

public interface ReaderJSON<C extends AbstractConfiguration, P extends AbstractProperty<?>> extends AbstractReader<C, P> {

    /**
     * This method allow to translate a property object inside an intermediate representation
     *
     * @param property The property instance
     */
    @Override
    void decode(P property) throws Exception;

    /**
     * This method decode object-only property
     *
     * @param property The property instance
     * @param obj      The intermediate object
     */
    void __decode_obj(P property, JsonObject obj) throws Exception;

    /**
     * This method decode array-only property
     *
     * @param property The property instance
     * @param obj      The intermediate array
     */
    void __decode_array(P property, JsonObject obj) throws Exception;

    /**
     * This method generate the final representation of the configuration
     *
     * @param instance The configuration instance
     * @throws Exception If something goes wrong during the process
     */
    @Override
    void toObject(C instance) throws Exception;

    /**
     * This method generate an intermediate object representation of the configuration from the file
     *
     * @param instance The configuration instance
     * @throws Exception If something goes wrong during the process
     */
    @Override
    Object fromFile(C instance) throws Exception;
}
