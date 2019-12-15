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

    private Property() {
        this.key = null;
        this.value = null;
        this.description = null;
        this.group = null;
        this.isOptional = false;
        this.isValid = null;
        this.listeners = new ArrayList<>();
    }

    private Property(String key, PropertyValue value, String description, String group, boolean isOptional,
                     Predicate<PropertyValue> isValid, List<PropertyListener> listeners) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.group = group;
        this.isOptional = isOptional;
        this.isValid = isValid;
        this.listeners = new ArrayList<>(listeners);
    }

    public String getKey() {
        return key;
    }

    public PropertyValue getValue() {
        return value;
    }

    public void setValue(Object value) {

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

    public String getDescription() {
        return description;
    }

    public String getGroup() {
        return group;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public boolean isValid() {
        return Objects.requireNonNull(this.isValid).test(this.value);
    }

    public List<PropertyListener> getListeners() {
        return new ArrayList<>(listeners);
    }

    public boolean addListener(PropertyListener.Type type, PropertyListener l) {

        boolean result = false;

        if (type == PropertyListener.Type.ON_PROPERTY_UPDATE)
            result = this.listeners.add(l);

        return result;
    }

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

        public Builder setValue(Object value) {

            if (value == null) {
                throw new NullPointerException("The value cannot be null");
            }

            this.value = new PropertyValue(value);

            return this;
        }

        public Builder setDescription(String description) {

            if (description == null) {
                throw new NullPointerException("The description cannot be null");
            }

            this.description = description;
            return this;
        }

        public Builder setGroup(String group) {

            if (group == null)
                throw new NullPointerException("The group name cannot be null");

            if (group.trim().isEmpty())
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
            Objects.requireNonNull(group, "The group must be set!");

            return new Property(key, value, description, group, isOptional, isValid, listeners);
        }

    }

}