package org.tinyconfiguration.data.base;

@SuppressWarnings("WeakerAccess")
public final class Value extends Datatype {

    Value(Object value) {
        super(value);
    }

    public String asString() {

        String s = null;

        if (type == String.class) {
            s = (String) value;
        } else if (type == Boolean.class) {
            s = String.valueOf(asBoolean());
        } else if (type == Byte.class) {
            s = String.valueOf(asByte());
        } else if (type == Short.class) {
            s = String.valueOf(asShort());
        } else if (type == Integer.class) {
            s = String.valueOf(asInt());
        } else if (type == Long.class) {
            s = String.valueOf(asLong());
        } else if (type == Float.class) {
            s = String.valueOf(asFloat());
        } else if (type == Double.class) {
            s = String.valueOf(asDouble());
        }

        return s;
    }

    public boolean asBoolean() {
        return (boolean) value;
    }

    public byte asByte() {
        return (byte) value;
    }

    public short asShort() {
        return (short) value;
    }

    public int asInt() {
        return (int) value;
    }

    public long asLong() {
        return (long) value;
    }

    public float asFloat() {
        return (float) value;
    }

    public double asDouble() {
        return (double) value;
    }
}
