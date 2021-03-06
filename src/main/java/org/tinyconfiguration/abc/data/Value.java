package org.tinyconfiguration.abc.data;

import org.tinyconfiguration.abc.data.base.AbstractValue;

import java.util.Arrays;

import static java.lang.String.valueOf;
import static org.tinyconfiguration.abc.data.Datatype.*;

/**
 * The {@link Value} is the container class which resolve any {@link AbstractValue} cast.<br>
 *
 * @author G. Baittiner
 * @version 0.1
 */
public class Value extends AbstractValue {

    private Datatype datatype;

    /**
     * Protected empty constructor
     */
    protected Value() {
        super();
    }

    /**
     * Constructor with parameters
     *
     * @param value The data to store as object instance
     */
    public Value(Object value) {
        super(value);
        evaluate();
    }

    /**
     * Returns the object class
     *
     * @return The object class
     */
    public Class<?> getType() {
        return super.type;
    }

    /**
     * Check if the datatype object is an array type
     *
     * @return true if it's an array otherwise false
     */
    public boolean isArray() {
        return this.type.isArray();
    }

    /**
     * Check if the datatype object is a byte datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *    <li>{@code byte}</li>
     *     <li>{@code Byte}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     */
    public boolean isByte() {
        return byte.class.isAssignableFrom(this.type) || Byte.class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a byte array datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *     <li>{@code byte[]}</li>
     *     <li>{@code Byte[]}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     * @see Value#isByte()
     */
    public boolean isByteArray() {
        return byte[].class.isAssignableFrom(this.type) ||
                Byte[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a short datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *    <li>{@code short}</li>
     *     <li>{@code Short}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     */
    public boolean isShort() {
        return
                short.class.isAssignableFrom(this.type) ||
                        Short.class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a short array datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *     <li>{@code short[]}</li>
     *     <li>{@code Short[]}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     * @see Value#isShort()
     */
    public boolean isShortArray() {
        return short[].class.isAssignableFrom(this.type) || Short[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is an integer datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *    <li>{@code int}</li>
     *     <li>{@code Integer}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     */
    public boolean isInteger() {
        return int.class.isAssignableFrom(this.type) || Integer.class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a int array datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *     <li>{@code int[]}</li>
     *     <li>{@code Integer[]}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     * @see Value#isInteger()
     */
    public boolean isIntegerArray() {
        return int[].class.isAssignableFrom(this.type) || Integer[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a long datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *    <li>{@code long}</li>
     *     <li>{@code Long}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     */
    public boolean isLong() {
        return long.class.isAssignableFrom(this.type) || Long.class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a long array datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *     <li>{@code long[]}</li>
     *     <li>{@code Long[]}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     * @see Value#isLong()
     */
    public boolean isLongArray() {
        return long[].class.isAssignableFrom(this.type) || Long[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a float datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *    <li>{@code float}</li>
     *     <li>{@code Float}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     */
    public boolean isFloat() {
        return float.class.isAssignableFrom(this.type) || Float.class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a float array datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *     <li>{@code float[]}</li>
     *     <li>{@code Float[]}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     * @see Value#isFloat()
     */
    public boolean isFloatArray() {
        return float[].class.isAssignableFrom(this.type) || Float[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a double datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *    <li>{@code double}</li>
     *     <li>{@code Double}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     */
    public boolean isDouble() {
        return double.class.isAssignableFrom(this.type) || Double.class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a double array datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *     <li>{@code double[]}</li>
     *     <li>{@code Double[]}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     * @see Value#isDouble()
     */
    public boolean isDoubleArray() {
        return double[].class.isAssignableFrom(this.type) || Double[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a boolean datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *    <li>{@code boolean}</li>
     *     <li>{@code Boolean}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     */
    public boolean isBoolean() {
        return boolean.class.isAssignableFrom(this.type) || Boolean.class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a boolean array datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *     <li>{@code boolean[]}</li>
     *     <li>{@code Boolean[]}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     * @see Value#isBoolean()
     */
    public boolean isBooleanArray() {
        return boolean[].class.isAssignableFrom(this.type) || Boolean[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a character datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *    <li>{@code character}</li>
     *     <li>{@code Character}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     */
    public boolean isCharacter() {
        return char.class.isAssignableFrom(this.type) || Character.class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a character array datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *     <li>{@code char[]}</li>
     *     <li>{@code Character[]}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     * @see Value#isCharacter()
     */
    public boolean isCharacterArray() {
        return char[].class.isAssignableFrom(this.type) || Character[].class.isAssignableFrom(this.type);
    }

    /**
     * Check if the datatype object is a string datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *    <li>{@code String}</li>
     *     <li>{@code CharSequence}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     */
    public boolean isString() {
        return String.class.isAssignableFrom(this.type) || this.object instanceof CharSequence;
    }

    /**
     * Check if the datatype object is a String array datatype
     *
     * <p>More specifically it looks on the following types:
     * <ul>
     *     <li>{@code String[]}</li>
     *     <li>{@code CharSequence[]}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     * @see Value#isString()
     */
    public boolean isStringArray() {
        return String[].class.isAssignableFrom(this.type) || this.object instanceof CharSequence[];
    }

    /**
     * Check if the datatype object is a numeric datatype
     * <p>
     * More it performs the following tests:
     * <ul>
     *    <li>{@link Value#isByte()} </li>
     *    <li>{@link Value#isShort()}</li>
     *    <li>{@link Value#isInteger()}</li>
     *    <li>{@link Value#isLong()}</li>
     *    <li>{@link Value#isFloat()}</li>
     *    <li>{@link Value#isDouble()}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     */
    public boolean isNumeric() {
        return isByte() || isShort() || isInteger() || isLong() || isFloat() || isDouble();
    }

    /**
     * Check if the datatype object is a numeric array datatype
     * <p>
     * More specifically, it performs the following tests:
     *
     * <ul>
     *    <li>{@link Value#isArray()} </li>
     *    <li>{@link Value#isByte()} </li>
     *    <li>{@link Value#isShort()}</li>
     *    <li>{@link Value#isInteger()}</li>
     *    <li>{@link Value#isLong()}</li>
     *    <li>{@link Value#isFloat()}</li>
     *    <li>{@link Value#isDouble()}</li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     * @see Value#isNumeric()
     */
    public boolean isNumericArray() {
        return isByteArray() || isShortArray() || isIntegerArray() || isLongArray() || isFloatArray() || isDoubleArray();
    }

    /**
     * Check if the datatype object is a textual datatype
     * <p>
     * More it performs the following tests:
     * <ul>
     *    <li>{@link Value#isCharacter()} </li>
     *    <li>{@link Value#isString()} </li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     */
    public boolean isText() {
        return isCharacter() || isString();
    }

    /**
     * Check if the datatype object is a textual array datatype
     * <p>
     * More it performs the following tests:
     * <ul>
     *     <li>{@link Value#isArray()} </li>
     *    <li>{@link Value#isCharacter()} </li>
     *    <li>{@link Value#isString()} </li>
     * </ul>
     *
     * @return true if any condition is matched otherwise false
     * @see Value#isArray()
     * @see Value#isText()
     */
    public boolean isTextArray() {
        return isCharacterArray() || isStringArray();
    }

    /**
     * Returns the current value
     *
     * @return The {@link Character} value contained by the property
     */
    public char asCharacter() {
        return (char) object;
    }

    /**
     * Returns the current value
     *
     * @return The {@link String} value contained by the property
     */
    public String asString() {

        String s = null;

        if (type == String.class) s = (String) object;
        else if (type == Character.class) s = valueOf(asCharacter());
        else if (type == Boolean.class) s = valueOf(asBoolean());
        else if (type == Byte.class) s = valueOf(asByte());
        else if (type == Short.class) s = valueOf(asShort());
        else if (type == Integer.class) s = valueOf(asInt());
        else if (type == Long.class) s = valueOf(asLong());
        else if (type == Float.class) s = valueOf(asFloat());
        else if (type == Double.class) s = valueOf(asDouble());

        else if (type == String[].class) s = Arrays.toString(asStringArray());
        else if (type == Character[].class) s = Arrays.toString(asCharacterArray());
        else if (type == Boolean[].class) s = Arrays.toString(asBooleanArray());
        else if (type == Byte[].class) s = Arrays.toString(asByteArray());
        else if (type == Short[].class) s = Arrays.toString(asShortArray());
        else if (type == Integer[].class) s = Arrays.toString(asIntArray());
        else if (type == Long[].class) s = Arrays.toString(asLongArray());
        else if (type == Float[].class) s = Arrays.toString(asFloatArray());
        else if (type == Double[].class) s = Arrays.toString(asDoubleArray());

        else if (type == char[].class) s = Arrays.toString(asCharacterArray());
        else if (type == boolean[].class) s = Arrays.toString(asBooleanArray());
        else if (type == byte[].class) s = Arrays.toString(asByteArray());
        else if (type == short[].class) s = Arrays.toString(asShortArray());
        else if (type == int[].class) s = Arrays.toString(asIntArray());
        else if (type == long[].class) s = Arrays.toString(asLongArray());
        else if (type == float[].class) s = Arrays.toString(asFloatArray());
        else if (type == double[].class) s = Arrays.toString(asDoubleArray());


        return s;
    }

    /**
     * Returns the current value
     *
     * @return The boolean value contained by the property
     */
    public boolean asBoolean() {
        return (boolean) object;
    }

    /**
     * Returns the current value
     *
     * @return The byte value contained by the property
     */
    public byte asByte() {
        return (byte) object;
    }

    /**
     * Returns the current value
     *
     * @return The short value contained by the property
     */
    public short asShort() {
        return (short) object;
    }

    /**
     * Returns the current value
     *
     * @return The integer value contained by the property
     */
    public int asInt() {
        return (int) object;
    }

    /**
     * Returns the current value
     *
     * @return The long value contained by the property
     */
    public long asLong() {
        return (long) object;
    }

    /**
     * Returns the current value
     *
     * @return The float value contained by the property
     */
    public float asFloat() {
        return (float) object;
    }

    /**
     * Returns the current value
     *
     * @return The double value contained by the property
     */
    public double asDouble() {
        return (double) object;
    }

    /**
     * Returns the current array value
     *
     * @return The char array contained by the property
     */
    public char[] asCharacterArray() {
        return (char[]) object;
    }

    /**
     * Returns the current array value
     *
     * @return The {@link String} array contained by the property
     */
    public String[] asStringArray() {

        String[] s = null;

        if (isStringArray()) {

            s = (String[]) object;

        } else if (isString()) {

            s = new String[]{(String) object};

        } else if (isCharacter()) {

            s = new String[]{valueOf(object)};

        } else if (isCharacterArray()) {

            char[] tmp = asCharacterArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Character.toString(tmp[i]);
            }

        } else if (isBoolean()) {

            s = new String[]{valueOf(object)};

        } else if (isBooleanArray()) {

            boolean[] tmp = asBooleanArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Boolean.toString(tmp[i]);
            }

        } else if (isByte()) {

            s = new String[]{valueOf(object)};

        } else if (isByteArray()) {

            byte[] tmp = asByteArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Byte.toString(tmp[i]);
            }

        } else if (isShort()) {

            s = new String[]{valueOf(object)};

        } else if (isShortArray()) {

            short[] tmp = asShortArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Short.toString(tmp[i]);
            }

        } else if (isInteger()) {

            s = new String[]{valueOf(object)};

        } else if (isIntegerArray()) {

            int[] tmp = asIntArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Integer.toString(tmp[i]);
            }

        } else if (isLong()) {

            s = new String[]{valueOf(object)};

        } else if (isLongArray()) {

            long[] tmp = asLongArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Long.toString(tmp[i]);
            }

        } else if (isFloat()) {

            s = new String[]{valueOf(object)};

        } else if (isFloatArray()) {

            float[] tmp = asFloatArray();

            s = new String[tmp.length];

            for (int i = 0; i < tmp.length; i++) {
                s[i] = Float.toString(tmp[i]);
            }

        } else if (isDouble()) {

            s = new String[]{valueOf(object)};

        } else if (isDoubleArray()) {

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
        return (boolean[]) object;
    }

    /**
     * Returns the current array value
     *
     * @return The byte array contained by the property
     */
    public byte[] asByteArray() {
        return (byte[]) object;
    }

    /**
     * Returns the current array value
     *
     * @return The short array contained by the property
     */
    public short[] asShortArray() {
        return (short[]) object;
    }

    /**
     * Returns the current array value
     *
     * @return The integer array contained by the property
     */
    public int[] asIntArray() {
        return (int[]) object;
    }

    /**
     * Returns the current array value
     *
     * @return The long array contained by the property
     */
    public long[] asLongArray() {
        return (long[]) object;
    }

    /**
     * Returns the current array value
     *
     * @return The float array contained by the property
     */
    public float[] asFloatArray() {
        return (float[]) object;
    }

    /**
     * Returns the current array value
     *
     * @return The double array contained by the property
     */
    public double[] asDoubleArray() {
        return (double[]) object;
    }

    /**
     * Returns the current datatype value
     *
     * @return The {@link Datatype} value contained by the property
     */
    public Datatype getDatatype() {
        return datatype;
    }

    /**
     * Identifies and set the datatype
     */
    private void evaluate() {
        if (!isArray()) {

            if (isNumeric()) {
                if (isByte()) {
                    this.datatype = BYTE;
                } else if (isShort()) {
                    this.datatype = SHORT;
                } else if (isInteger()) {
                    this.datatype = INT;
                } else if (isLong()) {
                    this.datatype = LONG;
                } else if (isFloat()) {
                    this.datatype = FLOAT;
                } else if (isDouble()) {
                    this.datatype = DOUBLE;
                }
            } else if (isText()) {
                if (isString()) {
                    this.datatype = STRING;
                } else if (isCharacter()) {
                    this.datatype = CHAR;
                }
            } else if (isBoolean()) {
                this.datatype = BOOLEAN;
            } else {
                this.datatype = UNKNOWN;
            }

        } else {
            if (isNumericArray()) {
                if (isByteArray()) {
                    this.datatype = ARR_BYTE;
                } else if (isShortArray()) {
                    this.datatype = ARR_SHORT;
                } else if (isIntegerArray()) {
                    this.datatype = ARR_INT;
                } else if (isLongArray()) {
                    this.datatype = ARR_LONG;
                } else if (isFloatArray()) {
                    this.datatype = ARR_FLOAT;
                } else if (isDoubleArray()) {
                    this.datatype = ARR_DOUBLE;
                }
            } else if (isTextArray()) {
                if (isStringArray()) {
                    this.datatype = ARR_STRING;
                } else if (isCharacterArray()) {
                    this.datatype = ARR_CHAR;
                }
            } else if (isBooleanArray()) {
                this.datatype = ARR_BOOLEAN;
            } else {
                this.datatype = ARR_UNKNOWN;
            }
        }
    }
}
