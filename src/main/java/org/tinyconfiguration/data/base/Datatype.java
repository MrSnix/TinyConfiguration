package org.tinyconfiguration.data.base;

/**
 * This class represent the data stored inside the property instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public abstract class Datatype {

    final Object value;
    final Class<?> type;

    /**
     * Creates a new Datatype instance.
     *
     * @param value The data to store as object instance
     */
    Datatype(Object value) {
        this.value = value;
        this.type = value.getClass();
    }

    /**
     * Returns the datatype object as single value
     *
     * @return The value which can represent the data
     */
    public Value asValue() {
        return new Value(this.value);
    }

    /**
     * Returns the datatype object as array value
     *
     * @return The array which can represent the data
     */
    public Array asArray() {
        return new Array(this.value);
    }

    /**
     * Check if the datatype object is an array value
     *
     * @return true if it's an array otherwise false
     */
    public boolean isArray() {
        return this.type.isArray();
    }

    /**
     * This class is used to perform common operations on Datatype
     *
     * @author G. Baittiner
     * @version 0.1
     */
    public static final class Utils {

        /**
         * This methods generate a new datatype reference
         *
         * @param o The Datatype to copy
         * @return The new Datatype instance
         * @see Datatype.Utils#generate(Object)
         */
        public static Datatype copy(Datatype o) {

            Datatype e;

            if (o.type.isArray())
                e = new Array(o.value);
            else
                e = new Value(o.value);

            return e;
        }

        /**
         * This methods generate a new datatype reference
         *
         * @param o The Object to copy
         * @return The new Datatype instance
         * @see Datatype.Utils#copy(Datatype)
         */
        public static Datatype generate(Object o) {
            Datatype e;

            if (o.getClass().isArray())
                e = new Array(o);
            else
                e = new Value(o);

            return e;
        }

    }

}