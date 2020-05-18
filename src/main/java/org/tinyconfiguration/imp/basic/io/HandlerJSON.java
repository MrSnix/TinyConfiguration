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

import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParsingException;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;

import static javax.json.JsonValue.ValueType.ARRAY;

/**
 * The {@link HandlerJSON} class contains the implementations of I/O operations as JSON format which can be executed on any {@link Configuration} instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class HandlerJSON {

    public static final ImplWriterJSON WRITER = new ImplWriterJSON();
    public static final ImplReaderJSON READER = new ImplReaderJSON();

    private HandlerJSON() {
    }

    /**
     * The {@link ImplWriterJSON} class contains the implementations of I/O operations which can be executed on any {@link Configuration} instance
     *
     * @author G. Baittiner
     * @version 0.1
     */
    public static final class ImplWriterJSON implements AbstractWriter<Configuration, Property, JsonObjectBuilder> {

        /**
         * Write the configuration file
         *
         * @param instance The configuration instance to write
         * @throws IOException If an I/O exception of some sort has occurred.
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

            // Obtaining object representation (#001)
            JsonObject obj = this.toObject(instance);

            // Creating stream-like string
            String data;

            try (StringWriter sw = new StringWriter();
                 javax.json.JsonWriter writer = writerFactory.createWriter(sw)) {
                // Writing on stream
                writer.writeObject(obj);
                // Obtaining data
                data = sw.toString();
            }

            //TODO: Fix this

            /* Removing annoying new-line char
            /* If there is '\n\r' or '\n' it removes it,
            /* otherwise leave as it is
            /* This bug comes from javax.json implementation (i think(?))
            /* Stuff, i found on internet related: "https://github.com/eclipse-ee4j/yasson/issues/289"
            /* Even if it talks about 'yasson', i got the same issue on (#001)
            /* I have tried different javax.json implementation, but it keeps staying there...*/

            data = data.
                    substring(data.indexOf("\n\r") + 1).
                    substring(data.indexOf('\n') + 1);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(instance.getFile()))) {
                // Writing on disk
                bw.write(data);
                // Say good-bye!
                bw.flush();
            }
        }

        /**
         * This method allow to insert a property object inside an intermediate representation
         *
         * @param property The property instance
         * @return The new representation
         * @throws IllegalStateException If the data-type cannot be encoded as JSON-like value
         */
        @Override
        public JsonObject encode(Property property) {

            // Creating object
            JsonObjectBuilder root = Json.createObjectBuilder();

            // Acquiring value
            Value dt = property.getValue();

            // Encoding
            if (dt.isArray())
                __encode_array(root, property);
            else
                __encode_obj(root, property);

            return root.build();
        }

        /**
         * This method encode object-only property
         *
         * @param property The property instance
         * @throws IllegalStateException If the data-type cannot be encoded as JSON-like value
         */
        @Override
        public void __encode_obj(JsonObjectBuilder obj, Property property) {

            // Acquiring value
            Value dt = property.getValue();

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

            // Inserting description
            obj.add("description", property.getDescription());

        }

        /**
         * This method encode array-only property
         *
         * @param property The property instance
         * @throws IllegalStateException If the data-type cannot be encoded as JSON-like value
         */
        @Override
        public void __encode_array(JsonObjectBuilder obj, Property property) {

            // Creating object
            JsonArrayBuilder values = Json.createArrayBuilder();

            // Acquiring value
            Value dt = property.getValue();

            if (dt.isNumericArray()) {

                if (dt.isByteArray()) {

                    byte[] tmp = dt.asByteArray();

                    for (byte e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isShortArray()) {

                    short[] tmp = dt.asShortArray();

                    for (short e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isIntegerArray()) {

                    int[] tmp = dt.asIntArray();

                    for (int e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isLongArray()) {

                    long[] tmp = dt.asLongArray();

                    for (long e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isFloatArray()) {

                    float[] tmp = dt.asFloatArray();

                    for (float e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isDoubleArray()) {

                    double[] tmp = dt.asDoubleArray();

                    for (double e : tmp) {
                        values.add(e);
                    }

                }

            } else if (dt.isTextArray()) {

                String[] tmp = dt.asStringArray();

                for (String e : tmp) {
                    values.add(e);
                }

            } else if (dt.isBooleanArray()) {

                boolean[] tmp = dt.asBooleanArray();

                for (boolean e : tmp) {
                    values.add(e);
                }

            } else {
                throw new IllegalStateException("Unknown datatype");
            }

            obj.add(property.getKey(), values);

            // Inserting description
            obj.add("description", property.getDescription());

        }
    }

    /**
     * The {@link ImplReaderJSON} class contains the implementations of I/O operations which can be executed on any {@link Configuration} instance
     *
     * @author G. Baittiner
     * @version 0.1
     */
    public static final class ImplReaderJSON implements AbstractReader<Configuration, Property, JsonObject> {

        private JsonArray properties;

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
        public synchronized void read(Configuration instance) throws
                IOException,
                InvalidConfigurationNameException,
                InvalidConfigurationVersionException,
                MalformedConfigurationPropertyException,
                MissingConfigurationPropertyException,
                MissingConfigurationIdentifiersException,
                InvalidConfigurationPropertyException,
                UnknownConfigurationPropertyException,
                ParsingProcessException,
                DuplicatedConfigurationPropertyException {

            READER.toObject(instance);
        }

        /**
         * Reads the configuration file asynchronously
         *
         * @param instance The configuration instance to read
         * @return Future object representing the reading task
         * @throws CompletionException If any exceptions occurs at runtime
         * @see HandlerJSON#READER#read(Configuration)
         */
        @Override
        public Future<Void> readAsync(Configuration instance) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    read(instance);
                } catch (
                        IOException | InvalidConfigurationNameException | InvalidConfigurationVersionException | MalformedConfigurationPropertyException | MissingConfigurationPropertyException | InvalidConfigurationPropertyException | UnknownConfigurationPropertyException | ParsingProcessException | MissingConfigurationIdentifiersException | DuplicatedConfigurationPropertyException e) {
                    throw new CompletionException(e);
                }
                return null;
            });
        }

        /**
         * This method allow to translate a property object inside an intermediate representation
         *
         * @param property The property instance
         */
        @Override
        public void decode(Property property) throws
                MissingConfigurationPropertyException,
                MalformedConfigurationPropertyException,
                InvalidConfigurationPropertyException,
                DuplicatedConfigurationPropertyException {

            // This is the intermediate property representation
            JsonObject property0 = null;
            // This flag stop the iteration
            boolean stop = false;

            // Now, we look inside "properties" on each node if the current "Property" object exists
            for (JsonValue p : properties) {
                // Acquiring value
                try {
                    // So, we pick the first object inside "properties" array
                    JsonObject tmp = p.asJsonObject();

                    // If there is mapping on the key we are looking for, then we assign it, otherwise stay null
                    if (tmp.get(property.getKey()) != null) {

                        // This means there is another property with the same key definition
                        if (stop)
                            throw new DuplicatedConfigurationPropertyException(property);

                        // Assign
                        property0 = tmp;
                        // Update flag
                        stop = true;
                    }

                } catch (ClassCastException ex) {
                    throw new MalformedConfigurationPropertyException("It was expecting an OBJECT type but an " + p.getValueType().name() + " type was found", property);
                }

            }

            // If the property was found, we proceed
            if (property0 != null) {

                // Now, the property may be an array or an object
                JsonValue.ValueType type = property0.get(property.getKey()).getValueType();

                // Let's handle both cases, checking validity then updating the property value
                if (type != ARRAY) {
                    __decode_obj(property, property0.asJsonObject());
                } else {
                    __decode_array(property, property0.asJsonObject());
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
        public void __decode_obj(Property property, JsonObject obj) throws MalformedConfigurationPropertyException, InvalidConfigurationPropertyException {

            Value value = property.getValue();

            JsonValue.ValueType type = obj.get(property.getKey()).getValueType();

            switch (type) {

                case STRING:

                    String s = obj.getString(property.getKey());

                    if (value.isString()) {
                        property.setValue(s);
                    } else if (value.isCharacter()) {

                        if (s.length() > 1) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as: " + value.getClass(), property);
                        }

                        property.setValue(s.charAt(0));

                    } else {
                        throw new MalformedConfigurationPropertyException("The value cannot be decoded as: " + value.getClass(), property);
                    }

                    break;
                case NUMBER:

                    BigInteger integral = obj.getJsonNumber(property.getKey()).bigIntegerValueExact();
                    BigDecimal decimal = obj.getJsonNumber(property.getKey()).bigDecimalValue();


                    if (value.isByte()) {

                        try {
                            property.setValue(integral.byteValueExact());
                        } catch (ArithmeticException ex) {
                            throw new MalformedConfigurationPropertyException("The value is out of byte range", property);
                        }

                    } else if (value.isShort()) {

                        try {
                            property.setValue(integral.shortValueExact());
                        } catch (ArithmeticException ex) {
                            throw new MalformedConfigurationPropertyException("The value is out of short range", property);
                        }

                    } else if (value.isInteger()) {

                        try {
                            property.setValue(integral.intValueExact());
                        } catch (ArithmeticException ex) {
                            throw new MalformedConfigurationPropertyException("The value is out of int range", property);
                        }

                    } else if (value.isLong()) {

                        try {
                            property.setValue(integral.longValueExact());
                        } catch (ArithmeticException ex) {
                            throw new MalformedConfigurationPropertyException("The value is out of long range", property);
                        }

                    } else if (value.isFloat()) {
                        property.setValue(decimal.floatValue());
                    } else if (value.isDouble()) {
                        property.setValue(decimal.doubleValue());
                    } else {
                        throw new MalformedConfigurationPropertyException("The value cannot be decoded as: " + value.getClass(), property);
                    }
                    break;
                case TRUE:
                case FALSE:
                    if (value.isBoolean())
                        property.setValue(obj.getBoolean(property.getKey()));
                    else {
                        throw new MalformedConfigurationPropertyException("The value cannot be decoded as: " + value.getClass(), property);
                    }
                    break;
                case NULL:
                    throw new MalformedConfigurationPropertyException("The value was NULL", property);
                default:
                    throw new MalformedConfigurationPropertyException("Unexpected value: " + obj.getValueType(), property);
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
        public void __decode_array(Property property, JsonObject obj) throws MalformedConfigurationPropertyException, InvalidConfigurationPropertyException {

            Value value = property.getValue();

            JsonArray array = obj.get(property.getKey()).asJsonArray();

            // If the array is empty...
            if (array.size() == 0) {

                // Just assigning empty arrays
                Handler.Internal.__empty_array(property);

            } else {

                switch (value.getDatatype()) {
                    case ARR_BOOLEAN:
                        try {
                            boolean[] booleans = new boolean[array.size()];
                            for (int i = 0; i < array.size(); ++i) {
                                booleans[i] = array.getBoolean(i);
                            }
                            property.setValue(booleans);
                        } catch (Exception e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as boolean array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_BYTE:
                        try {
                            byte[] bytes = new byte[array.size()];
                            for (int i = 0; i < array.size(); ++i) {
                                bytes[i] = Byte.parseByte(array.getInt(i) + "");
                            }
                            property.setValue(bytes);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as byte array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_SHORT:
                        try {
                            short[] shorts = new short[array.size()];
                            for (int i = 0; i < array.size(); ++i) {
                                shorts[i] = Short.parseShort(array.getInt(i) + "");
                            }
                            property.setValue(shorts);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as short array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_INT:
                        try {
                            int[] integers = new int[array.size()];
                            for (int i = 0; i < array.size(); ++i) {
                                integers[i] = Integer.parseInt(array.getInt(i) + "");
                            }
                            property.setValue(integers);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as int array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_LONG:
                        try {
                            long[] longs = new long[array.size()];
                            for (int i = 0; i < array.size(); ++i) {
                                longs[i] = Long.parseLong(array.getInt(i) + "");
                            }
                            property.setValue(longs);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as long array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_FLOAT:
                        try {
                            float[] floats = new float[array.size()];
                            for (int i = 0; i < array.size(); ++i) {
                                floats[i] = array.getJsonNumber(i).bigDecimalValue().floatValue();
                            }
                            property.setValue(floats);
                        } catch (Exception e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as float array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_DOUBLE:
                        try {
                            double[] doubles = new double[array.size()];
                            for (int i = 0; i < array.size(); ++i) {
                                doubles[i] = array.getJsonNumber(i).bigDecimalValue().doubleValue();
                            }
                            property.setValue(doubles);
                        } catch (Exception e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as double array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_STRING:
                        try {
                            String[] strings = new String[array.size()];
                            for (int i = 0; i < array.size(); ++i) {
                                strings[i] = array.getString(i);
                            }
                            property.setValue(strings);
                        } catch (Exception e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as string array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_CHAR:
                        try {
                            char[] characters = new char[array.size()];
                            for (int i = 0; i < array.size(); ++i) {

                                if (array.getString(i).length() > 1) {
                                    throw new IllegalArgumentException("One of the values cannot be decoded as char");
                                }

                                characters[i] = array.getString(i).charAt(0);
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

        /**
         * This method generate the final representation of the configuration
         *
         * @param instance The configuration instance
         */
        @Override
        public void toObject(Configuration instance) throws
                IOException,
                InvalidConfigurationNameException,
                InvalidConfigurationVersionException,
                MissingConfigurationPropertyException,
                MalformedConfigurationPropertyException,
                InvalidConfigurationPropertyException,
                UnknownConfigurationPropertyException,
                ParsingProcessException,
                MissingConfigurationIdentifiersException,
                DuplicatedConfigurationPropertyException {

            // Acquiring the intermediate representation
            JsonObject configuration = fromFile(instance);

            // Acquiring basic info
            String name = configuration.getString("name", null);
            String version = configuration.getString("version", null);

            // Basic check to verify file header integrity
            Handler.Internal.__decode_header(instance, name, version);

            this.properties = configuration.getJsonArray("properties");

            if (this.properties == null) {
                throw new ParsingProcessException("The 'properties' array is missing");
            }

            int read = this.properties.size();
            int expected = instance.getProperties().size();

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
         * @throws IOException             If an I/O exception of some sort has occurred.
         * @throws ParsingProcessException If a parsing exception of some sort has occurred.
         */
        @Override
        public JsonObject fromFile(Configuration instance) throws IOException, ParsingProcessException {

            // Creating stream and setting charset
            FileInputStream fis = new FileInputStream(instance.getFile());
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

            JsonObject obj;

            try (BufferedReader br = new BufferedReader(isr);
                 JsonReader reader = Json.createReader(br)) {

                obj = reader.readObject();

            } catch (JsonParsingException e) {
                throw new ParsingProcessException(e.getMessage());
            } catch (JsonException e) {
                throw new IOException(e.getMessage());
            }

            return obj;

        }
    }


}