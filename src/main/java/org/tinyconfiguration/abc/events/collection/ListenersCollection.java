package org.tinyconfiguration.abc.events.collection;

import org.tinyconfiguration.abc.events.Event;
import org.tinyconfiguration.abc.events.EventType;
import org.tinyconfiguration.abc.events.listeners.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@link ListenersCollection} is a container used to handle any type of listeners
 *
 * @param <T> The event type
 */
public final class ListenersCollection<T extends Event> {

    private Map<EventType<T>, List<EventListener<T>>> listeners;

    public ListenersCollection() {
        this.listeners = new ConcurrentHashMap<>();
    }

    /**
     * Sets a new listener for any {@link EventType} value.
     *
     * @param type     The event type
     * @param listener The custom function to execute when the event will be fired
     * @throws NullPointerException If the EventType is null or EventListener is null
     */
    public void addListener(EventType<T> type, EventListener<T> listener) {

        if (type == null) {
            throw new NullPointerException("The event type cannot be null");
        }

        if (listener == null) {
            throw new NullPointerException("The event listener cannot be null");
        }

        List<EventListener<T>> list = this.listeners.get(type);

        if (list != null) {

            list.add(listener);

            this.listeners.put(type, list);
        } else {

            list = new ArrayList<>();

            list.add(listener);

            this.listeners.put(type, list);
        }
    }

    /**
     * Remove listener for any {@link EventType} value.
     *
     * @param type     The event type
     * @param listener The custom function reference which was associated to the event
     * @return The boolean value representing the outcome on the removing operation
     * @throws NullPointerException If the EventType is null or EventListener is null
     */
    public boolean removeListener(EventType<T> type, EventListener<? extends T> listener) {

        boolean result = false;

        if (type == null) {
            throw new NullPointerException("The event type cannot be null");
        }

        if (listener == null) {
            throw new NullPointerException("The event listener cannot be null");
        }

        List<EventListener<T>> list = this.listeners.get(type);

        if (list != null) {

            result = list.remove(listener);

            this.listeners.put(type, list);
        }

        return result;
    }

    /**
     * Returns {@link ExecutableCollection} for any {@link EventType} value.
     *
     * @param type The event type
     * @return The {@link ExecutableCollection} holding functions references associated to the event
     */
    public ExecutableCollection<T> getListeners(EventType<T> type) {

        if (type == null) {
            throw new NullPointerException("The event type cannot be null");
        }

        List<EventListener<T>> list = this.listeners.getOrDefault(type, new ArrayList<>());

        return new ExecutableCollection<>(list);
    }

    /**
     * Returns the number of listeners on a specific event type.
     *
     * @return the number of listeners on a specific event type.
     */
    public int size(EventType<T> type) {

        if (type == null) {
            throw new NullPointerException("The event type cannot be null");
        }

        return this.listeners.getOrDefault(type, new ArrayList<>()).size();
    }

    /**
     * Returns <tt>true</tt> if this list contains no listeners.
     *
     * @return <tt>true</tt> if this list contains no listeners
     */
    public boolean isEmpty(EventType<T> type) {

        if (type == null) {
            throw new NullPointerException("The event type cannot be null");
        }

        return this.listeners.getOrDefault(type, new ArrayList<>()).isEmpty();
    }

    /**
     * Removes all listeners on a specific type associated to the current event source
     *
     * @param type The event type
     */
    public void clear(EventType<T> type) {

        if (type == null) {
            throw new NullPointerException("The event type cannot be null");
        }

        this.listeners.put(type, new ArrayList<>());

    }

    /**
     * Removes all listeners associated to the current event source
     */
    public void clear() {
        this.listeners.clear();
    }

}