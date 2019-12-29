package org.tinyconfiguration.io;

import org.tinyconfiguration.Configuration;
import org.tinyconfiguration.abc.io.AbstractHandler;
import org.tinyconfiguration.abc.io.AbstractHandlerIO;
import org.tinyconfiguration.abc.io.readers.ReaderJSON;
import org.tinyconfiguration.abc.io.writers.WriterJSON;
import org.tinyconfiguration.abc.listeners.ConfigurationListener;
import org.tinyconfiguration.common.Datatype;
import org.tinyconfiguration.common.Property;
import org.tinyconfiguration.ex.InvalidConfigurationNameException;
import org.tinyconfiguration.ex.InvalidConfigurationVersionException;
import org.tinyconfiguration.ex.MalformedConfigurationPropertyException;
import org.tinyconfiguration.ex.MissingConfigurationPropertyException;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;

import static javax.json.JsonValue.ValueType;
import static javax.json.JsonValue.ValueType.ARRAY;
import static org.tinyconfiguration.abc.listeners.ConfigurationListener.Type.*;

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
         */
        @Override
        public JsonObject toObject(Configuration instance) {

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
         * @throws IOException If an I/O exception of some sort has occurred.
         */
        @Override
        public void toFile(Configuration instance) throws IOException {

            // Defining exporting rules
            Map<String, Object> options = new HashMap<>(1);
            options.put(JsonGenerator.PRETTY_PRINTING, true);

            // Creating factory
            JsonWriterFactory writerFactory = Json.createWriterFactory(options);

            // Obtaining object representation
            JsonObject obj = this.toObject(instance);

            // Creating stream and setting charset
            FileOutputStream fos = new FileOutputStream(instance.getFile());
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);

            // Buffering and streaming
            try (BufferedWriter bw = new BufferedWriter(osw);
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

    private static final class ImplReaderJSON implements ReaderJSON<Configuration, Property, JsonArray, JsonObject> {


        /**
         * This method allow to return a property object inside an intermediate representation
         *
         * @param property       The property instance
         * @param representation The property intermediate representation instance
         */
        @Override
        public void decode(Property property, JsonArray representation) throws
                MissingConfigurationPropertyException,
                MalformedConfigurationPropertyException {

            // This is the intermediate property representation
            JsonObject property0 = null;
            // This flag stop the iteration
            boolean stop = false;

            // Now, we look inside "properties" on each node if the current "Property" object exists
            for (Iterator<JsonValue> iterator = representation.iterator(); iterator.hasNext() && !stop; ) {
                // Acquiring value
                JsonValue p = iterator.next();

                try {
                    // So, we pick the first object inside "properties" array
                    JsonObject tmp = p.asJsonObject();

                    // If there is mapping on the key we are looking for, then we assign it, otherwise stay null
                    if (tmp.get(property.getKey()) != null) {
                        // Assign
                        property0 = tmp;
                        // Update flag
                        stop = true;
                    }

                } catch (ClassCastException ex) {
                    throw new MalformedConfigurationPropertyException("It was expecting an OBJECT type but an " + p.getValueType().name() + " type was found", property);
                }

            }

            // In the end, if it is still null, no property with the given key was found inside the file
            if (property0 == null)
                throw new MissingConfigurationPropertyException(property);

            // Now, the property may be an array or an object
            ValueType type = property0.get(property.getKey()).getValueType();

            // Let's handle both cases, checking validity then updating the property value
            if (type != ARRAY) {
                __decode_obj(property, property0);
            } else {
                __decode_array(property, property0.asJsonArray());
            }

        }

        /**
         * This method decode object-only property
         *
         * @param property The property instance
         * @param obj      The intermediate object
         * @return The new representation
         */
        @Override
        public void __decode_obj(Property property, JsonObject obj) {

        }

        /**
         * This method decode array-only property
         *
         * @param property The property instance
         * @param array    The intermediate array
         * @return The new representation
         */
        @Override
        public void __decode_array(Property property, JsonArray array) {

        }

        /**
         * This method generate the final representation of the configuration
         *
         * @param instance The configuration instance
         * @throws InvalidConfigurationNameException       If the configuration name does not match the one inside the file
         * @throws InvalidConfigurationVersionException    If the configuration version does not match the one inside the file
         * @throws MissingConfigurationPropertyException   If any configuration property is missing from the file
         * @throws MalformedConfigurationPropertyException If any configuration property is not well-formed
         * @throws IOException                             If an I/O exception of some sort has occurred.
         */
        @Override
        public void toObject(Configuration instance) throws
                IOException,
                InvalidConfigurationNameException,
                InvalidConfigurationVersionException,
                MissingConfigurationPropertyException,
                MalformedConfigurationPropertyException {

            // Acquiring the intermediate representation
            JsonObject configuration = fromFile(instance);
            JsonArray properties = configuration.getJsonArray("properties");

            // Acquiring basic info
            String name = configuration.getString("name");
            String version = configuration.getString("version");

            if (!name.equals(instance.getName()))
                throw new InvalidConfigurationNameException(instance.getName(), name);

            if (!version.equals(instance.getVersion()))
                throw new InvalidConfigurationVersionException(instance.getVersion(), version);

            for (Property property : instance.getProperties()) {
                decode(property, properties);
            }

        }

        /**
         * This method generate an intermediate object representation of the configuration from the file
         *
         * @param instance The configuration instance
         * @throws IOException If an I/O exception of some sort has occurred.
         */
        @Override
        public JsonObject fromFile(Configuration instance) throws IOException {

            // Creating stream and setting charset
            FileInputStream fis = new FileInputStream(instance.getFile());
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

            JsonObject obj;

            try (BufferedReader br = new BufferedReader(isr);
                 JsonReader reader = Json.createReader(br)) {

                obj = reader.readObject();

            }

            return obj;

        }
    }

    public static final class HandlerJSON implements AbstractHandlerIO<Configuration> {

        // Writers
        private final ImplWriterJSON IMPL_WRITER_JSON = new ImplWriterJSON();
        // Readers
        private final ImplReaderJSON IMPL_READER_JSON = new ImplReaderJSON();

        /**
         * Private empty constructor
         */
        private HandlerJSON() {
        }

        /**
         * Reads the configuration file
         *
         * @param instance The configuration instance to read and update
         * @throws IOException If an I/O exception of some sort has occurred.
         */
        @Override
        public synchronized void read(Configuration instance) throws
                IOException,
                InvalidConfigurationNameException,
                InvalidConfigurationVersionException,
                MalformedConfigurationPropertyException,
                MissingConfigurationPropertyException {

            for (ConfigurationListener<Configuration> listener : instance.getListeners(ON_CONFIG_READ)) {
                listener.execute(instance);
            }

            IMPL_READER_JSON.toObject(instance);

        }

        /**
         * Reads the configuration file asynchronously
         *
         * @param instance The configuration instance to read
         * @return Future object representing the reading task
         */
        @Override
        public Future<Void> readAsync(Configuration instance) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    read(instance);
                } catch (IOException |
                        InvalidConfigurationNameException |
                        InvalidConfigurationVersionException |
                        MalformedConfigurationPropertyException |
                        MissingConfigurationPropertyException e) {
                    throw new CompletionException(e);
                }
                return null;
            });
        }

        /**
         * Write the configuration file
         *
         * @param instance The configuration instance to write
         * @throws IOException If an I/O exception of some sort has occurred.
         */
        @Override
        public synchronized void write(Configuration instance) throws IOException {

            for (ConfigurationListener<Configuration> listener : instance.getListeners(ON_CONFIG_WRITE)) {
                listener.execute(instance);
            }

            IMPL_WRITER_JSON.toFile(instance);
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
         * Delete the configuration file
         *
         * @param instance The configuration instance to delete
         * @throws IOException If an I/O exception of some sort has occurred.
         */
        @Override
        public synchronized void delete(Configuration instance) throws IOException {

            for (ConfigurationListener<Configuration> listener : instance.getListeners(ON_CONFIG_DELETE)) {
                listener.execute(instance);
            }

            Files.delete(instance.getFile().toPath());
        }

        /**
         * Delete the configuration file asynchronously
         *
         * @param instance The configuration instance to delete
         * @return Future object representing the deleting task
         */
        @Override
        public Future<Void> deleteAsync(Configuration instance) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    delete(instance);
                } catch (IOException e) {
                    throw new CompletionException(e);
                }
                return null;
            });
        }
    }

}
