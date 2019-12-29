package org.tinyconfiguration.abc.events.base;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.events.ObservableEvent;
import org.tinyconfiguration.abc.listeners.ConfigurationListener;

import static org.tinyconfiguration.abc.listeners.ConfigurationListener.Type;

public class ConfigurationEvent<C extends AbstractConfiguration> implements ObservableEvent {

    private final C instance;
    private final Type type;
    private boolean isConsumed;

    public ConfigurationEvent(C instance, Type type) {
        this.instance = instance;
        this.type = type;
        this.isConsumed = false;
    }

    /**
     * Gets the configuration
     *
     * @return The {@link C} instance associated with this event
     */
    public C getInstance() {
        return instance;
    }

    /**
     * Gets the event type
     *
     * @return The {@link ConfigurationListener.Type} instance associated with this event
     */
    public Type getType() {
        return type;
    }

    /**
     * This method consumes event so that it will not be processed in the default manner by the source which originated it
     */
    @Override
    public void consume() {
        this.isConsumed = true;
    }

    /**
     * Gets the consumed status
     *
     * @return True if it has been consumed, otherwise false
     */
    public boolean isConsumed() {
        return isConsumed;
    }
}
