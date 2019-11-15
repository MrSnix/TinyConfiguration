package org.tinyconfiguration.base;

import org.tinyconfiguration.events.ConfigurationListener;
import org.tinyconfiguration.io.impl.JsonWriter;
import org.tinyconfiguration.io.impl.XmlWriter;
import org.tinyconfiguration.utils.ExportType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
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
 *     <li>{@link ConfigurationIO#write(ExportType, Configuration)} - Blocking method to save configuration files</li>
 *     <li>{@link ConfigurationIO#writeAsync(ExportType, Configuration)} - Non-blocking method to save configuration files</li>
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
@SuppressWarnings("WeakerAccess")
public final class ConfigurationIO {

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
     * @param type     The export type which translate the configuration instance
     * @param instance The configuration instance to write
     * @throws IOException              If anything goes wrong while processing the file
     * @throws IllegalArgumentException If the export format type is unknown
     */
    public static void write(ExportType type, Configuration instance) throws Exception {

        switch (type) {
            case JSON:
                new JsonWriter().toFile(instance);
                break;
            case XML:
                new XmlWriter().toFile(instance);
                break;
            default:
                throw new IllegalArgumentException("The following format is unknown: " + type);
        }

    }

    /**
     * Write the configuration file asynchronously
     *
     * @param type     The export type which translate the configuration instance
     * @param instance The configuration instance to write
     * @return Future object representing the writing task
     */
    public static Future writeAsync(ExportType type, Configuration instance) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                write(type, instance);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
            return null;
        });
    }

    /**
     * Delete the configuration file
     *
     * @param instance The configuration instance to delete
     * @throws IOException If the configuration file cannot be deleted
     */
    public static void delete(Configuration instance) throws IOException {

        File cfg = Paths.get(instance.getPathname(), instance.getFilename()).toFile();

        for (ConfigurationListener listener : instance.getDeleteListeners()) {
            listener.execute(instance);
        }

        Files.delete(cfg.toPath());
    }

    /**
     * Delete the configuration file asynchronously
     *
     * @param instance The configuration instance to delete
     * @return Future object representing the deleting task
     */
    public static Future deleteAsync(Configuration instance) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                delete(instance);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
            return null;
        });
    }

    /**
     * Check if the configuration file exists
     *
     * @param instance The configuration instance to check
     * @return True or false
     */
    public static boolean exist(Configuration instance) {
        return instance.getFile().exists();
    }

}
