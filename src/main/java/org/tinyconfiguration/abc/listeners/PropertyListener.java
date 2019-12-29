package org.tinyconfiguration.abc.listeners;

import org.tinyconfiguration.abc.AbstractProperty;

import java.util.EventListener;

/**
 * This interface defines the custom function to execute when a specific event fires
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface PropertyListener<T extends AbstractProperty> extends EventListener {

    /**
     * Executes a custom function when a specific event fires
     *
     * @param property The property instance associated to the event
     */
    void onChange(T property);

    /**
     * All the events available
     */
    enum Type {
        ON_PROPERTY_UPDATE
    }

}
