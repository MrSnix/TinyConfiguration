package org.tinyconfiguration.abc.events;

import java.util.EventObject;

/**
 * The {@link Event} class is the foundation provided to build custom event types on custom configurations
 */
public class Event extends EventObject {

    // The root node at head of event hierarchy
    public static final EventType<Event> ANY = new EventType<>(null, "EVENT.ANY");

    private boolean isConsumed;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public Event(Object source) {
        super(source);
    }

    /**
     * Gets the type of this event
     *
     * @return The event type
     */
    public EventType<? extends Event> getEventType() {
        return Event.ANY;
    }

    /**
     * Gets the event state.
     *
     * @return True or false
     */
    public final boolean isConsumed() {
        return isConsumed;
    }

    /**
     * Consume the event
     */
    public final void consume() {
        this.isConsumed = true;
    }
}
