package org.tinyconfiguration.abc.listeners;

import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.events.base.PropertyEvent;

import java.util.EventListener;

/**
 * This interface defines the custom function to execute when a specific event fires
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface PropertyListener<P extends AbstractProperty> extends EventListener {

    /**
     * Executes a custom function when a specific event fires
     *
     * @param event The event which was fired
     */
    void onChange(PropertyEvent<P> event);

    /**
     * All the events available
     */
    enum Type {
        ON_PROPERTY_UPDATE
    }

}
