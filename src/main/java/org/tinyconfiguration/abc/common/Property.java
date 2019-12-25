package org.tinyconfiguration.abc.common;

import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.Buildable;
import org.tinyconfiguration.abc.events.ObservableProperty;
import org.tinyconfiguration.abc.listeners.PropertyListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class represent the properties stored inside the configuration instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class Property extends AbstractProperty implements ObservableProperty<Property> {

    private final boolean isOptional;
    private final Predicate<Property> isValid;
    private final List<PropertyListener<Property>> listeners;

    /**
     * Private empty constructor
     */
    private Property() {
        this.isOptional = false;
        this.isValid = null;
        this.listeners = new ArrayList<>();
    }

    /**
     * Private constructor with parameters
     */
    private Property(String key, Datatype value, String description, boolean isOptional, Predicate<Property> isValid, List<PropertyListener<Property>> listeners) {
        super(key, value, description);
        this.isOptional = isOptional;
        this.isValid = isValid;
        this.listeners = new ArrayList<>(listeners);
    }

    @Override
    public String getKey() {
        return super.getKey();
    }

    @Override
    public Datatype getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(String s) {

        super.setValue(s);

        this.listeners.forEach(listener -> listener.onChange(this));

    }

    @Override
    public void setValue(boolean b) {

        super.setValue(b);

        this.listeners.forEach(listener -> listener.onChange(this));

    }

    @Override
    public void setValue(char c) {

        super.setValue(c);

        this.listeners.forEach(listener -> listener.onChange(this));

    }

    @Override
    public void setValue(byte b) {

        super.setValue(b);

        this.listeners.forEach(listener -> listener.onChange(this));

    }

    @Override
    public void setValue(short s) {

        super.setValue(s);

        this.listeners.forEach(listener -> listener.onChange(this));

    }

    @Override
    public void setValue(int i) {
        super.setValue(i);

        this.listeners.forEach(listener -> listener.onChange(this));

    }

    @Override
    public void setValue(long l) {
        super.setValue(l);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public void setValue(float f) {
        super.setValue(f);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public void setValue(double d) {
        super.setValue(d);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public void setValue(String[] s) {
        super.setValue(s);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public void setValue(boolean[] b) {
        super.setValue(b);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public void setValue(char[] c) {
        super.setValue(c);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public void setValue(byte[] b) {
        super.setValue(b);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public void setValue(short[] s) {
        super.setValue(s);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public void setValue(int[] i) {
        super.setValue(i);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public void setValue(long[] l) {
        super.setValue(l);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public void setValue(float[] f) {
        super.setValue(f);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public void setValue(double[] d) {
        super.setValue(d);

        this.listeners.forEach(listener -> listener.onChange(this));
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    /**
     * Insert new listeners on this property
     *
     * @param type The {@link PropertyListener.Type} which specifies the listener type
     * @param l    The property listener to associate on this property object
     * @return The boolean value representing the outcome on the insert operation
     */
    @Override
    public boolean addListener(PropertyListener.Type type, PropertyListener<Property> l) {
        boolean result = false;

        if (type == PropertyListener.Type.ON_PROPERTY_UPDATE)
            result = this.listeners.add(l);

        return result;
    }

    /**
     * Remove listeners from this property
     *
     * @param type The {@link PropertyListener.Type} which specifies the listener type
     * @param l    The property listener to remove from this property object
     * @return The boolean value representing the outcome on the remove operation
     */
    @Override
    public boolean removeListener(PropertyListener.Type type, PropertyListener<Property> l) {

        boolean result = false;

        if (type == PropertyListener.Type.ON_PROPERTY_UPDATE)
            result = this.listeners.remove(l);

        return result;
    }

    /**
     * Removes all listeners associated to the current property object
     */
    @Override
    public void resetListeners() {
        this.listeners.clear();
    }

    /**
     * Gets the optionality
     *
     * @return The boolean value representing the optionality state
     */
    public boolean isOptional() {
        return isOptional;
    }

    /**
     * Gets the validity performing the specified {@link Predicate} test
     *
     * @return The boolean value representing the validity state
     * @throws NullPointerException If the validator function is null
     */
    public boolean isValid() {

        if (this.isValid == null)
            throw new NullPointerException("The validator function is null");

        return this.isValid.test(this);
    }

    /**
     * The {@link Property.Builder} class is used to build the {@link Property} object
     *
     * @author G. Baittiner
     * @version 0.1
     */
    public static final class Builder extends AbstractProperty implements ObservableProperty<Property>, Buildable {

        private boolean isOptional;
        private Predicate<Property> isValid;
        private List<PropertyListener<Property>> listeners;

        /**
         * Empty builder constructor
         */
        public Builder() {
            this.isOptional = false;
            this.isValid = null;
            this.listeners = new ArrayList<>();
        }

        /**
         * Sets the key value
         *
         * @param key The key value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the key is null
         * @throws IllegalArgumentException If the key is empty
         */
        public Builder setKey(String key) {

            if (key == null) {
                throw new NullPointerException("The key cannot be null");
            }

            if (key.trim().isEmpty()) {
                throw new IllegalArgumentException("The key cannot be empty");
            }

            this.key = key;

            return this;
        }

        /**
         * Sets the description value
         *
         * @param description The description value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the description is null
         * @throws IllegalArgumentException If the description is empty
         */
        public Builder setDescription(String description) {

            if (description == null)
                throw new NullPointerException("The description cannot be null");

            if (description.trim().isEmpty())
                throw new IllegalArgumentException("The description cannot be empty");

            this.description = description;

            return this;
        }

        /**
         * Sets the optionality
         *
         * @param optional The optional value
         * @return The {@link Property.Builder} current instance
         */
        public Builder setOptional(boolean optional) {
            isOptional = optional;
            return this;
        }

        /**
         * Sets the validator function
         *
         * @param validator The validator function
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException If the validator function is null
         */
        public Builder setValidator(Predicate<Property> validator) {

            if (validator == null) {
                throw new NullPointerException("The validator function cannot be null");
            }

            this.isValid = validator;

            return this;
        }

        @Override
        public boolean addListener(PropertyListener.Type type, PropertyListener<Property> l) {
            boolean result = false;

            if (type == PropertyListener.Type.ON_PROPERTY_UPDATE)
                result = this.listeners.add(l);

            return result;
        }

        @Override
        public boolean removeListener(PropertyListener.Type type, PropertyListener<Property> l) {
            boolean result = false;

            if (type == PropertyListener.Type.ON_PROPERTY_UPDATE)
                result = this.listeners.remove(l);

            return result;
        }

        @Override
        public void resetListeners() {
            this.listeners.clear();
        }

        @Override
        public void clear() {

            this.key = null;
            this.value = null;
            this.description = null;
            this.isValid = null;
            this.isOptional = false;

            this.listeners.clear();

        }

        /**
         * Build the property object
         *
         * @return The {@link Property} object
         * @throws NullPointerException If the key or value is not set
         */
        @Override
        public Property build() {

            if (this.key == null)
                throw new NullPointerException("The key must be set!");

            if (this.value == null)
                throw new NullPointerException("The value must be set!");

            return new Property(key, value, description, isOptional, isValid, listeners);
        }

    }

}
