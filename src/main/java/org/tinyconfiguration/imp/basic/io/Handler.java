package org.tinyconfiguration.imp.basic.io;

import org.tinyconfiguration.abc.io.AbstractHandlerIO;
import org.tinyconfiguration.abc.utils.ExportType;
import org.tinyconfiguration.imp.basic.Configuration;
import org.tinyconfiguration.imp.basic.Property;
import org.tinyconfiguration.imp.basic.ex.configuration.InvalidConfigurationNameException;
import org.tinyconfiguration.imp.basic.ex.configuration.InvalidConfigurationVersionException;
import org.tinyconfiguration.imp.basic.ex.configuration.MissingConfigurationIdentifiersException;
import org.tinyconfiguration.imp.basic.ex.io.ParsingProcessException;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.ScalarEvent;

import java.util.*;

/**
 * This class is the handler manager
 */
public final class Handler {

    private static final HandlerJSON handlerJSON = new HandlerJSON();
    private static final HandlerXML handlerXML = new HandlerXML();
    private static final HandlerYAML handlerYAML = new HandlerYAML();


    private Handler() {
    }

    public static AbstractHandlerIO<Configuration> get(ExportType format) {

        AbstractHandlerIO<Configuration> e;

        switch (format) {
            case XML:
                e = handlerXML;
                break;
            case JSON:
                e = handlerJSON;
                break;
            case YAML:
                e = handlerYAML;
                break;
            default:
                throw new IllegalArgumentException("The following format is not supported");
        }

        return e;

    }

    /**
     * This class contains common code shared between {@link AbstractHandlerIO} implementations
     */
    static final class Internal {

        public static void __empty_array(Property property) {
            switch (property.getValue().getDatatype()) {
                case ARR_BOOLEAN:
                    property.setValue(new boolean[0]);
                    break;
                case ARR_BYTE:
                    property.setValue(new byte[0]);
                    break;
                case ARR_SHORT:
                    property.setValue(new short[0]);
                    break;
                case ARR_INT:
                    property.setValue(new int[0]);
                    break;
                case ARR_LONG:
                    property.setValue(new long[0]);
                    break;
                case ARR_FLOAT:
                    property.setValue(new float[0]);
                    break;
                case ARR_DOUBLE:
                    property.setValue(new double[0]);
                    break;
                case ARR_STRING:
                    property.setValue(new String[0]);
                    break;
                case ARR_CHAR:
                    property.setValue(new char[0]);
                    break;
            }
        }

        public static void __decode_header(Configuration instance, String name, String version) throws
                MissingConfigurationIdentifiersException,
                InvalidConfigurationNameException,
                InvalidConfigurationVersionException {
            if (name == null)
                throw new MissingConfigurationIdentifiersException("name");

            if (version == null)
                throw new MissingConfigurationIdentifiersException("version");

            if (!name.equals(instance.getName()))
                throw new InvalidConfigurationNameException(instance.getName(), name);

            if (!version.equals(instance.getVersion()))
                throw new InvalidConfigurationVersionException(instance.getVersion(), version);
        }

        static final class YAML {

            /**
             * This method is specific __encode_header() YAML implementation
             *
             * @param graph The intermediate representation
             * @see Handler.Internal#__decode_header(Configuration, String, String)
             */
            public static void __decode_header(Configuration instance, ArrayDeque<Event> graph) throws
                    MissingConfigurationIdentifiersException,
                    InvalidConfigurationNameException,
                    InvalidConfigurationVersionException, ParsingProcessException {

                // These values must be declared as 'null'
                String name = null;
                String version = null;

                boolean stop = false;

                while (!graph.isEmpty() && !stop) {

                    Event e = graph.poll();

                    if (e.getEventId() == Event.ID.Scalar) {
                        // Creating property key
                        ScalarEvent key = (ScalarEvent) e;

                        // If the property key is any of these field, we can go on
                        if (key.getValue().equals("name") && graph.peek() != null) {

                            // Obtaining next token
                            Event e0 = graph.poll();

                            if (e0.getEventId() == Event.ID.Scalar) {
                                // It's the property value
                                ScalarEvent value = (ScalarEvent) e0;
                                // Assigning
                                name = value.getValue();
                            }

                        } else if (key.getValue().equals("version") && graph.peek() != null) {

                            // Obtaining next token
                            Event e0 = graph.poll();

                            if (e0.getEventId() == Event.ID.Scalar) {
                                // It's the property value
                                ScalarEvent value = (ScalarEvent) e0;
                                // Assigning
                                version = value.getValue();
                            }

                        } else if (key.getValue().equals("properties") && graph.peek() != null) {

                            // Obtaining next token
                            Event e0 = graph.poll();

                            // Now if properties is followed by a sequence start (properties list)
                            if (e0.getEventId() == Event.ID.SequenceStart) {
                                // We can stop there, honestly it may be more accurate but i will fix that later
                                stop = true;
                            }

                        }
                    }
                }

                // Calling default implementation
                Internal.__decode_header(instance, name, version);

                // If it didn't stop, 'properties' is missing from the configuration
                if (!stop) {
                    throw new ParsingProcessException("The 'properties' array is missing");
                }

                /* Now, removing the last four tag, which are:
                 *  - SequenceEndEvent => Means the properties list has finished
                 *  - MappingEndEvent  => Means the configuration object mapping has finished
                 *  - DocumentEndEvent => Means the document has been parsed
                 *  - StreamEndEvent   => Means the stream is going to close now
                 * _____________________________________________
                 *  This operation will simplify the parsing process,
                 *  those tag are not really that useful...
                 */

                for (int i = 0; i < 4; ++i) {
                    graph.removeLast();
                }

            }

            /**
             * This method is specific YAML implementation to simplify properties handling process
             *
             * @param instance The configuration instance
             * @param graph    The intermediate representation
             * @return The simplified intermediate representation
             */
            public static Map<String, Map<String, Object>> __decode_properties(Configuration instance, ArrayDeque<Event> graph) throws ParsingProcessException {

                Map<String, Map<String, Object>> properties = new LinkedHashMap<>();

                List<String> array = new ArrayList<>();
                boolean isArray = false;

                String current_property = null;

                while (graph.peek() != null) {

                    Event e = graph.poll();

                    switch (e.getEventId()) {
                        // Open and close properties tag
                        case MappingStart:
                        case MappingEnd:
                            break;
                        // Open and close array values
                        case SequenceStart:
                            // Setting flag on
                            isArray = true;
                            // Creating container
                            properties.put(current_property, new HashMap<>());
                            // Stop there
                            break;
                        case SequenceEnd:
                            // Saving
                            properties.get(current_property).put("values", new ArrayList<>(array));
                            // Deleting
                            array.clear();
                            // Setting flag off
                            isArray = false;
                            // Getting description
                            String desc = __decode_description(current_property, graph);
                            // Inserting
                            properties.get(current_property).put("description", desc);
                            // Stop there
                            break;
                        // Generic value
                        case Scalar:
                            // Casting
                            ScalarEvent o = (ScalarEvent) e;

                            if (isArray) {
                                // Inserting
                                array.add(o.getValue());
                            } else {
                                current_property = __decode_property(instance, graph, properties, current_property, o);
                            }

                            break;
                        default:
                            throw new ParsingProcessException("Unexpected encoding type: " + e.getEventId().name());
                    }

                }

                return properties;

            }

            private static String __decode_property(Configuration instance, ArrayDeque<Event> graph, Map<String, Map<String, Object>> properties, String current_property, ScalarEvent o) throws ParsingProcessException {
                // Checking if this value is a property name
                Optional<Property> p = instance.getProperties().stream().
                        filter(property -> o.getValue().equals(property.getKey())).
                        findFirst();

                // It's a property value
                if (p.isPresent()) {

                    // We store the key
                    current_property = p.get().getKey();

                    // If the next value is another scalar, we have found out the value
                    if (graph.peek() != null && graph.peek().getEventId() == Event.ID.Scalar) {

                        ScalarEvent s1 = (ScalarEvent) graph.poll();

                        if (s1 != null) {
                            // Creating container values
                            Map<String, Object> values = new HashMap<>();
                            // Inserting value
                            values.put("value", s1.getValue());
                            // Decoding description
                            String desc = __decode_description(current_property, graph);
                            // Inserting description
                            values.put("description", desc);
                            // Inserting container
                            properties.put(current_property, values);
                        } else {
                            throw new ParsingProcessException("While parsing 'value' could not be decoded properly: " + current_property);
                        }

                    }

                }

                return current_property;
            }

            private static String __decode_description(String current_property, ArrayDeque<Event> graph) throws ParsingProcessException {

                String description = null;

                // Acquiring description
                if (graph.peek() != null && graph.peek().getEventId() == Event.ID.Scalar) {

                    ScalarEvent s2 = (ScalarEvent) graph.poll();

                    if (s2 != null &&
                            s2.getValue() != null &&
                            s2.getValue().equals("description") &&
                            graph.peek() != null &&
                            graph.peek().getEventId() == Event.ID.Scalar) {

                        ScalarEvent s3 = (ScalarEvent) graph.poll();

                        if (s3 != null) {
                            // Inserting value
                            description = s3.getValue();
                        } else {
                            throw new ParsingProcessException("While parsing 'description' value could not be retrieved: " + current_property);
                        }

                    } else {
                        throw new ParsingProcessException("While parsing 'description' tag could not be decoded: " + current_property);
                    }

                }

                return description;
            }

            /**
             * This method is specific YAML implementation to retrieve the numbers of properties read
             *
             * @param graph The intermediate representation
             */
            public static int __evaluate_properties(ArrayDeque<Event> graph) throws ParsingProcessException {
                // Evaluating properties number
                long read = graph.stream().filter(event -> (
                        event.getEventId() == Event.ID.MappingStart) ||
                        (event.getEventId() == Event.ID.MappingEnd)).
                        count();

                // If some tags are missed from the yaml parsed
                if (read % 2 != 0) {
                    throw new ParsingProcessException("Invalid properties encoding");
                }

                return (int) (read / 2);

            }

        }

    }

}
