package org.tinyconfiguration.abc;

/**
 * The {@link AbstractBuilder} is the foundation class provided to implement builder objects
 *
 * @param <C> The type parameter which will be returned by {@link AbstractBuilder#build()}
 * @author G.Baittiner
 * @version 0.1
 */
public abstract class AbstractBuilder<C> {

    protected final boolean isCleanable;

    /**
     * Empty constructor
     */
    protected AbstractBuilder() {
        this.isCleanable = true;
    }

    /**
     * Constructor with parameters
     *
     * @param isCleanable Specifies if the builder should be {@link #clear()} on every {@link #build()} call
     */
    protected AbstractBuilder(boolean isCleanable) {
        this.isCleanable = isCleanable;
    }

    /**
     * Create the final object then call {@link AbstractBuilder#clear()} if the builder object is cleanable to make the builder reusable
     *
     * @return The new {@link C} instance
     * @throws NullPointerException If one or more properties are not set
     */
    public abstract C build();

    /**
     * Reset the builder in order to reuse it
     */
    public abstract void clear();

}
