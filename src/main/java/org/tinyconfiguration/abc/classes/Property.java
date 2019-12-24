package org.tinyconfiguration.abc.classes;

import org.tinyconfiguration.abc.models.AbstractProperty;
import org.tinyconfiguration.abc.listeners.PropertyListener;

import java.util.List;
import java.util.function.Predicate;

/**
 * This class represent the properties stored inside the configuration instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class Property extends AbstractProperty {

    private final boolean isOptional;
    private final Predicate<Datatype> isValid;
    private final List<PropertyListener<Property>> listeners;

    /**
     * Private empty constructor
     */
    private Property() {
        this.isOptional = false;
        this.isValid = null;
        this.listeners = null;
    }

    /**
     * Private constructor with parameters
     */
    private Property(String key, Datatype value, String description, boolean isOptional, Predicate<Datatype> isValid, List<PropertyListener<Property>> listeners) {
        super(key, value, description);
        this.isOptional = isOptional;
        this.isValid = isValid;
        this.listeners = listeners;
    }
}
