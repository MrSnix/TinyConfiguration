package org.tinyconfiguration.abc.events.listeners;

import org.tinyconfiguration.abc.events.Event;
import org.tinyconfiguration.abc.events.EventType;

import java.util.List;

/**
 * This interface defines methods to setup generic event source
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface EventSource<T extends Event> {

    /**
     * Sets a new listener for any {@link EventType} value.
     *
     * @param type     The event type
     * @param listener The custom function to execute when the event will be fired
     * @return The boolean value representing the outcome on the inserting operation
     * @throws NullPointerException If the EventType is null or EventListener is null
     */
    void addListener(EventType<T> type, EventListener<? extends T> listener);

    /**
     * Remove listener for any {@link EventType} value.
     *
     * @param type     The event type
     * @param listener The custom function reference which was associated to the event
     * @return The boolean value representing the outcome on the removing operation
     * @throws NullPointerException If the EventType is null or EventListener is null
     */
    boolean removeListener(EventType<T> type, EventListener<? extends T> listener);

    /**
     * Returns listeners {@link List} for any {@link EventType} value.
     *
     * @param type The event type
     * @return The list holding functions references associated to the event
     */
    List<EventListener<? extends T>> getListeners(EventType<T> type);

    /**
     * Removes all listeners associated to the current event source
     */
    void resetListeners();

}
