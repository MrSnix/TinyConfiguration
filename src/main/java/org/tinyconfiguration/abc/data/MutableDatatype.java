package org.tinyconfiguration.abc.data;

import org.tinyconfiguration.abc.data.base.AbstractDatatype;

/**
 * The {@link MutableDatatype} class should be the foundation class to implement mutable custom datatype
 *
 * @author G. Baittiner
 * @version 0.1
 */
public class MutableDatatype extends AbstractDatatype {

    /**
     * Private empty constructor
     */
    private MutableDatatype() {
        super();
    }

    /**
     * Public constructor with parameters
     *
     * @param value The data to store as object instance
     */
    public MutableDatatype(Object value) {
        super(value);
    }

    /**
     * Sets any generic value on this property
     *
     * @param value The new value
     * @throws NullPointerException If the value is null
     */
    @Override
    protected final void __setValue(Object value) {

        if (value == null) {
            throw new NullPointerException("The value cannot be null");
        }

        this.value = value;
    }

}
