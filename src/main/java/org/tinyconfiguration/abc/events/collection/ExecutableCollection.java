package org.tinyconfiguration.abc.events.collection;

import org.tinyconfiguration.abc.events.Event;
import org.tinyconfiguration.abc.events.listeners.EventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link ExecutableCollection} class is mainly used to expose action-only methods on {@link ListenersCollection}
 *
 * @param <T> The event type
 */
public class ExecutableCollection<T extends Event> {

    private final List<EventListener<T>> listeners;

    public ExecutableCollection(List<EventListener<T>> listeners) {
        this.listeners = new ArrayList<>(listeners);
    }

    /**
     * Fires all the {@link EventListener}
     * @param event The event object associated with this instance
     */
    public final void execute(T event) {

        if (event == null) {
            throw new NullPointerException("The event cannot be null");
        }

        this.listeners.forEach(listener -> {
            if (!event.isConsumed())
                listener.onEvent(event);
        });
    }
}
