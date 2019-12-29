package org.tinyconfiguration.io;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.tinyconfiguration.Configuration;
import org.tinyconfiguration.common.Property;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ConfigurationIOTest {


    @Test
    @Disabled
    void writeJSON() {

        Configuration.Builder b = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.json");

        b.put(new Property.Builder().
                setKey("language").
                setValue("EN").
                setDescription("Specifies the language environment for the session").
                build());

        b.put(new Property.Builder().
                setKey("user").
                setValue("root").
                setDescription("Sets the username required to open the application's database").
                build());

        b.put(new Property.Builder().
                setKey("password").
                setValue("toor").
                setDescription("Sets the password required to open the application's database").
                build());

        b.put(new Property.Builder().
                setKey("last-access").
                setValue("never").
                setDescription("Specifies when the last session was started").
                build());

        b.put(new Property.Builder().
                setKey("hex-digits").
                setValue(new String[]{"332a", "4f2e", "f0be", "cac2"}).
                setDescription("Specifies the seed numbers").
                build());

        Configuration c = b.build();

        assertDoesNotThrow(() -> {

            ConfigurationIO io = new ConfigurationIO();

            io.getHandlerJSON().write(c);

        });

    }

    @Test
    void readJSON() {

        Configuration.Builder b = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.json");

        b.put(new Property.Builder().
                setKey("language").
                setValue("IT").
                setDescription("Specifies the language environment for the session").
                build());

        b.put(new Property.Builder().
                setKey("user").
                setValue("kasper").
                setDescription("Sets the username required to open the application's database").
                build());

        b.put(new Property.Builder().
                setKey("password").
                setValue("skyfall").
                setDescription("Sets the password required to open the application's database").
                build());

        b.put(new Property.Builder().
                setKey("last-access").
                setValue("never").
                setDescription("Specifies when the last session was started").
                build());

        b.put(new Property.Builder().
                setKey("hex-digits").
                setValue(new String[]{"0000", "ffff", "222x", "r443"}).
                setDescription("Specifies the seed numbers").
                build());

        Configuration c = b.build();

        assertDoesNotThrow(() -> {

            ConfigurationIO io = new ConfigurationIO();

            io.getHandlerJSON().read(c);

        });

    }

}