package org.tinyconfiguration.io;

import org.tinyconfiguration.Configuration;
import org.tinyconfiguration.abc.io.AbstractHandler;
import org.tinyconfiguration.abc.io.AbstractHandlerIO;
import org.tinyconfiguration.abc.io.writers.WriterJSON;
import org.tinyconfiguration.common.Datatype;
import org.tinyconfiguration.common.Property;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * The {@link ConfigurationIO} class contains I/O operations which can be executed on any {@link Configuration} instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class ConfigurationIO implements AbstractHandler<Configuration> {

    // Handlers
    private final HandlerJSON HandlerJSON;

    /**
     * Public empty constructor
     */
    public ConfigurationIO() {
        this.HandlerJSON = new HandlerJSON();
    }

    /**
     * Gets the JSON handler
     *
     * @return The {@link AbstractHandlerIO} interface on {@link Configuration} as JSON
     */
    @Override
    public HandlerJSON getHandlerJSON() {
        return HandlerJSON;
    }

    /**
     * The {@link ImplWriterJSON} class contains the implementations of I/O operations which can be executed on any {@link Configuration} instance
     *
     * @author G. Baittiner
     * @version 0.1
     */
    private static final class ImplWriterJSON implements WriterJSON<Configuration, Property> {

        /**
         * This method allow to generate an object representation from the configuration instance
         *
         * @param instance The configuration instance
         * @return The object representation of the following instance
         * @throws Exception If something goes wrong during the process
         */
        @Override
        public JsonObject toObject(Configuration instance) throws Exception {

            JsonObjectBuilder root = Json.createObjectBuilder();
            JsonArrayBuilder nodes = Json.createArrayBuilder();

            root.add("name", instance.getName());
            root.add("version", instance.getVersion());

            for (Property property : instance.getProperties()) {
                nodes.add(encode(property));
            }

            root.add("properties", nodes);

            return root.build();
        }

        /**
         * This method allow to generate a file given any object representation of the configuration instance
         *
         * @param instance The configuration instance
         * @throws Exception If something goes wrong during the process
         */
        @Override
        public void toFile(Configuration instance) throws Exception {

            // Defining exporting rules
            Map<String, Object> options = new HashMap<>(1);
            options.put(JsonGenerator.PRETTY_PRINTING, true);

            // Creating factory
            JsonWriterFactory writerFactory = Json.createWriterFactory(options);

            // Obtaining object representation
            JsonObject obj = this.toObject(instance);

            // Buffering and streaming
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(instance.getFile()));
                 javax.json.JsonWriter writer = writerFactory.createWriter(bw)) {

                // Writing on disk
                writer.writeObject(obj);

            }
        }

        /**
         * This method allow to insert a property object inside an intermediate representation
         *
         * @param property The property instance
         * @return The new representation
         * @throws IllegalStateException If the datatype cannot be encoded as JSON-like value
         */
        @Override
        public JsonObject encode(Property property) {

            // Creating object
            JsonObjectBuilder root = Json.createObjectBuilder();

            // Acquiring value
            Datatype dt = property.getValue();

            // Encoding
            if (dt.isArray())
                __encode_array(root, property);
            else
                __encode_obj(root, property);

            // Inserting description
            root.add("description", property.getDescription());

            return root.build();
        }

        /**
         * This method encode object-only property
         *
         * @param property The property instance
         * @return The new representation
         * @throws IllegalStateException If the datatype cannot be encoded as JSON-like value
         */
        @Override
        public void __encode_obj(JsonObjectBuilder obj, Property property) {

            // Acquiring value
            Datatype dt = property.getValue();

            // Encoding
            if (dt.isText()) {

                obj.add(property.getKey(), dt.asString());

            } else if (dt.isNumeric()) {

                if (dt.isByte()) {
                    obj.add(property.getKey(), dt.asByte());
                } else if (dt.isShort()) {
                    obj.add(property.getKey(), dt.asShort());
                } else if (dt.isInteger()) {
                    obj.add(property.getKey(), dt.asInt());
                } else if (dt.isLong()) {
                    obj.add(property.getKey(), dt.asLong());
                } else if (dt.isFloat()) {
                    obj.add(property.getKey(), dt.asFloat());
                } else if (dt.isDouble()) {
                    obj.add(property.getKey(), dt.asDouble());
                } else {
                    throw new IllegalStateException("Unknown datatype");
                }

            } else if (dt.isBoolean()) {
                obj.add(property.getKey(), dt.asBoolean());
            } else {
                throw new IllegalStateException("Unknown datatype");
            }

        }

        /**
         * This method encode array-only property
         *
         * @param property The property instance
         * @return The new representation
         * @throws IllegalStateException If the datatype cannot be encoded as JSON-like value
         */
        @Override
        public void __encode_array(JsonObjectBuilder obj, Property property) {

            // Creating object
            JsonArrayBuilder values = Json.createArrayBuilder();

            // Acquiring value
            Datatype dt = property.getValue();

            if (dt.isNumeric()) {

                if (dt.isByte()) {

                    byte[] tmp = dt.asByteArray();

                    for (byte e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isShort()) {

                    short[] tmp = dt.asShortArray();

                    for (short e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isInteger()) {

                    int[] tmp = dt.asIntArray();

                    for (int e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isLong()) {

                    long[] tmp = dt.asLongArray();

                    for (long e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isFloat()) {

                    float[] tmp = dt.asFloatArray();

                    for (float e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isDouble()) {

                    double[] tmp = dt.asDoubleArray();

                    for (double e : tmp) {
                        values.add(e);
                    }

                }

            } else if (dt.isText()) {

                String[] tmp = dt.asStringArray();

                for (String e : tmp) {
                    values.add(e);
                }

            } else if (dt.isBoolean()) {

                boolean[] tmp = dt.asBooleanArray();

                for (boolean e : tmp) {
                    values.add(e);
                }

            } else {
                throw new IllegalStateException("Unknown datatype");
            }

            obj.add(property.getKey(), values);

        }
    }

    public static final class HandlerJSON implements AbstractHandlerIO<Configuration> {

        // Writers
        private final ImplWriterJSON IMPL_WRITER_JSON = new ImplWriterJSON();

        // Readers

        /**
         * Private empty constructor
         */
        private HandlerJSON() {
        }

        /**
         * Reads the configuration file
         *
         * @param instance The configuration instance to read and update
         * @throws Exception If anything goes wrong while processing the file
         */
        @Override
        public void read(Configuration instance) throws Exception {

        }

        /**
         * Reads the configuration file asynchronously
         *
         * @param instance The configuration instance to read
         * @return Future object representing the reading task
         * @throws Exception If anything goes wrong while processing the file
         */
        @Override
        public Future<Void> readAsync(Configuration instance) throws Exception {
            return null;
        }

        /**
         * Write the configuration file
         *
         * @param instance The configuration instance to write
         * @throws Exception If anything goes wrong while processing the file
         */
        @Override
        public void write(Configuration instance) throws Exception {
            IMPL_WRITER_JSON.toFile(instance);
        }

        /**
         * Write the configuration file asynchronously
         *
         * @param instance The configuration instance to write
         * @return Future object representing the writing task
         * @throws Exception If anything goes wrong while processing the file
         */
        @Override
        public Future<Void> writeAsync(Configuration instance) throws Exception {
            return null;
        }

        /**
         * Delete the configuration file
         *
         * @param instance The configuration instance to delete
         * @throws Exception If the configuration file cannot be deleted
         */
        @Override
        public void delete(Configuration instance) throws Exception {

        }

        /**
         * Delete the configuration file asynchronously
         *
         * @param instance The configuration instance to delete
         * @return Future object representing the deleting task
         */
        @Override
        public Future<Void> deleteAsync(Configuration instance) {
            return null;
        }
    }

}
