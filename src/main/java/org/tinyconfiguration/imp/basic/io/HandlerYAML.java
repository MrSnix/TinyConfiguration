package org.tinyconfiguration.imp.basic.io;

import org.tinyconfiguration.abc.data.Value;
import org.tinyconfiguration.abc.io.AbstractHandlerIO;
import org.tinyconfiguration.abc.io.handlers.AbstractReader;
import org.tinyconfiguration.abc.io.handlers.AbstractWriter;
import org.tinyconfiguration.imp.basic.Configuration;
import org.tinyconfiguration.imp.basic.Property;
import org.tinyconfiguration.imp.basic.ex.configuration.InvalidConfigurationNameException;
import org.tinyconfiguration.imp.basic.ex.configuration.InvalidConfigurationVersionException;
import org.tinyconfiguration.imp.basic.ex.configuration.MissingConfigurationIdentifiersException;
import org.tinyconfiguration.imp.basic.ex.io.ParsingProcessException;
import org.tinyconfiguration.imp.basic.ex.property.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.events.Event;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * The {@link HandlerYAML} class contains the implementations of I/O operations as YAML format which can be executed on any {@link Configuration} instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
final class HandlerYAML extends AbstractHandlerIO<Configuration> {

    private static final ImplWriterYAML IMPL_WRITER_YAML = new ImplWriterYAML();
    private static final ImplReaderYAML IMPL_READER_YAML = new ImplReaderYAML();

    /**
     * Delete the configuration file
     *
     * @param instance The configuration instance to delete
     * @throws IOException If the configuration file cannot be deleted
     */
    @Override
    public void delete(Configuration instance) throws IOException {
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

    /**
     * Reads the configuration file
     *
     * @param instance The configuration instance to read and update
     * @throws Exception If anything goes wrong while processing the file
     */
    @Override
    public void read(Configuration instance) throws Exception {
        IMPL_READER_YAML.toObject(instance);
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
            } catch (
                    IOException | InvalidConfigurationNameException | InvalidConfigurationVersionException | MalformedConfigurationPropertyException | MissingConfigurationPropertyException | InvalidConfigurationPropertyException | UnknownConfigurationPropertyException | ParsingProcessException | MissingConfigurationIdentifiersException | DuplicatedConfigurationPropertyException e) {
                throw new CompletionException(e);
            } catch (Exception ignored) {
            }
            return null;
        });
    }

    /**
     * Write the configuration file
     *
     * @param instance The configuration instance to write
     * @throws IOException If anything goes wrong while processing the file
     */
    @Override
    public void write(Configuration instance) throws IOException {
        IMPL_WRITER_YAML.toFile(instance);
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
     * The {@link ImplWriterYAML} class contains the implementations of I/O operations which can be executed on any {@link Configuration} instance
     *
     * @author G. Baittiner
     * @version 0.1
     */
    static final class ImplWriterYAML implements AbstractWriter<Configuration, Property, Map<String, Object>> {

        /**
         * This method allow to insert a property object inside an intermediate representation
         *
         * @param property The property instance
         * @return The new representation
         */
        @Override
        public Map<String, Object> encode(Property property) {

            // Acquiring value
            Value dt = property.getValue();

            // Creating container
            Map<String, Object> map = new LinkedHashMap<>();

            // Encoding
            if (dt.isArray())
                __encode_array(map, property);
            else
                __encode_obj(map, property);

            return map;
        }

        /**
         * This method allow to generate an object representation from the configuration instance
         *
         * @param instance The configuration instance
         * @return The object representation of the following instance
         */
        @Override
        public String toObject(Configuration instance) {

            // Obtaining new hash-map
            Map<String, Object> data = new LinkedHashMap<>();
            List<Map<String, Object>> properties = new ArrayList<>();

            // Putting basic header
            data.put("name", instance.getName());
            data.put("version", instance.getVersion());

            // Encoding
            for (Property property : instance.getProperties()) {
                properties.add(encode(property));
            }

            // Saving properties
            data.put("properties", properties);

            // Setting output
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            // Creating writer
            Yaml writer = new Yaml(options);

            // Dumping
            String representation = writer.dump(data);

            // Cleaning
            data.clear();

            return representation;
        }

        /**
         * This method allow to generate a file given any object representation of the configuration instance
         *
         * @param instance The configuration instance
         * @throws IOException If something goes wrong during the process
         */
        @Override
        public void toFile(Configuration instance) throws IOException {

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(instance.getFile()))) {
                // Writing
                bw.write(toObject(instance));
                // Flushing
                bw.flush();
            }

        }

        /**
         * This method encode object-only property
         *
         * @param root     The root object
         * @param property The property instance
         */
        @Override
        public void __encode_obj(Map<String, Object> root, Property property) {

            // Acquiring value
            Value dt = property.getValue();

            // Encoding
            if (dt.isText()) {

                root.put(property.getKey(), dt.asString());

            } else if (dt.isNumeric()) {

                if (dt.isByte()) {
                    root.put(property.getKey(), dt.asByte());
                } else if (dt.isShort()) {
                    root.put(property.getKey(), dt.asShort());
                } else if (dt.isInteger()) {
                    root.put(property.getKey(), dt.asInt());
                } else if (dt.isLong()) {
                    root.put(property.getKey(), dt.asLong());
                } else if (dt.isFloat()) {
                    root.put(property.getKey(), dt.asFloat());
                } else if (dt.isDouble()) {
                    root.put(property.getKey(), dt.asDouble());
                } else {
                    throw new IllegalStateException("Unknown datatype");
                }

            } else if (dt.isBoolean()) {
                root.put(property.getKey(), dt.asBoolean());
            } else {
                throw new IllegalStateException("Unknown datatype");
            }

            // Inserting description
            root.put("description", property.getDescription());

        }

        /**
         * This method encode array-only property
         *
         * @param root     The root object
         * @param property The property instance
         */
        @Override
        public void __encode_array(Map<String, Object> root, Property property) {

            // Acquiring value
            Value dt = property.getValue();

            if (dt.isNumericArray()) {

                if (dt.isByteArray()) {

                    byte[] tmp = dt.asByteArray();

                    root.put(property.getKey(), tmp);

                } else if (dt.isShortArray()) {

                    short[] tmp = dt.asShortArray();

                    root.put(property.getKey(), tmp);

                } else if (dt.isIntegerArray()) {

                    int[] tmp = dt.asIntArray();

                    root.put(property.getKey(), tmp);

                } else if (dt.isLongArray()) {

                    long[] tmp = dt.asLongArray();

                    root.put(property.getKey(), tmp);

                } else if (dt.isFloatArray()) {

                    float[] tmp = dt.asFloatArray();

                    root.put(property.getKey(), tmp);

                } else if (dt.isDoubleArray()) {

                    double[] tmp = dt.asDoubleArray();

                    root.put(property.getKey(), tmp);

                }

            } else if (dt.isTextArray()) {

                String[] tmp = dt.asStringArray();

                root.put(property.getKey(), tmp);

            } else if (dt.isBooleanArray()) {

                boolean[] tmp = dt.asBooleanArray();

                root.put(property.getKey(), tmp);

            } else {
                throw new IllegalStateException("Unknown datatype");
            }


            // Inserting description
            root.put("description", property.getDescription());
        }
    }

    /**
     * The {@link ImplReaderYAML} class contains the implementations of I/O operations which can be executed on any {@link Configuration} instance
     *
     * @author G. Baittiner
     * @version 0.1
     */
    static final class ImplReaderYAML implements AbstractReader<Configuration, Property, Map<String, Object>> {

        private Map<String, Map<String, Object>> properties;

        /**
         * This method allow to translate a property object inside an intermediate representation
         *
         * @param property The property instance
         */
        @Override
        public void decode(Property property) throws MissingConfigurationPropertyException, MalformedConfigurationPropertyException, InvalidConfigurationPropertyException, ParsingProcessException {

            if (properties.containsKey(property.getKey())) {

                Map<String, Object> root = properties.get(property.getKey());

                // Acquiring value
                Value dt = property.getValue();

                // Encoding
                if (dt.isArray())
                    __decode_array(property, root);
                else
                    __decode_obj(property, root);

            } else if (!property.isOptional()) {
                throw new MissingConfigurationPropertyException(property);
            }

        }

        /**
         * This method generate the final representation of the configuration
         *
         * @param instance The configuration instance
         * @throws IOException If something goes wrong during the process
         */
        @Override
        public void toObject(Configuration instance) throws IOException, MissingConfigurationIdentifiersException, InvalidConfigurationNameException, InvalidConfigurationVersionException, ParsingProcessException, UnknownConfigurationPropertyException, MissingConfigurationPropertyException, MalformedConfigurationPropertyException, InvalidConfigurationPropertyException {

            // Acquiring intermediate representation
            ArrayDeque<Event> graph = fromFile(instance);

            // Decoding header
            Handler.Internal.YAML.__decode_header(instance, graph);

            int read = Handler.Internal.YAML.__evaluate_properties(graph);
            int expected = instance.getProperties().size();

            if (read > expected)
                throw new UnknownConfigurationPropertyException();

            // This cannot work as always, we have to call internal implementation
            this.properties = Handler.Internal.YAML.__decode_properties(instance, graph);

            for (Property property : instance.getProperties()) {
                decode(property);
            }

        }

        /**
         * This method generate an intermediate object representation of the configuration from the file
         *
         * @param instance The configuration instance
         * @throws IOException If something goes wrong during the process
         */
        @Override
        public ArrayDeque<Event> fromFile(Configuration instance) throws IOException {

            Yaml reader = new Yaml();

            ArrayDeque<Event> events;

            try (BufferedReader br = new BufferedReader(new FileReader(instance.getFile()))) {
                // Acquiring all nodes
                Iterable<Event> iterator = reader.parse(br);
                // Creating a simple stream
                events = StreamSupport.stream(iterator.spliterator(), false).
                        collect(Collectors.toCollection(ArrayDeque::new));
            }

            return events;
        }

        /**
         * This method decode object-only property
         *
         * @param property The property instance
         * @param obj      The intermediate object
         */
        @Override
        public void __decode_obj(Property property, Map<String, Object> obj) throws MalformedConfigurationPropertyException, InvalidConfigurationPropertyException, ParsingProcessException {

            String content = null;

            try {
                content = (String) obj.get("value");
            } catch (RuntimeException e) {
                throw new ParsingProcessException("The 'value' could not be decoded as string");
            }

            if (content == null) {
                throw new MalformedConfigurationPropertyException("The 'value' node is missing", property);
            }

            switch (property.getValue().getDatatype()) {
                case BOOLEAN:
                    if (content.equalsIgnoreCase("true"))
                        property.setValue(true);
                    else if (content.equalsIgnoreCase("false"))
                        property.setValue(false);
                    else
                        throw new MalformedConfigurationPropertyException("The value cannot be decoded as boolean", property);
                    break;
                case BYTE:
                    try {
                        property.setValue(Byte.parseByte(content));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case SHORT:
                    try {
                        property.setValue(Short.parseShort(content));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case INT:
                    try {
                        property.setValue(Integer.parseInt(content));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case LONG:
                    try {
                        property.setValue(Long.parseLong(content));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case FLOAT:
                    try {
                        property.setValue(Float.parseFloat(content));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case DOUBLE:
                    try {
                        property.setValue(Double.parseDouble(content));
                    } catch (NumberFormatException e) {
                        throw new MalformedConfigurationPropertyException(e.getMessage(), property);
                    }
                    break;
                case STRING:
                    property.setValue(content);
                    break;
                case CHAR:
                    if (content.length() > 1) {
                        throw new MalformedConfigurationPropertyException("The value cannot be decoded as char", property);
                    }
                    property.setValue(content.charAt(0));
                    break;
                default:
                    throw new MalformedConfigurationPropertyException("Unexpected value: " + content, property);
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
        public void __decode_array(Property property, Map<String, Object> obj) throws InvalidConfigurationPropertyException, MalformedConfigurationPropertyException, ParsingProcessException {

            Value value = property.getValue();

            List<String> values;

            try {
                values = (List<String>) obj.get("values");
            } catch (RuntimeException e) {
                throw new ParsingProcessException("The array could not be decoded as list");
            }

            switch (value.getDatatype()) {
                case ARR_BOOLEAN:
                    try {
                        boolean[] booleans = new boolean[values.size()];
                        for (int i = 0; i < values.size(); ++i) {
                            booleans[i] = Boolean.parseBoolean(values.get(i));
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
                            bytes[i] = Byte.parseByte(values.get(i));
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
                            shorts[i] = Short.parseShort(values.get(i));
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
                            integers[i] = Integer.parseInt(values.get(i));
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
                            longs[i] = Long.parseLong(values.get(i));
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
                            floats[i] = Float.parseFloat(values.get(i));
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
                            doubles[i] = Double.parseDouble(values.get(i));
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
                            strings[i] = values.get(i);
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

                            if (values.get(i).length() > 1) {
                                throw new IllegalArgumentException("One of the values cannot be decoded as char");
                            }

                            characters[i] = values.get(i).charAt(0);
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
