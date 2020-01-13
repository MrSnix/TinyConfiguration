package org.tinyconfiguration.abc.events;

/**
 * The {@link Event} class is the foundation provided to build custom events on custom configurations
 */
public abstract class Event {

    public static final EventType<Event> ANY = new EventType<>(null, "EVENT.ANY");

    protected final Object source;
    private final EventType<? extends Event> type;

    private boolean isConsumed;

    private Event() {
        this.source = null;
        this.type = null;
    }

    /**
     * Constructs a new event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public Event(EventType<? extends Event> type, Object source) {

        if (source == null) {
            throw new IllegalArgumentException("The source cannot be null");
        }

        this.source = source;
        this.type = type;
    }

    /**
     * Returns the source from which the event was created by
     *
     * @return The event source
     */
    public abstract Object getSource();

    /**
     * Gets the type of this event
     *
     * @return The event type
     */
    public final EventType<? extends Event> getType() {
        return this.type;
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
