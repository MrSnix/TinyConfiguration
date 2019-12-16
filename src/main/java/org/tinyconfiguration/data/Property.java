package org.tinyconfiguration.data;

import org.tinyconfiguration.events.PropertyListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * This class represent the properties stored inside the configuration instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class Property {

    private final String key;
    private PropertyValue value;
    private final String description;
    private final String group;
    private final boolean isOptional;
    private final Predicate<PropertyValue> isValid;
    private final List<PropertyListener> listeners;

    /**
     * Private empty constructor
     */
    private Property() {
        this.key = null;
        this.value = null;
        this.description = null;
        this.group = null;
        this.isOptional = false;
        this.isValid = null;
        this.listeners = new ArrayList<>();
    }

    /**
     * Private configuration constructor with parameters
     */
    private Property(String key, PropertyValue value, String description, String group, boolean isOptional, Predicate<PropertyValue> isValid, List<PropertyListener> listeners) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.group = group;
        this.isOptional = isOptional;
        this.isValid = isValid;
        this.listeners = new ArrayList<>(listeners);
    }

    /**
     * Gets the key
     *
     * @return The key ({@link String}) associated to the property object.
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the value
     *
     * @return The value ({@link PropertyValue}) associated to the property object.
     */
    public PropertyValue getValue() {
        return value;
    }

    /**
     * Gets the description
     *
     * @return The description ({@link String}) associated to the property object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the group name
     *
     * @return The group ({@link String}) associated to the property object.
     */
    public String getGroup() {
        return group;
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
     */
    public boolean isValid() {
        return Objects.requireNonNull(this.isValid).test(this.value);
    }

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(String s) {
        __setValue(s);
    }

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(boolean b) {
        __setValue(b);
    }

    /**
     * Sets the value on this property
     *
     * @param c The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(char c) {
        __setValue(c);
    }

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(byte b) {
        __setValue(b);
    }

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(short s) {
        __setValue(s);
    }

    /**
     * Sets the value on this property
     *
     * @param i The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(int i) {
        __setValue(i);
    }

    /**
     * Sets the value on this property
     *
     * @param l The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(long l) {
        __setValue(l);
    }

    /**
     * Sets the value on this property
     *
     * @param f The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(float f) {
        __setValue(f);
    }

    /**
     * Sets the value on this property
     *
     * @param d The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(double d) {
        __setValue(d);
    }

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(String[] s) {
        __setValue(s);
    }

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(boolean[] b) {
        __setValue(b);
    }

    /**
     * Sets the array value on this property
     *
     * @param c The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(char[] c) {
        __setValue(c);
    }

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(byte[] b) {
        __setValue(b);
    }

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(short[] s) {
        __setValue(s);
    }

    /**
     * Sets the array value on this property
     *
     * @param i The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(int[] i) {
        __setValue(i);
    }

    /**
     * Sets the array value on this property
     *
     * @param l The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(long[] l) {
        __setValue(l);
    }

    /**
     * Sets the array value on this property
     *
     * @param f The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(float[] f) {
        __setValue(f);
    }

    /**
     * Sets the array value on this property
     *
     * @param d The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public void setValue(double[] d) {
        __setValue(d);
    }

    /**
     * Sets any generic value on this property
     *
     * @param value The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    private void __setValue(Object value) {

        if (value == null) {
            throw new NullPointerException("The value cannot be null");
        }

        if (value.getClass() != value.getClass()) {
            throw new IllegalArgumentException("The value must be of the same class as the one declared");
        }

        this.value = new PropertyValue(value);

        this.listeners.forEach(listener -> {
            listener.onChange(this);
        });

    }

    /**
     * Gets the listeners on this property
     *
     * @return The listeners associated to the property object.
     */
    public List<PropertyListener> getListeners() {
        return new ArrayList<>(listeners);
    }

    /**
     * Insert new listeners on this property
     *
     * @param type The {@link PropertyListener.Type} which specifies the listener type
     * @param l    The property listener to associate on this property object
     * @return The boolean value representing the outcome on the insert operation
     */
    public boolean addListener(PropertyListener.Type type, PropertyListener l) {

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
    public boolean removeListener(PropertyListener.Type type, PropertyListener l) {

        boolean result = false;

        if (type == PropertyListener.Type.ON_PROPERTY_UPDATE)
            result = this.listeners.remove(l);

        return result;
    }

    /**
     * Removes all listeners associated to the current property object
     */
    public void resetListeners() {
        this.listeners.clear();
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, description, group, isOptional, isValid);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return isOptional == property.isOptional &&
                Objects.equals(key, property.key) &&
                Objects.equals(value, property.value) &&
                Objects.equals(description, property.description) &&
                Objects.equals(group, property.group) &&
                Objects.equals(isValid, property.isValid) &&
                Objects.equals(listeners, property.listeners);
    }

    public static class Builder {

        private String key;
        private PropertyValue value;
        private String description;
        private String group;
        private boolean isOptional;
        private Predicate<PropertyValue> isValid;
        private List<PropertyListener> listeners;

        public Builder() {
            this.listeners = new ArrayList<>();
        }

        private Builder __setValue(Object value) {

            if (value == null) {
                throw new NullPointerException("The value cannot be null");
            }

            this.value = new PropertyValue(value);

            return this;
        }

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

        public Builder setValue(String s) {
            return __setValue(s);
        }

        public Builder setValue(boolean b) {
            return __setValue(b);
        }

        public Builder setValue(char c) {
            return __setValue(c);
        }

        public Builder setValue(byte b) {
            return __setValue(b);
        }

        public Builder setValue(short s) {
            return __setValue(s);
        }

        public Builder setValue(int i) {
            return __setValue(i);
        }

        public Builder setValue(long l) {
            return __setValue(l);
        }

        public Builder setValue(float f) {
            return __setValue(f);
        }

        public Builder setValue(double d) {
            return __setValue(d);
        }

        public Builder setValue(String[] s) {
            return __setValue(s);
        }

        public Builder setValue(boolean[] b) {
            return __setValue(b);
        }

        public Builder setValue(char[] c) {
            return __setValue(c);
        }

        public Builder setValue(byte[] b) {
            return __setValue(b);
        }

        public Builder setValue(short[] s) {
            return __setValue(s);
        }

        public Builder setValue(int[] i) {
            return __setValue(i);
        }

        public Builder setValue(long[] l) {
            return __setValue(l);
        }

        public Builder setValue(float[] f) {
            return __setValue(f);
        }

        public Builder setValue(double[] d) {
            return __setValue(d);
        }

        public Builder setDescription(String description) {

            if (description == null) {
                throw new NullPointerException("The description cannot be null");
            }

            this.description = description;
            return this;
        }
        public Builder setGroup(String group) {

            if (group != null && group.trim().isEmpty())
                throw new IllegalArgumentException("The group name cannot be empty");

            this.group = group;

            return this;
        }
        public Builder setOptional(boolean optional) {
            isOptional = optional;
            return this;
        }
        public Builder setValidator(Predicate<PropertyValue> validator) {

            if (validator == null) {
                throw new NullPointerException("The validator function cannot be null");
            }

            this.isValid = validator;

            return this;
        }

        public Property build() {

            Objects.requireNonNull(key, "The key must be set!");
            Objects.requireNonNull(value, "The value must be set!");

            return new Property(key, value, description, group, isOptional, isValid, listeners);
        }

    }

}