package org.tinyconfiguration.data.base;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public final class Array extends Datatype {

    Array(Object value) {
        super(value);
    }

    public String asString() {

        String s = null;

        if (type == String[].class) {
            s = Arrays.toString(asStringArray());
        } else if (type == boolean[].class) {
            s = Arrays.toString(asBooleanArray());
        } else if (type == byte[].class) {
            s = Arrays.toString(asByteArray());
        } else if (type == short[].class) {
            s = Arrays.toString(asShortArray());
        } else if (type == int[].class) {
            s = Arrays.toString(asIntArray());
        } else if (type == long[].class) {
            s = Arrays.toString(asLongArray());
        } else if (type == float[].class) {
            s = Arrays.toString(asFloatArray());
        } else if (type == double[].class) {
            s = Arrays.toString(asDoubleArray());
        }

        return s;
    }

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

}
