package org.tinyconfiguration.property;

import java.util.function.Predicate;

/**
 * This class represent the properties stored inside the configuration instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class PropertyDefinition {

    private String key;
    private PropertyValue value;
    private String description;
    private String placeholder;
    private boolean isOptional;
    private boolean isMissing;
    private Predicate<PropertyDefinition> isValid;

    public String getKey() {
        return key;
    }

    public PropertyValue getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public boolean isMissing() {
        return isMissing;
    }

    public boolean isValid() {
        return this.isValid.test(this);
    }

    public static PropertyDefinition copy(PropertyDefinition o) {

        PropertyDefinition p = new PropertyDefinition();

        p.key = o.key;
        p.value = PropertyValue.copy(o.value);
        p.description = o.description;
        p.placeholder = o.placeholder;
        p.isOptional = o.isOptional;
        p.isMissing = o.isMissing;
        p.isValid = o.isValid;

        return p;
    }

}