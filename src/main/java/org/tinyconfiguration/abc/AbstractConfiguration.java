package org.tinyconfiguration.abc;


import java.io.File;
import java.nio.file.Paths;
import java.util.List;

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

}