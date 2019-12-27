package org.tinyconfiguration.io;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.Configuration;
import org.tinyconfiguration.common.Property;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.tinyconfiguration.abc.io.utils.FormatType.JSON;

class ConfigurationIOTest {

    @Test
    void writeJSON() {

        Configuration.Builder b = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.json");

        b.put(new Property.Builder().
                setKey("language").
                setValue("EN").
                build());

        b.put(new Property.Builder().
                setKey("user").
                setValue("root").
                build());

        b.put(new Property.Builder().
                setKey("password").
                setValue("toor").
                build());

        b.put(new Property.Builder().
                setKey("last-access").
                setValue("never").
                build());

        b.put(new Property.Builder().
                setKey("hex-digits").
                setValue(new String[]{"332a", "4f2e", "f0be", "cac2"}).
                build());

        Configuration c = b.build();

        assertDoesNotThrow(() -> {

            ConfigurationIO io = new ConfigurationIO();

            io.write(JSON, c);

        });

    }

}