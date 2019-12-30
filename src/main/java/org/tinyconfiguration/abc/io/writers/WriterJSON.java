package org.tinyconfiguration.abc.io.writers;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.io.AbstractWriter;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * The {@link WriterJSON} provides methods to convert the underlying data representation as JSON
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface WriterJSON<C extends AbstractConfiguration, P extends AbstractProperty> extends AbstractWriter<C, P> {

    /**
     * This method allow to generate an object representation from the configuration instance
     *
     * @param instance The configuration instance
     * @return The object representation of the following instance
     * @throws Exception If something goes wrong during the process
     */
    @Override
    JsonObject toObject(C instance) throws Exception;

    /**
     * This method allow to generate a file given any object representation of the configuration instance
     *
     * @param instance The configuration instance
     * @throws Exception If something goes wrong during the process
     * @see AbstractWriter#toObject(AbstractConfiguration)
     */
    @Override
    void toFile(C instance) throws Exception;

    /**
     * This method allow to insert a property object inside an intermediate representation
     *
     * @param property The property instance
     * @return The new representation
     */
    @Override
    JsonObject encode(P property);

    /**
     * This method encode object-only property
     *
     * @param property The property instance
     * @param root     The root object
     */
    void __encode_obj(JsonObjectBuilder root, P property);

    /**
     * This method encode array-only property
     *
     * @param property The property instance
     * @param root     The root object
     */
    void __encode_array(JsonObjectBuilder root, P property);

}
