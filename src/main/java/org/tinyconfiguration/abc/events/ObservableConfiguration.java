package org.tinyconfiguration.abc.events;

import org.tinyconfiguration.abc.models.AbstractConfiguration;
import org.tinyconfiguration.abc.listeners.ConfigurationListener;

/**
 * This interface defines methods to setup configuration events
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface ObservableConfiguration<T extends AbstractConfiguration> {

    /**
     * Sets a new listener for any {@link ConfigurationListener.Type} value.
     *
     * @param type     The event type
     * @param listener The custom function to execute when the event will be fired
     * @return The boolean value representing the outcome on the inserting operation
     */
     boolean addListener(ConfigurationListener.Type type, ConfigurationListener<T> listener);

    /**
     * Remove listener for any {@link ConfigurationListener.Type} value.
     *
     * @param type     The event type
     * @param listener The custom function reference which was associated to the event
     * @return The boolean value representing the outcome on the removing operation
     */
    boolean removeListener(ConfigurationListener.Type type, ConfigurationListener<T> listener);

    /**
     * Removes all listeners associated to the current configuration object
     */
    void resetListeners();

}
