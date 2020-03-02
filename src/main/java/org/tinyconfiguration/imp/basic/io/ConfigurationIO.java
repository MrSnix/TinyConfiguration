package org.tinyconfiguration.imp.basic.io;

import org.tinyconfiguration.abc.io.AbstractHandlerIO;
import org.tinyconfiguration.imp.basic.Configuration;

/**
 * The {@link ConfigurationIO} class contains {@link AbstractHandlerIO} references which can be used on any {@link Configuration} instance
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class ConfigurationIO {

    public static final HandlerJSON JSON = new HandlerJSON();
    public static final HandlerXML XML = new HandlerXML();

}
