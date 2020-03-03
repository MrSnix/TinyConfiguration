package org.tinyconfiguration.abc;

import org.tinyconfiguration.abc.data.Value;
import org.tinyconfiguration.abc.data.base.Modifiable;

/**
 * This class represent generic properties stored inside configuration instances
 *
 * @author G. Baittiner
 * @version 0.1
 */
public abstract class AbstractProperty implements Modifiable {

    protected final String key;
    protected Value value;
    protected final String description;

    /**
     * Protected empty constructor
     */
    protected AbstractProperty() {
        this.key = null;
        this.value = null;
        this.description = null;
    }

    /**
     * Protected constructor with parameters
     */
    protected AbstractProperty(String key, Value value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    /**
     * Gets the key
     *
     * @return The key ({@link String}) associated to the property object.
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the value
     *
     * @return The {@link Value} associated to the property object.
     */
    public Value getValue() {
        return this.value;
    }

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(String s) {
        set(s);
    }

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(boolean b) {
        set(b);
    }

    /**
     * Sets the value on this property
     *
     * @param c The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(char c) {
        set(c);
    }

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(byte b) {
        set(b);
    }

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(short s) {
        set(s);
    }

    /**
     * Sets the value on this property
     *
     * @param i The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(int i) {
        set(i);
    }

    /**
     * Sets the value on this property
     *
     * @param l The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(long l) {
        set(l);
    }

    /**
     * Sets the value on this property
     *
     * @param f The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(float f) {
        set(f);
    }

    /**
     * Sets the value on this property
     *
     * @param d The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(double d) {
        set(d);
    }

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(String[] s) {
        set(s);
    }

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(boolean[] b) {
        set(b);
    }

    /**
     * Sets the array value on this property
     *
     * @param c The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(char[] c) {
        set(c);
    }

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(byte[] b) {
        set(b);
    }

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(short[] s) {
        set(s);
    }

    /**
     * Sets the array value on this property
     *
     * @param i The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(int[] i) {
        set(i);
    }

    /**
     * Sets the array value on this property
     *
     * @param l The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(long[] l) {
        set(l);
    }

    /**
     * Sets the array value on this property
     *
     * @param f The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(float[] f) {
        set(f);
    }

    /**
     * Sets the array value on this property
     *
     * @param d The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public final void setValue(double[] d) {
        set(d);
    }

    /**
     * Gets the description
     *
     * @return The description ({@link String}) associated to the property object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets any generic value on this property
     *
     * @param value The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    abstract protected void set(Object value);

}
