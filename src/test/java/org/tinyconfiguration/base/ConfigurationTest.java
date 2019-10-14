package org.tinyconfiguration.base;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.property.Property;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    @Test
    public void constructor__basic() {

        assertDoesNotThrow(() -> {
            Configuration cfg = new Configuration.Builder().
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
            Configuration cfg = new Configuration.Builder().build();
        });

    }

    @Test
    public void constructor__full() {

        assertDoesNotThrow(() -> {

            // Creating new configuration file
            Configuration.Builder cfg_builder = new Configuration.Builder().
                    setPathname("./").
                    setFilename("tiny-configuration.cfg");

            Configuration cfg;

            // Inserting new property
            Property p = new Property.Builder().
                    setKey("language").
                    setValue("en").
                    setDescription("This is the application language").
                    setPlaceholder("en").
                    setOptional(false).
                    setValidator(property -> property.getValue().asString().equalsIgnoreCase("EN") || property.getValue().asString().equalsIgnoreCase("IT")).
                    build();

            cfg_builder.put(p);

            cfg = cfg_builder.build();


            // Asserting equality
            assertEquals(p, cfg.get(null, "language"));

            // Inserting new property
            p = new Property.Builder().
                    setKey("username").
                    setValue("root").
                    setGroup("database").
                    setDescription("This is the database username").
                    setPlaceholder("root").
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
                    setPlaceholder("1234567890").
                    setOptional(false).
                    setValidator(property -> property.getValue().asString().length() >= 3).
                    build();

            cfg_builder.put(p);

            cfg = cfg_builder.build();



        });
    }

}