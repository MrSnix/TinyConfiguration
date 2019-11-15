package org.tinyconfiguration.data;

import org.tinyconfiguration.data.base.Datatype;
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
    private final Datatype value;
    private final String description;
    private final String group;
    private final boolean isOptional;
    private final Predicate<Datatype> isValid;
    private final List<PropertyListener> listeners;

    private Property() {
        this.key = null;
        this.value = null;
        this.description = null;
        this.group = null;
        this.isOptional = false;
        this.isValid = null;
        this.listeners = null;
    }

    private Property(String key, Datatype value, String description, String group, boolean isOptional,
                     Predicate<Datatype> isValid, List<PropertyListener> listeners) {
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

    public Datatype getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getGroup() {
        return group;
    }

    public List<PropertyListener> getListeners() {
        return new ArrayList<>(Objects.requireNonNull(listeners));
    }

    public boolean isOptional() {
        return isOptional;
    }

    public boolean isValid() {
        return Objects.requireNonNull(this.isValid).test(this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, description, group, isOptional, isValid);
    }

    public static class Builder {

        private String key;
        private Datatype value;
        private String description;
        private String group;
        private boolean isOptional;
        private Predicate<Datatype> isValid;
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

            this.value = Datatype.Utils.generate(value);

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

        public Builder setValidator(Predicate<Datatype> validator) {

            if (validator == null) {
                throw new NullPointerException("The validator function cannot be null");
            }

            this.isValid = validator;

            return this;
        }

        public Builder addListener(PropertyListener.Type type, PropertyListener l) {

            if (type == PropertyListener.Type.ON_PROPERTY_UPDATE)
                this.listeners.add(l);

            return this;
        }

        public Builder copy(Property o) {

            this.key = o.key;
            this.value = Datatype.Utils.copy(Objects.requireNonNull(o.value));
            this.description = o.description;
            this.group = o.group;
            this.isOptional = o.isOptional;
            this.isValid = o.isValid;
            this.listeners = new ArrayList<>(Objects.requireNonNull(o.listeners));

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