package org.tinyconfiguration.imp.basic.io;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.imp.basic.Configuration;
import org.tinyconfiguration.imp.basic.Property;

import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationIOTest {

    private final Configuration instance;

    public ConfigurationIOTest() {

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
    void writeJSON() {

        assertDoesNotThrow(() -> ConfigurationIO.JSON.write(this.instance));

        assertTrue(ConfigurationIO.JSON.exist(instance));

    }

    @Test
    void writeAsyncJSON() {

        assertDoesNotThrow(() -> {

            int x = 0;

            Future<Void> task = ConfigurationIO.JSON.writeAsync(this.instance);

            while (!task.isDone()) {
                ++x;
            }

            assertTrue(x != 0);

        });

        assertTrue(ConfigurationIO.JSON.exist(instance));

    }

    @Test
    void readJSON() {

        // Does it exists?
        if (!ConfigurationIO.JSON.exist(instance)) {

            // If so, let's executing the writing task, then execute it with get()
            assertDoesNotThrow(() -> ConfigurationIO.JSON.write(instance));

            // Now, it should exists
            assertTrue(ConfigurationIO.JSON.exist(instance));

        }

        // Now, reading the configuration instance
        assertDoesNotThrow(() -> ConfigurationIO.JSON.read(this.instance));

    }

    @Test
    void readAsyncJSON() {

        assertDoesNotThrow(() -> {

            int x = 0;

            Future<Void> task = ConfigurationIO.JSON.readAsync(this.instance);

            while (!task.isDone()) {
                // Do something
                ++x;
            }

            assertTrue(x >= 0);
        });

        assertTrue(ConfigurationIO.JSON.exist(this.instance));

    }

    @Test
    void deleteJSON() {

        // Does it exists?
        if (!ConfigurationIO.JSON.exist(instance)) {

            // If so, let's executing the writing task, then execute it with get()
            assertDoesNotThrow(() -> ConfigurationIO.JSON.write(instance));

            // Now, it should exists
            assertTrue(ConfigurationIO.JSON.exist(instance));

        }

        // Executing deleting task, then execute it
        assertDoesNotThrow(() -> ConfigurationIO.JSON.delete(instance));
        // Asserting does not exists any more
        assertFalse(ConfigurationIO.JSON.exist(instance));

    }

    @Test
    void deleteAsyncJSON() {

        // Does it exists?
        if (!ConfigurationIO.JSON.exist(instance)) {

            // If so, let's obtain an a-sync writing task, then execute it with get()
            assertDoesNotThrow(() -> ConfigurationIO.JSON.writeAsync(instance).get());

            // Now, it should exists
            assertTrue(ConfigurationIO.JSON.exist(instance));

        }
        // Obtaining deleting task, then execute it
        assertDoesNotThrow(() -> ConfigurationIO.JSON.deleteAsync(instance).get());
        // Asserting does not exists any more
        assertFalse(ConfigurationIO.JSON.exist(instance));

    }
}