package org.tinyconfiguration.abc;

import org.tinyconfiguration.Configuration;

public interface Buildable {

    /**
     * Create the final object
     *
     * @return The new {@link Configuration} instance
     * @throws NullPointerException If one or more properties are not set
     */
    Object build();

    /**
     * Reset the builder in order to reuse it
     */
    void clear();

}
