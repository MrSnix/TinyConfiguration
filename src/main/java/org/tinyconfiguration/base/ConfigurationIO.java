package org.tinyconfiguration.base;

import org.tinyconfiguration.events.ConfigurationListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * The {@link ConfigurationIO} class contains I/O operations which can be executed on any {@link Configuration} instance.
 *
 * <p></p>
 * <p>
 * This class provides simple methods in order to achieve maximum efficiency without creating complexity:
 *
 * <ul>
 *     <li>{@link ConfigurationIO#read(Configuration)} - Blocking method to load configuration files</li>
 *     <li>{@link ConfigurationIO#readAsync(Configuration)} - Non-blocking method to load configuration files</li>
 *     <li>{@link ConfigurationIO#write(Configuration)} - Blocking method to save configuration files</li>
 *     <li>{@link ConfigurationIO#writeAsync(Configuration)} - Non-blocking method to save configuration files</li>
 * </ul>
 *
 *
 * <ul>
 *     <li>{@link ConfigurationIO#delete(Configuration)} - Blocking method to delete configuration files</li>
 *     <li>{@link ConfigurationIO#deleteAsync(Configuration)} - Non-blocking method to delete configuration files</li>
 * </ul>
 *
 * <ul>
 *     <li>{@link ConfigurationIO#exist(Configuration)} - It can be used to verify if the configuration has been saved on disk </li>
 * </ul>
 *
 * @author G. Baittiner
 * @version 0.1
 */
public final class ConfigurationIO {

    private static final String PROPERTY_CHECKER = "(?<property>\\s*(?>#(?<description>.*?)#)?\\n*\\s*(?<key>.*?)=(?<value>.*\\\\?);)";

    /**
     * Private empty constructor
     */
    private ConfigurationIO() {

    }

    /**
     * Reads the configuration file
     * <p></p>
     *
     * @param instance The configuration instance to read and update
     * @throws IOException If anything goes wrong while processing the file
     */
    public static void read(Configuration instance) throws IOException {

    }

    /**
     * Reads the configuration file asynchronously
     * <p></p>
     *
     * @param instance The configuration instance to read and update
     * @return The result of the asynchronous reading computation
     */
    public static Future<Void> readAsync(Configuration instance) {

        return null;
    }

    /**
     * Write the configuration file
     *
     * @param instance The configuration instance to write
     * @throws IOException If anything goes wrong while processing the file
     */
    public static void write(Configuration instance) throws IOException {

    }

    /**
     * Write the configuration file asynchronously
     *
     * @param instance The configuration instance to write
     * @throws IOException If anything goes wrong while processing the file
     */
    public static Future<Void> writeAsync(Configuration instance) throws IOException {
        return null;
    }

    /**
     * Delete the configuration file
     *
     * @param instance The configuration instance to delete
     * @return True or false
     */
    public static boolean delete(Configuration instance) {

        File cfg = Paths.get(instance.getPathname(), instance.getFilename()).toFile();

        for (ConfigurationListener listener : instance.getDeleteListeners()) {
            listener.execute(instance);
        }

        return cfg.delete();
    }

    /**
     * Delete the configuration file asynchronously
     *
     * @param instance The configuration instance to delete
     * @return The result of the asynchronous deleting computation
     */
    public static Future<Boolean> deleteAsync(Configuration instance) {
        return CompletableFuture.supplyAsync(() -> delete(instance));
    }

    /**
     * Check if the configuration file exists
     *
     * @param instance The configuration instance to check
     * @return True or false
     */
    public static boolean exist(Configuration instance) {
        return Paths.get(instance.getPathname(), instance.getFilename()).toFile().exists();
    }

}
