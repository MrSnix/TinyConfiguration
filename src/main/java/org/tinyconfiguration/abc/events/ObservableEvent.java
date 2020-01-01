package org.tinyconfiguration.abc.events;

/**
 * This interface defines method to manipulate fired events
 *
 * @author G. Baittiner
 * @version 0.1
 */
public interface ObservableEvent {

    /**
     * This method consumes event so that it will not be processed in the default manner by the source which originated it
     */
    void consume();

}
