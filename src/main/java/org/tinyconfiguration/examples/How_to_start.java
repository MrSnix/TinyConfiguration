package org.tinyconfiguration.examples;

import org.tinyconfiguration.base.Configuration;
import org.tinyconfiguration.base.ConfigurationIO;
import org.tinyconfiguration.events.PropertyListener;

import java.io.IOException;

public class How_to_start {

    public static void main(String[] args) {

        // Creating a new instance
        Configuration cfg = new Configuration.Builder().
                setFilename("setting.cfg"). // FILE-NAME + .EXT
                setPathname("./"). // PATH-NAME ONLY, NO FILE-NAME
                build();

        /* The next step will be really important
         * because it defines which properties ConfigurationIO.read() can consider valid!
         * Please check configuration.setPolicy() for further information */

        /* These properties are invented just as example,
         * you can put whatever your software needs */

        /* The following method is overloaded as follow:
         * 1) cfg.put(String key, String value); [ RECOMMENDED ]
         * 2) cfg.put(String key, String value, String description); [ RECOMMENDED ]
         * 3) cfg.put(Property p); [ NOT RECOMMENDED ]*/

        cfg.put("CFG_VERSION", "0.1", "This value is used internally, please do not touch it!");
        cfg.put("LANGUAGE", "EN");
        cfg.put("FULLSCREEN", "False");
        cfg.put("MUSIC", "0.8");
        cfg.put("LI=N;K", "\n\n\r\t\b"); // It will be escaped on write() but unescaped by the read() method
        cfg.put("RENDERING_DISTANCE", "10", "This value should be set carefully");
        cfg.put("V-SYNC", "False");

        // Checking existence "./setting.cfg" file
        if (ConfigurationIO.exist(cfg)) {

            // Let's import the properties
            try {
                // Reading and updating values inside configuration instance
                ConfigurationIO.read(cfg);
            } catch (IOException e) {
                // You can catch the exception as you wish
                e.printStackTrace();
            }

        } else {

            /* Configuration does not exists,
             * we will use the default properties set initially */

            // But we want to write on the disk, so the user will be able to change them in the future
            try {
                ConfigurationIO.write(cfg);
            } catch (IOException e) {
                // You can catch the exception as you wish
                e.printStackTrace();
            }

        }

        /* At this point, if you need to retrieve any property information you can use cfg.get(String key)
         * ex. cfg.get("LANGUAGE").asString(); | cfg.get("MUSIC").asFloat();
         * IMPORTANT:
         * cfg.get() will return a "Property" object and you can use it to return any kind of value */

        cfg.get("CFG_VERSION").asString(); // cfg.get("").value().asLong();
        cfg.get("FULLSCREEN").asBoolean();
        cfg.get("MUSIC").asFloat();

        /* Now, you may want to modify the properties inside your program when something happen
         * (ex. User press "Apply Fullscreen" then "Save" button)
         * How do you do it ? Really simple, use cfg.put(key, value) again, it will replace the old data */

        cfg.put("FULLSCREEN", "True");

        /* Then, after the user has confirmed, you may want to listen for the configuration change,
         *  so your software can run a custom method to make the window full-screen...
         *  Just use this method cfg.addListener(PropertyListener, key, () -> {}) and ... that's it */

        cfg.addListener(PropertyListener.Type.ON_PROPERTY_UPDATE, "FULLSCREEN", property -> {

            /* You can do whatever you want inside this function
             *  It will run when cfg.put will update FULLSCREEN property*/

            /* So, for example you may do something like:
             * window.fullScreen(property.value().asBoolean()); */
        });

        // This will now trigger the listener
        cfg.put("FULLSCREEN", "False");

        /* In the end, it's time to save our new configuration instance, so next time
         * the user will be able to load his preferences... */
        try {
            ConfigurationIO.write(cfg);
        } catch (IOException e) {
            // You can catch the exception as you wish
            e.printStackTrace();
        }


        // For further information => "http://...."

    }

}
