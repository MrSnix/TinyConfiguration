package org.tinyconfiguration.base;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.events.ConfigurationListener;
import org.tinyconfiguration.property.Property;

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
        Configuration.Builder cfg_builder = new Configuration.Builder().
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
                setValidator(property ->
                        property.getValue().asString().equalsIgnoreCase("EN") ||
                                property.getValue().asString().equalsIgnoreCase("IT")).
                build();

        cfg_builder.put(p);

        cfg = cfg_builder.build();


        // Asserting equality
        assertEquals(p, cfg.get("general", "language"));

        // Inserting new property
        p = new Property.Builder().
                setKey("username").
                setValue("root").
                setGroup("database").
                setDescription("This is the database username").
                setOptional(false).
                setValidator(property -> property.getValue().asString().length() >= 3).
                build();

        cfg_builder.put(p);

        cfg = cfg_builder.build();

        // Asserting equality
        assertEquals(p, cfg.get("database", "username"));

        // Inserting new property
        p = new Property.Builder().
                setKey("password").
                setValue("1234567890").
                setGroup("database").
                setDescription("This is the database password").
                setOptional(false).
                setValidator(property -> property.getValue().asString().length() >= 3).
                build();

        cfg_builder.put(p);

        cfg = cfg_builder.build();

        // Asserting equality
        assertEquals(p, cfg.get("database", "password"));

    }


    @Test
    void contains() {

        // Creating new configuration file
        Configuration.Builder cfg_builder = new Configuration.Builder().
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
                    setValidator(property ->
                            property.getValue().asString().equalsIgnoreCase("EN") ||
                                    property.getValue().asString().equalsIgnoreCase("IT")).
                    build();
        });

        // Inserting new property
        Property p = new Property.Builder().
                setKey("password").
                setValue("1234567890").
                setGroup("database").
                setDescription("This is the database password").
                setOptional(false).
                setValidator(property -> property.getValue().asString().length() >= 3).
                build();

        cfg_builder.put(p);

        Configuration e = cfg_builder.build();

        assertTrue(e.contains("database", "password"));

        assertThrows(NullPointerException.class, () -> {
            e.contains(null, "language");
        });

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
        Configuration.Builder cfg_builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.cfg");

        cfg_builder.put(new Property.Builder().
                setKey("password").
                setValue("1234567890").
                setGroup("account").
                setDescription("This is the database password").
                setOptional(false).
                setValidator(property -> property.getValue().asString().length() >= 3).
                build());

        cfg = cfg_builder.build();

        // Now, checking again
        assertFalse(cfg.isEmpty());

    }

    @Test
    void addListener() {

        ConfigurationListener listener = configuration -> {

        };

        Configuration.Builder cfg_builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.cfg");

        cfg_builder.put(new Property.Builder().
                setKey("password").
                setValue("1234567890").
                setGroup("account").
                setDescription("This is the database password").
                setOptional(false).
                setValidator(property -> property.getValue().asString().length() >= 3).
                build());

        Configuration cfg = cfg_builder.build();

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