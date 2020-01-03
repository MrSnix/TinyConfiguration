package org.tinyconfiguration.abc.events.base;

import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.data.base.AbstractDatatype;

/**
 * The {@link UpdateEvent} is the foundation class provided to implement any event which update properties value
 *
 * @param <P> It should represent the property object type associated with the event
 * @author G. Baittiner
 * @version 0.1
 */
public class UpdateEvent<P extends AbstractProperty<D>, D extends AbstractDatatype> extends PropertyEvent<P> {

    private final D next;
    private final D current;

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
     * @param current  The current value hold by the property
     * @param next     The new value which is going to replace the current one
     */
    public UpdateEvent(P property, D current, D next) {
        super(property, Type.ON_PROPERTY_UPDATE);
        this.current = current;
        this.next = next;
    }

    /**
     * Gets the value which is going to replace the current one
     *
     * @return The {@link D} value associated with this event
     */
    public D getNextValue() {
        return next;
    }

    /**
     * Gets the current property value
     *
     * @return The {@link D} value associated with this event
     */
    public D getCurrentValue() {
        return current;
    }

}
