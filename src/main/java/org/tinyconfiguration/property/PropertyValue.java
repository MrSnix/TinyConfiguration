package org.tinyconfiguration.property;

import java.util.Objects;

public class PropertyValue {

    private String value;

    private PropertyValue() {
    }

    PropertyValue(String v) {
        this.value = v;
    }

    public String asString() {
        return value;
    }

    public boolean asBoolean() {
        return Boolean.parseBoolean(value);
    }

    public byte asByte() {
        return (byte) Integer.parseInt(value);
    }

    public short asShort() {
        return Short.parseShort(value);
    }

    public int asInt() {
        return Integer.parseInt(value);
    }

    public long asLong() {
        return Long.parseLong(value);
    }

    public float asFloat() {
        return Float.parseFloat(value);
    }

    public double asDouble() {
        return Double.parseDouble(value);
    }

    public static PropertyValue copy(PropertyValue o) {
        return new PropertyValue(o.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyValue that = (PropertyValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
