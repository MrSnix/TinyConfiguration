package org.tinyconfiguration.abc.listeners;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.events.base.ConfigurationEvent;

import java.util.EventListener;

/**
 * This interface defines the custom function to execute when a specific event fires
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface ConfigurationListener<C extends AbstractConfiguration> extends EventListener {

    /**
     * Executes a custom function when a specific event fires
     *
     * @param event The event which was fired
     */
    void execute(ConfigurationEvent<C> event);

}
