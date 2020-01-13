package org.tinyconfiguration.abc;

import org.tinyconfiguration.abc.data.base.AbstractDatatype;

/**
 * This class represent generic properties stored inside configuration instances
 *
 * @author G. Baittiner
 * @version 0.1
 */
public abstract class AbstractProperty<D extends AbstractDatatype> {

    protected final D value;
    protected final String key;
    protected final String description;

    /**
     * Protected empty constructor
     */
    protected AbstractProperty() {
        this.key = null;
        this.value = null;
        this.description = null;
    }

    /**
     * Protected constructor with parameters
     */
    protected AbstractProperty(String key, D value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    /**
     * Gets the key
     *
     * @return The key ({@link String}) associated to the property object.
     */
    protected String getKey() {
        return key;
    }

    /**
     * Gets the value
     *
     * @return The {@link D} associated to the property object.
     */
    protected D getValue() {
        return value;
    }

    /**
     * Gets the description
     *
     * @return The description ({@link String}) associated to the property object.
     */
    protected String getDescription() {
        return description;
    }

}
