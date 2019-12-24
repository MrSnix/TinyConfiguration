package org.tinyconfiguration.abc.listeners;

import org.tinyconfiguration.abc.models.AbstractConfiguration;

import java.util.EventListener;

/**
 * This interface defines the custom function to execute when a specific event fires
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface ConfigurationListener<T extends AbstractConfiguration> extends EventListener {

    /**
     * Executes a custom function when a specific event fires
     *
     * @param configuration The configuration instance associated to the event
     */
    void execute(T configuration);

    /**
     * All the events available
     */
    enum Type {
        ON_CONFIG_SAVE, ON_CONFIG_DELETE
    }

}
