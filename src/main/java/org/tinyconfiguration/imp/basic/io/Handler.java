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

import java.util.ArrayDeque;

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
        }

    }

}
