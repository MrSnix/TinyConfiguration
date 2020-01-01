package org.tinyconfiguration.abc.events;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.listeners.ConfigurationListener;

import java.util.List;

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
     * Returns {@link List} of listeners for any {@link ConfigurationListener.Type} value.
     *
     * @param type The event type
     * @return The list holding functions references associated to the event
     */
    List<ConfigurationListener<T>> getListeners(ConfigurationListener.Type type);

    /**
     * Removes all listeners associated to the current configuration object
     */
    void resetListeners();

}
