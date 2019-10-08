package org.tinyconfiguration.base;

import org.tinyconfiguration.events.ConfigurationListener;
import org.tinyconfiguration.utils.Delimiters;
import org.tinyconfiguration.utils.SpecialCharacters;

import java.io.*;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * Please, check {@link ConfigurationPolicy} for better understanding<br>
     * about how the policy can influence the behaviour of this method.
     *
     * @param instance The configuration instance to read and update
     * @throws IOException If anything goes wrong while processing the file
     */
    public static void read(Configuration instance) throws IOException {
        switch (instance.getPolicy()) {
            case STRICT_MODE:
                readRestricted(instance);
                break;
            case TOLERANT_MODE:
                readUnrestricted(instance);
                break;
        }
    }

    /**
     * Reads the configuration file asynchronously
     * <p></p>
     * Please, check {@link ConfigurationPolicy} for better understanding<br>
     * about how the policy can influence the behaviour of this method.
     *
     * @param instance The configuration instance to read and update
     * @return The result of the asynchronous reading computation
     */
    public static Future<Void> readAsync(Configuration instance) {

        Future<Void> task = null;

        switch (instance.getPolicy()) {
            case STRICT_MODE:
                task = readAsyncRestricted(instance);
                break;
            case TOLERANT_MODE:
                task = readAsyncUnrestricted(instance);
                break;
        }

        return task;
    }

    private static Future<Void> readAsyncRestricted(Configuration instance) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                readRestricted(instance);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
            return null;
        });

    }

    private static Future<Void> readAsyncUnrestricted(Configuration instance) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                readUnrestricted(instance);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
            return null;
        });

    }

    private static void readRestricted(Configuration instance) throws IOException {

        File cfg = Paths.get(instance.getPathname(), instance.getFilename()).toFile();

        try (FileReader fr = new FileReader(cfg);
             BufferedReader br = new BufferedReader(fr)) {

            StringBuilder sb = new StringBuilder();

            TreeSet<String> keys = __read(instance, br, sb);

            if (!keys.isEmpty())
                throw new NoSuchElementException("The following keys were not found inside the configuration: " + keys);
        }
    }

    private static TreeSet<String> __read(Configuration instance, BufferedReader br, StringBuilder sb) throws IOException {
        TreeSet<String> keys = instance.getKeys();

        Pattern pattern = Pattern.compile(PROPERTY_CHECKER);

        int previous;
        int current;

        String previous_char = "";
        String current_char = "";

        boolean toStop = false;

        while (!toStop) {

            if ((previous = br.read()) != -1) {

                previous_char = String.valueOf((char) previous);

                sb.append(previous_char);

                if (!previous_char.equals(Delimiters.PROPERTY.value()) && !current_char.equals(Delimiters.ESCAPE.value())) {

                    if ((current = br.read()) != -1) {

                        current_char = String.valueOf((char) current);

                        sb.append(current_char);

                        if (!previous_char.equals(Delimiters.ESCAPE.value()) &&
                                current_char.equals(Delimiters.PROPERTY.value())) {

                            matchProperty(instance, sb.toString().trim(), keys, pattern);

                            sb.setLength(0);
                        }
                    }
                } else {

                    matchProperty(instance, sb.toString().trim(), keys, pattern);

                    sb.setLength(0);
                }

            } else {
                toStop = true;
            }

        }

        if (sb.toString().trim().length() > 0)
            matchProperty(instance, sb.toString().trim(), keys, pattern);

        sb.setLength(0);

        return keys;
    }

    private static void matchProperty(Configuration instance, String s, TreeSet<String> keys, Pattern pattern) {

        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {

            String key = SpecialCharacters.substitute(SpecialCharacters.Type.UNESCAPED, matcher.group("key"));
            String value = SpecialCharacters.substitute(SpecialCharacters.Type.UNESCAPED, matcher.group("value"));
            String description = matcher.group("description");

            if (!instance.contains(key) && instance.getPolicy() == ConfigurationPolicy.STRICT_MODE) {
                throw new NoSuchElementException("While reading an unexpected key has been found: " + matcher.group("key") + System.lineSeparator() +
                        "Please, use ConfigurationBuilder.setPolicy(" + ConfigurationPolicy.TOLERANT_MODE + ") if loading unknown properties is the desired behaviour");
            }

            keys.remove(matcher.group("key"));

            instance.put(key, value, description);

            matcher.reset();

        } else {
            throw new IllegalStateException("The following property is invalid: " + System.lineSeparator() + s.toString());
        }
    }

    private static void readUnrestricted(Configuration instance) throws IOException {

        File cfg = Paths.get(instance.getPathname(), instance.getFilename()).toFile();

        try (FileReader fr = new FileReader(cfg);
             BufferedReader br = new BufferedReader(fr)) {

            StringBuilder sb = new StringBuilder();

            __read(instance, br, sb);

        }
    }

    /**
     * Write the configuration file
     *
     * @param instance The configuration instance to write
     * @throws IOException If anything goes wrong while processing the file
     */
    public static void write(Configuration instance) throws IOException {

        for (ConfigurationListener listener : instance.getSaveListeners()) {
            listener.execute(instance);
        }

        File cfg = Paths.get(instance.getPathname(), instance.getFilename()).toFile();

        StringBuilder sb = new StringBuilder();

        // Opening buffered stream
        try (FileWriter fw = new FileWriter(cfg);
             BufferedWriter writer = new BufferedWriter(fw)) {

            // Iterating each property and writing the file
            for (Property tmp : instance.getProperties()) {

                // Writing description
                if (tmp.description() != null) {

                    // Inserting [ delimiter ] + description + [ delimiter ]
                    sb.append(Delimiters.DESCRIPTION).append(tmp.description()).append(Delimiters.DESCRIPTION);

                    // Writing on file
                    writer.write(sb.toString());
                    writer.newLine();

                    // Reset the string builder
                    sb.setLength(0);

                }

                // Escaping
                String key = SpecialCharacters.substitute(SpecialCharacters.Type.ESCAPED, tmp.key());
                String value = SpecialCharacters.substitute(SpecialCharacters.Type.ESCAPED, tmp.asString());

                // Writing property
                sb.append(key).append(Delimiters.ASSIGNMENT).append(value).append(Delimiters.PROPERTY);

                // Appending on file
                writer.write(sb.toString());
                writer.newLine();

                // Reset the string builder
                sb.setLength(0);
            }

        }
    }

    /**
     * Write the configuration file asynchronously
     *
     * @param instance The configuration instance to write
     * @return The result of the asynchronous writing computation
     */
    public static Future<Void> writeAsync(Configuration instance) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                write(instance);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
            return null;
        });
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
