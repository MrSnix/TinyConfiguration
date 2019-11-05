package org.tinyconfiguration.base;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.property.Property;
import org.tinyconfiguration.utils.ExportType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ConfigurationIOTest {

    @Test
    void writeToJson() {

        Configuration.Builder builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.json");

        builder.put(new Property.Builder().
                setKey("language").
                setValue("en").
                setGroup("general").
                build());

        builder.put(new Property.Builder().
                setKey("username").
                setValue("root").
                setGroup("user").
                build());

        builder.put(new Property.Builder().
                setKey("password").
                setValue("toor").
                setGroup("user").
                build());

        final Configuration cfg0 = builder.build();

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(ExportType.JSON, cfg0);
        });

    }

    @Test
    void writeToXML() {

        Configuration.Builder builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.xml");

        builder.put(new Property.Builder().
                setKey("language").
                setValue("en").
                setGroup("general").
                build());

        builder.put(new Property.Builder().
                setKey("username").
                setValue("root").
                setGroup("user").
                build());

        builder.put(new Property.Builder().
                setKey("password").
                setValue("toor").
                setGroup("user").
                build());

        final Configuration cfg0 = builder.build();

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(ExportType.XML, cfg0);
        });
    }

    @Test
    void writeToCsv() {

        Configuration.Builder builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.csv");

        builder.put(new Property.Builder().
                setKey("language").
                setValue("en").
                setGroup("general").
                build());

        builder.put(new Property.Builder().
                setKey("username").
                setValue("root").
                setGroup("user").
                build());

        builder.put(new Property.Builder().
                setKey("password").
                setValue("toor").
                setGroup("user").
                build());

        final Configuration cfg0 = builder.build();

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(ExportType.CSV, cfg0);
        });
    }

}