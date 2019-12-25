package org.tinyconfiguration.abc.events;

import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.listeners.PropertyListener;

/**
 * This interface defines methods to setup property events
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface ObservableProperty<T extends AbstractProperty> {

    /**
     * Insert new listeners on this property
     *
     * @param type The {@link PropertyListener.Type} which specifies the listener type
     * @param l    The property listener to associate on this property object
     * @return The boolean value representing the outcome on the insert operation
     */
    boolean addListener(PropertyListener.Type type, PropertyListener<T> l);

    /**
     * Remove listeners from this property
     *
     * @param type The {@link PropertyListener.Type} which specifies the listener type
     * @param l    The property listener to remove from this property object
     * @return The boolean value representing the outcome on the remove operation
     */
    boolean removeListener(PropertyListener.Type type, PropertyListener<T> l);

    /**
     * Removes all listeners associated to the current property object
     */
    void resetListeners();

}
