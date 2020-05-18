package org.tinyconfiguration.abc.io.handlers;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.AbstractProperty;

import java.util.concurrent.Future;

/**
 * The {@link AbstractWriter} interface provides methods to convert the underlying data representation as common formats
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface AbstractWriter<C extends AbstractConfiguration<?>, P extends AbstractProperty, I> {


    /**
     * Write the configuration file
     *
     * @param instance The configuration instance to write
     * @throws Exception If anything goes wrong while processing the file
     */
    void write(C instance) throws Exception;

    /**
     * Write the configuration file asynchronously
     *
     * @param instance The configuration instance to write
     * @return Future object representing the writing task
     */
    Future<Void> writeAsync(C instance);

    /**
     * This method allow to insert a property object inside an intermediate representation
     *
     * @param property The property instance
     * @return The new representation
     */
    Object encode(P property);

    /**
     * This method allow to generate an object representation from the configuration instance
     *
     * @param instance The configuration instance
     * @return The object representation of the following instance
     * @throws Exception If something goes wrong during the process
     */
    Object toObject(C instance) throws Exception;

    /**
     * This method allow to generate a file given any object representation of the configuration instance
     *
     * @param instance The configuration instance
     * @throws Exception If something goes wrong during the process
     * @see AbstractWriter#toObject(AbstractConfiguration)
     */
    void toFile(C instance) throws Exception;

    /**
     * This method encode object-only property
     *
     * @param property The property instance
     * @param root     The root object
     */
    void __encode_obj(I root, P property);

    /**
     * This method encode array-only property
     *
     * @param property The property instance
     * @param root     The root object
     */
    void __encode_array(I root, P property);
}
