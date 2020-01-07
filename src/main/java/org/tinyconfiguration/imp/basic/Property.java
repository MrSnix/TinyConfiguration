package org.tinyconfiguration.imp.basic;

import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.builders.AbstractBuilder;
import org.tinyconfiguration.abc.builders.Mutable;
import org.tinyconfiguration.abc.data.ImmutableDatatype;
import org.tinyconfiguration.abc.events.EventType;
import org.tinyconfiguration.abc.events.listeners.EventListener;
import org.tinyconfiguration.imp.basic.events.base.PropertyEvent;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class represent the properties stored inside the configuration instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class Property extends AbstractProperty<ImmutableDatatype> {

    private final boolean isOptional;
    private final Predicate<Property> isValid;
    private final HashMap<EventType<PropertyEvent>, List<EventListener<? extends PropertyEvent>>> listeners;

    /**
     * Private empty constructor
     */
    private Property() {
        this.isOptional = false;
        this.isValid = null;
        this.listeners = new HashMap<>();
    }

    /**
     * Private constructor with parameters
     */
    private Property(String key, ImmutableDatatype value, String description, boolean isOptional, Predicate<Property> isValid) {
        super(key, value, description);
        this.isOptional = isOptional;
        this.isValid = isValid;
        this.listeners = new HashMap<>();
    }

    @Override
    public String getKey() {
        return super.getKey();
    }

    @Override
    public ImmutableDatatype getValue() {
        return super.getValue();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
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
     * <p></p>
     * It returns <u>always</u> <b>true</b>, if no validation function was defined
     *
     * @return The boolean value representing the validity state
     */
    public boolean isValid() {

        boolean isValid;

        if (this.isValid == null)
            isValid = true;
        else
            isValid = this.isValid.test(this);

        return isValid;
    }

    /**
     * The {@link Builder} class is used to build the {@link Property} object
     *
     * @author G. Baittiner
     * @version 0.1
     */
    public static final class Builder extends AbstractBuilder<Property> implements Mutable<Builder> {

        private String key;
        private ImmutableDatatype value;
        private String description;
        private boolean isOptional;
        private Predicate<Property> isValid;

        private final boolean isCleanable;

        /**
         * Empty builder constructor
         */
        public Builder() {
            this.key = null;
            this.value = null;
            this.description = null;
            this.isOptional = false;
            this.isValid = null;
            this.isCleanable = true;
        }

        /**
         * Builder constructor with parameters
         *
         * @param isCleanable If true on {@link Builder#build()} the object will be reusable
         */
        public Builder(boolean isCleanable) {
            this.key = null;
            this.value = null;
            this.description = null;
            this.isOptional = false;
            this.isValid = null;
            this.isCleanable = isCleanable;
        }

        /**
         * Sets the key value
         *
         * @param key The key value
         * @return The {@link Builder} current instance
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
         * @return The {@link Builder} current instance
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
         * @return The {@link Builder} current instance
         */
        public Builder setOptional(boolean optional) {
            isOptional = optional;
            return this;
        }

        /**
         * Sets the validator function
         *
         * @param validator The validator function
         * @return The {@link Builder} current instance
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
        public void clear() {

            this.key = null;
            this.value = null;
            this.description = null;
            this.isValid = null;
            this.isOptional = false;

        }

        /**
         * Build the property object then call {@link AbstractBuilder#clear()} to make the builder reusable
         *
         * @return The {@link Property} object
         * @throws NullPointerException If the key, value or description is not set
         */
        @Override
        public Property build() {

            if (this.key == null)
                throw new NullPointerException("The key must be set!");

            if (this.value == null)
                throw new NullPointerException("The value must be set!");

            if (this.description == null)
                throw new NullPointerException("The description must be set!");

            Property e = new Property(key, value, description, isOptional, isValid);

            if (isCleanable)
                clear();

            return e;
        }

        /**
         * Sets the value on this property
         *
         * @param s The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(String s) {

            if (value == null)
                this.value = new ImmutableDatatype(s);
            else
                value.setValue(s);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param b The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(boolean b) {

            if (value == null)
                this.value = new ImmutableDatatype(b);
            else
                value.setValue(b);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param c The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(char c) {

            if (value == null)
                this.value = new ImmutableDatatype(c);
            else
                value.setValue(c);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param b The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(byte b) {

            if (value == null)
                this.value = new ImmutableDatatype(b);
            else
                value.setValue(b);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param s The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(short s) {

            if (value == null)
                this.value = new ImmutableDatatype(s);
            else
                value.setValue(s);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param i The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(int i) {

            if (value == null)
                this.value = new ImmutableDatatype(i);
            else
                value.setValue(i);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param l The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(long l) {

            if (value == null)
                this.value = new ImmutableDatatype(l);
            else
                value.setValue(l);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param f The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(float f) {

            if (value == null)
                this.value = new ImmutableDatatype(f);
            else
                value.setValue(f);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param d The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(double d) {

            if (value == null)
                this.value = new ImmutableDatatype(d);
            else
                value.setValue(d);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param s The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(String[] s) {

            if (value == null)
                this.value = new ImmutableDatatype(s);
            else
                value.setValue(s);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param b The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(boolean[] b) {

            if (value == null)
                this.value = new ImmutableDatatype(b);
            else
                value.setValue(b);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param c The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(char[] c) {

            if (value == null)
                this.value = new ImmutableDatatype(c);
            else
                value.setValue(c);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param b The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(byte[] b) {

            if (value == null)
                this.value = new ImmutableDatatype(b);
            else
                value.setValue(b);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param s The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(short[] s) {

            if (value == null)
                this.value = new ImmutableDatatype(s);
            else
                value.setValue(s);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param i The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(int[] i) {

            if (value == null)
                this.value = new ImmutableDatatype(i);
            else
                value.setValue(i);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param l The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(long[] l) {

            if (value == null)
                this.value = new ImmutableDatatype(l);
            else
                value.setValue(l);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param f The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(float[] f) {

            if (value == null)
                this.value = new ImmutableDatatype(f);
            else
                value.setValue(f);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param d The new value
         * @return The {@link Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Builder setValue(double[] d) {

            if (value == null)
                this.value = new ImmutableDatatype(d);
            else
                value.setValue(d);

            return this;
        }
    }

}
