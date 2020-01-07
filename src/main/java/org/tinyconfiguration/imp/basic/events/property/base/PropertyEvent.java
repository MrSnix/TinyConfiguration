package org.tinyconfiguration.imp.basic.events.property.base;

import org.tinyconfiguration.abc.events.Event;
import org.tinyconfiguration.abc.events.EventType;
import org.tinyconfiguration.imp.basic.Property;

public class PropertyEvent extends Event {

    public static final EventType<PropertyEvent> ANY = new EventType<>(Event.ANY, "PROPERTY.ANY");

    public static final EventType<PropertyEvent> UPDATE = new EventType<>(PropertyEvent.ANY, "PROPERTY.UPDATE");

    /**
     * Constructs a prototypical Event.
     *
     * @param property The property instance on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PropertyEvent(Property property) {
        super(property);
    }

    @Override
    public Property getSource() {
        return (Property) super.getSource();
    }

}
