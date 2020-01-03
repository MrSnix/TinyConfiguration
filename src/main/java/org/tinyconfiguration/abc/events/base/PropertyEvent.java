package org.tinyconfiguration.abc.events.base;

import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.events.ObservableEvent;

/**
 * The {@link PropertyEvent} is the foundation class provided to implement any event which affects properties
 *
 * @param <P> It should represent the object caller class
 * @author G. Baittiner
 * @version 0.1
 */
public class PropertyEvent<P extends AbstractProperty<?>> implements ObservableEvent {

    private final P instance;
    private final Type type;
    private boolean isConsumed;

    /**
     * Protected empty constructor
     */
    protected PropertyEvent() {
        this.instance = null;
        this.type = null;
    }

    /**
     * Constructor with parameters
     *
     * @param instance The property instance
     * @param type     The event type
     */
    public PropertyEvent(P instance, Type type) {
        this.instance = instance;
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
        return instance;
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

    /**
     * All the events available
     */
    public enum Type {
        ON_PROPERTY_UPDATE
    }

}
