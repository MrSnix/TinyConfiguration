package org.tinyconfiguration.abc.events.listeners;

import org.tinyconfiguration.abc.events.Event;

/**
 * Generic event listener interface declaration
 *
 * @param <T> The event type
 */
public interface EventListener<T extends Event> {

    /**
     * Notifies this event listener about the new event
     *
     * @param event The upcoming event
     */
    void onEvent(T event);
}
