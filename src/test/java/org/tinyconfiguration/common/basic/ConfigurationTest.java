package org.tinyconfiguration.common.basic;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.abc.Property;

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

        Configuration.Builder b = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.json");

        Configuration e = b.build();

        assertEquals(0, e.getProperties().size());

        assertEquals(8, this.instance.getProperties().size());
    }

    @Test
    void get() {

        assertThrows(NullPointerException.class, () -> {
            this.instance.get(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            this.instance.get("");
        });

        assertThrows(NoSuchElementException.class, () -> {
            this.instance.get("unknown");
        });

        assertTrue(this.instance.get("password").getValue().asString().equalsIgnoreCase("toor"));
    }

    @Test
    void isEmpty() {
        assertFalse(instance.isEmpty());
    }

    @Test
    void clear() {

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

        Configuration e = b.build();

        e.clear();

        assertTrue(e.isEmpty());
    }

    @Test
    void contains() {

        assertDoesNotThrow(() -> {
            assertTrue(this.instance.contains("language"));
            assertFalse(this.instance.contains("unknown"));
        });

        assertThrows(NullPointerException.class, () -> {
            this.instance.contains(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            this.instance.contains("");
        });

    }

}