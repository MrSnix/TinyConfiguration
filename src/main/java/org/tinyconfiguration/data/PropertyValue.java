package org.tinyconfiguration.data;


import java.util.Objects;

import static java.lang.String.valueOf;

/**
 * This class represent the data stored inside the property instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
@SuppressWarnings("WeakerAccess")
public final class PropertyValue {

    private final Object value;
    private final Class<?> type;

    /**
     * Creates a new datatype instance.
     *
     * @param value The data to store as object instance
     */
    PropertyValue(Object value) {

        if (value instanceof PropertyValue) {
            PropertyValue o = (PropertyValue) value;
            this.type = o.type;
            this.value = o.value;
        } else {
            this.value = value;
            this.type = value.getClass();
        }
    }

    /**
     * Check if the datatype object is an array value
     *
     * @return true if it's an array otherwise false
     */
    public final boolean isArray() {
        return this.type.isArray();
    }
    public boolean isShort() {
        return
                short.class.isAssignableFrom(this.type) ||
                        short[].class.isAssignableFrom(this.type) ||
                        Short.class.isAssignableFrom(this.type) ||
                        Short[].class.isAssignableFrom(this.type);
    }
    public boolean isByte() {
        return
                byte.class.isAssignableFrom(this.type) ||
                        byte[].class.isAssignableFrom(this.type) ||
                        Byte.class.isAssignableFrom(this.type) ||
                        Byte[].class.isAssignableFrom(this.type);
    }
    public boolean isInteger() {
        return
                int.class.isAssignableFrom(this.type) ||
                        int[].class.isAssignableFrom(this.type) ||
                        Integer.class.isAssignableFrom(this.type) ||
                        Integer[].class.isAssignableFrom(this.type);
    }
    public boolean isLong() {
        return
                long.class.isAssignableFrom(this.type) ||
                        long[].class.isAssignableFrom(this.type) ||
                        Long.class.isAssignableFrom(this.type) ||
                        Long[].class.isAssignableFrom(this.type);
    }
    public boolean isFloat() {
        return
                float.class.isAssignableFrom(this.type) ||
                        float[].class.isAssignableFrom(this.type) ||
                        Float.class.isAssignableFrom(this.type) ||
                        Float[].class.isAssignableFrom(this.type);
    }
    public boolean isDouble() {
        return
                double.class.isAssignableFrom(this.type) ||
                        double[].class.isAssignableFrom(this.type) ||
                        Double.class.isAssignableFrom(this.type) ||
                        Double[].class.isAssignableFrom(this.type);
    }
    public boolean isNumeric() {
        return isByte() || isShort() || isInteger() || isLong() || isFloat() || isDouble();
    }
    public boolean isText() {
        return
                String.class.isAssignableFrom(this.type) || String[].class.isAssignableFrom(this.type) ||
                        char.class.isAssignableFrom(this.type) || char[].class.isAssignableFrom(this.type) ||
                        this.value instanceof CharSequence || this.value instanceof CharSequence[];
    }
    public boolean isBoolean() {
        return
                boolean.class.isAssignableFrom(this.type) ||
                        boolean[].class.isAssignableFrom(this.type) ||
                        Boolean.class.isAssignableFrom(this.type) ||
                        Boolean[].class.isAssignableFrom(this.type);
    }

    // --------------- << NATIVE >> ---------------
    public String asString() {

        String s = null;

        if (type == String.class) s = (String) value;
        else if (type == Boolean.class) s = valueOf(asBoolean());
        else if (type == Byte.class) s = valueOf(asByte());
        else if (type == Short.class) s = valueOf(asShort());
        else if (type == Integer.class) s = valueOf(asInt());
        else if (type == Long.class) s = valueOf(asLong());
        else if (type == Float.class) s = valueOf(asFloat());
        else if (type == Double.class) s = valueOf(asDouble());

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

    // --------------- << ARRAYS >> ---------------
    public String[] asStringArray() {

        String[] s = null;

        if (type == String[].class) {

            s = (String[]) value;

        } else if (type == boolean[].class) {

            boolean[] tmp = asBooleanArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Boolean.toString(tmp[i]);
            }

        } else if (type == byte[].class) {

            byte[] tmp = asByteArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Byte.toString(tmp[i]);
            }

        } else if (type == short[].class) {

            short[] tmp = asShortArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Short.toString(tmp[i]);
            }

        } else if (type == int[].class) {

            int[] tmp = asIntArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Integer.toString(tmp[i]);
            }

        } else if (type == long[].class) {

            long[] tmp = asLongArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Long.toString(tmp[i]);
            }

        } else if (type == float[].class) {

            float[] tmp = asFloatArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Float.toString(tmp[i]);
            }

        } else if (type == double[].class) {

            double[] tmp = asDoubleArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Double.toString(tmp[i]);
            }

        }

        return s;

    }
    public boolean[] asBooleanArray() {
        return (boolean[]) value;
    }
    public byte[] asByteArray() {
        return (byte[]) value;
    }
    public short[] asShortArray() {
        return (short[]) value;
    }
    public int[] asIntArray() {
        return (int[]) value;
    }
    public long[] asLongArray() {
        return (long[]) value;
    }
    public float[] asFloatArray() {
        return (float[]) value;
    }
    public double[] asDoubleArray() {
        return (double[]) value;
    }

    // --------------- << CLASS >> ---------------
    public Class<?> getType() {
        return type;
    }
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyValue that = (PropertyValue) o;
        return Objects.equals(value, that.value) && Objects.equals(type, that.type);
    }
    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}