package org.tinyconfiguration.imp.basic.io;

import org.tinyconfiguration.abc.io.AbstractHandlerIO;
import org.tinyconfiguration.abc.io.handlers.AbstractReader;
import org.tinyconfiguration.abc.io.handlers.AbstractWriter;
import org.tinyconfiguration.imp.basic.Configuration;
import org.tinyconfiguration.imp.basic.Property;
import org.tinyconfiguration.imp.basic.ex.configuration.InvalidConfigurationNameException;
import org.tinyconfiguration.imp.basic.ex.configuration.InvalidConfigurationVersionException;
import org.tinyconfiguration.imp.basic.ex.io.ParsingProcessException;
import org.tinyconfiguration.imp.basic.ex.property.InvalidConfigurationPropertyException;
import org.tinyconfiguration.imp.basic.ex.property.MalformedConfigurationPropertyException;
import org.tinyconfiguration.imp.basic.ex.property.UnknownConfigurationPropertyException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static org.tinyconfiguration.abc.utils.SpecialCharacters.Type.STR_ENCODE;
import static org.tinyconfiguration.abc.utils.SpecialCharacters.substitute;

public class HandlerCSV extends AbstractHandlerIO<Configuration> {

    private static final ImplWriterCSV IMPL_WRITER_CSV = new ImplWriterCSV();
    private static final ImplReaderCSV IMPL_READER_CSV = new ImplReaderCSV();

    /**
     * Reads the configuration file
     *
     * @param instance The configuration instance to read and update
     * @throws IOException If anything goes wrong while processing the file
     */
    @Override
    public void read(Configuration instance) throws Exception {
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
        return null;
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
        return null;
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
                values.add("\"" + substitute(STR_ENCODE, tmp) + "\"");
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
        private Configuration instance;
        private List<String> properties;

        /**
         * This method allow to translate a property object inside an intermediate representation
         *
         * @param property The property instance
         */
        @Override
        public void decode(Property property) throws ParsingProcessException, InvalidConfigurationNameException, InvalidConfigurationVersionException, MalformedConfigurationPropertyException, InvalidConfigurationPropertyException {

            // This is the intermediate property representation
            String property0 = null;
            // This flag stop the iteration
            boolean stop = false;

            // Now, we look inside "properties" on each node if the current "Property" object exists
            for (String s : properties) {
                // Reading field value
                String[] field = s.split(",");

                // Reading field number
                int length = field.length;
                // Verifying field number
                if (length != FIELDS) {
                    throw new ParsingProcessException("The fields are supposed to be " + FIELDS + " instead they are " + length);
                }

                // Verifying instance name
                if (!field[IDX_CFG_NAME].equals(instance.getName())) {
                    throw new InvalidConfigurationNameException(instance.getName(), field[IDX_CFG_NAME]);
                }
                // Verifying instance version
                if (!field[IDX_CFG_VERSION].equals(instance.getVersion())) {
                    throw new InvalidConfigurationVersionException(instance.getVersion(), field[IDX_CFG_VERSION]);
                }

                Optional<Property> p0 = instance.getProperties().stream().filter(p -> p.getKey().equals(field[IDX_KEY])).findFirst();

                if (p0.isPresent()) {

                    if (p0.get().getValue().isArray()) {
                        __decode_array(p0.get(), field[IDX_KEY]);
                    } else {
                        __decode_obj(p0.get(), field[IDX_KEY]);
                    }

                } else {
                    throw new ParsingProcessException("No properties with the following name exists => " + field[IDX_KEY]);
                }

            }

        }

        /**
         * This method generate the final representation of the configuration
         *
         * @param instance The configuration instance
         * @throws Exception If something goes wrong during the process
         */
        @Override
        public void toObject(Configuration instance) throws Exception {

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

            Handler.Internal.__decode_value(property, obj);

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
        public void __decode_array(Property property, String obj) {

        }
    }

}
