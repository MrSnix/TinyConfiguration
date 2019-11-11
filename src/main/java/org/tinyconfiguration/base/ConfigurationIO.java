package org.tinyconfiguration.base;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.tinyconfiguration.data.base.Datatype;
import org.tinyconfiguration.events.ConfigurationListener;
import org.tinyconfiguration.utils.ExportType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * The {@link ConfigurationIO} class contains I/O operations which can be executed on any {@link Configuration} instance.
 *
 * <p></p>
 * <p>
 * This class provides simple methods in order to achieve maximum efficiency without creating complexity:
 *
 * <ul>
 *     <li>{@link ConfigurationIO#read(Configuration)} - Blocking method to load configuration files</li>
 *     <li>{@link ConfigurationIO#readAsync(Configuration)} - Non-blocking method to load configuration files</li>
 *     <li>{@link ConfigurationIO#write(ExportType, Configuration)} - Blocking method to save configuration files</li>
 *     <li>{@link ConfigurationIO#writeAsync(Configuration)} - Non-blocking method to save configuration files</li>
 * </ul>
 *
 *
 * <ul>
 *     <li>{@link ConfigurationIO#delete(Configuration)} - Blocking method to delete configuration files</li>
 *     <li>{@link ConfigurationIO#deleteAsync(Configuration)} - Non-blocking method to delete configuration files</li>
 * </ul>
 *
 * <ul>
 *     <li>{@link ConfigurationIO#exist(Configuration)} - It can be used to verify if the configuration has been saved on disk </li>
 * </ul>
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class ConfigurationIO {

    /**
     * Private empty constructor
     */
    private ConfigurationIO() {

    }

    /**
     * Reads the configuration file
     * <p></p>
     *
     * @param instance The configuration instance to read and update
     * @throws IOException If anything goes wrong while processing the file
     */
    public static void read(Configuration instance) throws IOException {

    }

    /**
     * Reads the configuration file asynchronously
     * <p></p>
     *
     * @param instance The configuration instance to read and update
     * @return The result of the asynchronous reading computation
     */
    public static Future<Void> readAsync(Configuration instance) {

        return null;
    }

    /**
     * Write the configuration file
     *
     * @param type The export type which translate the configuration instance
     * @param instance The configuration instance to write
     * @throws IOException If anything goes wrong while processing the file
     * @throws IllegalArgumentException If the export format type is unknown
     */
    public static void write(ExportType type, Configuration instance) throws IOException {

        switch (type) {
            case JSON:
                Writer._exportToJSON(instance);
                break;
            case XML:
                try {
                    Writer._exportToXML(instance);
                } catch (ParserConfigurationException e) {
                    throw new IOException(e);
                }
                break;
            default:
                throw new IllegalArgumentException("The following format is unknown: " + type);
        }

    }

    /**
     * Write the configuration file asynchronously
     *
     * @param instance The configuration instance to write
     * @throws IOException If anything goes wrong while processing the file
     */
    public static Future<Void> writeAsync(Configuration instance) throws IOException {
        return null;
    }

    /**
     * Delete the configuration file
     *
     * @param instance The configuration instance to delete
     * @return True or false
     */
    public static boolean delete(Configuration instance) {

        File cfg = Paths.get(instance.getPathname(), instance.getFilename()).toFile();

        for (ConfigurationListener listener : instance.getDeleteListeners()) {
            listener.execute(instance);
        }

        return cfg.delete();
    }

    /**
     * Delete the configuration file asynchronously
     *
     * @param instance The configuration instance to delete
     * @return The result of the asynchronous deleting computation
     */
    public static Future<Boolean> deleteAsync(Configuration instance) {
        return CompletableFuture.supplyAsync(() -> delete(instance));
    }

    /**
     * Check if the configuration file exists
     *
     * @param instance The configuration instance to check
     * @return True or false
     */
    public static boolean exist(Configuration instance) {
        return instance.getFile().exists();
    }

    /**
     * The {@link Writer} class provides methods to exports configurations data as common formats
     *
     * @author G. Baittiner
     * @version 0.1
     */

    static class Writer {

        static void _exportToJSON(Configuration instance) throws IOException {

            Map<String, Object> options = new HashMap<>(1);
            options.put(JsonGenerator.PRETTY_PRINTING, true);

            JsonWriterFactory writerFactory = Json.createWriterFactory(options);
            JsonObject obj = ObjectWriter._writeAsJsonObject(instance);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(instance.getFile()));
                 JsonWriter writer = writerFactory.createWriter(bw)) {

                writer.writeObject(obj);

            }

        }

        static void _exportToXML(Configuration instance) throws ParserConfigurationException, IOException {

            Document obj = ObjectWriter._writeAsXMLObject(instance);

            OutputFormat format = new OutputFormat();

            format.setLineWidth(120);
            format.setIndenting(true);
            format.setIndent(4);
            format.setEncoding(StandardCharsets.UTF_8.name());

            XMLSerializer srx = new XMLSerializer(new FileWriter(instance.getFile()), format);

            srx.serialize(obj);

        }

    }

    /**
     * The {@link ObjectWriter} class provides methods to convert the underlying data representation as common formats
     *
     * @author G. Baittiner
     * @version 0.1
     */
    static class ObjectWriter {

        static JsonObject _writeAsJsonObject(Configuration instance) {

            JsonObjectBuilder root = Json.createObjectBuilder();
            JsonArrayBuilder nodes = Json.createArrayBuilder();

            root.add("name", instance.getName());
            root.add("version", instance.getVersion());

            instance.getGroups().forEach(group -> {

                JsonObjectBuilder node = Json.createObjectBuilder();

                JsonArrayBuilder properties = Json.createArrayBuilder();
                JsonObjectBuilder property = Json.createObjectBuilder();

                node.add("group", group);

                instance.get(group).forEach(p -> {

                    property.add("key", p.getKey());

                    Datatype dt = p.getValue();

                    if (dt.isArray()) {

                        JsonArrayBuilder values = Json.createArrayBuilder();
                        String[] tmp = dt.asArray().asStringArray();

                        for (String s : tmp) {
                            values.add(s);
                        }

                        property.add("values", values);

                    } else {
                        property.add("value", dt.asValue().asString());
                    }

                    if (p.getDescription() == null)
                        property.addNull("description");
                    else
                        property.add("description", p.getDescription());

                    properties.add(property);

                });

                node.add("properties", properties);

                nodes.add(node);

            });

            root.add("groups", nodes);

            return root.build();
        }

        static Document _writeAsXMLObject(Configuration instance) throws ParserConfigurationException {

            DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
            // Using factory to get an instance of document builder
            DocumentBuilder db = builder.newDocumentBuilder();
            // Creating the doc representation
            Document xml = db.newDocument();

            // The root element
            Element root = xml.createElement("configuration");

            root.setAttribute("name", instance.getName());
            root.setAttribute("version", instance.getVersion());

            // This will contain all elements to attach to the root
            Element groups = xml.createElement("groups");

            instance.getGroups().forEach(name -> {

                Element group = xml.createElement("group");

                group.setAttribute("name", name);

                Element properties = xml.createElement("properties");

                instance.get(name).forEach(p -> {

                    Element property = xml.createElement("property");

                    Element key = xml.createElement("key");
                    Element desc = xml.createElement("description");
                    Element values = null;

                    key.setTextContent(p.getKey());
                    desc.setTextContent(p.getDescription());

                    Datatype dt = p.getValue();

                    if (dt.isArray()) {

                        String[] tmp = dt.asArray().asStringArray();

                        values = xml.createElement("values");

                        for (String s : tmp) {
                            // Create single node
                            Element e = xml.createElement("value");
                            // Setting text
                            e.setTextContent(s);
                            // Appending
                            values.appendChild(e);
                        }

                    } else {
                        // Create single node
                        values = xml.createElement("value");
                        // Setting text
                        values.setTextContent(dt.asValue().asString());
                    }

                    if (p.getDescription() == null)
                        desc.setTextContent("null");
                    else
                        desc.setTextContent(p.getDescription());

                    property.appendChild(key);
                    property.appendChild(desc);
                    property.appendChild(values);

                    properties.appendChild(property);

                });

                group.appendChild(properties);

                groups.appendChild(group);

            });

            root.appendChild(groups);

            xml.appendChild(root);

            return xml;
        }

    }

}
