package org.tinyconfiguration.abc.events;

import org.tinyconfiguration.abc.AbstractProperty;
import org.tinyconfiguration.abc.events.base.PropertyEvent;
import org.tinyconfiguration.abc.listeners.PropertyListener;

/**
 * This interface defines methods to setup property events
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface ObservableProperty<P extends AbstractProperty<?>> {

    /**
     * Insert new listeners on this property
     *
     * @param type The {@link PropertyEvent.Type} which specifies the listener type
     * @param l    The property listener to associate on this property object
     * @return The boolean value representing the outcome on the insert operation
     */
    boolean addListener(PropertyEvent.Type type, PropertyListener<P> l);

    /**
     * Remove listeners from this property
     *
     * @param type The {@link PropertyEvent.Type} which specifies the listener type
     * @param l    The property listener to remove from this property object
     * @return The boolean value representing the outcome on the remove operation
     */
    boolean removeListener(PropertyEvent.Type type, PropertyListener<P> l);

    /**
     * Removes all listeners associated to the current property object
     */
    void resetListeners();

}
