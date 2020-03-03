package org.tinyconfiguration.imp.basic;

import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.builders.AbstractBuilder;
import org.tinyconfiguration.abc.builders.Mutable;
import org.tinyconfiguration.abc.data.Value;

import java.util.function.Predicate;

/**
 * This class represent the properties stored inside the configuration instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class Property extends AbstractProperty {

    private final boolean isOptional;
    private final Predicate<Property> isValid;

    /**
     * Private empty constructor
     */
    private Property() {
        this.isOptional = false;
        this.isValid = null;
    }

    /**
     * Private constructor with parameters
     */
    private Property(String key, Value value, String description, boolean isOptional, Predicate<Property> isValid) {
        super(key, value, description);
        this.isOptional = isOptional;
        this.isValid = isValid;
    }

    /**
     * Sets any generic value on this property
     *
     * @param value The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    protected final void set(Object value) {

        if (value == null) {
            throw new NullPointerException("The value cannot be null");
        }

        if (this.value.getType() != value.getClass()) {
            throw new IllegalArgumentException("The value must be of the same class as the one declared");
        }

        this.value = new Value(value);
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

        private final boolean isCleanable;
        private String key;
        private Value value;
        private String description;
        private boolean isOptional;
        private Predicate<Property> isValid;

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
            this.value = new Value(s);
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
            this.value = new Value(b);
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
            this.value = new Value(c);
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
            this.value = new Value(b);
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
            this.value = new Value(s);
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
            this.value = new Value(i);
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
            this.value = new Value(l);
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
            this.value = new Value(f);
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
            this.value = new Value(d);
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
            this.value = new Value(s);
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
            this.value = new Value(b);
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
            this.value = new Value(c);
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
            this.value = new Value(b);
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
            this.value = new Value(s);
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
            this.value = new Value(i);
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
            this.value = new Value(l);
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
            this.value = new Value(f);
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
            this.value = new Value(d);
            return this;
        }
    }

}
