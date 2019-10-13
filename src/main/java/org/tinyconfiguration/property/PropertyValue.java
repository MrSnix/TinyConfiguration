package org.tinyconfiguration.property;

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

}
