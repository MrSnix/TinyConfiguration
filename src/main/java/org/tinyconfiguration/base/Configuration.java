package org.tinyconfiguration.base;

import org.tinyconfiguration.data.Property;
import org.tinyconfiguration.events.ConfigurationListener;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

/**
 * The {@link Configuration} class defines all properties included inside the configuration file
 *
 * @author G. Baittiner
 * @version 0.1
 */
@SuppressWarnings("WeakerAccess")
public final class Configuration {

    private final String name;
    private final String filename;
    private final String pathname;
    private final File file;
    private final String version;
    private final LinkedHashMap<String, LinkedHashMap<String, Property>> properties;

    private final ArrayList<ConfigurationListener> onSave;
    private final ArrayList<ConfigurationListener> onDelete;

    /**
     * Private empty constructor
     */
    private Configuration() {
        this.name = null;
        this.filename = null;
        this.pathname = null;
        this.file = null;
        this.version = null;
        this.properties = null;
        this.onSave = null;
        this.onDelete = null;
    }

    /**
     * Private configuration constructor with parameters
     *
     * @param filename   The configuration filename
     * @param pathname   The configuration pathname
     * @param properties The configuration properties
     */
    private Configuration(String name, String version, String filename, String pathname, LinkedHashMap<String, LinkedHashMap<String, Property>> properties) {
        this.name = name;
        this.version = version;
        this.filename = filename;
        this.pathname = pathname;
        this.file = Paths.get(pathname, filename).toFile();
        this.properties = properties;
        this.onSave = new ArrayList<>();
        this.onDelete = new ArrayList<>();
    }

    /**
     * Gets the name.
     *
     * @return The name ({@link String}) associated to the configuration object.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the version.
     *
     * @return The version ({@link String}) associated to the configuration object.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Gets the filename.
     *
     * @return The filename ({@link String}) associated to the configuration object.
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Gets the pathname.
     *
     * @return The pathname ({@link String}) associated to the configuration object.
     */
    public String getPathname() {
        return this.pathname;
    }

    /**
     * Gets the file.
     *
     * @return The {@link File} associated to the configuration object.
     */
    public File getFile() {
        return file;
    }

    /**
     * Gets a specific property using the provided key and group
     *
     * @param group The group related to the key
     * @param key   The key used to identify the value
     * @return The {@link Property} object used to retrieve any known information
     * @throws NullPointerException     If the key or group is null
     * @throws IllegalArgumentException If the key is empty or the group is empty
     * @throws NoSuchElementException   If the key does not match any property
     */
    public Property get(String group, String key) {

        if (key == null)
            throw new NullPointerException("The key cannot be null");

        if (key.trim().isEmpty())
            throw new IllegalArgumentException("The key cannot be empty");

        if (group == null)
            throw new NullPointerException("The group cannot be null");

        if (group.trim().isEmpty())
            throw new IllegalArgumentException("The group cannot be empty");

        if (this.properties.get(group) == null)
            throw new NoSuchElementException("The following group does not exists: " + group);

        if (this.properties.get(group).get(key) == null)
            throw new NoSuchElementException("The following key does not exists: " + key);

        return this.properties.get(group).get(key);
    }

    /**
     * Gets a specific property using the provided key, <b>assuming it's not inserted in any group</b>
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

        if (this.properties.get(null) == null || this.properties.get(null).get(key) == null)
            throw new NoSuchElementException("The following key does not exists: " + key);

        return this.properties.get(null).get(key);
    }

    /**
     * Gets a set of property using the provided group
     *
     * @param group The group identifier
     * @return All the {@link Property} matching the group identifier
     * @throws IllegalArgumentException If the the group is empty
     * @throws NoSuchElementException   If the key does not match any property
     */
    public List<Property> getGroup(String group) {

        ArrayList<Property> propertiesList = new ArrayList<>();

        if (group == null)
            throw new NullPointerException("The group cannot be null");

        if (group.trim().isEmpty())
            throw new IllegalArgumentException("The group cannot be empty");

        if (this.properties.get(group) == null)
            throw new NoSuchElementException("The following group does not exists: " + group);

        for (Map.Entry<String, Property> entry : this.properties.get(group).entrySet()) {

            Property property = entry.getValue();

            propertiesList.add(property);
        }

        return propertiesList;
    }

    /**
     * Check if a specific key is stored by the given group
     *
     * @param group The group related to the key
     * @param key   The key used to identify the value
     * @return True or false
     * @throws NullPointerException     If the key or group is null
     * @throws IllegalArgumentException If the key or group is empty
     * @throws NoSuchElementException   If the group does not exists
     */
    public boolean contains(String group, String key) {

        if (key == null)
            throw new NullPointerException("The key cannot be null");

        if (key.isEmpty())
            throw new IllegalArgumentException("The key cannot be empty");

        if (group == null)
            throw new NullPointerException("The group cannot be null");

        if (group.trim().isEmpty())
            throw new IllegalArgumentException("The group cannot be empty");

        if (this.properties.get(group) == null)
            throw new NoSuchElementException("The following group does not exists: " + group);

        return this.properties.get(group).containsKey(key);
    }

    /**
     * Checks if any property has been inserted inside the configuration instance
     *
     * @return True or false
     */
    public boolean isEmpty() {
        return this.properties.size() == 0;
    }

    /**
     * Sets a new listener for any {@link ConfigurationListener.Type} value.
     *
     * @param type     The event type
     * @param listener The custom function to execute when the event will be fired
     */
    public boolean addListener(ConfigurationListener.Type type, ConfigurationListener listener) {

        boolean result = false;

        switch (type) {
            case ON_CONFIG_SAVE:
                result = this.onSave.add(listener);
                break;
            case ON_CONFIG_DELETE:
                result = this.onDelete.add(listener);
                break;
        }

        return result;
    }

    /**
     * Remove listener for any {@link ConfigurationListener.Type} value.
     *
     * @param type     The event type
     * @param listener The custom function reference which was associated to the event
     */
    public boolean removeListener(ConfigurationListener.Type type, ConfigurationListener listener) {

        boolean result = false;

        switch (type) {
            case ON_CONFIG_SAVE:
                result = this.onSave.remove(listener);
                break;
            case ON_CONFIG_DELETE:
                result = this.onDelete.remove(listener);
                break;
        }

        return result;
    }

    /**
     * Removes all listeners associated to the current configuration object
     */
    public void resetListeners() {
        this.onSave.clear();
        this.onDelete.clear();
    }

    /**
     * Returns a {@link Set} containing all groups stored inside this configuration instance
     *
     * @return The group names stored inside the configuration instance
     */
    public Set<String> getGroups() {
        return this.properties.keySet();
    }

    /**
     * Returns an {@link ArrayList} containing all the properties stored inside this configuration instance
     *
     * @return The properties stored in the configuration instance
     */
    public List<Property> getProperties() {

        ArrayList<Property> propertiesList = new ArrayList<>();

        // Iterate on each group
        this.properties.forEach((s, properties) -> {

            // Iterate on each property
            properties.forEach((s1, property) -> {

                // Adding on list
                propertiesList.add(property);
            });
        });

        return propertiesList;
    }

    /**
     * Returns an {@link ArrayList} containing all the "onSave" listeners stored inside this configuration instance
     *
     * @return The "onSave" listeners stored in the configuration instance
     */
    ArrayList<ConfigurationListener> getSaveListeners() {
        return onSave;
    }

    /**
     * Returns an {@link ArrayList} containing all the "onDelete" listeners stored inside this configuration instance
     *
     * @return The "onDelete" listeners stored in the configuration instance
     */
    ArrayList<ConfigurationListener> getDeleteListeners() {
        return onDelete;
    }

    /**
     * The {@link Configuration.Builder} class allows to generate {@link Configuration} instances.
     * <p></p>
     * <p>You can use {@link #build()} in order to retrieve a configuration instance.
     * The {@link Configuration.Builder} class will execute some checks on the given arguments
     * in order to prevent misleading values.</p>
     *
     * <p>In detail, {@link Configuration.Builder#setFilename(String)} is expecting a {@link String} value
     * formatted as follow:
     * <ul>
     *      <li>"filename.ext" -&gt; "configuration.cfg" -&gt; <b>VALID</b></li>
     *      <li>"filename.ext" -&gt; "setting.prop" -&gt; <b>VALID</b></li>
     *      <li>"../dir/filename.ext" -&gt; "../cfg/configuration.cfg" -&gt; <b>NOT VALID</b></li>
     *      <li>"./filename.ext" -&gt; "./setting.prop" -&gt; <b>NOT VALID</b></li>
     * </ul>
     * <p>while {@link Configuration.Builder#setPathname(String)} consider valid only the directory
     * into which the configuration file will be saved, for example:
     * <p>
     * <ul>
     *      <li>"./" -&gt; <b>VALID</b></li>
     *      <li>"../cfg/" -&gt; <b>VALID</b></li>
     *      <li>"./configuration.cfg" -&gt; <b>NOT VALID</b>
     *      <li>"../cfg/setting.prop" -&gt; <b>NOT VALID</b></li>
     * </ul>
     *
     * @author G. Baittiner
     * @since 0.1
     */
    public static final class Builder {

        private final LinkedHashMap<String, LinkedHashMap<String, Property>> properties;
        private String name;
        private String version;
        private String filename;
        private String pathname;

        /**
         * The {@link Configuration.Builder} constructor
         */
        public Builder() {
            this.properties = new LinkedHashMap<>();
        }

        /**
         * Sets the configuration name.
         *
         * @param name The configuration name
         * @return The {@link Configuration.Builder} current instance
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
         * @return The {@link Configuration.Builder} current instance
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
         * @return The {@link Configuration.Builder} current instance
         * @throws NullPointerException     If the given {@link String} is null
         * @throws IllegalArgumentException If the given {@link String} is empty
         */
        public Configuration.Builder setFilename(String filename) {

            if (filename == null)
                throw new NullPointerException("The filename cannot be null");

            if (filename.isEmpty())
                throw new IllegalArgumentException("The filename cannot be empty");

            this.filename = filename;

            return this;
        }

        /**
         * Sets the configuration pathname.
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
         * @return The {@link Configuration.Builder} current instance
         * @throws NullPointerException     If the given {@link String} is null
         * @throws IllegalArgumentException If the given {@link String} is empty
         */
        public Configuration.Builder setPathname(String pathname) {

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
         * @return The {@link Configuration.Builder} current instance
         * @throws NullPointerException If the property is null
         */
        public Configuration.Builder put(Property property) {

            if (property == null)
                throw new NullPointerException("The property object cannot be null");

            // Just in case, it has not been created yet
            LinkedHashMap<String, Property> map = new LinkedHashMap<>();
            map.put(property.getKey(), property);

            // If the group already exists
            if (properties.get(property.getGroup()) != null) {
                // Get group by group-key, then we add it
                properties.get(property.getGroup()).put(property.getKey(), property);
            }
            // else we create it now
            else if (property.getGroup() != null) {
                // Let's add our newly created group
                this.properties.put(property.getGroup(), map);
            }
            // No group needed
            else {
                // Inserting now on null key
                this.properties.put(null, map);
            }

            return this;
        }

        /**
         * Create the final object
         *
         * @return The new {@link Configuration} instance
         * @throws NullPointerException If one or more properties are not set
         */
        public Configuration build() {

            Objects.requireNonNull(name, "The name must be set!");
            Objects.requireNonNull(version, "The version must be set!");
            Objects.requireNonNull(filename, "The filename must be set!");
            Objects.requireNonNull(pathname, "The pathname must be set!");

            return new Configuration(name, version, filename, pathname, properties);
        }

    }

}

