package org.tinyconfiguration.base;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.property.Property;
import org.tinyconfiguration.utils.ExportType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ConfigurationIOTest {

    @Test
    void write() {

        Configuration.Builder builder = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.cfg");

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

        Configuration cfg = builder.build();

        assertDoesNotThrow(() -> {
            ConfigurationIO.write(ExportType.JSON, cfg);
        });
    }
}