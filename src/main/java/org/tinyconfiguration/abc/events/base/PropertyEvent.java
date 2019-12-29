package org.tinyconfiguration.abc.events.base;

import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.events.ObservableEvent;

import static org.tinyconfiguration.abc.listeners.PropertyListener.Type;

public class PropertyEvent<P extends AbstractProperty> implements ObservableEvent {

    private final P property;
    private final Type type;
    private boolean isConsumed;

    public PropertyEvent(P property, Type type) {
        this.property = property;
        this.type = type;
        this.isConsumed = false;
    }

    /**
     * This method consumes event so that it will not be processed in the default manner by the source which originated it
     */
    @Override
    public void consume() {
        this.isConsumed = true;
    }

    /**
     * Gets the property
     *
     * @return The {@link P} instance associated with this event
     */
    public P getProperty() {
        return property;
    }

    /**
     * Gets the event type
     *
     * @return The {@link Type} instance associated with this event
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets the consumed status
     *
     * @return True if it has been consumed, otherwise false
     */
    public boolean isConsumed() {
        return isConsumed;
    }
}
