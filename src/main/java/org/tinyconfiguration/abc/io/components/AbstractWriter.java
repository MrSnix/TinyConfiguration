package org.tinyconfiguration.abc.io.components;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.AbstractProperty;

/**
 * The {@link AbstractWriter} interface provides methods to convert the underlying data representation as common formats
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface AbstractWriter<C extends AbstractConfiguration> {

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
     * This method allow to insert a property object inside an intermediate representation
     *
     * @param property The property instance
     * @return The new representation
     */
    <P extends AbstractProperty> Object toProperty(P property);

}
