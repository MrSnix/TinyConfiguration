package org.tinyconfiguration.abc.models;

import org.tinyconfiguration.abc.classes.Datatype;

/**
 * This class represent generic properties stored inside configuration instances
 *
 * @author G. Baittiner
 * @version 0.1
 */
public abstract class AbstractProperty {

    private final String key;
    private final String description;
    private final Datatype value;

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
    protected AbstractProperty(String key, Datatype value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    /**
     * Gets the key
     *
     * @return The key ({@link String}) associated to the property object.
     */
    public final String getKey() {
        return key;
    }

    /**
     * Gets the value
     *
     * @return The {@link Datatype} associated to the property object.
     */
    public final Datatype getValue() {
        return value;
    }

    /**
     * Gets the description
     *
     * @return The description ({@link String}) associated to the property object.
     */
    public final String getDescription() {
        return description;
    }

}
