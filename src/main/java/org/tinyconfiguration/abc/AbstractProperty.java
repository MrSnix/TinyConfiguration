package org.tinyconfiguration.abc;

import org.tinyconfiguration.abc.common.Datatype;

import static java.util.Objects.requireNonNull;

/**
 * This class represent generic properties stored inside configuration instances
 *
 * @author G. Baittiner
 * @version 0.1
 */
public abstract class AbstractProperty implements Modifiable {

    protected Datatype value;
    protected String key;
    protected String description;

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
    protected AbstractProperty(String key, Datatype value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    /**
     * Gets the key
     *
     * @return The key ({@link String}) associated to the property object.
     */
    protected String getKey() {
        return key;
    }

    /**
     * Gets the value
     *
     * @return The {@link Datatype} associated to the property object.
     */
    protected Datatype getValue() {
        return value;
    }

    /**
     * Gets the description
     *
     * @return The description ({@link String}) associated to the property object.
     */
    protected String getDescription() {
        return description;
    }

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(String s) {
        requireNonNull(this.value, "The value must be set").setValue(s);
    }

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(boolean b) {
        requireNonNull(this.value, "The value must be set").setValue(b);
    }

    /**
     * Sets the value on this property
     *
     * @param c The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(char c) {
        requireNonNull(this.value, "The value must be set").setValue(c);
    }

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(byte b) {
        requireNonNull(this.value, "The value must be set").setValue(b);
    }

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(short s) {
        requireNonNull(this.value, "The value must be set").setValue(s);
    }

    /**
     * Sets the value on this property
     *
     * @param i The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(int i) {
        requireNonNull(this.value, "The value must be set").setValue(i);
    }

    /**
     * Sets the value on this property
     *
     * @param l The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(long l) {
        requireNonNull(this.value, "The value must be set").setValue(l);
    }

    /**
     * Sets the value on this property
     *
     * @param f The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(float f) {
        requireNonNull(this.value, "The value must be set").setValue(f);
    }

    /**
     * Sets the value on this property
     *
     * @param d The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(double d) {
        requireNonNull(this.value, "The value must be set").setValue(d);
    }

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(String[] s) {
        requireNonNull(this.value, "The value must be set").setValue(s);
    }

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(boolean[] b) {
        requireNonNull(this.value, "The value must be set").setValue(b);
    }

    /**
     * Sets the array value on this property
     *
     * @param c The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(char[] c) {
        requireNonNull(this.value, "The value must be set").setValue(c);
    }

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(byte[] b) {
        requireNonNull(this.value, "The value must be set").setValue(b);
    }

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(short[] s) {
        requireNonNull(this.value, "The value must be set").setValue(s);
    }

    /**
     * Sets the array value on this property
     *
     * @param i The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(int[] i) {
        requireNonNull(this.value, "The value must be set").setValue(i);
    }

    /**
     * Sets the array value on this property
     *
     * @param l The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(long[] l) {
        requireNonNull(this.value, "The value must be set").setValue(l);
    }

    /**
     * Sets the array value on this property
     *
     * @param f The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(float[] f) {
        requireNonNull(this.value, "The value must be set").setValue(f);
    }

    /**
     * Sets the array value on this property
     *
     * @param d The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    @Override
    public void setValue(double[] d) {
        requireNonNull(this.value, "The value must be set").setValue(d);
    }

}
