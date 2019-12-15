package org.tinyconfiguration.base;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.data.Property;
import org.tinyconfiguration.events.ConfigurationListener;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.tinyconfiguration.events.ConfigurationListener.Type;

class ConfigurationTest {

    @Test
    void constructor() {

        assertDoesNotThrow(() -> {
            Configuration cfg = new Configuration.Builder().
                    setName("ConfigurationTest").
                    setVersion("1.0.0").
                    setPathname("./").
                    setFilename("tiny-configuration.cfg").
                    build();
        });

        assertThrows(NullPointerException.class, () -> {
            Configuration cfg = new Configuration.Builder().
                    setPathname(null).
                    setFilename("tiny-configuration.cfg").
                    build();
        });

        assertThrows(NullPointerException.class, () -> {
            Configuration cfg = new Configuration.Builder().
                    setPathname("./").
                    setFilename(null).
                    build();
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Configuration cfg = new Configuration.Builder().
                    setPathname("").
                    setFilename("tiny-configuration.cfg").
                    build();
        });

        assertThrows(NullPointerException.class, () -> {
            Configuration cfg = new Configuration.Builder().
                    setPathname("./").
                    build();
        });

        assertThrows(NullPointerException.class, () -> {
            Configuration cfg = new Configuration.Builder().
                    setFilename("tiny-configuration.cfg").
                    build();
        });

        assertThrows(NullPointerException.class, () -> {
            Configuration cfg = new Configuration.Builder().
                    setName(null).
                    setVersion("1.0.0").
                    setPathname("./").
                    setFilename("tiny-configuration.cfg").
                    build();
        });

        assertThrows(NullPointerException.class, () -> {
            Configuration cfg = new Configuration.Builder().
                    setName("ConfigurationTest").
                    setVersion(null).
                    setPathname("./").
                    setFilename("tiny-configuration.cfg").
                    build();
        });


        assertThrows(IllegalArgumentException.class, () -> {
            Configuration cfg = new Configuration.Builder().
                    setName("ConfigurationTest").
                    setVersion("").
                    setPathname("./").
                    setFilename("tiny-configuration.cfg").
                    build();
        });

        assertThrows(NullPointerException.class, () -> {
            Configuration cfg = new Configuration.Builder().build();
        });

    }

    @Test
    void get() {
        // Creating new configuration file
        Configuration.Builder cfgBuilder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.cfg");

        Configuration cfg;

        // Inserting new property
        Property p = new Property.Builder().
                setKey("language").
                setValue("en").
                setGroup("general").
                setDescription("This is the application language").
                setOptional(false).
                setValidator(datatype -> Arrays.asList("EN", "IT").contains(datatype.asString())).
                build();

        cfgBuilder.put(p);

        cfg = cfgBuilder.build();


        // Asserting equality
        assertEquals(p, cfg.get("general", "language"));

        // Inserting new property
        p = new Property.Builder().
                setKey("username").
                setValue("root").
                setGroup("database").
                setDescription("This is the database username").
                setOptional(false).
                setValidator(datatype -> datatype.asString().length() >= 3).
                build();

        cfgBuilder.put(p);

        cfg = cfgBuilder.build();

        // Asserting equality
        assertEquals(p, cfg.get("database", "username"));

        // Inserting new property
        p = new Property.Builder().
                setKey("password").
                setValue("1234567890").
                setGroup("database").
                setDescription("This is the database password").
                setOptional(false).
                setValidator(datatype -> datatype.asString().length() >= 3).
                build();

        cfgBuilder.put(p);

        cfg = cfgBuilder.build();

        // Asserting equality
        assertEquals(p, cfg.get("database", "password"));

    }


    @Test
    void contains() {

        // Creating new configuration file
        Configuration.Builder cfgBuilder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.cfg");


        assertThrows(NullPointerException.class, () -> {
            // Inserting new property
            Property p = new Property.Builder().
                    setKey("language").
                    setValue("en").
                    setDescription("This is the application language").
                    setOptional(false).
                    setValidator(datatype -> Arrays.asList("EN", "IT").contains(datatype.asString())).
                    build();

            assertNotNull(p);
        });

        // Inserting new property
        Property p = new Property.Builder().
                setKey("password").
                setValue("1234567890").
                setGroup("database").
                setDescription("This is the database password").
                setOptional(false).
                setValidator(datatype -> datatype.asString().length() >= 3).
                build();

        cfgBuilder.put(p);

        Configuration e = cfgBuilder.build();

        assertTrue(e.contains("database", "password"));

        assertThrows(NullPointerException.class, () -> e.contains(null, "language"));

        assertThrows(NullPointerException.class, () -> e.contains(null, null));

        assertThrows(NullPointerException.class, () -> e.contains("", null));

        assertThrows(NoSuchElementException.class, () -> e.contains("not-exists", "property"));

    }

    @Test
    void isEmpty() {

        // Creating new configuration file
        Configuration cfg = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.cfg").build();

        // Checking emptiness
        assertTrue(cfg.isEmpty());

        // Adding property
        Configuration.Builder cfgBuilder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.cfg");

        cfgBuilder.put(new Property.Builder().
                setKey("password").
                setValue("1234567890").
                setGroup("account").
                setDescription("This is the database password").
                setOptional(false).
                setValidator(datatype -> datatype.asString().length() >= 3).
                build());

        cfg = cfgBuilder.build();

        // Now, checking again
        assertFalse(cfg.isEmpty());

    }

    @Test
    void isValid() {

        Configuration.Builder cfgBuilder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.cfg");

        cfgBuilder.put(new Property.Builder().
                setKey("password").
                setValue("1234567890").
                setGroup("account").
                setDescription("This is the database password").
                setOptional(false).
                setValidator(datatype -> datatype.asString().length() >= 3).
                build());

        cfgBuilder.put(new Property.Builder().
                setKey("invalid_password").
                setValue("12").
                setGroup("account").
                setDescription("This is the database password").
                setOptional(true).
                setValidator(datatype -> datatype.asString().length() >= 3).
                build());

        Configuration cfg = cfgBuilder.build();

        assertTrue(cfg.get("account", "password").isValid());
        assertFalse(cfg.get("account", "invalid_password").isValid());

        assertTrue(cfg.get("account", "invalid_password").isOptional());


    }

    @Test
    void addListener() {

        ConfigurationListener listener = configuration -> {
            // Do something
        };

        Configuration.Builder cfgBuilder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.cfg");

        cfgBuilder.put(new Property.Builder().
                setKey("password").
                setValue("1234567890").
                setGroup("account").
                setDescription("This is the database password").
                setOptional(false).
                setValidator(datatype -> datatype.asString().length() >= 3).
                build());

        Configuration cfg = cfgBuilder.build();

        cfg.addListener(Type.ON_CONFIG_DELETE, listener);
        cfg.addListener(Type.ON_CONFIG_SAVE, listener);

        assertEquals(1, cfg.getSaveListeners().size());
        assertEquals(1, cfg.getDeleteListeners().size());
    }

    @Test
    void removeListener() {

        ConfigurationListener listener0 = configuration -> {

        };

        ConfigurationListener listener1 = configuration -> {

        };

        ConfigurationListener listener2 = configuration -> {

        };

        Configuration cfg = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.cfg").
                build();

        cfg.addListener(Type.ON_CONFIG_DELETE, listener0);
        cfg.addListener(Type.ON_CONFIG_DELETE, listener1);

        cfg.addListener(Type.ON_CONFIG_SAVE, listener2);
        cfg.removeListener(Type.ON_CONFIG_SAVE, listener2);

        cfg.removeListener(Type.ON_CONFIG_DELETE, listener0);

        assertEquals(0, cfg.getSaveListeners().size());
        assertEquals(1, cfg.getDeleteListeners().size());

        cfg.resetListeners();

        assertTrue(cfg.getSaveListeners().isEmpty());
        assertTrue(cfg.getDeleteListeners().isEmpty());

    }
}