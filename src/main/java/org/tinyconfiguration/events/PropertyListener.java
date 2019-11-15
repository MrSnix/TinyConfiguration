package org.tinyconfiguration.events;

import org.tinyconfiguration.data.base.Datatype;

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
     * @param datatype The datatype instance associated to the event
     */
    void onChange(Datatype datatype);

    /**
     * All the events available
     */
    enum Type {
        ON_PROPERTY_UPDATE
    }

}
