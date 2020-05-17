package org.tinyconfiguration.abc.data.base;

/**
 * The {@link AbstractValue} is the foundation to represent generic data "as-it-is"
 *
 * @author G. Baittiner
 * @version 0.1
 */
public abstract class AbstractValue {

    protected Object object;
    protected Class<?> type;

    /**
     * Protected empty constructor
     */
    protected AbstractValue() {
    }

    /**
     * Protected constructor with parameters
     *
     * @param object The data to store as object instance
     */
    protected AbstractValue(Object object) {
        this.object = object;
        this.type = object.getClass();
    }
}