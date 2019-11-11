package org.tinyconfiguration.base;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.tinyconfiguration.data.Property;
import org.tinyconfiguration.utils.ExportType;

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
                build());

        builder.put(new Property.Builder().
                setKey("value-float").
                setValue(12.4f).
                setGroup("single-value").
                build());

        builder.put(new Property.Builder().
                setKey("value-boolean").
                setValue(true).
                setGroup("single-value").
                build());

        builder.put(new Property.Builder().
                setKey("value-string").
                setValue("This is a really cool string").
                setGroup("single-value").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new boolean[]{true, false, false}).
                setGroup("boolean-values").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new byte[]{1, 2, 3}).
                setGroup("byte-values").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new short[]{10, 23, 45}).
                setGroup("short-values").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new int[]{107, 273, 465}).
                setGroup("int-values").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new long[]{1054345347, 43554543, 46543432}).
                setGroup("long-values").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new float[]{1.0f, 20.5f, 3f}).
                setGroup("float-values").
                build());

        builder.put(new Property.Builder().
                setKey("values").
                setValue(new double[]{12d, 7.54d, 8e1d}).
                setGroup("double-values").
                build());

        final Configuration cfg0 = builder.build();

        // Write as XML
        assertDoesNotThrow(() -> ConfigurationIO.write(ExportType.XML, cfg0));
        assertDoesNotThrow(() -> ConfigurationIO.writeAsync(ExportType.XML, cfg0).get());

        // Changing .ext
        builder.setFilename("tiny-configuration.json");
        final Configuration cfg1 = builder.build();

        // Write as JSON
        assertDoesNotThrow(() -> ConfigurationIO.write(ExportType.JSON, cfg1));
        assertDoesNotThrow(() -> ConfigurationIO.writeAsync(ExportType.JSON, cfg1).get());

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
    void delete() {
        Configuration.Builder builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.xml");

        final Configuration cfg0 = builder.build();

        // Exists as XML
        assertDoesNotThrow(() -> ConfigurationIO.delete(cfg0));

        // Changing .ext
        builder.setFilename("tiny-configuration.json");
        final Configuration cfg1 = builder.build();

        // Exists as JSON
        assertDoesNotThrow(() -> ConfigurationIO.delete(cfg1));

    }
}