package org.tinyconfiguration.base;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationIOTest {

    private Configuration cfg_restricted;
    private Configuration cfg_tolerant;

    ConfigurationIOTest() {
        this.cfg_restricted = new Configuration.Builder().
                setFilename("tiny-configuration-strict.cfg").
                setPathname("./").
                setPolicy(ConfigurationPolicy.STRICT_MODE).
                build();

        this.cfg_tolerant = new Configuration.Builder().
                setFilename("tiny-configuration-tolerant.cfg").
                setPathname("./").
                setPolicy(ConfigurationPolicy.TOLERANT_MODE).
                build();
    }

    @Test
    void read_and_write__restricted() {

        cfg_restricted.put("key-a", "value-a", "This is a simple description");
        cfg_restricted.put("key-b", "value-b", "This is a simple description");

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(cfg_restricted);
        });

        cfg_restricted.clear();

        assertTrue(cfg_restricted.isEmpty());

        cfg_restricted.put("key-a", "value-1", "This is a different description");
        cfg_restricted.put("key-b", "value-2", "This is a different description");

        assertDoesNotThrow(() -> {
            ConfigurationIO.read(cfg_restricted);
        });

        assertEquals("value-a", cfg_restricted.get("key-a").asString());
        assertEquals("value-b", cfg_restricted.get("key-b").asString());

        assertEquals("This is a simple description", cfg_restricted.get("key-a").description());
        assertEquals("This is a simple description", cfg_restricted.get("key-b").description());

        // Inserting two new properties

        cfg_restricted.put("key-c", "value-3", "This is another description");
        cfg_restricted.put("key-d", "value-4", "This is another description");

        // It will throw because now key-c and key-d are missed from the file

        assertThrows(NoSuchElementException.class, () -> {
            ConfigurationIO.read(cfg_restricted);
        });

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(cfg_restricted);
        });

        // It will not throw because now key-c and key-d are written inside configuration

        assertDoesNotThrow(() -> {
            ConfigurationIO.read(cfg_restricted);
        });

        assertEquals("value-3", cfg_restricted.get("key-c").asString());
        assertEquals("value-4", cfg_restricted.get("key-d").asString());

        assertEquals("This is another description", cfg_restricted.get("key-c").description());
        assertEquals("This is another description", cfg_restricted.get("key-d").description());

        assertDoesNotThrow(() -> ConfigurationIO.delete(this.cfg_restricted));

        cfg_restricted.clear();

    }

    @Test
    void read_and_write__tolerant() {

        cfg_tolerant.put("key-a", "value-a", "This is a simple description");
        cfg_tolerant.put("key-b", "value-b", "This is a simple description");

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(cfg_tolerant);
        });

        cfg_tolerant.clear();

        assertTrue(cfg_tolerant.isEmpty());

        assertDoesNotThrow(() -> {
            ConfigurationIO.read(cfg_tolerant);
        });

        assertEquals("value-a", cfg_tolerant.get("key-a").asString());
        assertEquals("value-b", cfg_tolerant.get("key-b").asString());

        assertEquals("This is a simple description", cfg_tolerant.get("key-a").description());
        assertEquals("This is a simple description", cfg_tolerant.get("key-b").description());

        // Inserting two new properties

        cfg_tolerant.put("key-c", "value-3", "This is another description");
        cfg_tolerant.put("key-d", "value-4", "This is another description");

        // It will not throw even if key-c and key-d are missed from the file

        assertDoesNotThrow(() -> {
            ConfigurationIO.read(cfg_tolerant);
        });

        // It will keep "key-c" and "key-d" and will write them

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(cfg_tolerant);
        });

        assertEquals("value-3", cfg_tolerant.get("key-c").asString());
        assertEquals("value-4", cfg_tolerant.get("key-d").asString());

        assertEquals("This is another description", cfg_tolerant.get("key-c").description());
        assertEquals("This is another description", cfg_tolerant.get("key-d").description());

        // Now we append two new properties to the file emulating an unconventional file update by the user
        assertDoesNotThrow(() -> {
            try (FileWriter fw = new FileWriter(Paths.get(cfg_tolerant.getPathname(), cfg_tolerant.getFilename()).toFile(), true)) {
                fw.write("#This is one among many descriptions#\r\nunknown-key=unknown-value;");
            }
        });

        // Then, we load it
        assertDoesNotThrow(() -> {
            ConfigurationIO.read(cfg_tolerant);
        });

        assertEquals("unknown-value", cfg_tolerant.get("unknown-key").asString());
        assertEquals("This is one among many descriptions", cfg_tolerant.get("unknown-key").description());

        assertDoesNotThrow(() -> ConfigurationIO.delete(this.cfg_tolerant));

        cfg_tolerant.clear();

    }

    @Test
    void read__and__writeAsync() {

        // ---- Writing

        cfg_tolerant.put("key-a", "value-a", "This is a simple description");
        cfg_tolerant.put("key-b", "value-b", "This is a simple description");

        assertDoesNotThrow(() -> {
            Future<Void> f = ConfigurationIO.writeAsync(cfg_tolerant);

            while (!f.isDone()) {
                // Waiting...
            }

            assertTrue(f.isDone());
        });

        cfg_tolerant.clear();

        // ---- Reading

        cfg_tolerant.put("key-a", "value-a", "This is a simple description");
        cfg_tolerant.put("key-b", "value-b", "This is a simple description");

        assertDoesNotThrow(() -> {
            Future<Void> f = ConfigurationIO.readAsync(cfg_tolerant);

            while (!f.isDone()) {
                // Waiting...
            }

            assertTrue(f.isDone());
        });

        cfg_tolerant.clear();

    }

    @Test
    void delete() {

        cfg_tolerant.put("key-a", "value-a", "This is a simple description");
        cfg_tolerant.put("key-b", "value-b", "This is a simple description");

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(cfg_tolerant);
        });

        assertDoesNotThrow(() -> {
            ConfigurationIO.delete(cfg_tolerant);
        });

        cfg_tolerant.clear();

    }

    @Test
    void deleteAsync() {

        cfg_tolerant.put("key-a", "value-a", "This is a simple description");
        cfg_tolerant.put("key-b", "value-b", "This is a simple description");

        assertDoesNotThrow(() -> {
            Future<Void> f = ConfigurationIO.writeAsync(cfg_tolerant);

            while (!f.isDone()) {
                // Waiting...
            }

            assertTrue(f.isDone());
        });

        assertDoesNotThrow(() -> {
            Future<Boolean> f = ConfigurationIO.deleteAsync(cfg_tolerant);

            while (!f.isDone()) {
                // Waiting...
            }

            assertTrue(f.get());
        });

        cfg_tolerant.clear();

    }
}