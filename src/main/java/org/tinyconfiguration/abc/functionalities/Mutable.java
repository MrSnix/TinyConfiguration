package org.tinyconfiguration.abc.functionalities;

public interface Mutable {

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(String s);

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(boolean b);

    /**
     * Sets the value on this property
     *
     * @param c The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(char c);

    /**
     * Sets the value on this property
     *
     * @param b The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(byte b);

    /**
     * Sets the value on this property
     *
     * @param s The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(short s);

    /**
     * Sets the value on this property
     *
     * @param i The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(int i);

    /**
     * Sets the value on this property
     *
     * @param l The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(long l);

    /**
     * Sets the value on this property
     *
     * @param f The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(float f);

    /**
     * Sets the value on this property
     *
     * @param d The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(double d);

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(String[] s);

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(boolean[] b);

    /**
     * Sets the array value on this property
     *
     * @param c The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(char[] c);

    /**
     * Sets the array value on this property
     *
     * @param b The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(byte[] b);

    /**
     * Sets the array value on this property
     *
     * @param s The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(short[] s);

    /**
     * Sets the array value on this property
     *
     * @param i The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(int[] i);

    /**
     * Sets the array value on this property
     *
     * @param l The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(long[] l);

    /**
     * Sets the array value on this property
     *
     * @param f The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(float[] f);

    /**
     * Sets the array value on this property
     *
     * @param d The new value
     * @return An object which holds the data
     * @throws NullPointerException     If the value is null
     * @throws IllegalArgumentException If the class type is different from the one declared
     */
    Object setValue(double[] d);

}
