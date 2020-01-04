package org.tinyconfiguration.abc.events.base;

import org.tinyconfiguration.abc.events.Event;
import org.tinyconfiguration.abc.events.EventType;

public final class IOEvent extends Event {

    public static final EventType<IOEvent> ANY = new EventType<>(Event.ANY, "CONFIGURATION.IO.ANY");

    public static final EventType<IOEvent> WRITE = new EventType<>(IOEvent.ANY, "CONFIGURATION.IO.WRITE");
    public static final EventType<IOEvent> READ = new EventType<>(IOEvent.ANY, "CONFIGURATION.IO.READ");
    public static final EventType<IOEvent> DELETE = new EventType<>(IOEvent.ANY, "CONFIGURATION.IO.DELETE");

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public IOEvent(Object source) {
        super(source);
    }


}
