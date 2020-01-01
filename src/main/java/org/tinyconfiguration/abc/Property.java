package org.tinyconfiguration.abc;

import org.tinyconfiguration.abc.events.ObservableProperty;
import org.tinyconfiguration.abc.events.base.UpdateEvent;
import org.tinyconfiguration.abc.listeners.PropertyListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.tinyconfiguration.abc.listeners.PropertyListener.Type;
import static org.tinyconfiguration.abc.listeners.PropertyListener.Type.ON_PROPERTY_UPDATE;

/**
 * This class represent the properties stored inside the configuration instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public class Property extends AbstractProperty implements ObservableProperty<Property> {

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

        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(s), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(s);

    }

    @Override
    public void setValue(boolean b) {

        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(b), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(b);

    }

    @Override
    public void setValue(char c) {

        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(c), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(c);

    }

    @Override
    public void setValue(byte b) {

        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(b), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(b);

    }

    @Override
    public void setValue(short s) {

        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(s), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(s);

    }

    @Override
    public void setValue(int i) {

        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(i), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(i);

    }

    @Override
    public void setValue(long l) {

        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(l), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(l);
    }

    @Override
    public void setValue(float f) {
        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(f), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(f);
    }

    @Override
    public void setValue(double d) {
        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(d), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(d);
    }

    @Override
    public void setValue(String[] s) {
        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(s), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(s);
    }

    @Override
    public void setValue(boolean[] b) {
        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(b), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(b);
    }

    @Override
    public void setValue(char[] c) {
        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(c), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(c);
    }

    @Override
    public void setValue(byte[] b) {
        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(b), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(b);
    }

    @Override
    public void setValue(short[] s) {
        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(s), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(s);
    }

    @Override
    public void setValue(int[] i) {
        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(i), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(i);
    }

    @Override
    public void setValue(long[] l) {
        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(l), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(l);
    }

    @Override
    public void setValue(float[] f) {
        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(f), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(f);
    }

    @Override
    public void setValue(double[] d) {
        UpdateEvent<Property> e = new UpdateEvent<>(this, ON_PROPERTY_UPDATE, new Datatype(d), value);

        this.listeners.forEach(listener -> {
            if (!e.isConsumed())
                listener.onChange(e);
        });

        if (!e.isConsumed())
            super.setValue(d);
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    /**
     * Insert new listeners on this property
     *
     * @param type The {@link Type} which specifies the listener type
     * @param l    The property listener to associate on this property object
     * @return The boolean value representing the outcome on the insert operation
     */
    @Override
    public boolean addListener(Type type, PropertyListener<Property> l) {
        boolean result = false;

        if (type == ON_PROPERTY_UPDATE)
            result = this.listeners.add(l);

        return result;
    }

    /**
     * Remove listeners from this property
     *
     * @param type The {@link Type} which specifies the listener type
     * @param l    The property listener to remove from this property object
     * @return The boolean value representing the outcome on the remove operation
     */
    @Override
    public boolean removeListener(Type type, PropertyListener<Property> l) {

        boolean result = false;

        if (type == ON_PROPERTY_UPDATE)
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
     * <p></p>
     * It returns <u>always</u> <b>true</b>, if no validation function was defined
     *
     * @return The boolean value representing the validity state
     */
    public boolean isValid() {

        boolean isValid = false;

        if (this.isValid == null)
            isValid = true;
        else
            isValid = this.isValid.test(this);

        return isValid;
    }

    /**
     * The {@link Property.Builder} class is used to build the {@link Property} object
     *
     * @author G. Baittiner
     * @version 0.1
     */
    public static final class Builder extends AbstractBuilder<Property> implements ObservableProperty<Property>, Mutable<Builder> {

        private String key;
        private Datatype value;
        private String description;
        private boolean isOptional;
        private Predicate<Property> isValid;
        private List<PropertyListener<Property>> listeners;

        private boolean isCleanable;

        /**
         * Empty builder constructor
         */
        public Builder() {
            this.key = null;
            this.value = null;
            this.description = null;
            this.isOptional = false;
            this.isValid = null;
            this.listeners = new ArrayList<>();
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
            this.listeners = new ArrayList<>();
            this.isCleanable = isCleanable;
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
        public boolean addListener(Type type, PropertyListener<Property> l) {
            boolean result = false;

            if (type == ON_PROPERTY_UPDATE)
                result = this.listeners.add(l);

            return result;
        }

        @Override
        public boolean removeListener(Type type, PropertyListener<Property> l) {
            boolean result = false;

            if (type == ON_PROPERTY_UPDATE)
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

            this.listeners = new ArrayList<>();

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

            Property e = new Property(key, value, description, isOptional, isValid, listeners);

            if (isCleanable)
                clear();

            return e;
        }

        /**
         * Sets the value on this property
         *
         * @param s The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(String s) {

            if (value == null)
                this.value = new Datatype(s);
            else
                value.setValue(s);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param b The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(boolean b) {

            if (value == null)
                this.value = new Datatype(b);
            else
                value.setValue(b);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param c The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(char c) {

            if (value == null)
                this.value = new Datatype(c);
            else
                value.setValue(c);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param b The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(byte b) {

            if (value == null)
                this.value = new Datatype(b);
            else
                value.setValue(b);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param s The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(short s) {

            if (value == null)
                this.value = new Datatype(s);
            else
                value.setValue(s);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param i The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(int i) {

            if (value == null)
                this.value = new Datatype(i);
            else
                value.setValue(i);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param l The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(long l) {

            if (value == null)
                this.value = new Datatype(l);
            else
                value.setValue(l);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param f The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(float f) {

            if (value == null)
                this.value = new Datatype(f);
            else
                value.setValue(f);

            return this;
        }

        /**
         * Sets the value on this property
         *
         * @param d The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(double d) {

            if (value == null)
                this.value = new Datatype(d);
            else
                value.setValue(d);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param s The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(String[] s) {

            if (value == null)
                this.value = new Datatype(s);
            else
                value.setValue(s);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param b The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(boolean[] b) {

            if (value == null)
                this.value = new Datatype(b);
            else
                value.setValue(b);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param c The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(char[] c) {

            if (value == null)
                this.value = new Datatype(c);
            else
                value.setValue(c);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param b The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(byte[] b) {

            if (value == null)
                this.value = new Datatype(b);
            else
                value.setValue(b);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param s The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(short[] s) {

            if (value == null)
                this.value = new Datatype(s);
            else
                value.setValue(s);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param i The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(int[] i) {

            if (value == null)
                this.value = new Datatype(i);
            else
                value.setValue(i);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param l The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(long[] l) {

            if (value == null)
                this.value = new Datatype(l);
            else
                value.setValue(l);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param f The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(float[] f) {

            if (value == null)
                this.value = new Datatype(f);
            else
                value.setValue(f);

            return this;
        }

        /**
         * Sets the array value on this property
         *
         * @param d The new value
         * @return The {@link Property.Builder} current instance
         * @throws NullPointerException     If the value is null
         * @throws IllegalArgumentException If the class type is different from the one declared
         */
        @Override
        public Property.Builder setValue(double[] d) {

            if (value == null)
                this.value = new Datatype(d);
            else
                value.setValue(d);

            return this;
        }
    }

}
