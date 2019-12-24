package org.tinyconfiguration.abc.models;

import static java.lang.String.valueOf;

/**
 * The {@link AbstractDatatype} is the container class which resolve any {@link AbstractValue} cast
 *
 * @author G. Baittiner
 * @version 0.1
 */
public abstract class AbstractDatatype extends AbstractValue {

    /**
     * Protected empty constructor
     */
    protected AbstractDatatype() {
        super();
    }

    /**
     * Protected constructor with parameters
     *
     * @param value The data to store as object instance
     */
    protected AbstractDatatype(Object value) {
        super(value);
    }

    /**
     * Check if the datatype object is an array type
     *
     * @return true if it's an array otherwise false
     */
    public final boolean isArray() {
        return this.type.isArray();
    }

    /**
     * Check if the datatype object is a byte datatype
     * <p></p>
     * <p>More specifically it looks on the following types:
     *      <ul>
     *         <li>{@code byte}</li>
     *          <li>{@code byte[]}</li>
     *          <li>{@code Byte}</li>
     *          <li>{@code Byte[]}</li>
     *      </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see AbstractDatatype#isArray()
     */
    public final boolean isByte() {
        return
                byte.class.isAssignableFrom(this.type) ||
                byte[].class.isAssignableFrom(this.type) ||
                Byte.class.isAssignableFrom(this.type) ||
                Byte[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a short datatype
     * <p></p>
     * <p>More specifically it looks on the following types:
     *      <ul>
     *         <li>{@code short}</li>
     *          <li>{@code short[]}</li>
     *          <li>{@code Short}</li>
     *          <li>{@code Short[]}</li>
     *      </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see AbstractDatatype#isArray()
     */
    public final boolean isShort() {
        return
                short.class.isAssignableFrom(this.type) ||
                short[].class.isAssignableFrom(this.type) ||
                Short.class.isAssignableFrom(this.type) ||
                Short[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is an integer datatype
     * <p></p>
     * <p>More specifically it looks on the following types:
     *      <ul>
     *         <li>{@code int}</li>
     *          <li>{@code int[]}</li>
     *          <li>{@code Integer}</li>
     *          <li>{@code Integer[]}</li>
     *      </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see AbstractDatatype#isArray()
     */
    public final boolean isInteger() {
        return
                int.class.isAssignableFrom(this.type) ||
                int[].class.isAssignableFrom(this.type) ||
                Integer.class.isAssignableFrom(this.type) ||
                Integer[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a long datatype
     * <p></p>
     * <p>More specifically it looks on the following types:
     *      <ul>
     *         <li>{@code long}</li>
     *          <li>{@code long[]}</li>
     *          <li>{@code Long}</li>
     *          <li>{@code Long[]}</li>
     *      </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see AbstractDatatype#isArray()
     */
    public final boolean isLong() {
        return
                long.class.isAssignableFrom(this.type) ||
                long[].class.isAssignableFrom(this.type) ||
                Long.class.isAssignableFrom(this.type) ||
                Long[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a float datatype
     * <p></p>
     * <p>More specifically it looks on the following types:
     *      <ul>
     *         <li>{@code float}</li>
     *          <li>{@code float[]}</li>
     *          <li>{@code Float}</li>
     *          <li>{@code Float[]}</li>
     *      </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see AbstractDatatype#isArray()
     */
    public final boolean isFloat() {
        return
                float.class.isAssignableFrom(this.type) ||
                float[].class.isAssignableFrom(this.type) ||
                Float.class.isAssignableFrom(this.type) ||
                Float[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a double datatype
     * <p></p>
     * <p>More specifically it looks on the following types:
     *      <ul>
     *         <li>{@code double}</li>
     *          <li>{@code double[]}</li>
     *          <li>{@code Double}</li>
     *          <li>{@code Double[]}</li>
     *      </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see AbstractDatatype#isArray()
     */
    public final boolean isDouble() {
        return
                double.class.isAssignableFrom(this.type) ||
                double[].class.isAssignableFrom(this.type) ||
                Double.class.isAssignableFrom(this.type) ||
                Double[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a boolean datatype
     * <p></p>
     * <p>More specifically it looks on the following types:
     *      <ul>
     *         <li>{@code boolean}</li>
     *          <li>{@code boolean[]}</li>
     *          <li>{@code Boolean}</li>
     *          <li>{@code Boolean[]}</li>
     *      </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see AbstractDatatype#isArray()
     */
    public final boolean isBoolean() {
        return
                boolean.class.isAssignableFrom(this.type) ||
                boolean[].class.isAssignableFrom(this.type) ||
                Boolean.class.isAssignableFrom(this.type) ||
                Boolean[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a character datatype
     * <p></p>
     * <p>More specifically it looks on the following types:
     *      <ul>
     *         <li>{@code character}</li>
     *          <li>{@code character[]}</li>
     *          <li>{@code Character}</li>
     *          <li>{@code Character[]}</li>
     *      </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see AbstractDatatype#isArray()
     */
    public final boolean isCharacter() {
        return char.class.isAssignableFrom(this.type) || char[].class.isAssignableFrom(this.type) ||
        Character.class.isAssignableFrom(this.type) || Character[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a string datatype
     * <p></p>
     * <p>More specifically it looks on the following types:
     *      <ul>
     *         <li>{@code String}</li>
     *          <li>{@code String[]}</li>
     *          <li>{@code CharSequence}</li>
     *          <li>{@code CharSequence[]}</li>
     *      </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see AbstractDatatype#isArray()
     */
    public final  boolean isString() {
        return String.class.isAssignableFrom(this.type) || String[].class.isAssignableFrom(this.type) || this.value instanceof CharSequence || this.value instanceof CharSequence[];
    }

    /**
     * Check if the datatype object is a numeric datatype
     * <p></p>
     * More it performs the following tests:
     * <ul>
     *    <li>{@link AbstractDatatype#isByte()} </li>
     *    <li>{@link AbstractDatatype#isShort()}</li>
     *    <li>{@link AbstractDatatype#isInteger()}</li>
     *    <li>{@link AbstractDatatype#isLong()}</li>
     *    <li>{@link AbstractDatatype#isFloat()}</li>
     *    <li>{@link AbstractDatatype#isDouble()}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see AbstractDatatype#isArray()
     */
    public final boolean isNumeric() {
        return isByte() || isShort() || isInteger() || isLong() || isFloat() || isDouble();
    }

    /**
     * Check if the datatype object is a textual datatype
     * <p></p>
     * More it performs the following tests:
     * <ul>
     *    <li>{@link AbstractDatatype#isCharacter()} </li>
     *    <li>{@link AbstractDatatype#isString()} </li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see AbstractDatatype#isArray()
     */
    public final boolean isText() {
        return isCharacter() || isString();
    }

    /**
     * Returns the current value
     *
     * @return The {@link String} value contained by the property
     */
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

    /**
     * Returns the current value
     *
     * @return The boolean value contained by the property
     */
    public boolean asBoolean() {
        return (boolean) value;
    }

    /**
     * Returns the current value
     *
     * @return The byte value contained by the property
     */
    public byte asByte() {
        return (byte) value;
    }

    /**
     * Returns the current value
     *
     * @return The short value contained by the property
     */
    public short asShort() {
        return (short) value;
    }

    /**
     * Returns the current value
     *
     * @return The integer value contained by the property
     */
    public int asInt() {
        return (int) value;
    }

    /**
     * Returns the current value
     *
     * @return The long value contained by the property
     */
    public long asLong() {
        return (long) value;
    }

    /**
     * Returns the current value
     *
     * @return The float value contained by the property
     */
    public float asFloat() {
        return (float) value;
    }

    /**
     * Returns the current value
     *
     * @return The double value contained by the property
     */
    public double asDouble() {
        return (double) value;
    }

    /**
     * Returns the current array value
     *
     * @return The {@link String} array contained by the property
     */
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

    /**
     * Returns the current array value
     *
     * @return The boolean array contained by the property
     */
    public boolean[] asBooleanArray() {
        return (boolean[]) value;
    }

    /**
     * Returns the current array value
     *
     * @return The byte array contained by the property
     */
    public byte[] asByteArray() {
        return (byte[]) value;
    }

    /**
     * Returns the current array value
     *
     * @return The short array contained by the property
     */
    public short[] asShortArray() {
        return (short[]) value;
    }

    /**
     * Returns the current array value
     *
     * @return The integer array contained by the property
     */
    public int[] asIntArray() {
        return (int[]) value;
    }

    /**
     * Returns the current array value
     *
     * @return The long array contained by the property
     */
    public long[] asLongArray() {
        return (long[]) value;
    }

    /**
     * Returns the current array value
     *
     * @return The float array contained by the property
     */
    public float[] asFloatArray() {
        return (float[]) value;
    }

    /**
     * Returns the current array value
     *
     * @return The double array contained by the property
     */
    public double[] asDoubleArray() {
        return (double[]) value;
    }

}
