package org.tinyconfiguration.abc.events.base;

import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.Datatype;
import org.tinyconfiguration.abc.listeners.PropertyListener;

/**
 * The {@link UpdateEvent} is the foundation class provided to implement any event which update properties value
 *
 * @param <P> It should represent the property object type associated with the event
 * @author G. Baittiner
 * @version 0.1
 */
public class UpdateEvent<P extends AbstractProperty> extends PropertyEvent<P> {

    private final Datatype next;
    private final Datatype current;

    /**
     * Protected empty constructor
     */
    protected UpdateEvent() {
        super();
        this.next = null;
        this.current = null;
    }

    /**
     * Constructor with parameters
     *
     * @param property The property instance associated with the event
     * @param type     The event type
     * @param current  The current value hold by the property
     * @param next     The new value which is going to replace the current one
     */
    public UpdateEvent(P property, PropertyListener.Type type, Datatype current, Datatype next) {
        super(property, type);
        this.current = current;
        this.next = next;
    }

    /**
     * Gets the value which is going to replace the current one
     *
     * @return The {@link Datatype} value associated with this event
     */
    public Datatype getNextValue() {
        return next;
    }

    /**
     * Gets the current property value
     *
     * @return The {@link Datatype} value associated with this event
     */
    public Datatype getCurrentValue() {
        return current;
    }

}
