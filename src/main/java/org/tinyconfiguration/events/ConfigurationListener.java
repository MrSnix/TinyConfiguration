package org.tinyconfiguration.events;

import org.tinyconfiguration.base.Configuration;

import java.util.EventListener;

/**
 * This interface defines the custom function to execute when a specific event fires
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface ConfigurationListener extends EventListener {

    /**
     * Executes a custom function when a specific event fires
     *
     * @param configuration The configuration instance associated to the event
     */
    void execute(Configuration configuration);

    /**
     * All the events available
     */
    enum Type {
        ON_CONFIG_SAVE, ON_CONFIG_DELETE
    }

}
