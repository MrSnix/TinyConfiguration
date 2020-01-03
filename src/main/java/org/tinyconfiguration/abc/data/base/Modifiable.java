package org.tinyconfiguration.abc.data.base;

/**
 * The {@link Modifiable} interface provides methods to modify any generic value holden by the implementing class
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface Modifiable {

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(String s);

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(boolean b);

    /**
     * Sets the value on this property
     *
     * @param c The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(char c);

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(byte b);

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(short s);

    /**
     * Sets the value on this property
     *
     * @param i The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(int i);

    /**
     * Sets the value on this property
     *
     * @param l The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(long l);

    /**
     * Sets the value on this property
     *
     * @param f The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(float f);

    /**
     * Sets the value on this property
     *
     * @param d The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(double d);

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(String[] s);

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(boolean[] b);

    /**
     * Sets the array value on this property
     *
     * @param c The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(char[] c);

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(byte[] b);

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(short[] s);

    /**
     * Sets the array value on this property
     *
     * @param i The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(int[] i);

    /**
     * Sets the array value on this property
     *
     * @param l The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(long[] l);

    /**
     * Sets the array value on this property
     *
     * @param f The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(float[] f);

    /**
     * Sets the array value on this property
     *
     * @param d The new value
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    void setValue(double[] d);

}
