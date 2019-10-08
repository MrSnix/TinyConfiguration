package org.tinyconfiguration.base;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.events.ConfigurationListener;
import org.tinyconfiguration.events.PropertyListener;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    private Configuration cfg;

    ConfigurationTest() {
        this.cfg = new Configuration.Builder().
                setFilename("tiny-configuration.cfg").
                setPathname("./").
                setPolicy(ConfigurationPolicy.STRICT_MODE).
                build();
    }

    @Test
    void constructor__full() {
        Configuration cfg = new Configuration.Builder().
                setFilename("tiny-configuration.cfg").
                setPathname("./").
                setPolicy(ConfigurationPolicy.STRICT_MODE).
                build();

        assertEquals("tiny-configuration.cfg", cfg.getFilename());
        assertEquals("./", cfg.getPathname());

    }

    @Test
    void constructor__basic() {
        Configuration cfg = new Configuration.Builder().
                setFilename("tiny-configuration.cfg").
                setPathname("./").
                build();

        assertEquals("tiny-configuration.cfg", cfg.getFilename());
        assertEquals("./", cfg.getPathname());

    }

    @Test
    void constructor__with__exceptions() {

        assertThrows(NullPointerException.class, () -> {
            Configuration cfg = new Configuration.Builder().build();
        });

        assertThrows(NullPointerException.class, () -> {
            Configuration cfg = new Configuration.Builder().setPathname("./").build();
        });

        assertThrows(NullPointerException.class, () -> {
            Configuration cfg = new Configuration.Builder().setFilename("tiny-configuration.cfg").build();
        });

        assertThrows(NullPointerException.class, () -> {
            Configuration cfg = new Configuration.Builder().setFilename("tiny-configuration.cfg").setPathname("./").setPolicy(null).build();
        });

    }

    @Test
    void put_and_get() {

        cfg.put("key", "value");

        assertEquals("value", cfg.get("key").asString());

        cfg.put("key", "#value");

        assertEquals("#value", cfg.get("key").asString());

    }

    @Test
    void put__with__exceptions() {

        assertThrows(IllegalArgumentException.class, () -> {
            cfg.put("key", "");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            cfg.put("", "value");
        });

        assertThrows(NullPointerException.class, () -> {
            cfg.put("key", null);
        });

        assertThrows(NullPointerException.class, () -> {
            cfg.put(null, "value");
        });

    }

    @Test
    void get__with__exceptions() {

        assertThrows(NullPointerException.class, () -> {
            cfg.get(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            cfg.get("");
        });

        assertThrows(NoSuchElementException.class, () -> {
            cfg.get("not-inserted-key");
        });

    }

    @Test
    void addListener__onConfigSave() {

        AtomicInteger i = new AtomicInteger(0);

        cfg.addListener(ConfigurationListener.Type.ON_CONFIG_SAVE, configuration -> {
            i.set(-1);
        });

        assertEquals(0, i.get());

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(this.cfg);
        });

        assertEquals(-1, i.get());

        assertDoesNotThrow(() -> {
            assertTrue(ConfigurationIO.delete(this.cfg));
        });

    }

    @Test
    void addListener__onConfigDelete() {

        AtomicInteger i = new AtomicInteger(0);

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(this.cfg);
        });

        cfg.addListener(ConfigurationListener.Type.ON_CONFIG_DELETE, configuration -> {
            i.set(1);
        });

        assertEquals(0, i.get());

        assertDoesNotThrow(() -> {
            assertTrue(ConfigurationIO.delete(this.cfg));
        });

        assertEquals(1, i.get());
    }

    @Test
    void addListener__onPropertyChange() {

        AtomicInteger i = new AtomicInteger(0);

        cfg.put("key", "value");

        cfg.addListener(PropertyListener.Type.ON_PROPERTY_UPDATE, "key", configuration -> {
            i.set(1);
        });

        assertEquals(0, i.get());

        cfg.put("key", "update");

        assertEquals(1, i.get());

        assertThrows(NoSuchElementException.class, () -> {
            cfg.addListener(PropertyListener.Type.ON_PROPERTY_UPDATE, "key-does-not-exist", configuration -> {
                i.set(1);
            });
        });

        assertEquals(1, i.get());

    }

    @Test
    void removeListener__onConfigSave() {

        AtomicInteger i = new AtomicInteger(0);

        ConfigurationListener listener = configuration -> i.set(-1);

        cfg.addListener(ConfigurationListener.Type.ON_CONFIG_SAVE, listener);

        assertEquals(0, i.get());

        cfg.removeListener(ConfigurationListener.Type.ON_CONFIG_SAVE, listener);

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(this.cfg);
        });

        assertEquals(0, i.get());

        assertDoesNotThrow(() -> {
            assertTrue(ConfigurationIO.delete(this.cfg));
        });

    }

    @Test
    void removeListener__onConfigDelete() {

        AtomicInteger i = new AtomicInteger(0);

        ConfigurationListener listener = configuration -> i.set(-1);

        cfg.addListener(ConfigurationListener.Type.ON_CONFIG_DELETE, listener);

        assertEquals(0, i.get());

        cfg.removeListener(ConfigurationListener.Type.ON_CONFIG_DELETE, listener);

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(this.cfg);
        });

        assertDoesNotThrow(() -> {
            assertTrue(ConfigurationIO.delete(this.cfg));
        });

        assertEquals(0, i.get());

    }

    @Test
    void removeListener__onPropertyChange() {

        AtomicInteger i = new AtomicInteger(0);

        cfg.put("key", "value");

        PropertyListener listener = configuration -> i.set(-1);

        cfg.addListener(PropertyListener.Type.ON_PROPERTY_UPDATE, "key", listener);

        assertEquals(0, i.get());

        cfg.removeListener(PropertyListener.Type.ON_PROPERTY_UPDATE, "key", listener);

        cfg.put("key", "update");

        assertEquals(0, i.get());

    }


}