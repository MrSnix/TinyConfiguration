package org.tinyconfiguration.data;

import org.tinyconfiguration.data.base.Datatype;

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
    private Datatype value;
    private String description;
    private String group;
    private boolean isOptional;
    private Predicate<Datatype> isValid;

    private Property() {
    }

    private Property(String key, Datatype value, String description, String group, boolean isOptional, Predicate<Datatype> isValid) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.group = group;
        this.isOptional = isOptional;
        this.isValid = isValid;
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

    public boolean isOptional() {
        return isOptional;
    }

    public boolean isValid() {
        return this.isValid.test(this.value);
    }

    public static Property copy(Property o) {

        Property p = new Property();

        p.key = o.key;
        p.value = Datatype.Utils.copy(o.value);
        p.description = o.description;
        p.group = o.group;
        p.isOptional = o.isOptional;
        p.isValid = o.isValid;

        return p;
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

        public Property build() {

            Objects.requireNonNull(key, "The key must be set!");
            Objects.requireNonNull(value, "The value must be set!");
            Objects.requireNonNull(group, "The group must be set!");

            return new Property(key, value, description, group, isOptional, isValid);
        }

    }

}