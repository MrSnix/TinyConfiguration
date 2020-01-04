package org.tinyconfiguration.abc.events;

/**
 * The {@link EventType} class is the foundation class used to describe any generic event type
 *
 * @param <T>
 */
public class EventType<T extends Event> {

    private final EventType<?> parent;
    private final String type;

    /**
     * Constructor with parameters
     *
     * @param parent The event parent
     * @param name   The event-type name
     */
    public EventType(EventType<? super T> parent, String name) {
        this.parent = parent;
        this.type = name;
    }

    /**
     * Gets the event super type
     *
     * @return The event parent (null - if root)
     */
    public EventType<?> getParent() {
        return parent;
    }

    /**
     * Gets the event type
     *
     * @return The event name
     */
    public String getName() {
        return type;
    }
}
