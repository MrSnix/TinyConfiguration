package org.tinyconfiguration.abc;

import java.util.NoSuchElementException;

public interface AbstractContainer {

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
     *
     */
    void clear();

    /**
     * Gets a specific property using the provided key
     *
     * @param key The key used to identify the value
     * @return The {@link T} object (property) used to retrieve any known information
     * @throws NullPointerException     If the key is null
     * @throws IllegalArgumentException If the key is empty
     * @throws NoSuchElementException   If the key does not match any property
     */
     <T extends AbstractProperty> T get(String key);


}
