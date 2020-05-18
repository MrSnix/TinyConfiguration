package org.tinyconfiguration.abc.io;

import org.tinyconfiguration.abc.AbstractConfiguration;
import org.tinyconfiguration.abc.io.handlers.utils.Readable;
import org.tinyconfiguration.abc.io.handlers.utils.Writable;

/**
 * The {@link AbstractHandlerIO} interface provides common methods to perform I/O operations
 *
 * @author G. Baittiner
 * @version 0.1
 */
public abstract class AbstractHandlerIO<C extends AbstractConfiguration<?>> implements Readable<C>, Writable<C> {

}
