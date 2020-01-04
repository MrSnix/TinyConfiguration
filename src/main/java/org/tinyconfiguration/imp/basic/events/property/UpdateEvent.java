package org.tinyconfiguration.imp.basic.events.property;

import org.tinyconfiguration.abc.data.ImmutableDatatype;
import org.tinyconfiguration.imp.basic.Property;
import org.tinyconfiguration.imp.basic.events.base.PropertyEvent;

public class UpdateEvent extends PropertyEvent {

    private ImmutableDatatype current;
    private ImmutableDatatype next;

    /**
     * Constructs a prototypical Event.
     *
     * @param property The property instance on which the Event initially occurred
     * @param current  The value hold currently by the property
     * @param next     The value which is going to replace the current one
     * @throws IllegalArgumentException if source is null
     */
    public UpdateEvent(Property property, ImmutableDatatype current, ImmutableDatatype next) {
        super(property);
        this.current = current;
        this.next = next;
    }

    @Override
    public Property getSource() {
        return (Property) super.getSource();
    }

    /**
     * Gets the current value
     *
     * @return The value hold currently by the property
     */
    public ImmutableDatatype getCurrent() {
        return current;
    }

    /**
     * Gets the next value
     *
     * @return The value which is going to replace the current one
     */
    public ImmutableDatatype getNext() {
        return next;
    }
}
