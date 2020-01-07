package org.tinyconfiguration.abc;

/**
 * The {@link AbstractCollection} interfaces provides basic methods which should be common on any data container
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface AbstractCollection {

    /**
     * Check if a specific key is stored inside the container instance
     *
     * @param key The key used to identify the value
     * @return True or false
     * @throws NullPointerException     If the key is null
     * @throws IllegalArgumentException If the key is empty
     */
    boolean contains(String key);

    /**
     * Checks if any value has been inserted inside the container.
     *
     * @return True or false
     */
    boolean isEmpty();

    /**
     * Remove all values stored by the container.
     */
    void clear();

}
