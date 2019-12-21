package org.tinyconfiguration.base;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.tinyconfiguration.data.Property;
import org.tinyconfiguration.exceptions.InvalidConfigurationNameException;
import org.tinyconfiguration.exceptions.InvalidConfigurationVersionException;
import org.tinyconfiguration.exceptions.MissingConfigurationPropertyException;
import org.tinyconfiguration.exceptions.UnknownConfigurationPropertyException;
import org.tinyconfiguration.utils.FormatType;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.tinyconfiguration.utils.FormatType.JSON;

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
                setKey("ungrouped-0").
                setValue(10).
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("ungrouped-1").
                setValue("Hello").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("ungrouped-2").
                setValue(10d).
                setDescription("This is a truly useless description").
                build());

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
        assertDoesNotThrow(() -> ConfigurationIO.write(JSON, cfg1));
        assertDoesNotThrow(() -> ConfigurationIO.writeAsync(JSON, cfg1).get());

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
        Configuration.Builder builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.json");

        builder.put(new Property.Builder().
                setKey("ungrouped-0").
                setValue(10).
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("ungrouped-1").
                setValue("Hello").
                setDescription("This is a truly useless description").
                build());

        builder.put(new Property.Builder().
                setKey("ungrouped-2").
                setValue(10d).
                setDescription("This is a truly useless description").
                build());

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

        Assertions.assertDoesNotThrow(() -> {
            try {
                ConfigurationIO.read(JSON, cfg0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationVersionException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationNameException e) {
                e.printStackTrace();
            } catch (MissingConfigurationPropertyException e) {
                e.printStackTrace();
            } catch (UnknownConfigurationPropertyException e) {
                e.printStackTrace();
            }
        });


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
    @Order(5)
    @Disabled
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