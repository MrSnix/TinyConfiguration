package org.tinyconfiguration.abc.common;

import org.tinyconfiguration.abc.AbstractDatatype;

public class Datatype extends AbstractDatatype {

    /**
     * Private empty constructor
     */
    private Datatype() {
        super();
    }

    /**
     * Private constructor with parameters
     *
     * @param value The data to store as object instance
     */
    private Datatype(Object value) {
        super(value);
    }

    /**
     * Sets any generic value on this property
     *
     * @param value The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    protected final void __setValue(Object value) {

        if (value == null) {
            throw new NullPointerException("The value cannot be null");
        }

        if (value.getClass() != value.getClass()) {
            throw new IllegalArgumentException("The value must be of the same class as the one declared");
        }

        this.value = new Datatype(value);
    }
}
