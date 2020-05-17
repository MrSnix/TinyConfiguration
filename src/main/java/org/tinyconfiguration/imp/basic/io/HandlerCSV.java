package org.tinyconfiguration.imp.basic.io;

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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.tinyconfiguration.abc.utils.SpecialCharacters.Type.*;
import static org.tinyconfiguration.abc.utils.SpecialCharacters.substitute;

class HandlerCSV extends AbstractHandlerIO<Configuration> {

    private static final ImplWriterCSV IMPL_WRITER_CSV = new ImplWriterCSV();
    private static final ImplReaderCSV IMPL_READER_CSV = new ImplReaderCSV();

    /**
     * Reads the configuration file
     *
     * @param instance The configuration instance to read and update
     * @throws IOException If anything goes wrong while processing the file
     */
    @Override
    public void read(Configuration instance) throws IOException, MissingConfigurationPropertyException, InvalidConfigurationNameException, InvalidConfigurationPropertyException, ParsingProcessException, MalformedConfigurationPropertyException, InvalidConfigurationVersionException, UnknownConfigurationPropertyException, DuplicatedConfigurationPropertyException, MissingConfigurationIdentifiersException {
        IMPL_READER_CSV.toObject(instance);
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
                    IOException | InvalidConfigurationNameException | InvalidConfigurationVersionException | MalformedConfigurationPropertyException | MissingConfigurationPropertyException | InvalidConfigurationPropertyException | UnknownConfigurationPropertyException | ParsingProcessException | DuplicatedConfigurationPropertyException | MissingConfigurationIdentifiersException e) {
                throw new CompletionException(e);
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
        IMPL_WRITER_CSV.toFile(instance);
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

    final static class ImplWriterCSV implements AbstractWriter<Configuration, Property, StringBuilder> {

        private Configuration instance;

        /**
         * This method allow to insert a property object inside an intermediate representation
         *
         * @param property The property instance
         * @return The new representation
         */
        @Override
        public String encode(Property property) {

            StringBuilder sb = new StringBuilder();

            if (property.getValue().isArray()) {
                __encode_array(sb, property);
            } else {
                __encode_obj(sb, property);
            }

            return sb.toString();
        }

        /**
         * This method allow to generate an object representation from the configuration instance
         *
         * @param instance The configuration instance
         * @return The object representation of the following instance
         */
        @Override
        public List<String> toObject(Configuration instance) {

            this.instance = instance;
            List<String> e = new ArrayList<>();

            // Inserting header
            e.add("CFG_NAME,CFG_VERSION,KEY,VALUE,DESCRIPTION");

            // Encoding properties
            for (Property property : instance.getProperties()) {
                e.add(encode(property));
            }

            this.instance = null;

            return e;
        }

        /**
         * This method allow to generate a file given any object representation of the configuration instance
         *
         * @param instance The configuration instance
         * @throws IOException If something goes wrong during the process
         */
        @Override
        public void toFile(Configuration instance) throws IOException {

            List<String> lines = IMPL_WRITER_CSV.toObject(instance);


            try (BufferedWriter bw = new BufferedWriter(new FileWriter(instance.getFile()))) {

                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }

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
        public void __encode_obj(StringBuilder root, Property property) {

            root.append("\"").append(substitute(STR_ENCODE, instance.getName())).append("\"").append(",").
                    append("\"").append(substitute(STR_ENCODE, instance.getVersion())).append("\"").append(",").
                    append("\"").append(substitute(STR_ENCODE, property.getKey())).append("\"").append(",").
                    append("\"").append(substitute(STR_ENCODE, property.getValue().asString())).append("\"").append(",").
                    append("\"").append(substitute(STR_ENCODE, property.getDescription())).append("\"");

        }

        /**
         * This method encode array-only property
         *
         * @param root     The root object
         * @param property The property instance
         */
        @Override
        public void __encode_array(StringBuilder root, Property property) {

            List<String> values = new ArrayList<>();

            for (String tmp : property.getValue().asStringArray()) {
                values.add(substitute(ARR_ENCODE, tmp));
            }

            root.append("\"").append(substitute(STR_ENCODE, instance.getName())).append("\"").append(",").
                    append("\"").append(substitute(STR_ENCODE, instance.getVersion())).append("\"").append(",").
                    append("\"").append(substitute(STR_ENCODE, property.getKey())).append("\"").append(",").
                    append("\"").append(values.toString()).append("\"").append(",").
                    append("\"").append(substitute(STR_ENCODE, property.getDescription())).append("\"");

        }
    }

    final static class ImplReaderCSV implements AbstractReader<Configuration, Property, String> {

        private static final int FIELDS = 5;

        private static final int IDX_CFG_NAME = 0;
        private static final int IDX_CFG_VERSION = 1;
        private static final int IDX_KEY = 2;
        private static final int IDX_VALUE = 3;
        private static final int IDX_DESCRIPTION = 4;

        private static final Pattern MATCH_FIELD = Pattern.compile("((?:\"(?:\"{2}|,|\\n|[^\"]*)+\")|(?:[^,\"\\n]+))");
        private static final Pattern MATCH_COMMA = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        private static final Pattern MATCH_COMMA_ARRAY = Pattern.compile("(?<!\\\\),");
        private static final Pattern REMOVE_QUOTES = Pattern.compile("^\"|\"$");
        private static final Pattern REMOVE_PARENTHESIS = Pattern.compile("^\\[|]$");

        private List<String> properties;
        private Configuration instance;

        /**
         * This method allow to translate a property object inside an intermediate representation
         *
         * @param property The property instance
         */
        @Override
        public void decode(Property property) throws ParsingProcessException, InvalidConfigurationNameException, InvalidConfigurationVersionException, MalformedConfigurationPropertyException, InvalidConfigurationPropertyException, DuplicatedConfigurationPropertyException, MissingConfigurationPropertyException, MissingConfigurationIdentifiersException {

            String property0 = null;
            // This flag stop the iteration
            boolean stop = false;

            // Now, we look inside "properties" on each node if the current "Property" object exists
            for (String s : properties) {
                // Reading field value
                String[] field = MATCH_COMMA.split(s);
                // Verifying field number
                if (field.length != FIELDS) {
                    throw new ParsingProcessException("The fields are required to be " + FIELDS + " instead they are " + field.length);
                }
                // Decode
                for (int i = 0; i < field.length; i++) {
                    field[i] = REMOVE_QUOTES.matcher(field[i]).replaceAll("");
                }

                // Verifying instance name
                if (field[IDX_CFG_NAME].trim().isEmpty()) {
                    throw new MissingConfigurationIdentifiersException(instance.getName());
                }

                if (!field[IDX_CFG_NAME].equals(instance.getName())) {
                    throw new InvalidConfigurationNameException(instance.getName(), field[IDX_CFG_NAME]);
                }

                // Verifying instance version
                if (field[IDX_CFG_VERSION].trim().isEmpty()) {
                    throw new MissingConfigurationIdentifiersException(instance.getVersion());
                }

                if (!field[IDX_CFG_VERSION].equals(instance.getVersion())) {
                    throw new InvalidConfigurationVersionException(instance.getVersion(), field[IDX_CFG_VERSION]);
                }

                if (field[IDX_KEY].equals(property.getKey())) {

                    // This means there is another property with the same key definition
                    if (stop)
                        throw new DuplicatedConfigurationPropertyException(property);
                    // Flag to stop
                    stop = true;
                    // Assign value
                    property0 = s;

                }

            }

            // If the property was found, we proceed
            if (property0 != null) {

                // Reading field value
                String[] field = MATCH_COMMA.split(property0);

                // Removing field comma
                for (int i = 0; i < field.length; i++) {
                    field[i] = REMOVE_QUOTES.matcher(field[i]).replaceAll("");
                }

                if (property.getValue().isArray()) {
                    __decode_array(property, field[IDX_VALUE]);
                } else {
                    __decode_obj(property, field[IDX_VALUE]);
                }

            }

            // In the end, if it is still null, no property with the given key was found inside the file
            if (property0 == null && !property.isOptional()) {
                throw new MissingConfigurationPropertyException(property);
            }

        }

        /**
         * This method generate the final representation of the configuration
         *
         * @param instance The configuration instance
         */
        @Override
        public void toObject(Configuration instance) throws UnknownConfigurationPropertyException, IOException, ParsingProcessException, DuplicatedConfigurationPropertyException, MalformedConfigurationPropertyException, InvalidConfigurationNameException, InvalidConfigurationVersionException, InvalidConfigurationPropertyException, MissingConfigurationPropertyException, MissingConfigurationIdentifiersException {

            this.instance = instance;
            this.properties = fromFile(instance);

            int read = this.properties.size();
            int expected = instance.getProperties().size();

            if (read > expected)
                throw new UnknownConfigurationPropertyException();

            for (Property tmp : instance.getProperties()) {
                decode(tmp);
            }

            this.properties = null;
            this.instance = null;
        }

        /**
         * This method generate an intermediate object representation of the configuration from the file
         *
         * @param instance The configuration instance
         * @throws IOException If something goes wrong during the process
         */
        @Override
        public List<String> fromFile(Configuration instance) throws IOException {

            List<String> lines;

            try (BufferedReader br = new BufferedReader(new FileReader(instance.getFile()))) {
                // Read all lines
                lines = br.lines().collect(Collectors.toList());
            }

            // Remove header
            lines.remove(0);

            return lines;
        }

        /**
         * This method decode object-only property
         *
         * @param property The property instance
         * @param obj      The intermediate object
         */
        @Override
        public void __decode_obj(Property property, String obj) throws MalformedConfigurationPropertyException, InvalidConfigurationPropertyException {
            // Decoding
            String obj_decode = substitute(STR_DECODE, obj);
            // Setting value
            Handler.Internal.__decode_value(property, obj_decode);
            // Final check
            if (!property.isValid()) {
                throw new InvalidConfigurationPropertyException("The validation test failed", property);
            }
        }

        /**
         * This method decode array-only property
         *
         * @param property The property instance
         * @param array    The intermediate array
         */
        @Override
        public void __decode_array(Property property, String array) throws InvalidConfigurationPropertyException, MalformedConfigurationPropertyException {

            if (array.equals("[]")) {
                // Just assigning empty arrays
                Handler.Internal.__empty_array(property);

            } else {

                // Removing parenthesis
                array = REMOVE_PARENTHESIS.matcher(array).replaceAll("");
                // Splitting array values
                String[] arr0 = MATCH_COMMA_ARRAY.split(array);

                for (int i = 0; i < arr0.length; i++) {
                    // Decoding special chars
                    arr0[i] = substitute(ARR_DECODE, arr0[i]);
                }

                switch (property.getValue().getDatatype()) {
                    case ARR_BOOLEAN:
                        try {
                            boolean[] booleans = new boolean[arr0.length];
                            for (int i = 0; i < arr0.length; ++i) {
                                booleans[i] = Boolean.parseBoolean(arr0[i]);
                            }
                            property.setValue(booleans);
                        } catch (Exception e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as boolean array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_BYTE:
                        try {
                            byte[] bytes = new byte[arr0.length];
                            for (int i = 0; i < arr0.length; ++i) {
                                bytes[i] = Byte.parseByte(arr0[i]);
                            }
                            property.setValue(bytes);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as byte array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_SHORT:
                        try {
                            short[] shorts = new short[arr0.length];
                            for (int i = 0; i < arr0.length; ++i) {
                                shorts[i] = Short.parseShort(arr0[i]);
                            }
                            property.setValue(shorts);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as short array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_INT:
                        try {
                            int[] integers = new int[arr0.length];
                            for (int i = 0; i < arr0.length; ++i) {
                                integers[i] = Integer.parseInt(arr0[i]);
                            }
                            property.setValue(integers);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as int array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_LONG:
                        try {
                            long[] longs = new long[arr0.length];
                            for (int i = 0; i < arr0.length; ++i) {
                                longs[i] = Long.parseLong(arr0[i]);
                            }
                            property.setValue(longs);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as long array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_FLOAT:
                        try {
                            float[] floats = new float[arr0.length];
                            for (int i = 0; i < arr0.length; ++i) {
                                floats[i] = Float.parseFloat(arr0[i]);
                            }
                            property.setValue(floats);
                        } catch (Exception e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as float array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_DOUBLE:
                        try {
                            double[] doubles = new double[arr0.length];
                            for (int i = 0; i < arr0.length; ++i) {
                                doubles[i] = Double.parseDouble(arr0[i]);
                            }
                            property.setValue(doubles);
                        } catch (NumberFormatException e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as double array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_STRING:
                        try {
                            String[] strings = Arrays.copyOf(arr0, arr0.length);
                            property.setValue(strings);
                        } catch (Exception e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as string array: " + e.getMessage(), property);
                        }
                        break;
                    case ARR_CHAR:
                        try {
                            char[] characters = new char[arr0.length];
                            for (int i = 0; i < arr0.length; ++i) {

                                if (arr0[i].length() > 1) {
                                    throw new IllegalArgumentException("One of the values cannot be decoded as char");
                                }

                                characters[i] = arr0[i].charAt(0);
                            }
                            property.setValue(characters);
                        } catch (Exception e) {
                            throw new MalformedConfigurationPropertyException("The value cannot be decoded as chars array: " + e.getMessage(), property);
                        }
                        break;
                }
            }

            // Final check
            if (!property.isValid()) {
                throw new InvalidConfigurationPropertyException("The validation test failed", property);
            }

        }
    }

}
