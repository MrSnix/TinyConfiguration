package org.tinyconfiguration.base;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.tinyconfiguration.data.Property;
import org.tinyconfiguration.utils.FormatType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(OrderAnnotation.class)
class ConfigurationIOTest {

    @Test
    @Order(1)
    void write() {
        Configuration.Builder builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.xml");

        builder.put(new Property.Builder().
                setKey("value-int").
                setValue(10).
                setGroup("single-value").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("value-float").
                setValue(12.4f).
                setGroup("single-value").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("value-boolean").
                setValue(true).
                setGroup("single-value").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("value-string").
                setValue("This is a really cool string").
                setGroup("single-value").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new boolean[]{true, false, false}).
                setGroup("boolean-values").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new byte[]{1, 2, 3}).
                setGroup("byte-values").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new short[]{10, 23, 45}).
                setGroup("short-values").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new int[]{107, 273, 465}).
                setGroup("int-values").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new long[]{1054345347, 43554543, 46543432}).
                setGroup("long-values").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new float[]{1.0f, 20.5f, 3f}).
                setGroup("float-values").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new double[]{12d, 7.54d, 8e1d}).
                setGroup("double-values").
                setDescription("This is a truly useless description").
                build());

        final Configuration cfg0 = builder.build();

        // Write as XML
        assertDoesNotThrow(() -> ConfigurationIO.write(FormatType.XML, cfg0));
        assertDoesNotThrow(() -> ConfigurationIO.writeAsync(FormatType.XML, cfg0).get());

        // Changing .ext
        builder.setFilename("tiny-configuration.json");
        final Configuration cfg1 = builder.build();

        // Write as JSON
        assertDoesNotThrow(() -> ConfigurationIO.write(FormatType.JSON, cfg1));
        assertDoesNotThrow(() -> ConfigurationIO.writeAsync(FormatType.JSON, cfg1).get());

    }

    @Test
    @Order(2)
    void exist() {

        Configuration.Builder builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.xml");

        final Configuration cfg0 = builder.build();

        // Exists as XML
        assertTrue(ConfigurationIO.exist(cfg0));

        // Changing .ext
        builder.setFilename("tiny-configuration.json");
        final Configuration cfg1 = builder.build();

        // Exists as JSON
        assertTrue(ConfigurationIO.exist(cfg1));

    }

    @Test
    @Order(3)
    void read() {
        Configuration.Builder cfg0 = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.xml");
    }

    @Test
    @Order(4)
    void isEmpty() {

        Configuration.Builder builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.xml");

        final Configuration cfg0 = builder.build();

        // This configuration is empty
        assertTrue(cfg0::isEmpty);

    }


    @Test
    @Disabled
    @Order(5)
    void delete() {
        Configuration.Builder builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.xml");

        final Configuration cfg0 = builder.build();

        // Delete XML
        assertDoesNotThrow(() -> ConfigurationIO.delete(cfg0));

        // Changing .ext
        builder.setFilename("tiny-configuration.json");
        final Configuration cfg1 = builder.build();

        // Delete JSON
        assertDoesNotThrow(() -> ConfigurationIO.delete(cfg1));

        // Rewrite to test next async method
        write();

        // Delete async XML & JSON
        assertDoesNotThrow(() -> ConfigurationIO.deleteAsync(cfg0).get());
        assertDoesNotThrow(() -> ConfigurationIO.deleteAsync(cfg1).get());

    }
}