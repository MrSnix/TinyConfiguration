package org.tinyconfiguration.imp.basic;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.builders.AbstractBuilder;
import org.tinyconfiguration.abc.ex.ConfigurationException;
import org.tinyconfiguration.abc.ex.PropertyException;
import org.tinyconfiguration.abc.io.utils.Readable;
import org.tinyconfiguration.abc.io.utils.Writable;
import org.tinyconfiguration.abc.utils.FormatType;
import org.tinyconfiguration.imp.basic.io.HandlerCSV;
import org.tinyconfiguration.imp.basic.io.HandlerJSON;
import org.tinyconfiguration.imp.basic.io.HandlerXML;
import org.tinyconfiguration.imp.basic.io.HandlerYAML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Future;

/**
 * The {@link Configuration} class defines all properties included inside the configuration file
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class Configuration extends AbstractConfiguration<Property> implements Readable, Writable {

    private final LinkedHashMap<String, Property> properties;

    /**
     * Private empty constructor
     */
    private Configuration() {
        super();
        this.properties = new LinkedHashMap<>();
    }

    /**
     * Private configuration constructor with parameters
     */
    private Configuration(String name, String version, String filename, String pathname, LinkedHashMap<String, Property> properties) {
        super(name, version, filename, pathname);
        this.properties = properties;
    }

    /**
     * Gets the properties.
     *
     * @return The properties associated to the container object as {@link List}
     */
    @Override
    public List<Property> getProperties() {
        return new ArrayList<>(properties.values());
    }

    /**
     * Gets a specific property using the provided key
     *
     * @param key The key used to identify the value
     * @return The {@link Property} object used to retrieve any known information
     * @throws NullPointerException     If the key is null
     * @throws IllegalArgumentException If the key is empty
     * @throws NoSuchElementException   If the key does not match any property
     */
    public Property get(String key) {

        if (key == null)
            throw new NullPointerException("The key cannot be null");

        if (key.trim().isEmpty())
            throw new IllegalArgumentException("The key cannot be empty");

        if (this.properties.get(key) == null)
            throw new NoSuchElementException("The following key does not exists: " + key);

        return this.properties.get(key);
    }

    /**
     * Checks if any properties has been inserted inside the configuration.
     *
     * @return True or false
     */
    @Override
    public boolean isEmpty() {
        return properties.isEmpty();
    }

    /**
     * Remove all properties and listeners stored by the container.
     */
    @Override
    public void clear() {
        this.properties.clear();
    }

    /**
     * Check if a specific key is stored inside the configuration instance
     *
     * @param key The key used to identify the value
     * @return True or false
     * @throws NullPointerException     If the key is null
     * @throws IllegalArgumentException If the key is empty
     */
    @Override
    public boolean contains(String key) {

        if (key == null)
            throw new NullPointerException("The key cannot be null");

        if (key.trim().isEmpty())
            throw new IllegalArgumentException("The key cannot be empty");

        return this.properties.containsKey(key);
    }

    /**
     * Reads the configuration file
     *
     * @param type The configuration instance export type
     * @throws IOException            If anything goes wrong while processing the file
     * @throws ConfigurationException If configuration parsing fails
     * @throws PropertyException      If property parsing fails
     */
    @Override
    public void read(FormatType type) throws IOException, ConfigurationException, PropertyException {
        if (type == null)
            throw new NullPointerException("The export format cannot be null");

        switch (type) {
            case XML:
                HandlerXML.READER.read(this);
                break;
            case JSON:
                HandlerJSON.READER.read(this);
                break;
            case YAML:
                HandlerYAML.READER.read(this);
                break;
            case CSV:
                HandlerCSV.READER.read(this);
                break;
            default:
                throw new IllegalArgumentException("The following format is not supported");
        }
    }

    /**
     * Reads the configuration file asynchronously
     *
     * @param type The configuration instance export type
     * @return Future object representing the reading task
     */
    @Override
    public Future<Void> readAsync(FormatType type) {

        if (type == null)
            throw new NullPointerException("The export format cannot be null");

        Future<Void> e;

        switch (type) {
            case XML:
                e = HandlerXML.READER.readAsync(this);
                break;
            case JSON:
                e = HandlerJSON.READER.readAsync(this);
                break;
            case YAML:
                e = HandlerYAML.READER.readAsync(this);
                break;
            case CSV:
                e = HandlerCSV.READER.readAsync(this);
                break;
            default:
                throw new IllegalArgumentException("The following format is not supported");
        }

        return e;
    }

    /**
     * Write the configuration file
     *
     * @param type The configuration instance export type
     * @throws IOException If anything goes wrong while processing the file
     */
    @Override
    public void write(FormatType type) throws IOException {
        if (type == null)
            throw new NullPointerException("The export format cannot be null");

        switch (type) {
            case XML:
                HandlerXML.WRITER.write(this);
                break;
            case JSON:
                HandlerJSON.WRITER.write(this);
                break;
            case YAML:
                HandlerYAML.WRITER.write(this);
                break;
            case CSV:
                HandlerCSV.WRITER.write(this);
                break;
            default:
                throw new IllegalArgumentException("The following format is not supported");
        }
    }

    /**
     * Write the configuration file asynchronously
     *
     * @param type The configuration instance export type
     * @return Future object representing the writing task
     */
    @Override
    public Future<Void> writeAsync(FormatType type) {

        if (type == null)
            throw new NullPointerException("The format type cannot be null");

        Future<Void> e;

        switch (type) {
            case XML:
                e = HandlerXML.WRITER.writeAsync(this);
                break;
            case JSON:
                e = HandlerJSON.WRITER.writeAsync(this);
                break;
            case YAML:
                e = HandlerYAML.WRITER.writeAsync(this);
                break;
            case CSV:
                e = HandlerCSV.WRITER.writeAsync(this);
                break;
            default:
                throw new IllegalArgumentException("The following format is not supported");
        }

        return e;
    }


    /**
     * The {@link Builder} class allows to generate {@link Configuration} instances
     *
     * @author G. Baittiner
     * @since 0.1
     */
    public static final class Builder extends AbstractBuilder<Configuration> {

        private final boolean isCleanable;
        private String name;
        private String version;
        private String filename;
        private String pathname;
        private LinkedHashMap<String, Property> properties;

        /**
         * The {@link Builder} constructor
         */
        public Builder() {
            this.name = null;
            this.version = null;
            this.filename = null;
            this.pathname = null;
            this.properties = new LinkedHashMap<>();
            this.isCleanable = true;
        }

        /**
         * The {@link Builder} constructor
         *
         * @param isCleanable If true on {@link Property.Builder#build()} the object will be reusable
         */
        public Builder(boolean isCleanable) {
            this.name = null;
            this.version = null;
            this.filename = null;
            this.pathname = null;
            this.properties = new LinkedHashMap<>();
            this.isCleanable = isCleanable;
        }

        /**
         * Sets the configuration name.
         *
         * @param name The configuration name
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the given {@link String} is null
         * @throws IllegalArgumentException If the given {@link String} is empty
         */
        public Builder setName(String name) {

            if (name == null)
                throw new NullPointerException("The name cannot be null");

            if (name.isEmpty())
                throw new IllegalArgumentException("The name cannot be empty");

            this.name = name;

            return this;
        }

        /**
         * Sets the configuration version.
         *
         * @param version The configuration version value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the given {@link String} is null
         * @throws IllegalArgumentException If the given {@link String} is empty
         */
        public Builder setVersion(String version) {

            if (version == null)
                throw new NullPointerException("The version cannot be null");

            if (version.isEmpty())
                throw new IllegalArgumentException("The version cannot be empty");

            this.version = version;

            return this;
        }

        /**
         * Sets the configuration filename.
         *
         * <p>This method is expecting a {@link String} value formatted as follow:</p>
         *
         * <ul>
         *      <li>"filename.ext" -&gt; "configuration.cfg" -&gt; <b>VALID</b></li>
         *      <li>"filename.ext" -&gt; "setting.prop" -&gt; <b>VALID</b></li>
         *      <li>"../dir/filename.ext" -&gt; "../cfg/configuration.cfg" -&gt; <b>NOT VALID</b></li>
         *      <li>"./filename.ext" -&gt; "./setting.prop" -&gt; <b>NOT VALID</b></li>
         * </ul>
         *
         * @param filename The configuration filename formatted as "filename.ext"
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the given {@link String} is null
         * @throws IllegalArgumentException If the given {@link String} is empty
         */
        public Builder setFilename(String filename) {

            if (filename == null)
                throw new NullPointerException("The filename cannot be null");

            if (filename.isEmpty())
                throw new IllegalArgumentException("The filename cannot be empty");

            this.filename = filename;

            return this;
        }

        /**
         * Sets the configuration pathname.
         *
         * <p>This method is expecting a {@link String} value formatted as follow:</p>
         *
         * <ul>
         *      <li>"./" -&gt; <b>VALID</b></li>
         *      <li>"../cfg/" -&gt; <b>VALID</b></li>
         *      <li>"./configuration.cfg" -&gt; <b>NOT VALID</b>
         *      <li>"../cfg/setting.prop" -&gt; <b>NOT VALID</b></li>
         * </ul>
         *
         * @param pathname The configuration pathname formatted as "../dir/"
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the given {@link String} is null
         * @throws IllegalArgumentException If the given {@link String} is empty
         */
        public Builder setPathname(String pathname) {

            if (pathname == null)
                throw new NullPointerException("The pathname cannot be null");

            if (pathname.isEmpty())
                throw new IllegalArgumentException("The pathname cannot be empty");

            this.pathname = pathname;

            return this;
        }

        /**
         * Insert a specific property inside the configuration instance
         *
         * @param property The property instance to insert
         * @return The {@link Builder} current instance
         * @throws NullPointerException  If the property is null
         * @throws IllegalStateException If the property has been already inserted
         */
        @SuppressWarnings("UnusedReturnValue")
        public Builder put(Property property) {

            if (property == null)
                throw new NullPointerException("The property object cannot be null");

            if (properties.containsKey(property.getKey()))
                throw new IllegalStateException("The property has been already inserted");

            this.properties.put(property.getKey(), property);

            return this;
        }

        @Override
        public void clear() {
            this.name = null;
            this.version = null;
            this.filename = null;
            this.pathname = null;
            this.properties = new LinkedHashMap<>();
        }

        /**
         * Create the final object then call {@link AbstractBuilder#clear()} if the builder object is cleanable to make the builder reusable
         *
         * @return The new {@link Configuration} instance
         * @throws NullPointerException If one or more properties are not set
         */
        @Override
        public Configuration build() {

            if (name == null)
                throw new NullPointerException("The name must be set!");

            if (version == null)
                throw new NullPointerException("The version must be set!");

            if (filename == null)
                throw new NullPointerException("The filename must be set!");

            if (pathname == null)
                throw new NullPointerException("The pathname must be set!");

            Configuration e = new Configuration(name, version, filename, pathname, properties);

            if (this.isCleanable)
                clear();

            return e;
        }

    }

}
