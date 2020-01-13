package org.tinyconfiguration.imp.basic;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.builders.AbstractBuilder;
import org.tinyconfiguration.abc.data.ImmutableDatatype;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The {@link Configuration} class defines all properties included inside the configuration file
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class Configuration extends AbstractConfiguration<Property, ImmutableDatatype> {

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
         * <p></p>
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
         * <p></p>
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
