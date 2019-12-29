package org.tinyconfiguration.abc.io.readers;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.io.AbstractReader;

public interface ReaderJSON<C extends AbstractConfiguration, P extends AbstractProperty, A, O> extends AbstractReader<C, P, A> {

    /**
     * This method allow to return a property object inside an intermediate representation
     *
     * @param property The property instance
     * @param array    The properties intermediate representation instance
     */
    @Override
    void decode(P property, A array) throws Exception;

    /**
     * This method decode object-only property
     *
     * @param property The property instance
     * @param obj      The intermediate object
     * @return The new representation
     */
    void __decode_obj(P property, O obj);

    /**
     * This method decode array-only property
     *
     * @param property The property instance
     * @param array    The intermediate array
     * @return The new representation
     */
    void __decode_array(P property, A array);

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
