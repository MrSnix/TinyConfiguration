package org.tinyconfiguration.events;

import org.tinyconfiguration.data.Property;

import java.util.EventListener;

/**
 * This interface defines the custom function to execute when a specific event fires
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface PropertyListener extends EventListener {

    /**
     * Executes a custom function when a specific event fires
     *
     * @param property The property instance associated to the event
     */
    void onChange(Property property);

    /**
     * All the events available
     */
    enum Type {
        ON_PROPERTY_UPDATE
    }

}
