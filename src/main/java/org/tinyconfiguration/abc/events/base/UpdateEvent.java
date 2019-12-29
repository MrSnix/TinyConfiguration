package org.tinyconfiguration.abc.events.base;

import org.tinyconfiguration.abc.AbstractDatatype;
import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.listeners.PropertyListener;

public class UpdateEvent<P extends AbstractProperty, T extends AbstractDatatype> extends PropertyEvent<P> {

    private T nextValue;
    private T currentValue;

    public UpdateEvent(P property, PropertyListener.Type type, T currentValue, T nextValue) {
        super(property, type);
        this.currentValue = currentValue;
        this.nextValue = nextValue;
    }

    /**
     * Gets the value which is going to replace the old one
     *
     * @return The {@link T} value associated with this event
     */
    public T getNewValue() {
        return currentValue;
    }

    /**
     * Gets the current property value
     *
     * @return The {@link T} value associated with this event
     */
    public T getCurrentValue() {
        return nextValue;
    }

}
