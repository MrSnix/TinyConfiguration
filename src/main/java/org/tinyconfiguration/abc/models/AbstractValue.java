package org.tinyconfiguration.abc.models;

/**
 * The {@link AbstractValue} is the foundation to represent generic data "as-it-is"
 *
 * @author G. Baittiner
 * @version 0.1
 */
public abstract class AbstractValue {

    protected Object value;
    protected Class<?> type;

    /**
     * Protected empty constructor
     */
    protected AbstractValue() {}

    /**
     * Protected constructor with parameters
     *
     * @param value The data to store as object instance
     */
    protected AbstractValue(Object value) {
        this.value = value;
        this.type = value.getClass();
    }

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(String s) {
        __setValue(s);
    }

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(boolean b) {
        __setValue(b);
    }

    /**
     * Sets the value on this property
     *
     * @param c The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(char c) {
        __setValue(c);
    }

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(byte b) {
        __setValue(b);
    }

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(short s) {
        __setValue(s);
    }

    /**
     * Sets the value on this property
     *
     * @param i The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(int i) {
        __setValue(i);
    }

    /**
     * Sets the value on this property
     *
     * @param l The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(long l) {
        __setValue(l);
    }

    /**
     * Sets the value on this property
     *
     * @param f The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(float f) {
        __setValue(f);
    }

    /**
     * Sets the value on this property
     *
     * @param d The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(double d) {
        __setValue(d);
    }

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(String[] s) {
        __setValue(s);
    }

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(boolean[] b) {
        __setValue(b);
    }

    /**
     * Sets the array value on this property
     *
     * @param c The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(char[] c) {
        __setValue(c);
    }

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(byte[] b) {
        __setValue(b);
    }

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(short[] s) {
        __setValue(s);
    }

    /**
     * Sets the array value on this property
     *
     * @param i The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(int[] i) {
        __setValue(i);
    }

    /**
     * Sets the array value on this property
     *
     * @param l The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(long[] l) {
        __setValue(l);
    }

    /**
     * Sets the array value on this property
     *
     * @param f The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(float[] f) {
        __setValue(f);
    }

    /**
     * Sets the array value on this property
     *
     * @param d The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    public final void setValue(double[] d) {
        __setValue(d);
    }

    /**
     * Sets any generic value on this property
     *
     * @param value The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    protected abstract void __setValue(Object value);

    /**
     * Gets the value
     *
     * @return The current value
     */
    protected final Object getValue() {
        return value;
    }

    /**
     * Gets the object's class
     *
     * @return The current object's class
     */
    protected final Class<?> getType() {
        return type;
    }
}