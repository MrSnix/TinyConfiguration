package org.tinyconfiguration.property;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * This class represent the properties stored inside the configuration instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class Property {

    private String key;
    private PropertyValue value;
    private String description;
    private String group;
    private String placeholder;
    private boolean isOptional;
    private Predicate<Property> isValid;

    private Property() {
    }

    private Property(String key, PropertyValue value, String description, String group, String placeholder, boolean isOptional, Predicate<Property> isValid) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.group = group;
        this.placeholder = placeholder;
        this.isOptional = isOptional;
        this.isValid = isValid;
    }

    public String getKey() {
        return key;
    }

    public PropertyValue getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getGroup() {
        return group;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public boolean isValid() {
        return this.isValid.test(this);
    }

    public static Property copy(Property o) {

        Property p = new Property();

        p.key = o.key;
        p.value = PropertyValue.copy(o.value);
        p.description = o.description;
        p.group = o.group;
        p.placeholder = o.placeholder;
        p.isOptional = o.isOptional;
        p.isValid = o.isValid;

        return p;
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
                Objects.equals(placeholder, property.placeholder) &&
                Objects.equals(isValid, property.isValid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, description, group, placeholder, isOptional, isValid);
    }

    public static class Builder {

        private String key;
        private PropertyValue value;
        private String description;
        private String group;
        private String placeholder;
        private boolean isOptional;
        private Predicate<Property> isValid;

        public Builder() {
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

        public Builder setValue(String value) {

            if (value == null) {
                throw new NullPointerException("The value cannot be null");
            }

            if (value.isEmpty()) {
                throw new NullPointerException("The value cannot be empty");
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

            if (group != null && group.trim().isEmpty()) {
                throw new IllegalArgumentException("The group name cannot be empty");
            }

            this.group = group;

            return this;
        }

        public Builder setPlaceholder(String placeholder) {

            if (placeholder == null) {
                throw new NullPointerException("The placeholder value cannot be null");
            }

            if (placeholder.trim().isEmpty()) {
                throw new IllegalArgumentException("The placeholder value cannot be empty");
            }

            this.placeholder = placeholder;

            return this;
        }

        public Builder setOptional(boolean optional) {
            isOptional = optional;
            return this;
        }

        public Builder setValidator(Predicate<Property> validator) {

            if (validator == null) {
                throw new NullPointerException("The validator function cannot be null");
            }

            this.isValid = validator;
            return this;
        }

        public Property build() {

            Objects.requireNonNull(key, "The key must be set!");
            Objects.requireNonNull(value, "The value must be set!");
            Objects.requireNonNull(placeholder, "The placeholder must be set!");

            return new Property(key, value, description, group, placeholder, isOptional, isValid);
        }

    }

}