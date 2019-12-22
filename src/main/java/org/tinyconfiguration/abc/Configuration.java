package org.tinyconfiguration.abc;

public interface Configuration {

    /**
     * Checks if any property has been inserted inside the configuration instance.
     *
     * @return True or false
     */
    boolean isEmpty();

    /**
     * Remove all properties from the configuration.
     *
     */
    void clear();
}
