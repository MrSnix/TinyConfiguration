package org.tinyconfiguration.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class represent the properties stored inside the configuration instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class Property {

    private String key;
    private String value;
    private String description;

    /**
     * Private empty constructor
     */
    private Property() {
    }

    /**
     * Property constructor with parameters
     *
     * @param key   The key associated to the property
     * @param value The value associated to the property
     */
    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Property constructor with parameters
     *
     * @param key         The key associated to the property
     * @param value       The value associated to the property
     * @param description The description associated to the property
     */
    public Property(String key, String value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    static Property copy(Property o) {
        return (o == null) ? null : new Property(o.key, o.value, o.description);
    }

    static List<Property> copy(Collection<Property> o) {

        List<Property> list = null;

        if (o != null) {

            list = new ArrayList<>();

            for (Property property : o) {
                list.add(copy(property));
            }

        }

        return list;
    }

    /**
     * @return Gets the key
     */
    public String key() {
        return key;
    }

    /**
     * @return Gets the description
     */
    public String description() {
        return description;
    }

    /**
     * @return Gets the value as string
     */
    public String asString() {
        return value;
    }

    /**
     * @return Gets the value and parse it as integer
     */
    public int asInt() {
        return Integer.parseInt(value);
    }

    /**
     * @return Gets the value and parse it as long
     */
    public long asLong() {
        return Long.parseLong(value);
    }

    /**
     * @return Gets the value and parse it as float
     */
    public float asFloat() {
        return Float.parseFloat(value);
    }

    /**
     * @return Gets the value and parse it as double
     */
    public double asDouble() {
        return Double.parseDouble(value);
    }

    /**
     * @return Gets the value and parse it as boolean
     */
    public boolean asBoolean() {
        return Boolean.parseBoolean(value);
    }

}