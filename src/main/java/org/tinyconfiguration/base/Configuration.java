package org.tinyconfiguration.base;

import org.tinyconfiguration.events.ConfigurationListener;
import org.tinyconfiguration.events.PropertyListener;
import org.tinyconfiguration.property.PropertyDefinition;
import org.tinyconfiguration.property.PropertyValue;

import java.util.*;

/**
 * The {@link Configuration} class defines all properties included inside the configuration file
 *
 * <p>
 *
 * <p>It's an important component because all the relationship [ key - value ] stored will be
 * expected to be read from the concrete file, especially if the configuration policy is
 * set to {@link ConfigurationPolicy#STRICT_MODE}</p>
 *
 * <p>Let's explain better this concept using an example:</p>
 *
 * <pre>
 * {@code
 * // We add this property inside our configuration instance
 * cfg.put("LANGUAGE", "EN");
 * // Now, we save on disk
 * ConfigurationIO.write(cfg);
 *
 * //Let's look what we have generated as file
 * __________<< somewhere/on/disk/configuration.cfg >> ___________
 *
 * LANGUAGE="EN";
 * _______________________________________________________________
 *
 * // Now, we want to load it again, but first we reset our configuration instance
 * cfg.clear();
 *
 * // So, what's gonna happen now, after all properties have been removed?
 * ConfigurationIO.read(cfg); -&gt; Throws exception with ConfigurationPolicy.RESTRICTED}
 * </pre>
 * <p>
 * To add properties to the object, you can use the following method which is overloaded as follow:
 * <ol>
 *     <li>{@link Configuration#put(String, String)} <small>[ RECOMMENDED ]</small></li>
 *     <li>{@link Configuration#put(String, String, String)} <small>[ RECOMMENDED ]</small></li>
 *     <li>{@link Configuration#put(PropertyDefinition)} <small>[ NOT RECOMMENDED ]</small></li>
 * </ol>
 * <p>
 * While, the only way to remove properties is {@link Configuration#clear()}.<br>
 * Remember this will delete <u><b>all</b> the stored properties and their listeners</u>.
 * <p>As usual, it's possible to retrieve the current value for any key, using the following method:</p>
 * {@link Configuration#get(String)} which will retrieve the associated {@link PropertyDefinition} object from
 * the instance using a specific key.
 *
 * <p>You can parse then return the current value as described using PropertyDefinition#getValue(): </p>
 *
 * <p>
 *     <ul>
 *          <li>{@link PropertyValue#asString()}</li>
 *          <li>{@link PropertyValue#asBoolean()}</li>
 *          <li>{@link PropertyValue#asByte()}</li>
 *          <li>{@link PropertyValue#asShort()}</li>
 *          <li>{@link PropertyValue#asInt()}</li>
 *          <li>{@link PropertyValue#asLong()}</li>
 *          <li>{@link PropertyValue#asFloat()}</li>
 *          <li>{@link PropertyValue#asDouble()}</li>
 *     </ul>
 *
 * <p>
 * Through {@link Configuration#addListener(ConfigurationListener.Type, ConfigurationListener)} is possible
 * to detect read and write events in order to execute some custom code <b>before</b> loading and saving.
 *
 * <pre>
 * {@code
 *  cfg.addListener(ConfigurationLister.Type.ON_SAVE, instance, () -> {
 *      // Custom function
 *  });}
 * </pre>
 *
 * <p>
 * It's even possible to track down a single property change,
 * using {@link Configuration#addListener(PropertyListener.Type, String, PropertyListener)} )}
 * in order to execute some custom code replacing the tracked value.
 * <pre>
 * {@code
 *  cfg.addListener(PropertyListener.Type.ON_PROPERTY_UPDATE, "key", property -> {
 *      // Custom function to handle a specific property change
 *  });}
 * </pre>
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class Configuration {

    private String filename;
    private String pathname;
    private LinkedHashMap<String, PropertyDefinition> properties;
    private ConfigurationPolicy policy;

    private ArrayList<ConfigurationListener> onSave;
    private ArrayList<ConfigurationListener> onDelete;
    private HashMap<String, ArrayList<PropertyListener>> onPropertyChange;

    /**
     * Private empty constructor
     */
    private Configuration() {
    }

    /**
     * Private configuration constructor with parameters
     *
     * @param filename The configuration filename
     * @param pathname The configuration pathname
     * @param policy   The policy used by this configuration
     */
    private Configuration(String filename, String pathname, ConfigurationPolicy policy) {
        this.filename = filename;
        this.pathname = pathname;
        this.properties = new LinkedHashMap<>();
        this.policy = policy;
        this.onSave = new ArrayList<>();
        this.onDelete = new ArrayList<>();
        this.onPropertyChange = new HashMap<>();
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
     * Gets a specific property using the provided key
     *
     * @param key The key used to identify the value
     * @return An immutable {@link PropertyDefinition} object used to retrieve any known information
     * @throws NullPointerException     If the key is null
     * @throws IllegalArgumentException If the key is empty
     * @throws NoSuchElementException   If the key does not match any property
     */
    public PropertyDefinition get(String key) {

        if (key == null)
            throw new NullPointerException("The key cannot be null");

        if (key.isEmpty())
            throw new IllegalArgumentException("The key cannot be empty");

        if (this.properties.get(key) == null)
            throw new NoSuchElementException("The following key does not exists: " + key);

        return PropertyDefinition.copy(this.properties.get(key));
    }

    /**
     * Check if a specific key is stored by the configuration object
     *
     * @param key The key used to identify the value
     * @return True or false
     * @throws NullPointerException     If the key is null
     * @throws IllegalArgumentException If the key is empty
     */
    public boolean contains(String key) {

        if (key == null)
            throw new NullPointerException("The key cannot be null");

        if (key.isEmpty())
            throw new IllegalArgumentException("The key cannot be empty");

        return this.properties.containsKey(key);
    }


    /**
     * Retrieves an immutable {@link List} containing all the properties stored
     *
     * @return All properties related to this configuration object as {@link List}
     */
    public List<PropertyDefinition> getProperties() {
        return PropertyDefinition.copy(this.properties.values());
    }

    /**
     * Insert a new {@link PropertyDefinition} object, if it already exists the old value will be replaced.
     *
     * @param property The new {@link PropertyDefinition} to store inside the configuration
     * @throws NullPointerException     If the property reference is null
     * @throws NullPointerException     If {@link PropertyDefinition#key()} is null
     * @throws NullPointerException     If {@link PropertyDefinition#asString()} is null
     * @throws IllegalArgumentException If {@link PropertyDefinition#key()} is empty
     * @throws IllegalArgumentException If {@link PropertyDefinition#asString()} is empty
     */
    public void put(PropertyDefinition property) {

        if (property == null)
            throw new NullPointerException("The property cannot be null");

        if (property.key() == null)
            throw new NullPointerException("The key cannot be null");

        if (property.key().isEmpty())
            throw new IllegalArgumentException("The key cannot be empty");

        if (property.asString() == null)
            throw new NullPointerException("The value cannot be null");

        if (property.asString().isEmpty())
            throw new IllegalArgumentException("The value cannot be empty");

        this.properties.put(property.key(), property);

        if (this.onPropertyChange.get(property.key()) != null) {
            this.onPropertyChange.get(property.key()).forEach(listener -> listener.onChange(this.get(property.key())));
        }

    }

    /**
     * Insert a new {@link PropertyDefinition} object, if it already exists the old value will be replaced.
     *
     * @param key   The property key
     * @param value The property value
     * @throws NullPointerException     If key is null
     * @throws NullPointerException     If value is null
     * @throws IllegalArgumentException If key is empty
     * @throws IllegalArgumentException If value is empty
     */
    public void put(String key, String value) {

        if (key == null)
            throw new NullPointerException("The key cannot be null");

        if (key.isEmpty())
            throw new IllegalArgumentException("The key cannot be empty");

        if (value == null)
            throw new NullPointerException("The value cannot be null");

        if (value.isEmpty())
            throw new IllegalArgumentException("The value cannot be empty");

        this.properties.put(key, new PropertyDefinition(key, value));

        if (this.onPropertyChange.get(key) != null) {
            this.onPropertyChange.get(key).forEach(listener -> listener.onChange(this.get(key)));
        }

    }

    /**
     * Insert a new {@link PropertyDefinition} object, if it already exists the old value will be replaced.
     *
     * @param key         The property key
     * @param value       The property value
     * @param description The property description
     * @throws NullPointerException     If key is null
     * @throws NullPointerException     If value is null
     * @throws IllegalArgumentException If key is empty
     * @throws IllegalArgumentException If value is empty
     * @throws IllegalArgumentException If description is empty
     */
    public void put(String key, String value, String description) {

        if (key == null)
            throw new NullPointerException("The key cannot be null");

        if (key.isEmpty())
            throw new IllegalArgumentException("The key cannot be empty");

        if (value == null)
            throw new NullPointerException("The value cannot be null");

        if (value.isEmpty())
            throw new IllegalArgumentException("The value cannot be empty");

        if (description != null && description.isEmpty())
            throw new IllegalArgumentException("The description cannot be empty");

        this.properties.put(key, new PropertyDefinition(key, value, description));

        if (this.onPropertyChange.get(key) != null) {
            this.onPropertyChange.get(key).forEach(listener -> listener.onChange(this.get(key)));
        }

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
     * Removes all the properties and listeners associated to the current configuration object
     */
    public void clear() {

        this.onSave.clear();
        this.onDelete.clear();
        this.onPropertyChange.clear();

        this.properties.clear();
    }

    /**
     * Sets a new listener for any {@link ConfigurationListener.Type} value.
     *
     * @param type     The event type
     * @param listener The custom function to execute when the event will be fired
     */
    public void addListener(ConfigurationListener.Type type, ConfigurationListener listener) {
        switch (type) {
            case ON_CONFIG_SAVE:
                this.onSave.add(listener);
                break;
            case ON_CONFIG_DELETE:
                this.onDelete.add(listener);
                break;
        }
    }

    /**
     * Sets a new listener for any {@link PropertyListener.Type} value.
     *
     * @param type     The event type
     * @param key      The key associated with the requested property
     * @param listener The custom function to execute when the event will be fired
     */
    public void addListener(PropertyListener.Type type, String key, PropertyListener listener) {
        switch (type) {
            case ON_PROPERTY_UPDATE:
                if (this.properties.get(key) == null) {
                    throw new NoSuchElementException("The following key does not exists: " + key);
                }

                if (this.onPropertyChange.get(key) != null) {
                    this.onPropertyChange.get(key).add(listener);
                } else {
                    this.onPropertyChange.put(key, new ArrayList<>());
                    this.onPropertyChange.get(key).add(listener);
                }
                break;
        }
    }

    /**
     * Remove listener for any {@link ConfigurationListener.Type} value.
     *
     * @param type     The event type
     * @param listener The custom function reference which was associated to the event
     */
    public void removeListener(ConfigurationListener.Type type, ConfigurationListener listener) {
        switch (type) {
            case ON_CONFIG_SAVE:
                this.onSave.remove(listener);
                break;
            case ON_CONFIG_DELETE:
                this.onDelete.remove(listener);
                break;
        }
    }

    /**
     * Remove listener for any {@link PropertyListener.Type} value.
     *
     * @param type     The event type
     * @param key      The key associated with the requested property
     * @param listener The custom function reference which was associated to the event
     */
    public void removeListener(PropertyListener.Type type, String key, PropertyListener listener) {
        switch (type) {
            case ON_PROPERTY_UPDATE:
                if (this.onPropertyChange.get(key) != null)
                    this.onPropertyChange.get(key).remove(listener);
                break;
        }
    }


    /**
     * Returns a {@link TreeSet} containing all the keys stored inside this configuration instance
     *
     * @return The keys stored in the configuration instance
     */
    TreeSet<String> getKeys() {
        return new TreeSet<>(this.properties.keySet());
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
     * Gets the {@link ConfigurationPolicy} associated to this configuration instance.
     *
     * @return The current policy set on this configuration object
     */
    ConfigurationPolicy getPolicy() {
        return policy;
    }

    /**
     * The {@link Configuration.Builder} class allows to generate {@link Configuration} instances.
     * <p></p>
     * <p>You can use {@link #build()} in order to retrieve a configuration instance,
     * then using {@link Configuration#put(String, String)} is possible to insert new properties;
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

        private String filename;
        private String pathname;
        private ConfigurationPolicy policy;

        /**
         * The {@link Configuration.Builder} constructor
         *
         * <p>By default, the configuration policy is set to {@link ConfigurationPolicy#STRICT_MODE}</p>
         */
        public Builder() {
            this.policy = ConfigurationPolicy.STRICT_MODE;
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
         * Sets the configuration policy.
         * <p></p>
         * <p>The {@link ConfigurationPolicy} is a <b>really important</b> rule,<br>
         * which must be applied carefully and with some consideration
         * by the developer.</p>
         * <p>
         * This value affects the behaviours of the following methods:
         * <ul>
         *      <li>{@link ConfigurationIO#read(Configuration)}</li>
         *      <li>{@link ConfigurationIO#write(Configuration)}</li>
         * </ul>
         *
         * <br>
         * <p>
         * On {@link ConfigurationPolicy#STRICT_MODE}:
         * <ol>
         *     <li>Unknown properties read from the configuration file will cause an exception</li>
         *     <li>Any missing property from the configuration file will cause an exception</li>
         *     <li>Any invalid syntax from the configuration file will cause an exception</li>
         * </ol>
         * It is recommended when:
         * <ul>
         *     <li>You <b>do not want</b> allow the user to externally modify the configuration file</li>
         *     <li>You <b>do not want</b> missing property from the configuration file</li>
         * </ul>
         * <p>
         * On {@link ConfigurationPolicy#TOLERANT_MODE}:
         * <ol>
         *     <li>Unknown properties read from the configuration file will be loaded</li>
         *     <li>Any missing property from the configuration file will be ignored</li>
         *     <li>Any invalid syntax from the configuration file will cause an exception</li>
         * </ol>
         * It is recommended when:
         * <ul>
         *     <li>You <b>want</b> allow the user to externally modify the configuration file</li>
         *     <li>You <b>want</b> to ignore missing property from the configuration file</li>
         * </ul>
         * <p>
         * By default, the configuration policy is set to {@link ConfigurationPolicy#STRICT_MODE}
         *
         * @return The {@link Configuration.Builder} current instance
         */
        public Configuration.Builder setPolicy(ConfigurationPolicy policy) {

            if (policy == null)
                throw new NullPointerException("The policy cannot be null");

            this.policy = policy;
            return this;
        }

        /**
         * Create the final object
         *
         * @return The new {@link Configuration} instance
         * @throws NullPointerException If one or more properties are not set
         */
        public Configuration build() {

            Objects.requireNonNull(filename, "The filename must be set!");
            Objects.requireNonNull(pathname, "The pathname must be set!");
            Objects.requireNonNull(policy, "The policy cannot be null");

            return new Configuration(filename, pathname, policy);
        }

    }

}

