package org.tinyconfiguration.abc.io.writers;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.io.AbstractWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The {@link WriterXML} provides methods to convert the underlying data representation as XML
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface WriterXML<C extends AbstractConfiguration<P>, P extends AbstractProperty> extends AbstractWriter<C, P> {

    /**
     * This method allow to generate an object representation from the configuration instance
     *
     * @param instance The configuration instance
     * @return The object representation of the following instance
     * @throws Exception If something goes wrong during the process
     */
    @Override
    Document toObject(C instance) throws Exception;

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
    Element encode(P property);

    /**
     * This method encode object-only property
     *
     * @param property The property instance
     * @param root     The root object
     */
    void __encode_obj(Element root, P property);

    /**
     * This method encode array-only property
     *
     * @param property The property instance
     * @param root     The root object
     */
    void __encode_array(Element root, P property);

}
