package org.tinyconfiguration.abc;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;

/**
 * The {@link AbstractConfiguration} is the base class used to define any configuration data structure
 *
 * @author G. Baittiner
 * @version 0.1
 */
public abstract class AbstractConfiguration<T extends AbstractProperty> implements AbstractCollection {

    protected final String name;
    protected final String filename;
    protected final String pathname;
    protected final File file;
    protected final String version;


    /**
     * Protected empty constructor
     */
    protected AbstractConfiguration() {
        this.name = null;
        this.filename = null;
        this.pathname = null;
        this.file = null;
        this.version = null;
    }

    /**
     * Protected container constructor with parameters
     *
     * @param name     The configuration name
     * @param version  The configuration version
     * @param filename The configuration filename
     * @param pathname The configuration pathname
     */
    protected AbstractConfiguration(String name, String version, String filename, String pathname) {
        this.name = name;
        this.version = version;
        this.filename = filename;
        this.pathname = pathname;
        this.file = Paths.get(pathname, filename).toFile();
    }

    /**
     * Gets the name.
     *
     * @return The name ({@link String}) associated to the container object.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the version.
     *
     * @return The version ({@link String}) associated to the container object.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Gets the filename.
     *
     * @return The filename ({@link String}) associated to the container object.
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     * Gets the pathname.
     *
     * @return The pathname ({@link String}) associated to the container object.
     */
    public String getPathname() {
        return this.pathname;
    }

    /**
     * Gets the file.
     *
     * @return The {@link File} associated to the container object.
     */
    public File getFile() {
        return file;
    }

    /**
     * Gets the properties.
     *
     * @return The properties associated to the container object as {@link List}
     */
    public abstract List<T> getProperties();

    /**
     * Delete the configuration file
     *
     * @throws IOException If the configuration file cannot be deleted
     */
    public void delete() throws IOException {
        Files.delete(this.getFile().toPath());
    }

    /**
     * Delete the configuration file asynchronously
     *
     * @return Future object representing the deleting task
     */
    public Future<Void> deleteAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                delete();
            } catch (IOException e) {
                throw new CompletionException(e);
            }
            return null;
        });
    }

    /**
     * Check if the configuration file exists
     *
     * @return True or false
     */
    public boolean exist() {
        return this.getFile().exists();
    }

}