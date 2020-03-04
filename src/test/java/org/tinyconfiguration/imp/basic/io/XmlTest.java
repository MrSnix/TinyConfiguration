package org.tinyconfiguration.imp.basic.io;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.imp.basic.Configuration;
import org.tinyconfiguration.imp.basic.ConfigurationIO;
import org.tinyconfiguration.imp.basic.Property;

import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.tinyconfiguration.abc.utils.ExportType.XML;

class XmlTest {

    private final Configuration instance;

    public XmlTest() {

        Configuration.Builder b = new Configuration.Builder().
                setName("ConfigurationTest").
                setVersion("1.0.0").
                setPathname("./").
                setFilename("tiny-configuration.xml");

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
                setKey("sex").
                setValue('M').
                setDescription("Sets the user gender").
                build());

        b.put(new Property.Builder().
                setKey("last-access").
                setValue("never").
                setDescription("Specifies when the last session was started").
                build());

        b.put(new Property.Builder().
                setKey("auto-update").
                setValue(true).
                setDescription("Specifies if the application should regularly check for new software releases")
                .build());

        b.put(new Property.Builder().
                setKey("auto-update-reminder").
                setValue(5).
                setDescription("Specifies how many times the application should remind to install the new release")
                .build());

        b.put(new Property.Builder().
                setKey("hex-digits").
                setValue(new String[]{"332a", "4f2e", "f0be", "cac2"}).
                setDescription("Specifies the seed numbers").
                build());

        b.put(new Property.Builder().
                setKey("special-digits").
                setValue(new int[0]).
                setDescription("Specifies the seed special numbers").
                build());

        this.instance = b.build();

    }

    @Test
    void readXML() {
        // Does it exists?
        if (!ConfigurationIO.as(XML).exist(instance)) {

            // If so, let's executing the writing task, then execute it with get()
            assertDoesNotThrow(() -> ConfigurationIO.as(XML).write(instance));

            // Now, it should exists
            assertTrue(ConfigurationIO.as(XML).exist(instance));

        }

        // Modifying the instance
        instance.get("user").setValue("ruut");
        instance.get("password").setValue("toor007");
        instance.get("sex").setValue('F');
        instance.get("special-digits").setValue(new int[]{10, 15});

        assertEquals("ruut", instance.get("user").getValue().asString());
        assertEquals("toor007", instance.get("password").getValue().asString());
        assertEquals('F', instance.get("sex").getValue().asCharacter());
        assertArrayEquals(new int[]{10, 15}, instance.get("special-digits").getValue().asIntArray());

        // Now, reading the configuration instance
        assertDoesNotThrow(() -> ConfigurationIO.as(XML).read(this.instance));

        assertEquals("root", instance.get("user").getValue().asString());
        assertEquals("toor", instance.get("password").getValue().asString());
        assertEquals('M', instance.get("sex").getValue().asCharacter());
        assertArrayEquals(new int[0], instance.get("special-digits").getValue().asIntArray());

    }

    @Test
    void writeXML() {

        assertDoesNotThrow(() -> ConfigurationIO.as(XML).write(this.instance));

        assertTrue(ConfigurationIO.as(XML).exist(instance));
    }

    @Test
    void writeAsyncXML() {

        assertDoesNotThrow(() -> {

            int x = 0;

            Future<Void> task = ConfigurationIO.as(XML).writeAsync(this.instance);

            while (!task.isDone()) {
                ++x;
            }

            assertTrue(x != 0);

        });

        assertTrue(ConfigurationIO.as(XML).exist(instance));

    }

    @Test
    void deleteXML() {

        // Does it exists?
        if (!ConfigurationIO.as(XML).exist(instance)) {

            // If so, let's executing the writing task, then execute it with get()
            assertDoesNotThrow(() -> ConfigurationIO.as(XML).write(instance));

            // Now, it should exists
            assertTrue(ConfigurationIO.as(XML).exist(instance));

        }

        // Executing deleting task, then execute it
        assertDoesNotThrow(() -> ConfigurationIO.as(XML).delete(instance));
        // Asserting does not exists any more
        assertFalse(ConfigurationIO.as(XML).exist(instance));

    }

    @Test
    void deleteAsyncXML() {

        // Does it exists?
        if (!ConfigurationIO.as(XML).exist(instance)) {

            // If so, let's obtain an a-sync writing task, then execute it with get()
            assertDoesNotThrow(() -> ConfigurationIO.as(XML).writeAsync(instance).get());

            // Now, it should exists
            assertTrue(ConfigurationIO.as(XML).exist(instance));

        }
        // Obtaining deleting task, then execute it
        assertDoesNotThrow(() -> ConfigurationIO.as(XML).deleteAsync(instance).get());
        // Asserting does not exists any more
        assertFalse(ConfigurationIO.as(XML).exist(instance));

    }
}