package org.tinyconfiguration.imp.basic.io;


import org.tinyconfiguration.abc.data.Value;
import org.tinyconfiguration.abc.io.handlers.AbstractReader;
import org.tinyconfiguration.abc.io.handlers.AbstractWriter;
import org.tinyconfiguration.imp.basic.Configuration;
import org.tinyconfiguration.imp.basic.Property;
import org.tinyconfiguration.imp.basic.ex.configuration.InvalidConfigurationNameException;
import org.tinyconfiguration.imp.basic.ex.configuration.InvalidConfigurationVersionException;
import org.tinyconfiguration.imp.basic.ex.configuration.MissingConfigurationIdentifiersException;
import org.tinyconfiguration.imp.basic.ex.io.ParsingProcessException;
import org.tinyconfiguration.imp.basic.ex.property.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;

/**
 * The {@link HandlerXML} provides methods to convert the underlying data representation as XML
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class HandlerXML {

    public static final ImplWriterXML WRITER = new ImplWriterXML();
    public static final ImplReaderXML READER = new ImplReaderXML();

    private HandlerXML() {
    }

    public static final class ImplWriterXML implements AbstractWriter<Configuration, Property, Element> {

        private Document xml;


        /**
         * Write the configuration file
         *
         * @param instance The configuration instance to write
         * @throws IOException If anything goes wrong while processing the file
         */
        @Override
        public synchronized void write(Configuration instance) throws IOException {
            WRITER.toFile(instance);
        }

        /**
         * Write the configuration file asynchronously
         *
         * @param instance The configuration instance to write
         * @return Future object representing the writing task
         */
        @Override
        public Future<Void> writeAsync(Configuration instance) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    write(instance);
                } catch (IOException e) {
                    throw new CompletionException(e);
                }
                return null;
            });
        }


        /**
         * This method allow to generate an object representation from the configuration instance
         *
         * @param instance The configuration instance
         * @return The object representation of the following instance
         * @throws IOException If something goes wrong during the process
         */
        @Override
        public Document toObject(Configuration instance) throws IOException {

            DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
            // Using factory to get an instance of document builder
            DocumentBuilder db;

            try {
                db = builder.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new IOException(e);
            }

            // Creating the doc representation
            this.xml = db.newDocument();

            // The root element
            Element root = xml.createElement("configuration");

            root.setAttribute("name", instance.getName());
            root.setAttribute("version", instance.getVersion());

            Element properties = xml.createElement("properties");

            for (Property property : instance.getProperties()) {
                Element node = encode(property);
                properties.appendChild(node);
            }

            root.appendChild(properties);

            xml.appendChild(root);

            xml.normalize();

            return xml;
        }

        /**
         * This method allow to generate a file given any object representation of the configuration instance
         *
         * @param instance The configuration instance
         * @throws IOException If something goes wrong during the process
         */
        @Override
        public void toFile(Configuration instance) throws IOException {

            try {
                // Getting document
                Document obj = this.toObject(instance);

                // Generating xml writer
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();

                // Applying output property
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                // Creating output stream
                DOMSource source = new DOMSource(obj);
                StreamResult stream = new StreamResult(instance.getFile());

                // Writing
                transformer.transform(source, stream);
            } catch (TransformerException e) {
                throw new IOException(e);
            }

        }

        /**
         * This method allow to insert a property object inside an intermediate representation
         *
         * @param property The property instance
         * @return The new representation
         */
        @Override
        public Element encode(Property property) {
            // Creating object
            Element root = xml.createElement(property.getKey());
            // Acquiring value
            Value dt = property.getValue();

            // Encoding
            if (dt.isArray())
                __encode_array(root, property);
            else
                __encode_obj(root, property);

            return root;
        }

        /**
         * This method encode object-only property
         *
         * @param root     The root object
         * @param property The property instance
         */
        @Override
        public void __encode_obj(Element root, Property property) {

            Element value = this.xml.createElement("value");
            value.setTextContent(property.getValue().asString());

            Element description = this.xml.createElement("description");
            description.setTextContent(property.getDescription());

            root.appendChild(value);
            root.appendChild(description);
        }

        /**
         * This method encode array-only property
         *
         * @param root     The root object
         * @param property The property instance
         */
        @Override
        public void __encode_array(Element root, Property property) {

            Element values = this.xml.createElement("values");
            for (String tmp : property.getValue().asStringArray()) {
                // Generating each value
                Element value = this.xml.createElement("value");
                value.setTextContent(tmp);
                // Now appending to it
                values.appendChild(value);
            }

            Element description = this.xml.createElement("description");
            description.setTextContent(property.getDescription());

            root.appendChild(values);
            root.appendChild(description);

        }
    }

    public static final class ImplReaderXML implements AbstractReader<Configuration, Property, Element> {

        private List<Element> properties;

        /**
         * Reads the configuration file
         *
         * @param instance The configuration instance to read and update
         * @throws MissingConfigurationIdentifiersException If any configuration identifier (name, version) is missed
         * @throws InvalidConfigurationNameException        If the configuration name does not match the one inside the file
         * @throws InvalidConfigurationVersionException     If the configuration version does not match the one inside the file
         * @throws MissingConfigurationPropertyException    If any configuration property is missing from the file
         * @throws MalformedConfigurationPropertyException  If any configuration property is not well-formed
         * @throws DuplicatedConfigurationPropertyException If any configuration property is declared multiple times
         * @throws InvalidConfigurationPropertyException    If any configuration property fails its own validation test
         * @throws UnknownConfigurationPropertyException    If there are more properties inside the file than the one declared
         * @throws ParsingProcessException                  If a parsing exception of some sort has occurred.
         * @throws IOException                              If an I/O exception of some sort has occurred.
         */
        @Override
        public synchronized void read(Configuration instance) throws IOException, MissingConfigurationPropertyException, InvalidConfigurationNameException, InvalidConfigurationPropertyException, ParsingProcessException, MissingConfigurationIdentifiersException, InvalidConfigurationVersionException, UnknownConfigurationPropertyException, DuplicatedConfigurationPropertyException, MalformedConfigurationPropertyException {
            READER.toObject(instance);
        }

        /**
         * Reads the configuration file asynchronously
         *
         * @param instance The configuration instance to read
         * @return Future object representing the reading task
         * @throws CompletionException If any exceptions occurs at runtime
         */
        @Override
        public Future<Void> readAsync(Configuration instance) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    read(instance);
                } catch (
                        IOException | InvalidConfigurationNameException | InvalidConfigurationVersionException | MalformedConfigurationPropertyException | MissingConfigurationPropertyException | InvalidConfigurationPropertyException | UnknownConfigurationPropertyException | ParsingProcessException | MissingConfigurationIdentifiersException | DuplicatedConfigurationPropertyException e) {
                    throw new CompletionException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            });
        }

        /**
         * This method generate the final representation of the configuration
         *
         * @param instance The configuration instance
         */
        @Override
        public void toObject(Configuration instance) throws ParsingProcessException, IOException, MissingConfigurationIdentifiersException, InvalidConfigurationNameException, InvalidConfigurationVersionException, UnknownConfigurationPropertyException, DuplicatedConfigurationPropertyException, MissingConfigurationPropertyException, InvalidConfigurationPropertyException, MalformedConfigurationPropertyException {
            // Acquiring the intermediate representation
            Element configuration = fromFile(instance).getDocumentElement();

            // Acquiring basic info
            NamedNodeMap basic = configuration.getAttributes();

            String name = basic.getNamedItem("name").getNodeValue();
            String version = basic.getNamedItem("version").getNodeValue();
            this.properties = new ArrayList<>();

            // Basic check to verify file header integrity
            Handler.Internal.__decode_header(instance, name, version);

            NodeList properties = configuration.getElementsByTagName("properties");

            if (properties == null || properties.item(0) == null) {
                throw new ParsingProcessException("The 'properties' node is missing");
            }

            int read = 0;
            int expected = instance.getProperties().size();

            Node root = properties.item(0);

            // Iterating properties list
            for (Node p = root.getFirstChild(); p != null; p = p.getNextSibling()) {
                // Skipping any useless node like
                if (p.getNodeType() == Node.ELEMENT_NODE) {
                    // Adding as element to properties list
                    this.properties.add((Element) p);
                    // Summing node properties
                    read += 1;
                }
            }

            if (read > expected)
                throw new UnknownConfigurationPropertyException();

            for (Property property : instance.getProperties()) {
                decode(property);
            }

            this.properties = null;
        }

        /**
         * This method generate an intermediate object representation of the configuration from the file
         *
         * @param instance The configuration instance
         * @throws IOException             If something goes wrong during the process
         * @throws ParsingProcessException If a parsing exception of some sort has occurred.
         */
        @Override
        public Document fromFile(Configuration instance) throws ParsingProcessException, IOException {

            DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();

            Document doc;

            try {
                // Using factory to get an instance of document builder
                DocumentBuilder db = builder.newDocumentBuilder();
                // Creating the doc representation
                doc = db.parse(instance.getFile());
            } catch (ParserConfigurationException | SAXException e) {
                throw new ParsingProcessException(e.getMessage());
            }

            doc.normalize();

            return doc;
        }

        /**
         * This method allow to translate a property object inside an intermediate representation
         *
         * @param property The property instance
         */
        @Override
        public void decode(Property property) throws DuplicatedConfigurationPropertyException, MissingConfigurationPropertyException, MalformedConfigurationPropertyException, InvalidConfigurationPropertyException {

            // The node we are looking for
            Element property0 = null;
            // This flag stop the iteration
            boolean stop = false;

            // Iterating properties list
            for (Element p : properties) {
                // Found, let's get it
                if (p.getNodeName().equals(property.getKey())) {
                    // This means there is another property with the same key definition
                    if (stop) {
                        throw new DuplicatedConfigurationPropertyException(property);
                    }
                    // Save
                    property0 = p;
                    // Update flag
                    stop = true;
                }
            }

            // If the property was found, we proceed
            if (property0 != null) {

                boolean isArray = false;

                // Now, the property may be an array or an object
                if (property0.getElementsByTagName("values").getLength() != 0) {
                    isArray = true;
                }

                // Let's handle both cases, checking validity then updating the property value
                if (!isArray) {
                    __decode_obj(property, property0);
                } else {
                    __decode_array(property, property0);
                }


            }

            // In the end, if it is still null, no property with the given key was found inside the file
            if (property0 == null && !property.isOptional()) {
                throw new MissingConfigurationPropertyException(property);
            }
        }

        /**
         * This method decode object-only property
         *
         * @param property The property instance
         * @param obj      The intermediate object
         */
        @Override
        public void __decode_obj(Property property, Element obj) throws MalformedConfigurationPropertyException, InvalidConfigurationPropertyException {

            // Obtaining "value" element
            NodeList obj0 = obj.getElementsByTagName("value");

            // If exists, well done, we can move on
            if (obj0 == null || obj0.getLength() == 0) {
                throw new MalformedConfigurationPropertyException("The 'value' node is missing", property);
            }

            // Acquiring the element
            Element va0 = (Element) obj0.item(0);

            switch (property.getValue().getDatatype()) {
                case BOOLEAN:
                    if (va0.getTextContent().equalsIgnoreCase("true"))
                        property.setValue(true);
                    else if (va0.getTextContent().equalsIgnoreCase("false"))
                        property.setValue(false);
                    else
                        throw new MalformedConfigurationPropertyException("The value cannot be decoded as boolean", property);
                    break;
                case BYTE:
                    try {
                        property.setValue(Byte.parseByte(va0.getTextContent()));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case SHORT:
                    try {
                        property.setValue(Short.parseShort(va0.getTextContent()));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case INT:
                    try {
                        property.setValue(Integer.parseInt(va0.getTextContent()));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case LONG:
                    try {
                        property.setValue(Long.parseLong(va0.getTextContent()));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case FLOAT:
                    try {
                        property.setValue(Float.parseFloat(va0.getTextContent()));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case DOUBLE:
                    try {
                        property.setValue(Double.parseDouble(va0.getTextContent()));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case STRING:
                    property.setValue(va0.getTextContent());
                    break;
                case CHAR:
                    if (va0.getTextContent().length() > 1) {
                        throw new MalformedConfigurationPropertyException("The value cannot be decoded as char", property);
                    }
                    property.setValue(va0.getTextContent().charAt(0));
                    break;
                default:
                    throw new MalformedConfigurationPropertyException("Unexpected value: " + obj.getTextContent(), property);
            }

            // Final check
            if (!property.isValid()) {
                throw new InvalidConfigurationPropertyException("The validation test failed", property);
            }

        }

        /**
         * This method decode array-only property
         *
         * @param property The property instance
         * @param obj      The intermediate array
         */
        @Override
        public void __decode_array(Property property, Element obj) throws MalformedConfigurationPropertyException, InvalidConfigurationPropertyException {

            Value value = property.getValue();

            // Obtaining "value" element
            NodeList obj0 = obj.getElementsByTagName("values");

            // If exists, well done, we can move on
            if (obj0 == null || obj0.getLength() == 0) {
                throw new MalformedConfigurationPropertyException("The 'values' node is missing", property);
            }

            // Acquiring the element
            Element va0 = (Element) obj0.item(0);

            // Acquiring all node values
            NodeList values0 = va0.getElementsByTagName("value");

            // If it's empty, well done, we can move on
            if (values0 == null || values0.getLength() == 0) {
                // Just assigning empty arrays
                Handler.Internal.__empty_array(property);
            } else {

                List<Element> values = new ArrayList<>();

                // Acquiring all values node
                for (int i = 0; i < values0.getLength(); ++i) {

                    if (values0.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        // Fast cast to verify if element is right type
                        Element value1 = (Element) values0.item(i);
                        // Adding only if it's a value
                        if (value1.getTagName().equals("value"))
                            values.add((Element) values0.item(i));
                    }

                }

                switch (value.getDatatype()) {
                    case ARR_BOOLEAN:
                        try {
                            boolean[] booleans = new boolean[values.size()];
                            for (int i = 0; i < values.size(); ++i) {
                                booleans[i] = Boolean.parseBoolean(values.get(i).getTextContent());
                            }
                            property.setValue(booleans);
                        } catch (Exception e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as boolean array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_BYTE:
                        try {
                            byte[] bytes = new byte[values.size()];
                            for (int i = 0; i < values.size(); ++i) {
                                bytes[i] = Byte.parseByte(values.get(i).getTextContent());
                            }
                            property.setValue(bytes);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as byte array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_SHORT:
                        try {
                            short[] shorts = new short[values.size()];
                            for (int i = 0; i < values.size(); ++i) {
                                shorts[i] = Short.parseShort(values.get(i).getTextContent());
                            }
                            property.setValue(shorts);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as short array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_INT:
                        try {
                            int[] integers = new int[values.size()];
                            for (int i = 0; i < values.size(); ++i) {
                                integers[i] = Integer.parseInt(values.get(i).getTextContent());
                            }
                            property.setValue(integers);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as int array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_LONG:
                        try {
                            long[] longs = new long[values.size()];
                            for (int i = 0; i < values.size(); ++i) {
                                longs[i] = Long.parseLong(values.get(i).getTextContent());
                            }
                            property.setValue(longs);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as long array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_FLOAT:
                        try {
                            float[] floats = new float[values.size()];
                            for (int i = 0; i < values.size(); ++i) {
                                floats[i] = Float.parseFloat(values.get(i).getTextContent());
                            }
                            property.setValue(floats);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as float array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_DOUBLE:
                        try {
                            double[] doubles = new double[values.size()];
                            for (int i = 0; i < values.size(); ++i) {
                                doubles[i] = Double.parseDouble(values.get(i).getTextContent());
                            }
                            property.setValue(doubles);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as double array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_STRING:
                        try {
                            String[] strings = new String[values.size()];
                            for (int i = 0; i < values.size(); ++i) {
                                strings[i] = values.get(i).getTextContent();
                            }
                            property.setValue(strings);
                        } catch (Exception e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as string array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_CHAR:
                        try {
                            char[] characters = new char[values.size()];
                            for (int i = 0; i < values.size(); ++i) {

                                if (values.get(i).getTextContent().length() > 1) {
                                    throw new IllegalArgumentException("One of the values cannot be decoded as char");
                                }

                                characters[i] = values.get(i).getTextContent().charAt(0);
                            }
                            property.setValue(characters);
                        } catch (Exception e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as chars array: " + e.getMessage(), property);
                        }
                        break;
                }

                // Final check
                if (!property.isValid()) {
                    throw new InvalidConfigurationPropertyException("The validation test failed", property);
                }

            }

        }
    }

}
