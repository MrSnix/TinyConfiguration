package org.tinyconfiguration.abc.events.listeners;

import org.tinyconfiguration.abc.events.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class ExecutableListeners<T extends Event> {

    private List<EventListener<T>> listeners;

    public ExecutableListeners(List<EventListener<T>> listeners) {
        this.listeners = new ArrayList<>(listeners);
    }

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
