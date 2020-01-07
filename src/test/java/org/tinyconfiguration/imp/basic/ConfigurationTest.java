package org.tinyconfiguration.imp.basic;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    private final Configuration instance;

    public ConfigurationTest() {

        Configuration.Builder b = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.json");

        b.put(new Property.Builder().
                setKey("language").
                setValue("EN").
                setDescription("Specifies the language environment for the session").
                build());

        b.put(new Property.Builder().
                setKey("user").
                setValue("root").
                setDescription("Sets the username required to open the application's database").
                build());

        b.put(new Property.Builder().
                setKey("password").
                setValue("toor").
                setDescription("Sets the password required to open the application's database").
                build());

        b.put(new Property.Builder().
                setKey("sex").
                setValue('M').
                setDescription("Sets the user gender").
                build());

        b.put(new Property.Builder().
                setKey("last-access").
                setValue("never").
                setDescription("Specifies when the last session was started").
                build());

        b.put(new Property.Builder().
                setKey("auto-update").
                setValue(true).
                setDescription("Specifies if the application should regularly check for new software releases")
                .build());

        b.put(new Property.Builder().
                setKey("auto-update-reminder").
                setValue(5).
                setDescription("Specifies how many times the application should remind to install the new release")
                .build());

        b.put(new Property.Builder().
                setKey("hex-digits").
                setValue(new String[]{"332a", "4f2e", "f0be", "cac2"}).
                setDescription("Specifies the seed numbers").
                build());

        this.instance = b.build();

    }

    @Test
    void getProperties() {
        // Retrieving properties declared inside constructor
        assertEquals(8, this.instance.getProperties().size());

        // Clearing the configuration
        this.instance.clear();

        // Checking if it's empty
        assertEquals(0, this.instance.getProperties().size());
    }

    @Test
    void get() {
        // The null property instance is not allowed
        assertThrows(NullPointerException.class, () -> this.instance.get(null));
        // The empty string is not a valid identifier
        assertThrows(IllegalArgumentException.class, () -> this.instance.get(""));
        // Looking for property which does not exists
        assertThrows(NoSuchElementException.class, () -> this.instance.get("unknown"));
        // Checking String value assuming get() returns not-null property
        assertTrue(this.instance.get("password").getValue().asString().equalsIgnoreCase("toor"));
    }

    @Test
    void isEmpty() {

        // Checking if empty as declared inside constructor
        assertFalse(this.instance.isEmpty());

        // Clearing the configuration
        this.instance.clear();

        // Checking if it's empty
        assertTrue(this.instance.isEmpty());
    }

    @Test
    void clear() {

        // Clearing the configuration
        this.instance.clear();

        // Checking if it's empty
        assertTrue(this.instance.isEmpty());
    }

    @Test
    void contains() {

        // Looking for existing property key
        assertTrue(this.instance.contains("language"));
        // Looking for not-existing property key
        assertFalse(this.instance.contains("unknown"));

        // The null String instance is not allowed
        assertThrows(NullPointerException.class, () -> this.instance.contains(null));
        // The empty string is not a valid identifier
        assertThrows(IllegalArgumentException.class, () -> this.instance.contains(""));

    }
}