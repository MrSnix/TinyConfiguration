package org.tinyconfiguration.abc;

/**
 * The {@link Mutable} interface provides methods to modify the implementing class itself
 *
 * @param <T> It should represent the implementing class
 * @author G. Baittiner
 * @version 0.1
 */
public interface Mutable<T> {

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(String s);

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(boolean b);

    /**
     * Sets the value on this property
     *
     * @param c The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(char c);

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(byte b);

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(short s);

    /**
     * Sets the value on this property
     *
     * @param i The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(int i);

    /**
     * Sets the value on this property
     *
     * @param l The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(long l);

    /**
     * Sets the value on this property
     *
     * @param f The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(float f);

    /**
     * Sets the value on this property
     *
     * @param d The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(double d);

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(String[] s);

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(boolean[] b);

    /**
     * Sets the array value on this property
     *
     * @param c The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(char[] c);

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(byte[] b);

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(short[] s);

    /**
     * Sets the array value on this property
     *
     * @param i The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(int[] i);

    /**
     * Sets the array value on this property
     *
     * @param l The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(long[] l);

    /**
     * Sets the array value on this property
     *
     * @param f The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(float[] f);

    /**
     * Sets the array value on this property
     *
     * @param d The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    T setValue(double[] d);

}
